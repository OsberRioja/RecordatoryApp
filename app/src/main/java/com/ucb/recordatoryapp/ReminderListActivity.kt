package com.ucb.recordatoryapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ucb.recordatoryapp.adapter.ReminderAdapter
import com.ucb.recordatoryapp.data.ReminderDatabase
import kotlinx.android.synthetic.main.activity_reminder_list.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ReminderListActivity : AppCompatActivity() {
    private lateinit var adapter: ReminderAdapter
    private val db by lazy { ReminderDatabase.getDatabase(this).reminderDao() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminder_list)

        adapter = ReminderAdapter(emptyList()) { reminder ->
            lifecycleScope.launch { db.delete(reminder) }
        }
        rvReminders.layoutManager = LinearLayoutManager(this)
        rvReminders.adapter = adapter

        btnAdd.setOnClickListener {
            startActivity(Intent(this, AddReminderActivity::class.java))
        }

        lifecycleScope.launch {
            db.getAll().collect { list ->
                adapter.submitList(list)
            }
        }
    }
}