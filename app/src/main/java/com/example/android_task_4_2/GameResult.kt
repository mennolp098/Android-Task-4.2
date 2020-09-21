package com.example.android_task_4_2

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "game_result_table")
data class GameResult(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null,

    @ColumnInfo(name = "timestamp")
    var timestamp: Int,

    @ColumnInfo(name = "result")
    var result: Int,

    @ColumnInfo(name = "computer")
    var computer: Int,

    @ColumnInfo(name = "you")
    var you: Int
) : Parcelable
