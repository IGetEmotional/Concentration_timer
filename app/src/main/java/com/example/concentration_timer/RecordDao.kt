package com.example.concentration_timer

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Room
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface RecordDao {

    @Query("SELECT * FROM records ORDER BY id ASC")
    fun getAll(): Flow<List<Record>>

    @Insert(onConflict = OnConflictStrategy.NONE)
    suspend fun insert(record: Record)

    @Delete
    suspend fun delete(record: Record)




}