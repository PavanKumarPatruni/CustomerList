package com.pavankumarpatruni.eazydiner_task.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.pavankumarpatruni.eazydiner_task.room_db.Customer

class CustomerViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: CustomerRepository =
        CustomerRepository(application)
    private var allCustomers: LiveData<List<Customer>> = repository.getAllCustomers()

    fun insert(customer: Customer) {
        repository.insert(customer)
    }

    fun deleteAll() {
        repository.deleteAll()
    }

    fun getAllCustomers(): LiveData<List<Customer>> {
        return allCustomers
    }

    fun update(customer: Customer) {
        repository.update(customer)
    }

}