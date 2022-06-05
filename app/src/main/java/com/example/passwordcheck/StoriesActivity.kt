package com.example.passwordcheck

import ViewPagerAdapter
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.passwordcheck.databinding.ActivityStoriesBinding
import jp.wasabeef.blurry.Blurry
import java.sql.Date

class StoriesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStoriesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stories)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_stories)
        binding.viewPager2.adapter = ViewPagerAdapter(this)

        binding.passwordResetImageView.setOnClickListener {
            val mPrefs = getSharedPreferences("data", 0)
            mPrefs.edit().clear().apply()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    //по таймеру
    private fun changeStoryOnTimer()
    {

    }

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