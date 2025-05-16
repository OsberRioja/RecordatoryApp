package com.ucb.recordatoryapp

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ucb.recordatoryapp.data.Importance
import com.ucb.recordatoryapp.data.Reminder
import com.ucb.recordatoryapp.data.ReminderDatabase
import com.ucb.recordatoryapp.databinding.ActivityAddReminderBinding

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class AddReminderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddReminderBinding
    private val db by lazy { ReminderDatabase.getDatabase(this).reminderDao() }
    private var selectedDate: Date = Date()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddReminderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.etDate.setOnClickListener {
            val cal = Calendar.getInstance()
            DatePickerDialog(this,
                { _, y, m, d ->
                    cal.set(y, m, d)
                    selectedDate = cal.time
                    binding.etDate.setText("$d/${m + 1}/$y")
                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        binding.btnSave.setOnClickListener {
            val name = binding.etName.text.toString()
            val imp = when (binding.spinnerImportance.selectedItemPosition) {
                1 -> Importance.IMPORTANTE
                2 -> Importance.MUY_IMPORTANTE
                else -> Importance.NORMAL
            }

            val reminder = Reminder(name = name, date = selectedDate, importance = imp)

            CoroutineScope(Dispatchers.IO).launch {
                db.insert(reminder)
                finish()
            }
        }
    }
}