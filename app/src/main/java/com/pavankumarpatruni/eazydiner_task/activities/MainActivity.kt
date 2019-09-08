package com.pavankumarpatruni.eazydiner_task.activities

import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.pavankumarpatruni.eazydiner_task.R
import com.pavankumarpatruni.eazydiner_task.room_db.Customer
import com.pavankumarpatruni.eazydiner_task.viewmodel.CustomerViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {

    companion object {
        const val REG = "^(\\+91)?[0]?(91)?\\d{10}\$"
    }

    private var pattern: Pattern = Pattern.compile(REG)

    private lateinit var customerViewModel: CustomerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        customerViewModel = ViewModelProviders.of(this).get(CustomerViewModel::class.java)
        customerViewModel.getAllCustomers().observe(this, Observer {
            textViewId.text = (it.size + 1).toString()
        })

        var type = "Group"

        buttonViewAll.setOnClickListener {
            val intent = Intent(this, ListActivity::class.java)
            startActivity(intent)
        }

        buttonCreate.setOnClickListener {
            val name = editTextName.text.toString()
            val customerId = textViewId.text.toString()
            val address = editTextAddress.text.toString()
            val contactNumber = editTextContactNumber.text.toString()

            if (name.isBlank() || customerId.isBlank() || address.isBlank() || contactNumber.isBlank() || type.isBlank()) {
                Toast.makeText(this, getString(R.string.INVALID_DETAILS), Toast.LENGTH_SHORT).show()
            } else if (!contactNumber.isDigitsOnly() || !contactNumber.isPhoneNumber()) {
                Toast.makeText(this, getString(R.string.INVALID_PHONE_NUMBER), Toast.LENGTH_SHORT).show()
            } else if (customerId.isDigitsOnly()) {
                val id = customerId.toIntOrNull()

                if (id != null) {
                    val customer = Customer(id, name, address, contactNumber, type)
                    customerViewModel.insert(customer)

                    clearValues()
                    Toast.makeText(this, getString(R.string.SUCCESS), Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, getString(R.string.INVALID_ID), Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, getString(R.string.INVALID_ID), Toast.LENGTH_SHORT).show()
            }
        }

        radioGroup.setOnCheckedChangeListener { radioGroup, index ->
            val radioButton = radioGroup.findViewById<RadioButton>(index)
            type = radioButton.text.toString()
        }

    }

    private fun clearValues() {
        editTextName.text.clear()
        textViewId.text = ""
        editTextAddress.text.clear()
        editTextContactNumber.text.clear()
    }

    private fun CharSequence.isPhoneNumber(): Boolean = pattern.matcher(this).find()
}
