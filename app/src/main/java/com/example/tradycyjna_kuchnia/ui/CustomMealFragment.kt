package com.example.tradycyjna_kuchnia.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.tradycyjna_kuchnia.databinding.FragmentCustomMealBinding

class CustomMealFragment : Fragment() {

    private var _binding: FragmentCustomMealBinding? = null
    private val binding get() = _binding!!

    // 🔹 odbiór argumentów Safe Args
    private val args: CustomMealFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCustomMealBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 🔹 pobranie orderId
        val orderId = args.orderId

        // przykład użycia
//        binding.textExample.text = "Odebrane orderId: $orderId"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
