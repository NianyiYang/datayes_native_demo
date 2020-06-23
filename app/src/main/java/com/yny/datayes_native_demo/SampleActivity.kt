package com.yny.datayes_native_demo

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.widget.Toast
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugins.GeneratedPluginRegistrant
import java.util.*
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ScheduledThreadPoolExecutor
import java.util.concurrent.TimeUnit

class SampleActivity : FlutterActivity() {

    companion object {
        const val NATIVE_TO_FLUTTER_CHANNEL: String = "com.datayes.demo/nativeToFlutter"
        const val FLUTTER_TO_NATIVE_CHANNEL: String = "com.datayes.demo/flutterToNative"

        fun withNewEngine(activityClass: Class<out FlutterActivity?>): NewMyEngineIntentBuilder {
            return NewMyEngineIntentBuilder(activityClass)
        }
    }

    // TODO 1.12版本 临时
    class NewMyEngineIntentBuilder(activityClass: Class<out FlutterActivity>) :
        NewEngineIntentBuilder(activityClass)

    private var nativeToFlutterChannel: MethodChannel? = null

    private val handler: Handler = Handler()

    private val timeService: ScheduledExecutorService = ScheduledThreadPoolExecutor(1)

    private var count = 0

    override fun onStart() {
        super.onStart()
        timeService.scheduleAtFixedRate(timerTask, 3000, 3000, TimeUnit.MILLISECONDS)
    }

    override fun onStop() {
        super.onStop()
        timeService.shutdown()
    }

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        GeneratedPluginRegistrant.registerWith(flutterEngine)

        nativeToFlutterChannel =
            MethodChannel(flutterEngine.dartExecutor.binaryMessenger, NATIVE_TO_FLUTTER_CHANNEL)

        val flutterToNativeChannel =
            MethodChannel(flutterEngine.dartExecutor.binaryMessenger, FLUTTER_TO_NATIVE_CHANNEL)

        // From Flutter
        flutterToNativeChannel.setMethodCallHandler { call, result ->
            when (call.method) {
                "jumpToNative" -> {
                    val param = call.argument<String>("param")
                    startActivity(Intent(this@SampleActivity, TestActivity::class.java))
                }
            }
        }
    }

    private val timerTask: TimerTask = object : TimerTask() {
        override fun run() {
            handler.post {
                count++
                nativeToFlutterChannel?.invokeMethod("nativeCallFlutter", "原生传给Flutter的参数$count")
            }
        }
    }

}