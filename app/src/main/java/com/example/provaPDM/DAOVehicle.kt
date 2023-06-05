package com.example.provaPDM

object DAOVehicle
{
    var vehicles : ArrayList<Vehicle> = ArrayList()

    fun saveVehicle(vehicle: Vehicle)
    {
        vehicles.add(vehicle)
    }

    fun getVehicles() : List<Vehicle>
    {
        return vehicles
    }
}