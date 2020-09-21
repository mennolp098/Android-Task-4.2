package com.example.android_task_4_2

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface GameResultDao {

    @Query("SELECT * FROM game_result_table")
    suspend fun getAllGameResults(): List<GameResult>

    @Insert
    suspend fun insertGameResult(result: GameResult)

    @Delete
    suspend fun deleteGameResult(result: GameResult)

    @Query("DELETE FROM game_result_table")
    suspend fun deleteAllGameResult()
}