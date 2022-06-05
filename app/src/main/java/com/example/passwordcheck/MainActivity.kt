package com.example.passwordcheck

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.appcompat.app.AppCompatActivity
import com.example.passwordcheck.databinding.ActivityMainBinding
import kotlin.math.abs
import kotlin.reflect.KFunction

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mPrefs: SharedPreferences

    private var currentPassword = ""
    private val passwordLength = 4
    private val longHoverDelay: Long = 500

    private val cursor = object {
        var buttonHover: Button? = null
        var previousTime = Long.MAX_VALUE
        var active = false
        var x_pos = 0f
        var y_pos = 0f
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        mPrefs = getSharedPreferences("data", 0)
        val enter: KFunction<Unit>

        if (mPrefs.getString("password", "not_found") != "not_found") {
            binding.descriptionTextView.text = getString(R.string.enter)
            enter = ::check
        } else {
            binding.descriptionTextView.text = getString(R.string.enter_new_password)
            enter = ::submit
        }

        binding.buttons.referencedIds.forEach { id ->
            val button = findViewById<Button>(id)

            button.setOnClickListener {
                currentPassword += button.text
                activateIndicator(currentPassword.length)

                if (currentPassword.length == passwordLength) {
                    enter()
                }
            }
        }

        binding.layout.setOnTouchListener { _, motionEvent ->
            cursor.x_pos = motionEvent.x
            cursor.y_pos = motionEvent.y

            when (motionEvent.action) {
                MotionEvent.ACTION_MOVE -> {
                    binding.buttons.referencedIds.forEach { id ->
                        val button = findViewById<Button>(id)

                        when {
                            cursor.buttonHover == null && !triggerOnButton(button) -> return@forEach
                            cursor.buttonHover == null && triggerOnButton(button) -> {
                                cursor.buttonHover = button
                                cursor.previousTime = System.currentTimeMillis()
                            }
                            triggerOnButton(cursor.buttonHover!!) -> return@forEach
                            System.currentTimeMillis() - cursor.previousTime < longHoverDelay -> {
                                cursor.buttonHover = null
                            }
                            else -> {
                                cursor.buttonHover!!.performClick()
                                cursor.buttonHover = null
                            }
                        }
                    }

                    true
                }

                MotionEvent.ACTION_DOWN -> {
                    cursor.active = false
                    true
                }
                else -> {
                    cursor.active = true
                    cursor.buttonHover = null
                    false
                }
            }
        }

        binding.buttonBack.setOnClickListener {
            if (currentPassword.isNotEmpty()) {
                deactivateIndicator(currentPassword.length)
                currentPassword = currentPassword.dropLast(1)
            }
        }
    }

    private fun triggerOnButton(button: Button) =
        abs(button.x + button.width / 2 - cursor.x_pos) < button.width / 2 &&
                abs(button.y + button.height / 2 - cursor.y_pos) < button.height / 2

    private fun activateIndicator(index: Int) {
        when (index) {
            1 -> binding.indicator1.setImageResource(R.drawable.active_indicator)
            2 -> binding.indicator2.setImageResource(R.drawable.active_indicator)
            3 -> binding.indicator3.setImageResource(R.drawable.active_indicator)
            4 -> binding.indicator4.setImageResource(R.drawable.active_indicator)
        }
    }

    private fun deactivateIndicator(index: Int) {
        when (index) {
            1 -> binding.indicator1.setImageResource(R.drawable.inactive_indicator)
            2 -> binding.indicator2.setImageResource(R.drawable.inactive_indicator)
            3 -> binding.indicator3.setImageResource(R.drawable.inactive_indicator)
            4 -> binding.indicator4.setImageResource(R.drawable.inactive_indicator)
        }
    }

    private fun check() {
        if (currentPassword == mPrefs.getString("password", "not_found")) {
            startActivity(Intent(this, StoriesActivity::class.java))
        } else {
            binding.descriptionTextView.text = getString(R.string.invalid)
            clearPassword()
        }
    }

    private fun clearPassword() {
        currentPassword = ""

        for (i in 1..passwordLength) {
            deactivateIndicator(i)
        }
    }

    private fun submit() {
        when {
            mPrefs.getString("password", "not_found") == "not_found" -> {
                mPrefs.edit().putString("password", currentPassword).apply()
                binding.descriptionTextView.text = getString(R.string.confirm)
                clearPassword()
            }

            currentPassword == mPrefs.getString("password", "not_found") -> {
                startActivity(Intent(this, StoriesActivity::class.java))
            }

            else -> {
                binding.descriptionTextView.text = getString(R.string.failConfirm)
                mPrefs.edit().clear().apply()
                clearPassword()
            }
        }

    }
}