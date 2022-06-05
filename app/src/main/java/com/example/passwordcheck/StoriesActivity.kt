package com.example.passwordcheck

import android.content.ClipData
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.passwordcheck.databinding.ActivityStoriesBinding
import jp.wasabeef.blurry.Blurry

class StoriesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStoriesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stories)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_stories)
        //Blurry.with(this).radius(25).sampling(2).onto(binding.layout)
    }

}