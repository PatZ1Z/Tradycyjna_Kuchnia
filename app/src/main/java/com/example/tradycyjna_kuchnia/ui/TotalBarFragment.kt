package com.example.tradycyjna_kuchnia.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.tradycyjna_kuchnia.R
import com.example.tradycyjna_kuchnia.databinding.FragmentTotalBarBinding
import com.example.tradycyjna_kuchnia.model.Order
import com.example.tradycyjna_kuchnia.model.MealItem
import com.example.tradycyjna_kuchnia.ui.OrderViewModel

class TotalBarFragment : Fragment() {

    private var _binding: FragmentTotalBarBinding? = null
    private val binding get() = _binding!!

    // Uzyskanie dostępu do OrderViewModel
    private val orderViewModel: OrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTotalBarBinding.inflate(inflater, container, false)

        // Nasłuchiwanie na zmiany w liście zamówień
        orderViewModel.orders.observe(viewLifecycleOwner, Observer { orders ->
            val totalAmount = calculateTotalAmount(orders)
            // Wyświetlanie sumy do zapłaty w TextView
            binding.totalText.text = "Total: $totalAmount"
        })

        return binding.root
    }

    // Funkcja obliczająca całkowitą kwotę do zapłaty
    private fun calculateTotalAmount(orders: List<Order>): Double {
        var total = 0.0
        // Iteracja przez wszystkie zamówienia i potrawy
        for (order in orders) {
            for (meal in order.meals) {
                total += meal.price * meal.quantity // Cena * ilość
            }
        }
        return total
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
