package com.pavankumarpatruni.eazydiner_task.room_db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface CustomerDao {

    @Query("SELECT * FROM customer_table ORDER BY type ASC")
    fun getAllCustomers(): LiveData<List<Customer>>

    @Insert(onConflict = REPLACE)
    fun insert(customer: Customer)

    @Query("DELETE FROM customer_table")
    fun deleteAll()

    @Query("UPDATE customer_table SET type = :type WHERE id = :id")
    fun update(type: String, id: Int)


}