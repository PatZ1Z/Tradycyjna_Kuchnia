package com.example.tradycyjna_kuchnia.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// Klasa reprezentująca zamówienie
data class Order(val name: String, val description: String)

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
        _orders.value = currentOrders + order  // Dodanie nowego zamówienia do listy

        // Po dodaniu zamówienia zmiana stanu ikony
        _iconState.value = "added"
    }

    // Metoda sprawdzająca czy zamówienia istnieją
    fun ordersExist(): Boolean {
        return _orders.value?.isNotEmpty() == true
    }
}

