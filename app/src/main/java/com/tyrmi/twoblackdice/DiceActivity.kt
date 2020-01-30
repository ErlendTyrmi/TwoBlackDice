package com.tyrmi.twoblackdice

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_dice.*
import kotlin.random.Random

class DiceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dice)

        if (intent.getIntExtra("num", 2) == 2) {
            // Show second dice
            secondDie.visibility = View.VISIBLE
        } else {
            secondDie.visibility = View.GONE
        }

        // fade in dice?


        // Onclick roll dice and update view
        diceScreen.setOnClickListener {

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
}
