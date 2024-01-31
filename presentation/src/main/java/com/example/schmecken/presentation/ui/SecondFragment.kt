package com.example.schmecken.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.schmecken.R
import com.example.domain.Requestmaker
import com.example.data.RecipeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class SecondFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var recipeRepository: RecipeRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_second, container, false)
        val myButton: Button = view.findViewById(R.id.button)
        val mytext: TextView = view.findViewById(R.id.textView2)

        myButton.setOnClickListener {
            }

        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            SecondFragment().apply {

            }
    }
}