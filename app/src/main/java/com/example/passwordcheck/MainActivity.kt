package com.example.passwordcheck

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.appcompat.app.AppCompatActivity
import com.example.passwordcheck.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mPrefs: SharedPreferences
    private var currentPassword = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mPrefs = getSharedPreferences("data", 0)

        if (mPrefs.getString("password", "not_found") != "not_found") {
            setEnterMode()
        } else {
            setSubmitMode()
        }

        binding.buttonBack.setOnClickListener {
            if (currentPassword.isNotEmpty()) {
                currentPassword = currentPassword.dropLast(1)
                binding.textView.text = currentPassword
            }
        }

        binding.buttons.referencedIds.forEach { id ->
            val button = findViewById<Button>(id)

            button.setOnClickListener {
                if (currentPassword.length < 4) {
                    currentPassword += button.text
                    binding.textView.text = currentPassword
                }
            }
        }
    }

    private fun setEnterMode() {
        binding.submitOrEnterButton.text = getString(R.string.enter)

        binding.submitOrEnterButton.setOnClickListener {
            if (currentPassword == mPrefs.getString("password", "not_found")) {
                startActivity(Intent(this, StoriesActivity::class.java))
            } else {
                binding.textView.text = getString(R.string.invalid)
            }
        }
    }

    private fun setSubmitMode() {
        binding.submitOrEnterButton.text = getString(R.string.submit)

        binding.submitOrEnterButton.setOnClickListener {
            when {
                currentPassword.length < 4 -> binding.textView.text =
                    resources.getString(R.string.show_incorrect)

                mPrefs.getString("password", "not_found") == "not_found" -> {
                    mPrefs.edit().putString("password", currentPassword).apply()
                    binding.textView.text = getString(R.string.confirm)
                    currentPassword = ""
                }

                currentPassword == mPrefs.getString("password", "not_found") -> {
                    startActivity(Intent(this, StoriesActivity::class.java))
                }

                else -> {
                    binding.textView.text = getString(R.string.failConfirm)
                    mPrefs.edit().clear().apply()
                    currentPassword = ""
                }
            }
        }
    }
}