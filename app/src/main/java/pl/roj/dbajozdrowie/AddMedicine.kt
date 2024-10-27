package pl.roj.dbajozdrowie

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.exp

class AddMedicine : AppCompatActivity()
{
    private var CAMERA_REQUEST_KEY: Int = 1
    private var CALENDAR_REQUEST_KEY: Int = 2

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_medicine)
    }

    // Adding medicine
    //TODO: Make adding med
    fun addMed()
    {
        if (dataAccuracy()) Toast.makeText(this, "Lek dodany", Toast.LENGTH_LONG).show()
    }

    // Checking is typed data is good
    fun dataAccuracy(): Boolean
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
        val intent = Intent(this, CalendarActivity::class.java)
        startActivity(intent)
    }

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
            CALENDAR_REQUEST_KEY ->
            {
                if(resultCode == Activity.RESULT_OK)
                {
                    val expButton: Button = findViewById(R.id.exp_button)
                    val date = data?.extras?.get("data") as String
                    expButton.text = date
                }
            }
        }
    }
}
