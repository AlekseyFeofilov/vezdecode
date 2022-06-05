package com.example.passwordcheck

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.passwordcheck.databinding.ActivityStoriesBinding

class StoriesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStoriesBinding

    var numberStory = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stories)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_stories)
        //Blurry.with(this).radius(25).sampling(2).onto(binding.layout)

//        binding.passwordResetImageView.setOnClickListener{
//            val mPrefs = getSharedPreferences("data", 0)
//            mPrefs.edit().clear().apply()
//            startActivity(Intent(this, MainActivity::class.java))
//        }


    }


//    @SuppressLint("UseCompatLoadingForDrawables")
//    private fun addRectForStory() {
//        val story = ImageView(this)
//
//        story.setImageDrawable(resources.getDrawable(R.drawable.rectangle))
//
//        if (numberStory == 1) {
//           params = LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.WRAP_CONTENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT
//            ).apply {
//                marginEnd = 4
//            }
//        }
//
//
//        story.layoutParams = params
//    }
}