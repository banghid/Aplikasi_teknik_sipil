package com.example.aplikasipembelajarantekniksipil.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.aplikasipembelajarantekniksipil.model.UserStageModel

@Dao
interface UserStageDAO{

    @Query("SELECT * FROM user_stage WHERE chapter_id = :chapterId")
    fun getByChapter(chapterId: Int):List<UserStageModel>

    @Insert(onConflict = REPLACE)
    fun insert(userStageData: UserStageModel)
}
