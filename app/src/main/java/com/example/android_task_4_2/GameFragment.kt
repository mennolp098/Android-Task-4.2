package com.example.android_task_4_2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.get
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_game_history.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.Instant
import java.time.format.DateTimeFormatter
import kotlin.random.Random

const val ROCK = 0
const val PAPER = 1
const val SCISSORS = 2

const val WIN = 0;
const val LOSE = 1;
const val DRAW = 2;

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class GameFragment : Fragment() {
    private lateinit var gameResultRepository: GameResultRepository
    private lateinit var navController: NavController
    private val mainScope = CoroutineScope(Dispatchers.Main)

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()

        var toolbar:Toolbar? = getActivity()?.findViewById(R.id.toolbar)
        if(toolbar != null) {
            toolbar.setOnMenuItemClickListener {
                onMenuItemClick(it)
            }
            //toolbar.menu[0].setIcon(R.drawable.ic_history_white_24dp)
            //toolbar.menu.getItem(R.id.action_icon).setIcon(R.drawable.ic_history_white_24dp)
        }

        gameResultRepository = GameResultRepository(requireContext())

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
        var result = 0;

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
            result = DRAW
        } else if((option == ROCK && computer == SCISSORS) || (option == PAPER && computer == ROCK) || (option == SCISSORS && computer == PAPER)) {
            resultTextView.text = getString(R.string.you_win)
            result = WIN
        } else {
            resultTextView.text = getString(R.string.you_lose)
            result = LOSE
        }

        mainScope.launch {
            val gameResult = GameResult(
                timestamp = System.currentTimeMillis(),
                computer = computer,
                you = option,
                result = result
            )
            withContext(Dispatchers.IO) {
                gameResultRepository.insertGameResult(gameResult)
            }
        }
    }

    private fun onMenuItemClick(menuItem: MenuItem): Boolean {
        if(menuItem.itemId == R.id.action_icon)
        {
            navController.navigate(
                R.id.action_FirstFragment_to_SecondFragment
            )
            return true
        }
        return false
    }
}