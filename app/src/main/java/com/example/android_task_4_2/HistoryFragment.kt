package com.example.android_task_4_2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_history.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class HistoryFragment : Fragment() {
    private lateinit var gameResultRepository: GameResultRepository
    private val mainScope = CoroutineScope(Dispatchers.Main)
    private val gameResultsList = arrayListOf<GameResult>()
    private val gameResultsAdapter =
        GameResultAdapter(gameResultsList)
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gameResultRepository = GameResultRepository(requireContext())
        getGameResultsFromDatabase()

        var toolbar: Toolbar? = getActivity()?.findViewById(R.id.toolbar)
        if(toolbar != null) {
            toolbar.setNavigationIcon(R.drawable.ic_history_white_24dp)
            toolbar.setNavigationOnClickListener {
                findNavController().navigate(
                    R.id.action_SecondFragment_to_FirstFragment
                )
            }
            toolbar.setOnMenuItemClickListener {
                onMenuItemClick(it)
            }
            //toolbar.menu.getItem(R.id.action_icon).setIcon(R.drawable.ic_delete_white_24dp)
        }

        initRv()
    }

    private fun initRv() {
        viewManager = LinearLayoutManager(activity)
        rv_history.addItemDecoration(
            DividerItemDecoration(
                activity,
                DividerItemDecoration.VERTICAL
            )
        )

        rv_history.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = gameResultsAdapter
        }
    }

    private fun removeAllGameResults() {
        mainScope.launch {
            withContext(Dispatchers.IO) {
                gameResultRepository.deleteAllGameResults()
            }
            getGameResultsFromDatabase()
        }
    }

    private fun getGameResultsFromDatabase() {
        mainScope.launch {
            val gameResultslist = withContext(Dispatchers.IO) {
                gameResultRepository.getAllGameResults()
            }
            this@HistoryFragment.gameResultsList.clear()
            this@HistoryFragment.gameResultsList.addAll(gameResultslist)
            this@HistoryFragment.gameResultsAdapter.notifyDataSetChanged()
        }
    }

    private fun onMenuItemClick(menuItem: MenuItem): Boolean {
        if(menuItem.itemId == R.id.action_icon)
        {
            removeAllGameResults()
            return true
        }
        return false
    }
}