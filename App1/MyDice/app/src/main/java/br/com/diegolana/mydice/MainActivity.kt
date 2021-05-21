package br.com.diegolana.mydice

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var image : ImageView
    private lateinit var textView : TextView
    private lateinit var button : Button
    private val listImage = listOf(R.drawable.dice1,R.drawable.dice2,R.drawable.dice3,R.drawable.dice4,R.drawable.dice5,R.drawable.dice6)
    private var contentText = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setup()
    }

    private fun setup() {
        image = findViewById(R.id.imageView)
        textView = findViewById(R.id.textView)
        button = findViewById(R.id.button)
        contentText = loadData()
        textView.text = contentText

        button.setOnClickListener{
            roll()
        }
    }

    private fun roll() {
        val diceResult = Random.nextInt(0,6)
        val timeResult = SimpleDateFormat("HH:mm:ss.SSS").format(Date())
        val lastLine = "${diceResult+1} : $timeResult \n"
        contentText += lastLine
        textView.text = contentText

        image.setImageResource(listImage[diceResult])
    }

    override fun onStop() {
        saveData(contentText)
        super.onStop()
    }

    private fun saveData(str: String) {
        val prefs: SharedPreferences = this.getSharedPreferences("shared", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putString("key", str)
        editor.apply()
        editor.commit()
    }

    private fun loadData(): String {
        val prefs: SharedPreferences = this.getSharedPreferences("shared", Context.MODE_PRIVATE)
        return prefs.getString("key", "") ?: ""
    }
}