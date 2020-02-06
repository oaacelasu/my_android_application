package com.example.myandroidapplication

import android.content.Context
import io.flutter.plugin.common.MethodChannel
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.annotation.NonNull
import com.kount.api.DataCollector
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodCall
import java.util.*

class MethodCallHandlerImpl(private val flutterEngine: FlutterEngine, private  val context: Context): MethodChannel.MethodCallHandler {

    override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: MethodChannel.Result) {
        when (call.method) {
            "getPlatformVersion" -> {
                result.success("Android ${android.os.Build.VERSION.RELEASE}")
            }
            "onAddCardSuccess" -> handleOnAddCardSuccess(call, result)
            "onAddCardFail" -> handleOnAddCardFail(call, result)
            "pop" -> handlePop(call, result)

            else -> {
                result.notImplemented()
            }
        }
    }

    private fun handleOnAddCardSuccess(call: MethodCall, result: MethodChannel.Result) {
        Log.wtf("SDKSDK-SUCCESS", call.arguments.toString())
        flutterEngine.navigationChannel.popRoute()
        flutterEngine.navigationChannel.popRoute()

        Toast.makeText(context,call.arguments.toString(),Toast.LENGTH_SHORT).show()

        result.success(null)
    }
    private fun handleOnAddCardFail(call: MethodCall, result: MethodChannel.Result) {
        Log.wtf("SDKSDK-FAIL", call.arguments.toString())
        flutterEngine.navigationChannel.popRoute()
        flutterEngine.navigationChannel.popRoute()

        Toast.makeText(context,call.arguments.toString(),Toast.LENGTH_SHORT).show()
        result.success(null)
    }
    private fun handlePop(call: MethodCall, result: MethodChannel.Result) {
        flutterEngine.navigationChannel.popRoute()
        flutterEngine.navigationChannel.popRoute()

        result.success(null)
    }
}
