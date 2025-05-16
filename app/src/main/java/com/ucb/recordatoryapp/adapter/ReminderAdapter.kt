package com.ucb.recordatoryapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ucb.recordatoryapp.R
import com.ucb.recordatoryapp.data.Reminder
import java.text.SimpleDateFormat
import java.util.*

class ReminderAdapter(
    private var list: List<Reminder>,
    private val onDelete: (Reminder) -> Unit
) : RecyclerView.Adapter<ReminderAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTv: TextView = view.findViewById(R.id.tvName)
        val dateTv: TextView = view.findViewById(R.id.tvDate)
        val impTv: TextView = view.findViewById(R.id.tvImportance)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_reminder, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val reminder = list[position]
        holder.nameTv.text = reminder.name
        holder.dateTv.text = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(reminder.date)
        holder.impTv.text = reminder.importance.name.replace("_", " ")

        holder.itemView.setOnLongClickListener {
            onDelete(reminder)
            true
        }
    }

    override fun getItemCount() = list.size

    fun submitList(newList: List<Reminder>) {
        list = newList
        notifyDataSetChanged()
    }
}