package pl.roj.dbajozdrowie

import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.icu.number.Scale
import android.icu.util.Calendar
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.time.Year
import kotlin.math.exp

class AddMedicine : AppCompatActivity()
{
    private var CAMERA_REQUEST_KEY: Int = 1

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_medicine)
    }

    // Getting back to main activity (main page)
    fun getBack(view: View)
    {
        startActivity(Intent(this, MainActivity::class.java))
        overridePendingTransition(R.anim.from_left, R.anim.to_right)
    }

    // Taking image functions
    // Camera activity
    fun takePhoto(view: View)
    {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA_REQUEST_KEY)
    }

    // onActivityResult handling camera and calendar usage
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode)
        {
            CAMERA_REQUEST_KEY ->
            {
                if (resultCode == Activity.RESULT_OK) {
                    val imageView: ImageView = findViewById(R.id.med_img)
                    val bitmap = data?.extras?.get("data") as Bitmap
                    imageView.setImageBitmap(bitmap)
                }
            }
        }
    }

    fun unitDialog(view: View)
    {
        val items = resources.getStringArray(R.array.units)

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Wybierz jednostkę")
        builder.setItems(items) {dialog, witch ->
            val selectetOption = items[witch]
            findViewById<Button>(R.id.unit_button).text = selectetOption.toString()
        }
        val dialog = builder.create()
        dialog.show()
    }

    // Setting expiring date function
    // Calendar dialog
    fun calendarDialog(view: View)
    {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePicker = DatePickerDialog(
            this, { _, selectedYear, selectedMonth, selectedDay ->
                val date = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                findViewById<Button>(R.id.exp_button). text = date
            },
            year,
            month,
            day
        )
        datePicker.show()
    }

    // Adding medicine functions
    // Adding medicine
    //TODO: Add a script for adding the medicine to the database
    fun addMed(view: View)
    {
        if (dataAccuracy())
        {
            val name: EditText = findViewById(R.id.med_name_edit)
            val count: EditText = findViewById(R.id.count_edit)
            val exp: Button = findViewById(R.id.exp_button)
            val unit: Button = findViewById(R.id.unit_button)

            Toast.makeText(this, "Lek ${name.text.toString()} dodany. Ilość: ${count.text.toString()} ${unit.text.toString()}. Data: ${exp.text.toString()}", Toast.LENGTH_LONG).show()
        }
    }

    // Checking is typed data is good
    private fun dataAccuracy(): Boolean
    {
        val name: EditText = findViewById(R.id.med_name_edit)
        val count: EditText = findViewById(R.id.count_edit)
        val exp: Button = findViewById(R.id.exp_button)
        val unit: Button = findViewById(R.id.unit_button)


        val nameBool = name.text.toString() != ""
        val countBool = count.text.toString() != ""
        val expBool = exp.text.toString() != resources.getString(R.string.exp_edit_hint)
        val unitBool = unit.text.toString() != resources.getString(R.string.unit_button)

        if (!nameBool) Toast.makeText(this, "Brak nazwy leku", Toast.LENGTH_SHORT).show()
        else if (!countBool) Toast.makeText(this, "Brak ilośći tabletek", Toast.LENGTH_SHORT).show()
        else if (!unitBool) Toast.makeText(this, "Brak jednostki", Toast.LENGTH_SHORT).show()
        else if (!expBool) Toast.makeText(this, "Brak daty ważności", Toast.LENGTH_SHORT).show()

        return nameBool && countBool && expBool && unitBool
    }
}