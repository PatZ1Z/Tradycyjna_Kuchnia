package com.example.tradycyjna_kuchnia.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tradycyjna_kuchnia.model.MealItem
import com.example.tradycyjna_kuchnia.model.Order

class OrderViewModel : ViewModel() {

    // Lista zamówień
    private val _orders = MutableLiveData<List<Order>>(emptyList())
    val orders: LiveData<List<Order>> get() = _orders

    // Zmienna do przechowywania stanu ikony (np. "default", "added")
    private val _iconState = MutableLiveData<String>("default")
    val iconState: LiveData<String> get() = _iconState

    // Metoda dodająca nowe zamówienie
    fun addOrder(order: Order) {
        val currentOrders = _orders.value ?: emptyList()
        _orders.value = currentOrders + order

        // Po dodaniu zamówienia zmiana stanu ikony
        _iconState.value = "added"
    }

    fun addMealToOrder(orderId: Long, meal: MealItem) {
        val currentOrders = _orders.value?.toMutableList() ?: return

        val orderIndex = currentOrders.indexOfFirst { it.ID == orderId }
        if (orderIndex == -1) return

        val order = currentOrders[orderIndex]

        // jeśli potrawa już istnieje — zwiększamy ilość
        val existingMeal = order.meals.find { it.name == meal.name }
        if (existingMeal != null) {
            existingMeal.quantity += meal.quantity
        } else {
            order.meals.add(meal)
        }

        _orders.value = currentOrders
    }

    fun updateOrders(order: Order) {
        val current = _orders.value?.toMutableList() ?: return
        val index = current.indexOfFirst { it.ID == order.ID }
        if (index != -1) {
            current[index] = order
            _orders.value = current
        }
    }


    // Metoda sprawdzająca czy zamówienia istnieją
    fun ordersExist(): Boolean {
        return _orders.value?.isNotEmpty() == true
    }

    // Opcjonalnie: pobranie zamówienia po ID
    fun getOrderById(id: Long): Order? {
        return _orders.value?.find { it.ID == id }
    }
}
