package pl.roj.dbajozdrowie.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MedDao {

    @Insert
    suspend fun insertAll(vararg med: Med)

    @Delete
    suspend fun delete(med: List<Med>)

    @Query("SELECT * FROM MEDS")
    fun getAll(): List<Med>
}