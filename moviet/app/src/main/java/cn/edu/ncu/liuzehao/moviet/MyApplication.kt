package cn.edu.ncu.liuzehao.moviet

import android.app.Application
import cn.edu.ncu.liuzehao.moviet.flutter.FlutterCacheManager
import com.alibaba.android.arouter.launcher.ARouter

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        ARouter.init(this)
        FlutterCacheManager.instance?.preLoad(this)
    }
}