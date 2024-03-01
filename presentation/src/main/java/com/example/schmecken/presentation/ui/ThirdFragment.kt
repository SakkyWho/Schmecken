package com.example.schmecken.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.DigIngr
import com.example.domain.DomainPresentationProvider
import com.example.domain.RecycIngr
import com.example.schmecken.R
import com.example.schmecken.di.FirstAdapter
import com.example.schmecken.di.SecondAdapter
import com.example.schmecken.di.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ThirdFragment : Fragment() {
    @Inject
    lateinit var dbs: DomainPresentationProvider
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_third, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel.selectedItemId.observe(viewLifecycleOwner, Observer { itemId ->
            refreshData(itemId)
        })
    }

    private fun refreshData(itemId: Int) {
        viewLifecycleOwner.lifecycleScope.launch {
            val thirdInfo = dbs.getThirdInfo(itemId)

            val textlabel = view?.findViewById<TextView>(R.id.textView4)
            sharedViewModel.selectedItemLabel.observe(viewLifecycleOwner, Observer { label ->
                textlabel?.text = label
            })

            val recycIngrRecyclerView = view?.findViewById<RecyclerView>(R.id.recyclerViewIngr)
            val digIngrRecyclerView = view?.findViewById<RecyclerView>(R.id.digests)

            recycIngrRecyclerView?.let { setupRecyclerView1(it, thirdInfo.recycIngrList) }
            digIngrRecyclerView?.let { setupRecyclerView2(it, thirdInfo.digIngrList) }
        }
    }


    private fun setupRecyclerView1(recyclerView: RecyclerView, list: List<RecycIngr>) {
        recyclerView.adapter = FirstAdapter(list)
        recyclerView.adapter?.notifyDataSetChanged()
    }

    private fun setupRecyclerView2(recyclerView: RecyclerView, list: List<DigIngr>) {
        recyclerView.adapter = SecondAdapter(list)
        recyclerView.adapter?.notifyDataSetChanged()
    }
}


