package com.yny.datayes_native_demo

import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.FragmentActivity

class TestActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(TextView(this).apply {
            this.text = "Flutter跳转"
        })
    }
}