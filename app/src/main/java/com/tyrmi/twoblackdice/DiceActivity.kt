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
import java.sql.Timestamp
import kotlin.random.Random


class DiceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dice)

        // Screen on forever
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        // TODO: USe multiple XML to make it more sturdy
        // Get selected number of dice
        if (intent.getIntExtra("num", 2) == 2) {
            // Show second dice
            secondDie.visibility = View.VISIBLE
        } else {
            secondDie.visibility = View.GONE
        }

        // Setuo animations "flash"
        val anim1 = ValueAnimator.ofArgb(Color.BLACK, Color.WHITE)
        anim1.addUpdateListener { valueAnimator -> flasher.setBackgroundColor(valueAnimator.animatedValue as Int) }
        anim1.duration = 30

        val anim2 = ValueAnimator.ofArgb(Color.WHITE, Color.BLACK)
        anim2.addUpdateListener { valueAnimator -> flasher.setBackgroundColor(valueAnimator.animatedValue as Int) }
        anim2.duration = 400

        val fadein = AnimationUtils.loadAnimation(this, R.anim.fadein)

        val makeClickableAfterDelay = Runnable {
            diceScreen.isClickable = true
        }

        // Onclick roll dice and update view
        diceScreen.setOnClickListener {
            val thisRoll = Timestamp(System.currentTimeMillis())

            // Temporarily disable button if animation runs
            diceScreen.isClickable = false
            // TODO: Make sure this is safe
            Handler().postDelayed(makeClickableAfterDelay, 1500)

            // Play sound

            // Run animations
            dice.visibility = View.INVISIBLE
            anim1.start()
            anim2.startDelay = 42
            anim2.start()
            dice.startAnimation(fadein)
            dice.visibility = View.VISIBLE

            // Update dice
            firstDie.setImageResource(getNewRollImageId())

            if (secondDie.isVisible) {
                secondDie.setImageResource(getNewRollImageId())
            }

        }

    }

    // Returns an image ID based on a roll
    fun getNewRollImageId(): Int {
        when (roll()) {
            1 -> return R.drawable.done
            2 -> return R.drawable.dtwo
            3 -> return R.drawable.dthree
            4 -> return R.drawable.dfour
            5 -> return R.drawable.dfive
            6 -> return R.drawable.dsix
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
