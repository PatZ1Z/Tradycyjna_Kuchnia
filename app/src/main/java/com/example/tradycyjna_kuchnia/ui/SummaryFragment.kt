package com.example.tradycyjna_kuchnia.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.tradycyjna_kuchnia.databinding.FragmentSummaryBinding
import com.example.tradycyjna_kuchnia.model.Order

class SummaryFragment : Fragment() {

    private var _binding: FragmentSummaryBinding? = null
    private val binding get() = _binding!!

    // Pobieramy ViewModel współdzielony z innymi fragmentami
    private val orderViewModel: OrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSummaryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Obserwujemy listę zamówień
        orderViewModel.orders.observe(viewLifecycleOwner) { orders ->
            displayOrders(orders)
        }
    }

    private fun displayOrders(orders: List<Order>) {
        val container = binding.summaryContainer
        container.removeAllViews() // czyścimy poprzednie widoki

        if (orders.isEmpty()) {
            val emptyText = TextView(requireContext()).apply {
                text = "Brak zamówień"
                textSize = 18f
                setPadding(16, 16, 16, 16)
            }
            container.addView(emptyText)
            return
        }

        orders.forEach { order ->
            // Layout dla pojedynczego zamówienia
            val orderLayout = LinearLayout(requireContext()).apply {
                orientation = LinearLayout.VERTICAL
                setPadding(16, 16, 16, 16)
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(0, 0, 0, 24)
                }
                setBackgroundResource(android.R.drawable.dialog_holo_light_frame)
            }

            // Nazwa zamówienia
            val orderTitle = TextView(requireContext()).apply {
                text = "${order.name} (${order.description})"
                textSize = 20f
                setPadding(0, 0, 0, 8)
            }
            orderLayout.addView(orderTitle)

            if (order.meals.isEmpty()) {
                val noMealsText = TextView(requireContext()).apply {
                    text = "Brak posiłków"
                    textSize = 16f
                    setPadding(8, 4, 8, 4)
                }
                orderLayout.addView(noMealsText)
            } else {
                order.meals.forEach { meal ->
                    // Linia z nazwą, ilością i ceną
                    val mealText = TextView(requireContext()).apply {
                        text = "${meal.name} - ${meal.quantity} x ${meal.price} zł"
                        textSize = 16f
                        setPadding(8, 4, 8, 0)
                    }
                    orderLayout.addView(mealText)

                    // Opcje, jeśli istnieją, w osobnej linii
                    if (meal.option.isNotEmpty()) {
                        val optionText = TextView(requireContext()).apply {
                            text = "Opcja: ${meal.option}"
                            textSize = 14f
                            setPadding(16, 0, 8, 4)
                        }
                        orderLayout.addView(optionText)
                    }
                }
            }

            container.addView(orderLayout)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
