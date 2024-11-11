package pl.roj.dbajozdrowie

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// DatabaseHelper class is responsible for creating and updating app database
class DatabaseHelper private constructor(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION)
{
    // onCreate class is called only once, when database is created
    override fun onCreate(db: SQLiteDatabase?)
    {
        // SQLite query for creating table med
        db?.execSQL("CREATE TABLE IF NOT EXISTS med (med_id INTEGER PRIMARY KEY AUTOINCREMENT, med_name TEXT, med_count INTEGER, med_quantity TEXT, med_exp_date TEXT, photo_path TEXT)")
    }

    // onUpgrade function is called everytime when database version change
    override fun onUpgrade(db: SQLiteDatabase?, old: Int, new: Int)
    {
        // SQLite query for deleting table med
        db?.execSQL("DROP TABLE IF EXISTS med")
        // Re-creating table
        onCreate(db)
    }

    // Const objects
    companion object {
        private const val DATABASE_NAME = "app_db.db"
        private const val DATABASE_VERSION = 1;

        // Static and only one instance of database
        @Volatile private var instance: DatabaseHelper? = null

        // Function returning instance of database
        fun getInstance(context: Context): DatabaseHelper =
            instance?: synchronized(this){
                instance ?: DatabaseHelper(context.applicationContext).also { instance = it }
            }
    }

    // Function that inserts medication data to database
    fun addMedicine(name: String, medCount: Int, medQuantity: String, medExpDate: String, photoPath: String)
    {
        val db = writableDatabase

        val values = ContentValues().apply {
            put("name", name)
            put("med_count", medCount)
            put("med_quantity", medQuantity)
            put("med_exp_date", medExpDate)
            put("photo_path", photoPath)
        }

        db.insert("med", null, values)
    }
}