package com.example.android_task_4_2

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class GameResultAdapter(private val gameresults: List<GameResult>) : RecyclerView.Adapter<GameResultAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_game_history, parent, false)
        )
    }

    override fun getItemCount(): Int = gameresults.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(gameresults[position])

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(gameResult: GameResult) {
            var resultText = Resources.getSystem().getString(R.string.you_win)
            var computerDrawable = 0
            var youDrawable = 0;
            val date = Date(gameResult.timestamp)
            val df = SimpleDateFormat("dd:MM:yy:HH:mm:ss", Locale.getDefault())

            when(gameResult.result) {
                WIN -> resultText = Resources.getSystem().getString(R.string.you_win)
                LOSE -> resultText = Resources.getSystem().getString(R.string.computer_win)
                DRAW -> resultText = Resources.getSystem().getString(R.string.draw)
            }
            when(gameResult.computer)
            {
                SCISSORS -> computerDrawable = R.drawable.scissors
                ROCK -> computerDrawable = R.drawable.rock
                PAPER -> computerDrawable = R.drawable.paper
            }
            when(gameResult.you)
            {
                SCISSORS -> youDrawable = R.drawable.scissors
                ROCK -> youDrawable = R.drawable.rock
                PAPER -> youDrawable = R.drawable.paper
            }

            itemView.findViewById<TextView>(R.id.tv_result).text = resultText
            itemView.findViewById<TextView>(R.id.tv_date).text = df.format(date)
            itemView.findViewById<ImageView>(R.id.iv_computer).setImageResource(computerDrawable)
            itemView.findViewById<ImageView>(R.id.iv_you).setImageResource(youDrawable)
        }
    }
}