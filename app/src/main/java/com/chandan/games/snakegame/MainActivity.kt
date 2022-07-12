package com.chandan.games.snakegame

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

var NO_OF_ROWS = 0
var NO_OF_COLUMNS = 0
class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var NO_OF_NODES = 0

    private var nodeList = arrayListOf<Node>()
    private lateinit var rvNodes : RecyclerView
    private lateinit var adapter : Adapter
    private val directionList = Direction.values()
    private val directionListSize = directionList.size

    private lateinit var head : Node
    private lateinit var tail : Node

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<View>(R.id.mainlayoutView).setOnClickListener(this)
        NO_OF_ROWS = calculateNoOfRows(this, 15F)
        NO_OF_COLUMNS = calculateNoOfColumns(this, 15F)
        NO_OF_NODES = NO_OF_ROWS * NO_OF_COLUMNS
        repeat(NO_OF_NODES) { nodeList.add(Node())}
        val randomPos = Random().nextInt(NO_OF_NODES)
        nodeList[randomPos].isON = false
        head = nodeList[randomPos]
        head.index = randomPos
        head.direction = Direction.RIGHT
        tail = head
        setAdapter()
    }

    override fun onClick(p0: View?) {
        synchronized(head) {
            head.direction = directionList[(head.direction!!.ordinal + 1) % directionListSize]
        }
    }

    override fun onResume() {
        super.onResume()
        resumeGame()
    }

    private fun resumeGame() {
        /*val task = object : TimerTask() {
            override fun run() {
                val newHeadPos = head.nextIndex()
                head.isON = false
                nodeList[newHeadPos].isON = true
                Handler(Looper.getMainLooper()).post {
                    adapter.notifyItemChanged(head.index)
                    adapter.notifyItemChanged(newHeadPos)
                }
            }
        }
        val timer = Timer()
        timer.scheduleAtFixedRate(task, 5000, 1000)*/
        object : CountDownTimer(1000000, 50) {
            override fun onTick(millisUntilFinished: Long) {
                Log.i("Snake", millisUntilFinished.toString())
                synchronized(head) {
                    val oldHeadPos = head.index
                    val newHeadPos = head.nextIndex()
                    head.isON = false
                    nodeList[newHeadPos].isON = true
                    nodeList[newHeadPos].index = newHeadPos
                    nodeList[newHeadPos].direction = head.direction
                    head = nodeList[newHeadPos]
                    adapter.notifyItemChanged(oldHeadPos)
                    adapter.notifyItemChanged(newHeadPos)
                }
            }

            override fun onFinish() {
                //resumeGame()
            }
        }.start()
    }

    private fun setAdapter() {
        rvNodes = findViewById(R.id.rv_nodes)
        //rvNodes.setOnClickListener(this)
        rvNodes.layoutManager = GridLayoutManager(this, NO_OF_COLUMNS,
            LinearLayoutManager.VERTICAL, false)
        //rvNodes.isScrollbarFadingEnabled = false
        rvNodes.isHorizontalScrollBarEnabled = false
        rvNodes.isVerticalScrollBarEnabled = false
        adapter = Adapter(nodeList)
        rvNodes.adapter = adapter
        //adapter.notifyDataSetChanged()
    }

    private fun calculateNoOfColumns(
        context: Context,
        columnWidthDp: Float
    ): Int { // For example columnWidthdp=180
        val displayMetrics: DisplayMetrics = context.resources.displayMetrics
        val screenWidthDp = displayMetrics.widthPixels / displayMetrics.density
        return (screenWidthDp / columnWidthDp + 0.5).toInt() // +0.5 for correct rounding to int.
    }

    private fun calculateNoOfRows(
        context: Context,
        rowHeightDp: Float
    ): Int { // For example columnWidthdp=180
        val displayMetrics: DisplayMetrics = context.resources.displayMetrics
        val screenHeightDp = displayMetrics.heightPixels / displayMetrics.density
        return (screenHeightDp / rowHeightDp + 0.5).toInt() // +0.5 for correct rounding to int.
    }
}