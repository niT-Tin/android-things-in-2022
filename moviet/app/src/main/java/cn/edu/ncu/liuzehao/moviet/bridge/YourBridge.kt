package cn.edu.ncu.liuzehao.moviet.bridge

interface YourBridge<P, CallBack> {
    // 返回上一个页面
    fun onBack(p: P?)
    // 传递参数Flutter或者ReactNative转跳到对应页面
    fun goToNative(p: P)
    // Flutter或者ReactNative获取Header信息
    fun getHeaderParams(callBack: CallBack)
}