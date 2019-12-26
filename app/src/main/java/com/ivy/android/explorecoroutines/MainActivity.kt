package com.ivy.android.explorecoroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private val RESULT_1 = "Result 1"
    private val RESULT_2 = "Result 2"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            CoroutineScope(IO).launch {
                fakeApiRequest()
            }

        }
    }


    private suspend fun setTextOnMainThread(output: String) {
        withContext(Main) {
            textView.text = textView.text.toString() + "\n ${output}"
        }
    }

    private suspend fun fakeApiRequest() {

        val string = getResult1FromApi()
        setTextOnMainThread(string)

        val string2 = getResult2FromApi()
        setTextOnMainThread(string2)

    }

    private suspend fun getResult1FromApi(): String {
        logThread("getResult1FromApi")
        delay(1000)
        return RESULT_1
    }

    private suspend fun getResult2FromApi(): String {
        logThread("getResult2FromApi")
        delay(1000)
        return RESULT_2
    }

    private fun logThread(methodName: String) {
        println("Debug : ${methodName} : ${Thread.currentThread().name}")
    }
}
