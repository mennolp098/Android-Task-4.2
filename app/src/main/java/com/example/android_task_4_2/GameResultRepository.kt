package com.example.android_task_4_2

import android.content.Context

class GameResultRepository(context: Context) {

    private val gameResultDao: GameResultDao

    init {
        val database =
            GameHistoryDatabase.getDatabase(
                context
            )
        gameResultDao = database!!.gameResultDao()
    }

    suspend fun getAllGameResults(): List<GameResult> = gameResultDao.getAllGameResults()

    suspend fun insertGameResult(gameResult: GameResult) = gameResultDao.insertGameResult(gameResult)

    suspend fun deleteGameResult(gameResult: GameResult) = gameResultDao.deleteGameResult(gameResult)

    suspend fun deleteAllGameResults() = gameResultDao.deleteAllGameResult()

}