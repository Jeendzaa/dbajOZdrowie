package pl.roj.dbajozdrowie

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Im
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AddMedicine : AppCompatActivity()
{
    private var our_request_code: Int = 123

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_medicine)
    }

    // Adding medicine
    //TODO: Make adding med
    fun addMed(view: View)
    {
        if (checkIfData()) Toast.makeText(this, "Lek dodany", Toast.LENGTH_LONG).show()
    }

    // Checking is typed data is good
    fun checkIfData(): Boolean
    {
        val name: EditText = findViewById(R.id.med_name_edit)
        val count: EditText = findViewById(R.id.count_edit)
        val nameBool = name.text.toString() != ""
        val countBool = count.text.toString() != ""

        if (!nameBool) Toast.makeText(this, "Brak nazwy leku", Toast.LENGTH_SHORT).show()
        else if (!countBool) Toast.makeText(this, "Brak ilośći tabletek", Toast.LENGTH_SHORT).show()

        return nameBool && countBool
    }

    // Showing calendar
    fun showCalendar(view: View)
    {
    }

    // Camera usage
    fun takePhoto(view: View)
    {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, our_request_code)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == our_request_code && resultCode == RESULT_OK)
        {
            val imageView: ImageView = findViewById(R.id.med_img)
            val bitmap = data?.extras?.get("data") as Bitmap
            imageView.setImageBitmap(bitmap)
        }
    }
}