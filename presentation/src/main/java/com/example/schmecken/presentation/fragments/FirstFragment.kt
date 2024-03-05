package com.example.schmecken.presentation.fragments

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
import com.example.domain.dataclasses.domeindata
import com.example.domain.logic.DomainPresentationProvider
import com.example.schmecken.R
import com.example.schmecken.adapters.ItemAdapter
import com.example.schmecken.di.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FirstFragment : Fragment() {
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var itemAdapter: ItemAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val itemsList: RecyclerView = view.findViewById(R.id.recyclerview)
        itemsList.layoutManager = LinearLayoutManager(requireContext())
        sharedViewModel.loadBitmapDataListFromDB()
        val bitmapDataList: List<domeindata>? = sharedViewModel.getBitmapDataList().value
        sharedViewModel.bitmapDataList.value = bitmapDataList
        val switch: Switch = view.findViewById(R.id.switchfav)
        switch.setOnCheckedChangeListener { _, isChecked ->
                sharedViewModel.isSwitchChecked.value = isChecked
                if (isChecked) {
                    sharedViewModel.loadLikedItems()
                } else {
                    sharedViewModel.loadItems()

                }
        }



        itemAdapter = ItemAdapter(sharedViewModel.itemList.value?.toMutableList() ?: mutableListOf(), requireContext(), sharedViewModel)
        itemsList.layoutManager = LinearLayoutManager(requireContext())
        itemsList.adapter = itemAdapter

        sharedViewModel.loadItems()
        itemAdapter.updateItems(sharedViewModel.itemList.value ?: emptyList())
        itemAdapter.setOnItemClickListener { item ->
            sharedViewModel.selectedItemId.value = item.id
            viewLifecycleOwner.lifecycleScope.launch {
                val secondInfo = sharedViewModel.getsecondinfo(item.id)
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
                if (!sharedViewModel.isLoading && lastVisibleItem == totalItemCount - 1 && !(sharedViewModel.isSwitchChecked.value ?: false)) {
                    sharedViewModel.loadItems()
                }
            }
        })
        sharedViewModel.loadItems()
        switch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                itemAdapter.updateItems(sharedViewModel.itemList.value?.filter { it.isLiked } ?: emptyList())
            } else {
                itemAdapter.updateItems(sharedViewModel.itemList.value ?: emptyList())
            }
        }
        sharedViewModel.filteredItems.observe(viewLifecycleOwner, Observer { itemList ->
            itemAdapter.updateItems(itemList)
        })

    }
}








