package com.tyrmi.twoblackdice

import android.animation.ValueAnimator
import android.graphics.Color
import android.graphics.drawable.Animatable
import android.media.MediaPlayer
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

        var mediaPlayer: MediaPlayer? = MediaPlayer.create(applicationContext, R.raw.dice)

        // Onclick roll dice and update view
        diceScreen.setOnClickListener {

            // Temporarily disable button if animation runs
            diceScreen.isClickable = false

            animationHandler.postDelayed(showDiceAfterUpdate, 120) // Before anim2 starts
            animationHandler.postDelayed(
                makeClickableAfterDelay,
                880
            ) // Not clickable until sound is done or you may lose sound

            // Play sound
            mediaPlayer?.start()

            // Run animations
            anim1.start()
            anim2.startDelay = 200
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

    override fun onStart() {
        super.onStart()

        // Temporarily disable click
        diceScreen.isClickable = false

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
            diceScreen.isClickable = true
        }
        Handler().postDelayed(showAnimation, 300)
        Handler().postDelayed(closeWecomeBox, 900)
    }

    // Overridden to make animated transition back to menu
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)

        AnimationUtils.loadAnimation(applicationContext, R.anim.fadein)
    }

}
