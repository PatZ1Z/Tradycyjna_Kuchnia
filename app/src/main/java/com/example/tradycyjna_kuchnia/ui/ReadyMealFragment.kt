package com.example.tradycyjna_kuchnia.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.tradycyjna_kuchnia.databinding.FragmentReadyMealBinding
import com.example.tradycyjna_kuchnia.model.MealItem

class ReadyMealFragment : Fragment() {

    private var _binding: FragmentReadyMealBinding? = null
    private val binding get() = _binding!!

    private val args: ReadyMealFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReadyMealBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val orderId = args.orderId

        val viewModel = ViewModelProvider(requireActivity())[OrderViewModel::class.java]

        binding.imgPierogi.setOnClickListener {
            val mealName = "Pierogi"
            val mealPrice = 15.00

            viewModel.addMealToOrder(orderId, MealItem(mealName, mealPrice, 1))
            binding.pierogiQuantityLayout.visibility = View.VISIBLE

            val meal = viewModel.getOrderById(orderId)?.meals?.find { it.name == mealName }
            binding.txtPierogiQuantity.text = meal?.quantity.toString()

            binding.btnPlusPierogi.setOnClickListener {
                viewModel.addMealToOrder(orderId, MealItem(mealName, mealPrice, 1))
                val updated = viewModel.getOrderById(orderId)?.meals?.find { it.name == mealName }
                binding.txtPierogiQuantity.text = updated?.quantity.toString()
            }

            binding.btnMinusPierogi.setOnClickListener {
                val order = viewModel.getOrderById(orderId)
                val updated = order?.meals?.find { it.name == mealName }

                if (updated != null && updated.quantity > 1) {
                    updated.quantity--
                    binding.txtPierogiQuantity.text = updated.quantity.toString()
                    viewModel.updateOrders(order)
                }
            }
        }


        binding.imgRosol.setOnClickListener {
            val mealName = "Rosół"

            viewModel.addMealToOrder(
                orderId,
                MealItem(mealName, 12.50, 1)
            )

            // pokaż panel zmiany ilości
            binding.rosolQuantityLayout.visibility = View.VISIBLE

            // pobierz aktualną potrawę z zamówienia
            val order = viewModel.getOrderById(orderId)
            val meal = order?.meals?.find { it.name == mealName }

            binding.txtRosolQuantity.text = meal?.quantity.toString()

            // +
            binding.btnPlusRosol.setOnClickListener {
                viewModel.addMealToOrder(orderId, MealItem(mealName, 12.50, 1))
                val updatedMeal = viewModel.getOrderById(orderId)?.meals?.find { it.name == mealName }
                binding.txtRosolQuantity.text = updatedMeal?.quantity.toString()
            }

            // –
            binding.btnMinusRosol.setOnClickListener {
                val updatedOrder = viewModel.getOrderById(orderId)
                val updatedMeal = updatedOrder?.meals?.find { it.name == mealName }

                if (updatedMeal != null && updatedMeal.quantity > 1) {
                    updatedMeal.quantity--
                    binding.txtRosolQuantity.text = updatedMeal.quantity.toString()
                    viewModel.updateOrders(updatedOrder)
                }
            }
        }


        binding.imgSchabowy.setOnClickListener {
            val mealName = "Schabowy"
            val mealPrice = 22.00

            viewModel.addMealToOrder(orderId, MealItem(mealName, mealPrice, 1))
            binding.schabowyQuantityLayout.visibility = View.VISIBLE

            val meal = viewModel.getOrderById(orderId)?.meals?.find { it.name == mealName }
            binding.txtSchabowyQuantity.text = meal?.quantity.toString()

            binding.btnPlusSchabowy.setOnClickListener {
                viewModel.addMealToOrder(orderId, MealItem(mealName, mealPrice, 1))
                val updated = viewModel.getOrderById(orderId)?.meals?.find { it.name == mealName }
                binding.txtSchabowyQuantity.text = updated?.quantity.toString()
            }

            binding.btnMinusSchabowy.setOnClickListener {
                val order = viewModel.getOrderById(orderId)
                val updated = order?.meals?.find { it.name == mealName }

                if (updated != null && updated.quantity > 1) {
                    updated.quantity--
                    binding.txtSchabowyQuantity.text = updated.quantity.toString()
                    viewModel.updateOrders(order)
                }
            }
        }


        binding.imgBigos.setOnClickListener {
            val mealName = "Bigos"
            val mealPrice = 14.00

            viewModel.addMealToOrder(orderId, MealItem(mealName, mealPrice, 1))
            binding.bigosQuantityLayout.visibility = View.VISIBLE

            val meal = viewModel.getOrderById(orderId)?.meals?.find { it.name == mealName }
            binding.txtBigosQuantity.text = meal?.quantity.toString()

            binding.btnPlusBigos.setOnClickListener {
                viewModel.addMealToOrder(orderId, MealItem(mealName, mealPrice, 1))
                val updated = viewModel.getOrderById(orderId)?.meals?.find { it.name == mealName }
                binding.txtBigosQuantity.text = updated?.quantity.toString()
            }

            binding.btnMinusBigos.setOnClickListener {
                val order = viewModel.getOrderById(orderId)
                val updated = order?.meals?.find { it.name == mealName }

                if (updated != null && updated.quantity > 1) {
                    updated.quantity--
                    binding.txtBigosQuantity.text = updated.quantity.toString()
                    viewModel.updateOrders(order)
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
