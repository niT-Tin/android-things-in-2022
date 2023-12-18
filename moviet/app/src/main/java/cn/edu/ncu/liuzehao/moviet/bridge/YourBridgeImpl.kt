package cn.edu.ncu.liuzehao.moviet.bridge

import android.util.Log
import com.alibaba.android.arouter.launcher.ARouter
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel

class YourBridgeImpl: YourBridge<Any?, MethodChannel.Result>, MethodChannel.MethodCallHandler {

    private var methodChannels = mutableListOf<MethodChannel>()

    companion object {
        @JvmStatic
        var instance: YourBridgeImpl? = null
            private set
        @JvmStatic
        fun init(flutterEngine: FlutterEngine): YourBridgeImpl? {
            val methodChannel = MethodChannel(flutterEngine.dartExecutor, "YourBridge")
            if (instance == null) {
                YourBridgeImpl().also { instance = it }
            }
            methodChannel.setMethodCallHandler(instance)
            // 因为每个FlutterEngine都需要单独注册一个MethodChannel,所以使用集合将MethodChannel保存起来
            instance!!.apply { methodChannels.add(methodChannel) }
            return instance
        }
    }

    fun fire(method: String, args: Any?) {
        methodChannels.forEach {
            it.invokeMethod(method, args)
        }
    }

    fun fire(method: String, args: Any?, callBack: MethodChannel.Result) {
        methodChannels.forEach {
            it.invokeMethod(method, args, callBack)
        }
    }

    override fun onBack(p: Any?) {
        println(p)

    }

    override fun goToNative(p: Any?) {
        if (p is Map<*, *>) {
            val action: Any? = p["action"]
            if (action == "goToMovie") {
                val movieId = p["movieId"]
                print("---------onNative ingoToNative------------")
                print("movieId $movieId \n")
            }
            if (action == "jump") {
                val ac = p["jump"]
                if (ac == "true") {
                    NavigationHelper.model = true
                    Log.d("goToNative", "isToReactNative is ${NavigationHelper.model}")
                }
            }
        }
    }

    override fun getHeaderParams(callBack: MethodChannel.Result) {
        print("onHeaderParams")
        TODO("Not yet implemented")
    }

    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        print("${call.method}\n")
        when(call.method) {
            "onBack" -> onBack(call.arguments)
            "getHeaderParams" -> getHeaderParams(result)
            "goToNative" -> goToNative(call.arguments)
            else -> result.notImplemented()
        }
    }
}