package com.sample.android.goals.util

import android.content.Context
import android.view.LayoutInflater

val Context.layoutInflater: LayoutInflater get() = LayoutInflater.from(this)