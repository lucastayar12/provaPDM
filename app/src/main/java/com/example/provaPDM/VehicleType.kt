package com.example.provaPDM

enum class VehicleType(val type : String) {
    HATCH("Hatch"), TRUCK("Truck"), MOTORBIKE("Motorbike"), SEDAN("Sedan"), PICKUP_TRUCK("Pickup Truck"), VAN("Van"), SUV("SUV");

    companion object{
        fun getList(): List<String> {
            return values().map {
                it.type
            }
        }
    }

}