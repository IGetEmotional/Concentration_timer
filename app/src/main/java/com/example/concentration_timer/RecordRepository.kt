package com.example.concentration_timer

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

class RecordRepository(private val recordDao: RecordDao) {

    val readAllData: Flow<List<Record>> = recordDao.getAll()

    suspend fun addRecord(record: Record){
        recordDao.insert(record)
    }


}