package com.example.schmecken.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.DomainPresentationProvider
import com.example.schmecken.R
import com.example.schmecken.di.ItemAdapter
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModel
import androidx.fragment.app.viewModels
import com.example.data.DishDao
import com.example.data.filters.FiltersDao
import com.example.domain.SharedViewModel


class FirstFragment() : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        /*super.onViewCreated(view, savedInstanceState)

        val viewModel: SharedViewModel by activityViewModels()


        val itemsList: RecyclerView = view.findViewById(R.id.recyclerview)

        viewLifecycleOwner.lifecycleScope.launch {
            val dpp = DomainPresentationProvider(dishDao, filterDao)
            val items = dpp.getPreviewList()

            itemsList.layoutManager = LinearLayoutManager(requireContext())
            itemsList.adapter = ItemAdapter(items, requireContext())
        }
         */

    }
}
