package com.example.passwordcheck

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.appcompat.app.AppCompatActivity
import com.example.passwordcheck.databinding.ActivityMainBinding
import kotlin.reflect.KFunction

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mPrefs: SharedPreferences
    private var currentPassword = ""
    private val passwordLength = 4

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
            binding.descriptionTextView.text = getString(R.string.submit)
            enter = ::submit
        }

        binding.buttons.referencedIds.forEach { id ->
            val button = findViewById<Button>(id)

            button.setOnClickListener {
                currentPassword += button.text
                activateIndicator(currentPassword.length)

                if(currentPassword.length == passwordLength){
                    enter()
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