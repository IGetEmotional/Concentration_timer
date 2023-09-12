package com.example.concentration_timer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import kotlinx.coroutines.launch


class TimerViewModel(val database: RecordDatabase) : ViewModel() {
    val recordN: Record? = null

    fun insertItem(time: String, isDone: Boolean) = viewModelScope.launch {
        val record = recordN?.copy(declaredTime = time, isComplete = isDone)
            ?: Record(0, time, isDone)
       //database.dao.insert(Record(time, isDone))
        database.dao.insert(record)
    }

    companion object{
        val fatory: ViewModelProvider.Factory = object : ViewModelProvider.Factory{
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val database = (checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]) as App).database
                return TimerViewModel(database) as T
            }
        }
    }
}