package com.tyrmi.twoblackdice

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import kotlinx.android.synthetic.main.activity_menu.*

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        // Menu
        topDieSelect.setOnClickListener {

            val intent = Intent(this, DiceActivity::class.java).apply {
                putExtra("num", 1)
            }

            val bundle = ActivityOptionsCompat.makeCustomAnimation(
                applicationContext,
                android.R.anim.fade_in, android.R.anim.fade_out
            ).toBundle()
            startActivity(intent, bundle)

        }

        bottomDieSelect.setOnClickListener {

            val intent = Intent(this, DiceActivity::class.java).apply {
                putExtra("num", 2)
            }

            val bundle = ActivityOptionsCompat.makeCustomAnimation(
                applicationContext,
                android.R.anim.fade_in, android.R.anim.fade_out
            ).toBundle()
            startActivity(intent, bundle)

        }
    }
}
