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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val itemsList: RecyclerView = view.findViewById(R.id.recyclerview)

        itemAdapter = ItemAdapter(emptyList(), requireContext(), sharedViewModel)
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



        viewLifecycleOwner.lifecycleScope.launch {
            val items = dbs.getPreviewList()
            val bitmapDataList = withContext(Dispatchers.IO) { dbs.getbitmaplist() }
            sharedViewModel.bitmapData.value = bitmapDataList
            itemAdapter.items = items
            itemAdapter.notifyDataSetChanged()
        }
    }
}





