package com.pavankumarpatruni.eazydiner_task.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.RadioButton
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.pavankumarpatruni.eazydiner_task.R
import com.pavankumarpatruni.eazydiner_task.adapters.CustomerAdapter
import com.pavankumarpatruni.eazydiner_task.listeners.OnLongClickListener
import com.pavankumarpatruni.eazydiner_task.room_db.Customer
import com.pavankumarpatruni.eazydiner_task.viewmodel.CustomerViewModel
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.layout_type_change.view.*

class ListActivity : AppCompatActivity(), OnLongClickListener {

    private var type: String = "Group"

    private lateinit var customerViewModel: CustomerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        imageButtonBack.setOnClickListener {
            onBackPressed()
        }

        val customerAdapter = CustomerAdapter(this)
        customerAdapter.setOnLongClickListener(this)

        customerViewModel = ViewModelProviders.of(this).get(CustomerViewModel::class.java)
        customerViewModel.getAllCustomers().observe(this, Observer {

            customerAdapter.setItems(it)

            if (it.isNotEmpty()) {
                textViewNoCustomers.visibility = View.GONE
                buttonDeleteAll.visibility = View.VISIBLE
            } else {
                textViewNoCustomers.visibility = View.VISIBLE
                buttonDeleteAll.visibility = View.GONE
            }
        })

        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = customerAdapter

        buttonDeleteAll.setOnClickListener {
            customerViewModel.deleteAll()
        }
    }

    override fun onLongClick(position: Int, customer: Customer) {
        showAlert(customer)
    }

    private fun showAlert(customer: Customer) {
        val view = LayoutInflater.from(this).inflate(R.layout.layout_type_change, null)

        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.app_name)
        builder.setMessage(String.format(getString(R.string.ALERT_TYPE_CHANGE), customer.type))
        builder.setView(view)

        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()

        if (customer.type == type) {
            view.radioGroup.check(R.id.radioButtonGroup)
        } else {
            view.radioGroup.check(R.id.radioButtonIndividual)
        }


        view.radioGroup.setOnCheckedChangeListener { radioGroup, index ->
            val radioButton = radioGroup.findViewById<RadioButton>(index)
            customer.type = radioButton.text.toString()
            updateItem(customer)
            alertDialog.dismiss()
        }
    }

    private fun updateItem(customer: Customer) {
        customerViewModel.update(customer)
    }

}
