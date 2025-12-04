package com.example.tradycyjna_kuchnia.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.tradycyjna_kuchnia.R
import com.example.tradycyjna_kuchnia.databinding.FragmentStartBinding

class StartFragment : Fragment() {

    private var _binding: FragmentStartBinding? = null
    private val binding get() = _binding!!

    // Używamy activityViewModels, aby ViewModel był współdzielony między fragmentami w tym samym Activity
    private val orderViewModel: OrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Obserwowanie stanu ikony
        orderViewModel.iconState.observe(viewLifecycleOwner) { state ->
            val icon: ImageView = binding.addCustomer // Zakładając, że masz ImageView w layout
            when (state) {
                "added" -> icon.setImageResource(R.drawable.ic_card)  // Zmień na odpowiednią ikonę
                "default" -> icon.setImageResource(R.drawable.ic_add)  // Zmień na domyślną ikonę
            }
        }

        // Przykładowe dodanie zamówienia
        binding.addCustomer.setOnClickListener {
            // Tworzymy nowe zamówienie
            val newOrder = Order("Zamówienie ${System.currentTimeMillis()}", "Opis zamówienia")
            orderViewModel.addOrder(newOrder)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /*companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            StartFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }*/
}
