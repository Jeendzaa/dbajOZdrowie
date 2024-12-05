package pl.roj.dbajozdrowie

import ReminderReceiver
import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class ScheduleActivity : AppCompatActivity()
{
    private var iteration: String? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule)

        createNotificationChannel(this)

        val setButton = findViewById<Button>(R.id.set_button)
        setButton.setOnClickListener {
            if (isValid())
            {
                setReminder(this, AlarmManager.INTERVAL_DAY * getIteration())
                Toast.makeText(this, "Powiadomienie dodane", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
                overridePendingTransition(R.anim.from_left, R.anim.to_right)
            }
        }
    }

    private fun getIteration(): Int {
        val items = resources.getStringArray(R.array.schedule_options)
        var iteration: Int = 1
        val option = findViewById<Button>(R.id.it_button).text.toString()

        if (option == items[0].toString()) iteration = 1
        else if (option == items[1].toString()) iteration = 2
        else if (option == items[2].toString()) iteration = 3

        return iteration
    }

    private fun isValid(): Boolean {
        val name = findViewById<EditText>(R.id.sch_name).text.toString() != ""
        val iteration = findViewById<Button>(R.id.it_button).text.toString() != resources.getString(R.string.iteration_button_hint)

        if (!name) Toast.makeText(this, "Brak nazwy", Toast.LENGTH_SHORT).show()
        else if (!iteration) Toast.makeText(this, "Brak powtórzeń", Toast.LENGTH_SHORT).show()

        return name && iteration
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun createNotificationChannel(context: Context)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            val name = "Medication Reminder"
            val descriptionText = "Channel for medication reminders"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("medication_reminder", name, importance).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun getBack(view: View)
    {
        startActivity(Intent(this, MainActivity::class.java))
        overridePendingTransition(R.anim.from_left, R.anim.to_right)
    }

    fun dialog(view: View)
    {
        val items = resources.getStringArray(R.array.schedule_options)

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Wybierz powtarzanie")
        builder.setItems(items) {_, witch ->
            val selected = items[witch]
            findViewById<Button>(R.id.it_button).text = selected.toString()
            iteration = selected.toString()
        }

        val dial = builder.create()
        dial.show()
    }

    fun setReminder(context: Context, intervalInMillis: Long)
    {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, ReminderReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis() + intervalInMillis,
            intervalInMillis,
            pendingIntent
        )
    }
}