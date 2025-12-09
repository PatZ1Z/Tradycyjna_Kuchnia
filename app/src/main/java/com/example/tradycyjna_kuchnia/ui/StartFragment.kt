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

        // Dodaj pierwszy przycisk "+", aby użytkownik mógł zacząć
        addNewAddButton()

        // Obserwacja — zmiana ikon nie jest już potrzebna (zostawiam, jeśli użyjesz)
        orderViewModel.iconState.observe(viewLifecycleOwner) { }
    }

    private fun addNewAddButton() {
        val button = createAddCustomerButton()
        binding.customersContainer.addView(button)
    }

    private fun createAddCustomerButton(): ImageView {
        return ImageView(requireContext()).apply {

            // 1️⃣ ustaw ikonę "+" i tag
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
                    // 🔹 Tworzymy nowe zamówienie
                    val nextId = (orderViewModel.orders.value?.size ?: 0) + 1

                    val newOrder = Order(
                        ID = nextId.toLong(),
                        name = "Zamówienie $nextId",
                        description = "Opis zamówienia"
                    )

                    orderViewModel.addOrder(newOrder)

                    // 🔹 Zmieniamy ikonę i tag na ID zamówienia
                    setImageResource(R.drawable.ic_card)
                    tag = newOrder.ID

                    // 🔹 Dodaj nowy pusty "+"
                    addNewAddButton()

                } else {
                    // 🔹 Sprawdzamy, czy tag jest Long (ID zamówienia)
                    val orderId = tagValue as? Long
                    if (orderId != null) {
                        // Przejście do MenuChoiceFragment z argumentem
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
