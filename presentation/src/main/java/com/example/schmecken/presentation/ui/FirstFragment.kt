package com.example.schmecken.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.DomainPresentationProvider
import com.example.domain.SimpleDish
import com.example.schmecken.R
import com.example.schmecken.di.ItemAdapter
import com.example.schmecken.di.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class FirstFragment : Fragment() {
    @Inject lateinit var dbs: DomainPresentationProvider
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var itemAdapter: ItemAdapter
    private var currentPage = 0
    private val itemsPerPage = 15
    private var isLoading = false
    private var isUpdating = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val itemsList: RecyclerView = view.findViewById(R.id.recyclerview)

        val simpleDishList = emptyList<SimpleDish>().toMutableList()

        itemAdapter = ItemAdapter(simpleDishList, requireContext(), sharedViewModel)
        itemsList.layoutManager = LinearLayoutManager(requireContext())
        itemsList.adapter = itemAdapter

        itemAdapter.setOnItemClickListener { item ->
            sharedViewModel.selectedItemId.value = item.id
            viewLifecycleOwner.lifecycleScope.launch {
                val secondInfo = dbs.ddpbasicallinfo(item.id)
                sharedViewModel.secondInfo.value = secondInfo
            }
            sharedViewModel.switchFragmentCommand.value = 1

            Toast.makeText(requireContext(), "Dish selected! :)", Toast.LENGTH_SHORT).show()
        }

        itemsList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                if (!isLoading && lastVisibleItem == totalItemCount - 1) {
                    loadItems()
                }
            }
        })
        sharedViewModel.bitmapDataList.observe(viewLifecycleOwner, Observer { bitmapDataList ->
           updatebit()
            //val likedIds = bitmapDataList.filter { it.isLiked }.map { it.id }
            //val likedItems = items.filter { it.id in likedIds }

            //itemAdapter.updateItems(likedItems)
        })
        loadItems()
        viewLifecycleOwner.lifecycleScope.launch {
            val bitmapDataListFromDB = withContext(Dispatchers.IO) {
                dbs.getBitmapList()
            }
            for (bitmapdata in bitmapDataListFromDB) {
                sharedViewModel.addBitmapData(bitmapdata)
            }
        }


       /* val switchFav: Switch = view.findViewById(R.id.switchfav)
        switchFav.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // Если переключатель включен, показываем только избранные элементы
                val likedItems = sharedViewModel.getBitmapDataList().value?.filter { it.isLiked } ?: emptyList()
                itemAdapter.updateItems(likedItems)
            } else {
                // Если переключатель выключен, показываем все элементы
                itemAdapter.updateItems(sharedViewModel.getBitmapDataList().value ?: emptyList())
            }
        }*/
    }
    private fun updatebit(){
    if (!isUpdating) {
        isUpdating = true
        val bitmapDataList = sharedViewModel.getBitmapDataList().value ?: emptyList()
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            dbs.updateBitBase(bitmapDataList)
            isUpdating = false
        }
    }
    }
    private fun loadItems() {
        isLoading = true
        viewLifecycleOwner.lifecycleScope.launch {
            val items = withContext(Dispatchers.IO) {
                dbs.getPreviewList(currentPage * itemsPerPage, itemsPerPage)
            }
            itemAdapter.addItems(items)
            itemAdapter.notifyDataSetChanged()
            currentPage++
            isLoading = false
        }
    }
}








