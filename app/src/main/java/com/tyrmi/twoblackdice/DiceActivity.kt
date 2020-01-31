package com.tyrmi.twoblackdice

import android.animation.ValueAnimator
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_dice.*
import kotlin.random.Random


class DiceActivity : AppCompatActivity() {
    val screenTimeoutHandler = Handler()
    val animationHandler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dice)

        // TODO: USe multiple XML to make it more sturdy
        // Get selected number of dice
        if (intent.getIntExtra("num", 2) == 2) {
            // Show second dice
            secondDie.visibility = View.VISIBLE
        } else {
            secondDie.visibility = View.GONE
        }

        // Setup animations "flash"
        val anim1 = ValueAnimator.ofArgb(Color.TRANSPARENT, Color.WHITE)
        anim1.addUpdateListener { valueAnimator -> flasher.setBackgroundColor(valueAnimator.animatedValue as Int) }
        anim1.duration = 30

        val anim2 = ValueAnimator.ofArgb(Color.WHITE, Color.TRANSPARENT)
        anim2.addUpdateListener { valueAnimator -> flasher.setBackgroundColor(valueAnimator.animatedValue as Int) }
        anim2.duration = 400

        val fadein = AnimationUtils.loadAnimation(this, R.anim.fadein)

        val postponeScreensaver = Runnable {
            // Screen on not forever
            window.clearFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }

        val showDiceAfterUpdate = Runnable {
            // Update dice
            firstDie.setImageResource(getNewRollImageId())

            if (secondDie.isVisible) {
                secondDie.setImageResource(getNewRollImageId())
            }
        }

        val makeClickableAfterDelay = Runnable {
            diceScreen.isClickable = true
        }

        // Onclick roll dice and update view
        diceScreen.setOnClickListener {

            // Temporarily disable button if animation runs
            diceScreen.isClickable = false

            animationHandler.postDelayed(showDiceAfterUpdate, 42)
            animationHandler.postDelayed(makeClickableAfterDelay, 501)

            // Play sound
            // Preferably 1/2 second long, so the player has a cue for when the dice is useable again?

            // Run animations
            anim1.start()
            anim2.startDelay = 300
            anim2.start()

            // Set screen always on and set new countdown
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
            screenTimeoutHandler.removeCallbacks(postponeScreensaver)
            screenTimeoutHandler.postDelayed(postponeScreensaver, 180000) // 3 min
        }

    }

    // Returns an image ID based on a roll
    fun getNewRollImageId(): Int {
        when (roll()) {
            1 -> return R.drawable.one
            2 -> return R.drawable.two
            3 -> return R.drawable.three
            4 -> return R.drawable.four
            5 -> return R.drawable.five
            6 -> return R.drawable.six
            else -> Toast.makeText(this, "Error, please write a review.", Toast.LENGTH_LONG).show()
        }
        return R.drawable.twodice
    }

    fun roll(): Int {
        return Random.nextInt(1, 7)
    }

    // Overridden to make animated transition back to menu
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)

        AnimationUtils.loadAnimation(applicationContext, R.anim.fadein)
    }

}
