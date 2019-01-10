package com.david.prestoq.data

data class ManagerSpecialsResponse(
    val canvasUnit: Int,
    val managerSpecials: List<ManagerSpecial>)

data class ManagerSpecial(
    val display_name: String,
    val height: Int,
    val imageUrl: String,
    val original_price: String,
    val price: String,
    val width: Int)