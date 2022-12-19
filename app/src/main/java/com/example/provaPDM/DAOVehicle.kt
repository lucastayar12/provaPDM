package com.example.provaPDM

object DAOVehicle{
    lateinit var vehicles : ArrayList<Vehicle>

    fun saveVehicle(vehicle: Vehicle){
        vehicles.add(vehicle)
    }

    fun getVehicles() : List<Vehicle>{
        return vehicles
    }
}