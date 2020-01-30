package com.tyrmi.twoblackdice

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_menu.*

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        // Menu

        topDieSelect.setOnClickListener {

            Toast.makeText(applicationContext, "ONE", Toast.LENGTH_LONG).show()

            val intent = Intent(this, DiceActivity::class.java).apply {
                putExtra("num", 1)
            }

            startActivity(intent)

        }

        bottomDieSelect.setOnClickListener {

            Toast.makeText(applicationContext, "TWO", Toast.LENGTH_LONG).show()

            val intent = Intent(this, DiceActivity::class.java).apply {
                putExtra("num", 2)
            }

            startActivity(intent)
        }
    }
}
