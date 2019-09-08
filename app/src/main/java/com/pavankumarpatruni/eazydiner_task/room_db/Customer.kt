package com.pavankumarpatruni.eazydiner_task.room_db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "customer_table")
data class Customer(
    @PrimaryKey val id: Int,
    val name: String,
    val address: String,
    val contactNumber: String,
    var type: String
)