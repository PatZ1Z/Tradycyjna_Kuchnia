package com.example.tradycyjna_kuchnia.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.tradycyjna_kuchnia.databinding.FragmentMenuChoiceBinding

class MenuChoiceFragment : Fragment() {

    private var _binding: FragmentMenuChoiceBinding? = null
    private val binding get() = _binding!!

    private var orderId: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            // Odczyt ID zamówienia przekazanego z StartFragment
            orderId = MenuChoiceFragmentArgs.fromBundle(it).orderId
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuChoiceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Kliknięcie "Custom meals"
        binding.btnCustomMeals.setOnClickListener {
            val action = MenuChoiceFragmentDirections
                .actionMenuChoiceFragmentToCustomMealFragment(orderId)
            findNavController().navigate(action)
        }

        // Kliknięcie "Ready meals"
        binding.btnReadyMeals.setOnClickListener {
            val action = MenuChoiceFragmentDirections
                .actionMenuChoiceFragmentToReadyMealFragment(orderId)
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
