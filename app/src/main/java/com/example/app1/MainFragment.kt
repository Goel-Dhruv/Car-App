package com.example.app1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions


class MainFragment : Fragment() {

    lateinit var  rootView: View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        rootView = inflater.inflate(R.layout.fragment_car_model, container, false)
        // Inflate the layout for this fragment
        initViews()
        return rootView
    }

    private fun initViews() {
        val bundle = bundleOf("position" to 2)
        findNavController().navigate(R.id.action_main_fragment_to_car_model_fragment)
    }

}