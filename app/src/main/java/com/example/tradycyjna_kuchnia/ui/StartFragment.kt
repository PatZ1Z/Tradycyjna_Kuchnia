package com.example.tradycyjna_kuchnia.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.tradycyjna_kuchnia.R
import com.example.tradycyjna_kuchnia.databinding.FragmentStartBinding
import com.example.tradycyjna_kuchnia.model.Order


class StartFragment : Fragment() {

    private var _binding: FragmentStartBinding? = null
    private val binding get() = _binding!!

    private val orderViewModel: OrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Obserwacja zamówień w ViewModel
        orderViewModel.orders.observe(viewLifecycleOwner) { orders ->
            // Ładujemy istniejące zamówienia
            loadExistingOrders(orders)

            // Dodaj przycisk "+" na końcu (zawsze po załadowaniu zamówień)
            // Dodajemy go tylko raz tutaj, aby uniknąć podwójnego dodania
            addNewAddButton()
        }
    }

    private fun loadExistingOrders(orders: List<Order>) {
        // Jeśli są zamówienia, dodaj je do kontenera
        binding.customersContainer.removeAllViews()  // Usuwamy poprzednie przyciski
        orders.forEach { order ->
            val orderButton = createOrderButton(order)
            binding.customersContainer.addView(orderButton)
        }

        // Po załadowaniu zamówień dodaj przycisk "+" tylko raz
        // Nie dodajemy przycisku "+" bezpośrednio po dodaniu zamówienia
        addNewAddButton()
    }

    private fun createOrderButton(order: Order): ImageView {
        return ImageView(requireContext()).apply {

            // Ustawiamy obrazek reprezentujący zamówienie
            setImageResource(R.drawable.ic_card)  // Możesz ustawić dowolną ikonę
            tag = order.ID

            layoutParams = ViewGroup.MarginLayoutParams(
                150,
                150
            ).apply {
                setMargins(16, 16, 16, 16)
            }

            // Obsługa kliknięcia przycisku reprezentującego zamówienie
            setOnClickListener {
                val orderId = tag as? Long
                if (orderId != null) {
                    // Przejście do ReadyMealFragment z ID zamówienia
                    val action = StartFragmentDirections
                        .actionStartFragmentToMenuChoiceFragment(orderId)
                    findNavController().navigate(action)
                }
            }
        }
    }

    private fun addNewAddButton() {
        // Dodaj przycisk "+" tylko raz
        // Sprawdzamy, czy przycisk "+" już jest w kontenerze
        if (binding.customersContainer.findViewWithTag<ImageView>("add") == null) {
            val button = createAddCustomerButton()
            binding.customersContainer.addView(button)
        }
    }

    private fun createAddCustomerButton(): ImageView {
        return ImageView(requireContext()).apply {

            // Ustaw ikonę "+" i tag
            setImageResource(R.drawable.ic_add)
            tag = "add"

            layoutParams = ViewGroup.MarginLayoutParams(
                150,
                150
            ).apply {
                setMargins(16, 16, 16, 16)
            }

            setOnClickListener {

                val tagValue = tag

                if (tagValue == "add") {
                    // Tworzymy nowe zamówienie
                    val nextId = (orderViewModel.orders.value?.size ?: 0) + 1

                    val newOrder = Order(
                        ID = nextId.toLong(),
                        name = "Zamówienie $nextId",
                        description = "Opis zamówienia"
                    )

                    orderViewModel.addOrder(newOrder)

                    // Zmieniamy ikonę i tag na ID zamówienia
                    setImageResource(R.drawable.ic_card)
                    tag = newOrder.ID

                    // Po dodaniu zamówienia przycisk "+" zostanie dodany tylko raz
                    // Brak potrzeby dodawania kolejnego przycisku "+" tutaj
                } else {
                    // Sprawdzamy, czy tag jest Long (ID zamówienia)
                    val orderId = tagValue as? Long
                    if (orderId != null) {
                        // Przejście do ReadyMealFragment z argumentem
                        val action = StartFragmentDirections
                            .actionStartFragmentToMenuChoiceFragment(orderId)
                        findNavController().navigate(action)
                    }
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

