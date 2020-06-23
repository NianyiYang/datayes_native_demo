package com.yny.datayes_native_demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import io.flutter.embedding.android.FlutterActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
    }

    fun onHomeNew(view: View) {
        startActivity(
            FlutterActivity.createDefaultIntent(this)
        )
    }

    fun onHomeCached(view: View) {
        // TODO 无法指定路由
        startActivity(
            FlutterActivity
                .withCachedEngine(Constants.FlutterEngineTag)
                .build(this)
        )
    }

    fun onMessage(view: View) {
        startActivity(
            SampleActivity.withNewEngine(SampleActivity::class.java)
                .initialRoute("/native")
                .build(this)
        )
    }

    fun onNetwork(view: View) {
        startActivity(
            SampleActivity.withNewEngine(SampleActivity::class.java)
                .initialRoute("/network")
                .build(this)
        )
    }
}