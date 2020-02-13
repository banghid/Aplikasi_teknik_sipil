package com.example.aplikasipembelajarantekniksipil.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import android.util.Log
import com.example.aplikasipembelajarantekniksipil.model.UserStageModel

@Database(entities = arrayOf(UserStageModel::class),version = 1, exportSchema = false)
abstract class UserStageDatabase: RoomDatabase(){

    abstract fun userStageDao(): UserStageDAO

    companion object{
        private var INSTANCE: UserStageDatabase? = null

        fun getInstance(context: Context):UserStageDatabase?{
            try {
                if (INSTANCE == null){
                    synchronized(UserStageDatabase::class){
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            UserStageDatabase::class.java,
                            "user_stage.db"
                        ).allowMainThreadQueries().build()
                    }
                }
                Log.d(">>>>UerStageDatabase", "Success to instance")
            }catch (e: Exception){
                Log.d(">>>>UserStageDatabase", "Fail to instance "+ e.message)
            }
            return INSTANCE
        }
        fun destroyInstance(){
            INSTANCE = null
        }
    }

}