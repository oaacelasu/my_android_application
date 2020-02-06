package com.example.myandroidapplication

import  androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor
import io.flutter.plugin.common.MethodChannel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private lateinit var flutterEngine : FlutterEngine
    private lateinit var methodChannel : MethodChannel
    private var engineId: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {

            // Cache the FlutterEngine to be used by FlutterActivity.
            startActivity(
                FlutterActivity
                    .withCachedEngine(engineId.toString()).destroyEngineWithActivity(true)
                    .build(this))
        }
    }

    override fun onResume() {
        engineId += 1
        flutterEngine = FlutterEngine(this)
        flutterEngine.navigationChannel.setInitialRoute("/add_card?uid=uid12345&email=dev@paymentez.com")
        // Start executing Dart code to pre-warm the FlutterEngine.
        flutterEngine.dartExecutor.executeDartEntrypoint(
            DartExecutor.DartEntrypoint.createDefault()
        )
        methodChannel = MethodChannel(
            flutterEngine.dartExecutor.binaryMessenger,
            "paymentez"
        )
        methodChannel.setMethodCallHandler(MethodCallHandlerImpl(flutterEngine, this))
        // Cache the FlutterEngine to be used by FlutterActivity.
        FlutterEngineCache
            .getInstance()
            .put(engineId.toString(), flutterEngine)
        callToFlutter("setEnvironment", mapOf("test_mode" to "prod", "paymentez_client_app_code" to "IOS-CO-CLIENT" , "paymentez_client_app_key" to "AKKqsezFDHtanNv1G0ARyxb8DiYARE"))
        super.onResume()
    }

    private fun callToFlutter(name:String, arguments:Any) {
        methodChannel.invokeMethod(name, arguments)
    }

}
