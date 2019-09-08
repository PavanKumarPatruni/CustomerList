package com.pavankumarpatruni.eazydiner_task.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.pavankumarpatruni.eazydiner_task.R
import com.pavankumarpatruni.eazydiner_task.listeners.OnLongClickListener
import com.pavankumarpatruni.eazydiner_task.room_db.Customer
import kotlinx.android.synthetic.main.layout_item_customer.view.*


class CustomerAdapter(private val context: Context) :
    RecyclerView.Adapter<CustomerAdapter.ViewHolder>() {

    private lateinit var diff: DiffUtil.DiffResult
    private lateinit var onLongClickListener: OnLongClickListener
    private var items: List<Customer> = ArrayList()

    fun setItems(addItems: List<Customer>) {
        items = addItems
        notifyDataSetChanged()
    }

    fun setOnLongClickListener(listener: OnLongClickListener) {
        onLongClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.layout_item_customer,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val customer = items.get(position)

        holder.bind(customer, onLongClickListener)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnLongClickListener {

        private val textViewName: TextView = view.textViewName
        private val textViewMobile: TextView = view.textViewMobile
        private val textViewAddress: TextView = view.textViewAddress
        private val textViewType: TextView = view.textViewType

        private lateinit var onLongClickListener: OnLongClickListener
        private lateinit var customerData: Customer

        fun bind(customer: Customer, listener: OnLongClickListener) {
            textViewName.text = customer.name
            textViewMobile.text = customer.contactNumber
            textViewAddress.text = customer.address
            textViewType.text = customer.type

            onLongClickListener = listener
            customerData = customer

            itemView.setOnLongClickListener(this)
        }

        override fun onLongClick(p0: View?): Boolean {
            onLongClickListener.onLongClick(adapterPosition, customerData)
            return true
        }
    }

}