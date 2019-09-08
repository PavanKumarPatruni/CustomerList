package com.pavankumarpatruni.eazydiner_task.listeners

import com.pavankumarpatruni.eazydiner_task.room_db.Customer

interface OnLongClickListener {

    fun onLongClick(position: Int, customer: Customer)

}