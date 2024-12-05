package pl.roj.dbajozdrowie

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import kotlinx.coroutines.launch
import pl.roj.dbajozdrowie.db.AppDatabase

class MedList : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var medAdapter: MedAdapter
    private lateinit var database: AppDatabase // Baza danych

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_med_list)

        // Inicjalizacja bazy danych
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "meds_database"
        ).build()

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        medAdapter = MedAdapter(emptyList())
        recyclerView.adapter = medAdapter

        lifecycleScope.launch {
            database.medDao().getAll().collect { meds ->
                medAdapter.updateData(meds)
            }
        }
    }
}
