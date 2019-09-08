package com.pavankumarpatruni.eazydiner_task.viewmodel

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.pavankumarpatruni.eazydiner_task.room_db.AppDatabase
import com.pavankumarpatruni.eazydiner_task.room_db.Customer
import com.pavankumarpatruni.eazydiner_task.room_db.CustomerDao


class CustomerRepository(application: Application) {

    private var customerDao: CustomerDao

    private var allCustomers: LiveData<List<Customer>>

    init {
        val database: AppDatabase = AppDatabase.getInstance(
            application.applicationContext
        )
        customerDao = database.customerDao()
        allCustomers = customerDao.getAllCustomers()
    }

    fun insert(customer: Customer) {
        AddCustomerAsyncTask(customerDao).execute(customer)
    }

    fun deleteAll() {
        DeleteAllAsyncTask(
            customerDao
        ).execute()
    }

    fun getAllCustomers(): LiveData<List<Customer>> {
        return allCustomers
    }

    fun update(customer: Customer) {
        UpdateCustomerAsyncTask(customerDao).execute(customer)
    }

    private class AddCustomerAsyncTask(val customerDao: CustomerDao) :
        AsyncTask<Customer, Unit, Unit>() {

        override fun doInBackground(vararg customer: Customer?) {
            customerDao.insert(customer[0]!!)
        }
    }

    private class UpdateCustomerAsyncTask(val customerDao: CustomerDao) :
        AsyncTask<Customer, Unit, Unit>() {

        override fun doInBackground(vararg customer: Customer?) {
            customerDao.update(customer[0]!!.type, customer[0]!!.id)
        }
    }

    private class DeleteAllAsyncTask(val customerDao: CustomerDao) :
        AsyncTask<Unit, Unit, Unit>() {

        override fun doInBackground(vararg p0: Unit?) {
            customerDao.deleteAll()
        }
    }

}
