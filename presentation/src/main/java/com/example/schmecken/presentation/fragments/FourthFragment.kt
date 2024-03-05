package com.example.schmecken.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.RadioGroup
import android.widget.Spinner
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.logic.DomainPresentationProvider
import com.example.domain.dataclasses.FourthInfo
import com.example.schmecken.R
import com.example.schmecken.di.SharedViewModel
import com.example.schmecken.adapters.forthAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class FourthFragment : Fragment() {
    @Inject
    lateinit var dbs: DomainPresentationProvider
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val fourthInfo = MutableLiveData<List<FourthInfo>>()

    val ageList = (18..100).toList()
    val weightList = (40..150).toList()
    val heightList = (140..200).toList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fourth, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view4444)
        val adapter = forthAdapter(emptyList(), requireContext(), sharedViewModel)
        recyclerView.adapter = adapter

        fourthInfo.observe(viewLifecycleOwner, Observer { newItems ->
            adapter.items = newItems
            adapter.notifyDataSetChanged()
        })
        adapter.setOnItemClickListener(object : forthAdapter.OnItemClickListener {
            override fun onItemClick(item: FourthInfo) {
                sharedViewModel.selectedItemId.value = item.Dishid
                viewLifecycleOwner.lifecycleScope.launch {
                    val secondInfo = sharedViewModel.getsecondinfo(item.Dishid)
                    sharedViewModel.secondInfo.value = secondInfo
                }
                sharedViewModel.switchFragmentCommand.value = 1
            }
        })

        val button = view.findViewById<Button>(R.id.button_calculate)
        button.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
                val age = sharedViewModel.age.value ?: 0
                val gender = sharedViewModel.gender.value ?: "male"
                val weight = sharedViewModel.weight.value ?: 0.0
                val height = sharedViewModel.height.value ?: 0.0
                val newItems = sharedViewModel.getdietinfo(age, gender, weight, height)
                withContext(Dispatchers.Main) {
                    if (newItems.isNotEmpty()) {
                        fourthInfo.value = newItems
                    }
                }
            }
        }




        // Set up Spinners
        val radioGroupGender: RadioGroup = view.findViewById(R.id.radio_group_gender)
        val spinnerAge = view.findViewById<Spinner>(R.id.spinner_age)
        val spinnerWeight = view.findViewById<Spinner>(R.id.spinner_weight)
        val spinnerHeight = view.findViewById<Spinner>(R.id.spinner_height)

        val ageAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, ageList)
        ageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerAge.adapter = ageAdapter
        spinnerAge.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                sharedViewModel.setAge(parent.getItemAtPosition(pos) as Int)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Handle when nothing is selected in Spinner
            }
        }

        val weightAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, weightList)
        weightAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerWeight.adapter = weightAdapter
        spinnerWeight.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                sharedViewModel.setWeight((parent.getItemAtPosition(pos) as Int).toDouble())
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Handle when nothing is selected in Spinner
            }
        }

        val heightAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, heightList)
        heightAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerHeight.adapter = heightAdapter
        spinnerHeight.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                sharedViewModel.setHeight((parent.getItemAtPosition(pos) as Int).toDouble())
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Handle when nothing is selected in Spinner
            }
        }
        radioGroupGender.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.radio_male -> {
                    sharedViewModel.setGender("male")
                }
                R.id.radio_female -> {
                    sharedViewModel.setGender("female")
                }
            }
        }
    }
}
