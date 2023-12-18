package cn.edu.ncu.liuzehao.moviet.flutter

import android.content.Context
import android.os.Looper
import cn.edu.ncu.liuzehao.moviet.bridge.YourBridgeImpl
import io.flutter.FlutterInjector
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor

class FlutterCacheManager private constructor(){

    /**
     * 预加载Flutter
     *
     * @param context
     */
    fun preLoad(context: Context) {
        // 在线程空闲时执行预加载任务
       Looper.myQueue().addIdleHandler {
           initFlutterEngine(context, MODULE_NAME_FAVORITE)
           initFlutterEngine(context, MODULE_NAME_RECOMMEND)
           false
       }
    }

    /**
     * 获取预加载的Flutter引擎
     *
     * @param moduleName
     * @param context
     * @return
     */
    fun getCachedFlutterEngine(moduleName: String, context: Context?): FlutterEngine {
       var engine = FlutterEngineCache.getInstance()[moduleName]
        if (engine == null && context != null) {
            engine = initFlutterEngine(context, moduleName)
        }
        return engine!!
    }

    /**
     * 初始化Flutter引擎
     *
     */
    private fun initFlutterEngine(context: Context, moduleName: String): FlutterEngine {
        val flutterEngine = FlutterEngine(context)
        // 注册插件
        flutterEngine.dartExecutor.executeDartEntrypoint(
            DartExecutor.DartEntrypoint(FlutterInjector.instance()
                .flutterLoader().findAppBundlePath(), moduleName)
        )
        YourBridgeImpl.init(flutterEngine)
        FlutterEngineCache.getInstance().put(moduleName, flutterEngine)
        return flutterEngine
    }

    companion object {
        const val MODULE_NAME_FAVORITE = "main"
        const val MODULE_NAME_RECOMMEND = "recommend"
        @JvmStatic
        @get:Synchronized
        var instance: FlutterCacheManager? = null
        get() {
            if (field == null) {
                field = FlutterCacheManager()
            }
            return field
        }
        private set
    }
}