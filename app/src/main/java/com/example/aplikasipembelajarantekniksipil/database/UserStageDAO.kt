package com.example.aplikasipembelajarantekniksipil.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import com.example.aplikasipembelajarantekniksipil.model.UserStageModel

@Dao
interface UserStageDAO{

    @Query("SELECT * FROM user_stage WHERE chapter_id = :chapterId")
    fun getByChapter(chapterId: Int):List<UserStageModel>

    @Insert(onConflict = REPLACE)
    fun insert(userStageData: UserStageModel)
}
