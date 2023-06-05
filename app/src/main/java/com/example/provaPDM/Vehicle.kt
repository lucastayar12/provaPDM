package com.example.provaPDM

import java.text.NumberFormat
import java.util.Currency

data class Vehicle(val model : String, var price : Float, val type: VehicleType?, var sold : Boolean = false ){

    lateinit var status : String

    fun setStatus(){
        if (!sold) {
            status = "This vehicle is available"
        }else
        {
            status = "This vehicle is not available anymore"
        }
    }

    fun formatCurrency(): String {
        val format = NumberFormat.getCurrencyInstance()
        format.maximumFractionDigits = 2
        format.currency = Currency.getInstance("BRL")
        return format.format(price/100).toString()
    }



}
