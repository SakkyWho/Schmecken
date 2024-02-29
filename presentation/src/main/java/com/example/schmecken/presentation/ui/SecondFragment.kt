package com.example.schmecken.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.DomainPresentationProvider
import com.example.schmecken.R
import com.example.schmecken.di.SharedViewModel
import com.example.schmecken.di.StringListAdapter
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class SecondFragment : Fragment() {
    @Inject lateinit var dbs: DomainPresentationProvider
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel.selectedItemId.observe(viewLifecycleOwner, Observer { itemId ->
            refreshData(itemId)
        })

    }

    private fun refreshData(itemId: Int) {
        viewLifecycleOwner.lifecycleScope.launch {
            val secondInfo = dbs.ddpbasicallinfo(itemId)
            val imageView = view?.findViewById<ImageView>(R.id.image)
            val imageCircle = view?.findViewById<ImageView>(R.id.imagecircle)

            Picasso.get().load(secondInfo.image)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(imageView)
            Picasso.get().load(secondInfo.image)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(imageCircle)

            val labelTextView = view?.findViewById<TextView>(R.id.label)
            val totalTime = view?.findViewById<TextView>(R.id.totaltime)
            val dietLabelsRecyclerView = view?.findViewById<RecyclerView>(R.id.dietLabels)
            val healthLabelsRecyclerView = view?.findViewById<RecyclerView>(R.id.healthLabels)
            val cautionsRecyclerView = view?.findViewById<RecyclerView>(R.id.cautions)
            val cuisineTypeRecyclerView = view?.findViewById<RecyclerView>(R.id.cuisineType)
            val mealTypeRecyclerView = view?.findViewById<RecyclerView>(R.id.mealType)
            val dishTypeRecyclerView = view?.findViewById<RecyclerView>(R.id.dishType)

            labelTextView?.text = secondInfo.label
            totalTime?.text = secondInfo.totalTime.toString()

            dietLabelsRecyclerView?.let { setupRecyclerView(it, secondInfo.dietLabels) }
            healthLabelsRecyclerView?.let { setupRecyclerView(it, secondInfo.healthLabels) }
            cautionsRecyclerView?.let { setupRecyclerView(it, secondInfo.cautions) }
            cuisineTypeRecyclerView?.let { setupRecyclerView(it, secondInfo.cuisinType) }
            mealTypeRecyclerView?.let { setupRecyclerView(it, secondInfo.mealType) }
            dishTypeRecyclerView?.let { setupRecyclerView(it, secondInfo.dishType) }

        }
    }

    private fun setupRecyclerView(recyclerView: RecyclerView, list: List<String>) {
        recyclerView.adapter = StringListAdapter(requireContext(), list)
        recyclerView.adapter?.notifyDataSetChanged()
    }
}

