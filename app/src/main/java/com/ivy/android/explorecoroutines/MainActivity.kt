package com.ivy.android.explorecoroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

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

        withContext(IO){
            val job = withTimeoutOrNull(1900L) {
                val string = getResult1FromApi()
                setTextOnMainThread(string)

                val string2 = getResult2FromApi()
                setTextOnMainThread(string2)
            }

            if (job==null){
                setTextOnMainThread("Too long : Time out")
            }


        }


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
