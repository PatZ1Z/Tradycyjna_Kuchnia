package com.example.tradycyjna_kuchnia.model

data class Order(
    val ID: Long,
    val name: String,
    val description: String,
    val meals: MutableList<MealItem> = mutableListOf()
)


