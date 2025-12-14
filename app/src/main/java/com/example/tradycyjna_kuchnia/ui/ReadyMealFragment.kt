package com.example.tradycyjna_kuchnia.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tradycyjna_kuchnia.databinding.FragmentReadyMealBinding
import com.example.tradycyjna_kuchnia.model.MealItem
import com.example.tradycyjna_kuchnia.R

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

        fun setupMeal(
            imageViewId: Int,
            quantityLayoutId: Int,
            quantityTextId: Int,
            btnPlusId: Int,
            btnMinusId: Int,
            mealName: String,
            mealPrice: Double
        ) {
            val img = binding.root.findViewById<View>(imageViewId)
            val layout = binding.root.findViewById<View>(quantityLayoutId)
            val txtQuantity = binding.root.findViewById<TextView>(quantityTextId)
            val btnPlus = binding.root.findViewById<Button>(btnPlusId)
            val btnMinus = binding.root.findViewById<Button>(btnMinusId)

            fun updateQuantity() {
                val meal = viewModel.getOrderById(orderId)?.meals?.find { it.name == mealName }
                txtQuantity.text = meal?.quantity.toString()
            }

            img.setOnClickListener {
                viewModel.addMealToOrder(orderId, MealItem(mealName, mealPrice, 1))
                layout.visibility = View.VISIBLE
                updateQuantity()
            }

            btnPlus.setOnClickListener {
                viewModel.addMealToOrder(orderId, MealItem(mealName, mealPrice, 1))
                updateQuantity()
            }

            btnMinus.setOnClickListener {
                val order = viewModel.getOrderById(orderId)
                val meal = order?.meals?.find { it.name == mealName }
                if (meal != null && meal.quantity > 1) {
                    meal.quantity--
                    viewModel.updateOrders(order)
                    updateQuantity()
                }
            }
        }

        // Zupy
        setupMeal(R.id.imgRosol, R.id.rosolQuantityLayout, R.id.txtRosolQuantity, R.id.btnPlusRosol, R.id.btnMinusRosol, "Rosół", 12.5)
        setupMeal(R.id.imgZurek, R.id.zurekQuantityLayout, R.id.txtZurekQuantity, R.id.btnPlusZurek, R.id.btnMinusZurek, "Żurek", 14.0)
        setupMeal(R.id.imgBarszcz, R.id.barszczQuantityLayout, R.id.txtBarszczQuantity, R.id.btnPlusBarszcz, R.id.btnMinusBarszcz, "Barszcz", 10.0)
        setupMeal(R.id.imgOgorkowa, R.id.ogorkowaQuantityLayout, R.id.txtOgorkowaQuantity, R.id.btnPlusOgorkowa, R.id.btnMinusOgorkowa, "Zupa ogórkowa", 11.0)

        // Drugie dania
        setupMeal(R.id.imgPierogi, R.id.pierogiQuantityLayout, R.id.txtPierogiQuantity, R.id.btnPlusPierogi, R.id.btnMinusPierogi, "Pierogi", 18.99)
        setupMeal(R.id.imgSchabowy, R.id.schabowyQuantityLayout, R.id.txtSchabowyQuantity, R.id.btnPlusSchabowy, R.id.btnMinusSchabowy, "Schabowy", 24.0)
        setupMeal(R.id.imgBigos, R.id.bigosQuantityLayout, R.id.txtBigosQuantity, R.id.btnPlusBigos, R.id.btnMinusBigos, "Bigos", 22.0)
        setupMeal(R.id.imgGolabki, R.id.golabkiQuantityLayout, R.id.txtGolabkiQuantity, R.id.btnPlusGolabki, R.id.btnMinusGolabki, "Gołąbki", 20.0)

        // Napoje
        setupMeal(R.id.imgKawa, R.id.kawaQuantityLayout, R.id.txtKawaQuantity, R.id.btnPlusKawa, R.id.btnMinusKawa, "Kawa", 8.0)
        setupMeal(R.id.imgHerbata, R.id.herbataQuantityLayout, R.id.txtHerbataQuantity, R.id.btnPlusHerbata, R.id.btnMinusHerbata, "Herbata", 7.0)
        setupMeal(R.id.imgWoda, R.id.wodaQuantityLayout, R.id.txtWodaQuantity, R.id.btnPlusWoda, R.id.btnMinusWoda, "Woda", 5.0)
        setupMeal(R.id.imgCola, R.id.colaQuantityLayout, R.id.txtColaQuantity, R.id.btnPlusCola, R.id.btnMinusCola, "Cola", 6.0)

        // Powrót
        binding.btnBackToStart.setOnClickListener {
            val action = ReadyMealFragmentDirections.actionReadyMealFragmentToStartFragment()
            view.findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
