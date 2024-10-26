package pl.roj.dbajozdrowie

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import android.widget.Button

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
        }
    }
}