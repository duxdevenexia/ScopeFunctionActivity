package edu.temple.scopefunctionactivity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var testAry = getTestDataArray()
        var testAryDoubles = testAry.map { it.toDouble() } // Convert each Int to Double

        if (averageLessThanMedian(testAryDoubles)) {
            // set up the list View just created in activity_main
            val listView: ListView = findViewById(R.id.listView)
            listView.adapter = MyAdapter(this, testAry) // call MyAdapter()
        }

        // test two array works
        Log.d("function output", testAry.toString())
        Log.d("function output", testAryDoubles.toString())



        // show boolean result by calling averageLessThanMedian()
        Log.d("Average < Median output", averageLessThanMedian(testAryDoubles).toString())

        var key = true

        // do ... while loop until averageLessThanMedian(testAryDoubles) return true
        do {
            if (averageLessThanMedian(testAryDoubles)) {
                key = false
                // set up the list View just created in activity_main
                val listView: ListView = findViewById(R.id.listView)
                listView.adapter = MyAdapter(this, testAry) // call MyAdapter()

            }
            Log.d("function output", testAry.toString())
            Log.d("function output", testAryDoubles.toString())

            // show boolean result by calling averageLessThanMedian()
            Log.d("Average < Median output", averageLessThanMedian(testAryDoubles).toString())

            // renew two array
            testAry = getTestDataArray()
            testAryDoubles = testAry.map { it.toDouble() } // Convert each Int to Double
        } while(key)


        // You can test your helper functions by  calling them from onCreate() and
        // printing their output to the Log, which is visible in the LogCat:
        // eg. Log.d("function output", getTestDataArray().toString())

    }


    /* Convert all the helper functions below to Single-Expression Functions using Scope Functions */
    // eg. private fun getTestDataArray() = ...

    // HINT when constructing elaborate scope functions:
    // Look at the final/return value and build the function "working backwards"

    // Return a list of random, sorted integers
    /*
    private fun getTestDataArray() : List<Int> {
        val testArray = MutableList(10){ Random.nextInt()}
        testArray.sort()
        return testArray
    }

     */

    private fun getTestDataArray(): List<Int> = MutableList(15) { Random.nextInt() }.apply {
        sort()
        }


    // Return true if average value in list is greater than median value, false otherwise
    /*
    private fun averageLessThanMedian(listOfNumbers: List<Double>): Boolean {
        val avg = listOfNumbers.average()
        val sortedList = listOfNumbers.sorted()
        val median = if (sortedList.size % 2 == 0)
            (sortedList[sortedList.size / 2] + sortedList[(sortedList.size - 1) / 2]) / 2
        else
            sortedList[sortedList.size / 2]

        return avg < median
    }

     */

    private fun averageLessThanMedian(listOfNumbers: List<Double>): Boolean {
        return listOfNumbers.let {
            val avg = it.average()
            val sortedList = it.sorted()
            val median = if (sortedList.size % 2 == 0)
                (sortedList[sortedList.size / 2] + sortedList[(sortedList.size - 1) / 2]) / 2
            else
                sortedList[sortedList.size / 2]

            avg < median
        }
    }


    // Create a view from an item in a collection, but recycle if possible (similar to an AdapterView's adapter)
    /*
    private fun getView(position: Int, recycledView: View?, collection: List<Int>, context: Context): View {
        val textView: TextView

        if (recycledView != null) {
            textView = recycledView as TextView
        } else {
            textView = TextView(context)
            textView.setPadding(5, 10, 10, 0)
            textView.textSize = 22f
        }

        textView.text = collection[position].toString()

        return textView
    }

     */

    // new getViews here
    private fun getView(position: Int, recycledView: View?, collection: List<Int>, context: Context): View =
        (recycledView as? TextView ?: TextView(context).apply {
            setPadding(5, 10, 10, 0)
            textSize = 22f
        }).apply {
            text = collection[position].toString()
        }

    // set a adapter as inner class
    private inner class MyAdapter(private val context: Context, private val collection: List<Int>) : BaseAdapter() {
        override fun getCount(): Int = collection.size

        override fun getItem(position: Int): Any = collection[position]

        override fun getItemId(position: Int): Long = position.toLong()

        override fun getView(position: Int, recycledView: View?, parent: ViewGroup?): View {
            return getView(position, recycledView, collection, context)
        }
    }

}