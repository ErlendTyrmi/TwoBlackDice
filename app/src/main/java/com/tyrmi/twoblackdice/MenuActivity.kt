package com.tyrmi.twoblackdice

import android.content.Intent
import android.graphics.drawable.Animatable
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.View.INVISIBLE
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
        try {
            boot() // possible problem with animation in oncreate
        } catch (e: Exception) {
            welcome.visibility = INVISIBLE
        }
    }


    //override fun onStart() {
    //    super.onStart()
    private fun boot() {
        // Temporarily disable click
        topDieSelect.isClickable = false
        bottomDieSelect.isClickable = false

        // Setup animation
        welcome.visibility = View.VISIBLE
        animBox.setBackgroundResource(R.drawable.diceanim)
        val logoAnimation = animBox.background

        val showAnimation = Runnable {
            if (logoAnimation is Animatable) {
                logoAnimation.start()
            }
        }
        val closeWecomeBox = Runnable {
            welcome.visibility = View.INVISIBLE
            topDieSelect.isClickable = true
            bottomDieSelect.isClickable = true
        }
        Handler().postDelayed(showAnimation, 300)
        Handler().postDelayed(closeWecomeBox, 900)
    }
}
