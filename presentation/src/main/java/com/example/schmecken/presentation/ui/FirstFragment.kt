package com.example.schmecken.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val itemsList: RecyclerView = view.findViewById(R.id.recyclerview)

        itemAdapter = ItemAdapter(emptyList<SimpleDish>().toMutableList(), requireContext(), sharedViewModel)
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
                if (!isLoading && totalItemCount <= lastVisibleItem + itemsPerPage) {
                    loadItems()
                }
            }
        })

        loadItems()
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






