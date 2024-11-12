package pl.roj.dbajozdrowie.db

import androidx.room.*

@Entity(tableName = "meds")
data class Med(
    @PrimaryKey(autoGenerate = true) val medId: Int? = null,
    val medName: String,
    val medCount: Int,
    val medUnit: String,
    val medExpDate: String
)
