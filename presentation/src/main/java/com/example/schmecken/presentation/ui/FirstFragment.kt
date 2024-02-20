package com.example.schmecken.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.DBsInitialization
//import com.example.domain.DomainPresentationProvider
import com.example.schmecken.R
import com.example.schmecken.di.ItemAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

//@AndroidEntryPoint
class FirstFragment() : Fragment() {
  /*  @Inject
    lateinit var dPP: DomainPresentationProvider

   */
  private lateinit var dbs: DBsInitialization

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dbs = DBsInitialization(requireContext())
        super.onViewCreated(view, savedInstanceState)
        val itemsList: RecyclerView = view.findViewById(R.id.recyclerview)

        viewLifecycleOwner.lifecycleScope.launch {
            val items = dbs.getPreviewList()

            itemsList.layoutManager = LinearLayoutManager(requireContext())
            itemsList.adapter = ItemAdapter(items, requireContext())
        }
            //val itemsList: RecyclerView = view.findViewById(R.id.recyclerview)
/*
        viewLifecycleOwner.lifecycleScope.launch {
            val items = dPP.getPreviewList()

            itemsList.layoutManager = LinearLayoutManager(requireContext())
            itemsList.adapter = ItemAdapter(items, requireContext())
        }

 */
    }
}

