package com.example.schmecken.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.DomainPresentationProvider
import com.example.schmecken.R
import com.example.schmecken.di.ItemAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FirstFragment() : Fragment() {
    @Inject
    lateinit var dbs: DomainPresentationProvider

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

        // Инициализируйте адаптер с пустым списком
        itemAdapter = ItemAdapter(emptyList(), requireContext())
        itemsList.layoutManager = LinearLayoutManager(requireContext())
        itemsList.adapter = itemAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            val items = dbs.getPreviewList()

            // Обновите адаптер с загруженными данными
            itemAdapter.items = items
            itemAdapter.notifyDataSetChanged()
        }
    }
}


