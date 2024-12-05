package pl.roj.dbajozdrowie

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val addMedButton: Button = findViewById(R.id.add_med_button)
        addMedButton.setOnClickListener {
            val intent = Intent(this, AddMedicine::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.from_right, R.anim.to_left)
        }

        val scheduleButton: Button = findViewById(R.id.schedule_button)
        scheduleButton.setOnClickListener {
            startActivity(Intent(this, ScheduleActivity::class.java))
            overridePendingTransition(R.anim.from_right, R.anim.to_left)
        }

        val listButton: Button = findViewById(R.id.list_button)
        listButton.setOnClickListener {
            startActivity(Intent(this, MedList::class.java))
            overridePendingTransition(R.anim.from_right, R.anim.to_left)
        }
    }
}