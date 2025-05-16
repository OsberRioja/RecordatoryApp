package com.ucb.recordatoryapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ucb.recordatoryapp.adapter.ReminderAdapter
import com.ucb.recordatoryapp.data.ReminderDatabase
import com.ucb.recordatoryapp.databinding.ActivityReminderListBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ReminderListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReminderListBinding
    private lateinit var adapter: ReminderAdapter
    private val db by lazy { ReminderDatabase.getDatabase(this).reminderDao() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReminderListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ReminderAdapter(emptyList()) { reminder ->
            lifecycleScope.launch { db.delete(reminder) }
        }

        binding.rvReminders.layoutManager = LinearLayoutManager(this)
        binding.rvReminders.adapter = adapter

        binding.btnAdd.setOnClickListener {
            startActivity(Intent(this, AddReminderActivity::class.java))
        }

        lifecycleScope.launch {
            db.getAll().collect { list ->
                adapter.submitList(list)
            }
        }
    }

}