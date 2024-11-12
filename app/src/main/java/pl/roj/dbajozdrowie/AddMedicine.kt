package pl.roj.dbajozdrowie

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.icu.util.Calendar
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import pl.roj.dbajozdrowie.db.AppDatabase
import pl.roj.dbajozdrowie.db.Med
import java.io.File
import java.io.FileOutputStream

class AddMedicine : AppCompatActivity()
{
    private var CAMERA_REQUEST_KEY: Int = 1
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_medicine)
        db = AppDatabase.getInstance(this)
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
                findViewById<Button>(R.id.exp_button).text = date
            },
            year,
            month,
            day
        )
        datePicker.show()
    }

    // Adding medicine functions
    // Adding medicine
    fun addMed(view: View)
    {
        val medDao = db.medDao()
        if (dataAccuracy())
        {
            val medName: String = findViewById<EditText>(R.id.med_name_edit).toString()
            var medCount: Int? = findViewById<EditText>(R.id.count_edit).toString().toIntOrNull()
            val medUnit: String = findViewById<Button>(R.id.unit_button).text.toString()
            val medExpDate: String = findViewById<Button>(R.id.exp_button).text.toString()

            if (medCount == null) medCount = 0

            GlobalScope.launch(Dispatchers.IO)
            {
                val med = Med(medName = medName, medCount = medCount, medUnit = medUnit, medExpDate = medExpDate)
                medDao.insertAll(med)
            }

            Toast.makeText(this, "Lek dodany" , Toast.LENGTH_SHORT).show()
            //saveToInternalStorage(medimg.drawable.toBitmap(), medphotoname)

            startActivity(Intent(this, MainActivity::class.java))
            overridePendingTransition(R.anim.from_left, R.anim.to_right)
        }
    }

    private fun saveToInternalStorage(img: Bitmap, name: String): String
    {
        val file = File(filesDir, name)
        val outputStream = FileOutputStream(file)

        img.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream.flush()
        outputStream.close()

        return file.absoluteFile.toString()
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