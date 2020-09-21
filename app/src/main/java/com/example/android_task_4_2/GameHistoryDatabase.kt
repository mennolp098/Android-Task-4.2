package com.example.android_task_4_2

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [GameResult::class], version = 1, exportSchema = false)
abstract class GameHistoryDatabase : RoomDatabase() {

    abstract fun gameResultDao(): GameResultDao

    companion object {
        private const val DATABASE_NAME = "GAME_HISTORY_DATABASE"

        @Volatile
        private var gameHistoryDatabaseInstance: GameHistoryDatabase? = null

        fun getDatabase(context: Context): GameHistoryDatabase? {
            if (gameHistoryDatabaseInstance == null) {
                synchronized(GameHistoryDatabase::class.java) {
                    if (gameHistoryDatabaseInstance == null) {
                        gameHistoryDatabaseInstance =
                            Room.databaseBuilder(context.applicationContext,
                                GameHistoryDatabase::class.java,
                                DATABASE_NAME
                            ).build()
                    }
                }
            }
            return gameHistoryDatabaseInstance
        }
    }

}