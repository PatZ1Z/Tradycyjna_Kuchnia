package com.example.tradycyjna_kuchnia.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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

    private val orderViewModel: OrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTotalBarBinding.inflate(inflater, container, false)

        orderViewModel.orders.observe(viewLifecycleOwner) { orders ->
            showOrdersAndTotal(orders)
        }

        return binding.root
    }

    private fun showOrdersAndTotal(orders: List<Order>) {
        binding.ordersContainer.removeAllViews()

        var totalAmount = 0.0

        orders.forEachIndexed { index, order ->
            val orderTotal = calculateOrderTotal(order)
            totalAmount += orderTotal

            val orderTextView = TextView(requireContext()).apply {
                text = "Zamówienie ${index + 1}: %.2f zł".format(orderTotal)
                textSize = 16f
                setTextColor(resources.getColor(android.R.color.white))
                setPadding(0, 4, 0, 4)
            }

            binding.ordersContainer.addView(orderTextView)
        }

        binding.totalText.text = "Do zapłaty: %.2f zł".format(totalAmount)
    }

    private fun calculateOrderTotal(order: Order): Double {
        var sum = 0.0
        for (meal in order.meals) {
            sum += meal.price * meal.quantity
        }
        return sum
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

