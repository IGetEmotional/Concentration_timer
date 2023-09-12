package com.example.concentration_timer

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        Record::class
               ],
    version = 1
)
abstract class RecordDatabase: RoomDatabase() {

   // abstract fun getRecordDao(): RecordDao

    abstract val dao: RecordDao

    companion object{
 //       @Volatile
//        private  var INSTANCE: RecordDatabase? = null

        /*
        fun getDatabase(context: Context): RecordDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        RecordDatabase::class.java,
                        "the_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }

         */

        fun createDataBase(context: Context): RecordDatabase{
            return Room.databaseBuilder(
                context,
                RecordDatabase::class.java,
                "ness.db"
            ).build()
        }


   //     private var instance: RecordDatabase? = null
   //     fun getInstance(context: Context): RecordDatabase {
   //         if(instance == null){
   //             instance = Room.databaseBuilder(context, RecordDatabase::class.java, "the_database.db")
   //                 .allowMainThreadQueries()
   //                 .build()
   //        }
   //        return instance as RecordDatabase
   //     }
    }
}