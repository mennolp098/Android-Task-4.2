package com.example.android_task_4_2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.item_game_history.*
import kotlin.random.Random

const val ROCK = 0
const val PAPER = 1
const val SCISSORS = 2

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class GameFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<ImageView>(R.id.iv_paper).setOnClickListener {
            onOptionSelected(view, PAPER)
        }
        view.findViewById<ImageView>(R.id.iv_rock).setOnClickListener {
            onOptionSelected(view, ROCK)
        }
        view.findViewById<ImageView>(R.id.iv_scissors).setOnClickListener {
            onOptionSelected(view, SCISSORS)
        }
    }

    private fun onOptionSelected(view: View, option:Int) {
        val computer = Random.nextInt(0, 3)
        val computerImageView = view.findViewById<ImageView>(R.id.iv_pc)
        val youImageView = view.findViewById<ImageView>(R.id.iv_you)
        val resultTextView = view.findViewById<TextView>(R.id.tv_output)

        when(computer) {
            0 -> computerImageView.setImageResource(R.drawable.rock)
            1 -> computerImageView.setImageResource(R.drawable.paper)
            2 -> computerImageView.setImageResource(R.drawable.scissors)
        }

        when(option) {
            0 -> youImageView.setImageResource(R.drawable.rock)
            1 -> youImageView.setImageResource(R.drawable.paper)
            2 -> youImageView.setImageResource(R.drawable.scissors)
        }

        if(option == computer)
        {
            resultTextView.text = getString(R.string.draw)
        } else if((option == ROCK && computer == SCISSORS) || (option == PAPER && computer == ROCK) || (option == SCISSORS && computer == PAPER)) {
            resultTextView.text = getString(R.string.you_win)
        } else {
            resultTextView.text = getString(R.string.you_lose)
        }
    }
}