package com.example.concentration_timer

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "records", indices = [Index("id")])
 data class Record(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "declaredTime") val declaredTime: String = "",
    @ColumnInfo(name = "isComplete") val isComplete: Boolean = false

)
{
   //  constructor(declaredTime: String, isComplete: Boolean): this(Int.MIN_VALUE, declaredTime, isComplete)
}