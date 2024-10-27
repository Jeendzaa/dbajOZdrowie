package pl.roj.dbajozdrowie

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CalendarView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class CalendarActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)
    }

    fun setDate(view: View)
    {
        val calendar: CalendarView = findViewById(R.id.calendar)
        val date = calendar.date.toString()

        val result = Intent()
        result.putExtra("2", date)
        setResult(Activity.RESULT_OK, result)
        finish()
    }
}