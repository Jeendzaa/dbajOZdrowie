package pl.roj.dbajozdrowie

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Calendar

class PhotoNameCreator
{
    @SuppressLint("SimpleDateFormat")
    fun createName(name: String): String
    {
        val time = Calendar.getInstance().time
        val formater = SimpleDateFormat("yyyy-MM-dd_HH:MM")
        val date = formater.format(time)

        val photoName = "${date}_${name}"

        return photoName
    }
}