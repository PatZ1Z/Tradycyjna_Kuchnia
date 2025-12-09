package com.example.tradycyjna_kuchnia.model

data class MealItem(
    val name: String,
    val price: Double,
    var quantity: Int = 1
)

