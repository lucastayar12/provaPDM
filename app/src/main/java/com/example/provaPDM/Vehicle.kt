package com.example.provaPDM

data class Vehicle(val model : String, var price : Float, val type: VehicleType, var sold : Boolean = false ){

    lateinit var status : String

    fun setStatus(){
        if (!sold) {
            status = "This vehicle is available"
        }else
        {
            status = "This vehicle is not available anymore"
        }
    }


}
