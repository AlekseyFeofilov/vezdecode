package com.example.passwordcheck;


import android.content.Context;

interface StoryView {

    /**
     * Возвращает [Context] вьюшки
     */
    fun getContext(): Context

    /**
     * [StoriesActivity] этой вьюшки
     */
    val activity: StoriesActivity
        get() = getContext() as StoriesActivity
}