import 'package:flutter/services.dart';

class YourBridge {
  static final YourBridge _instance = YourBridge._();

  final MethodChannel _bridge  = const MethodChannel("YourBridge");
  final _listeners = {};
  YourBridge._() {
    _bridge.setMethodCallHandler((MethodCall call) {
      String method = call.method;
      if (_listeners[method] != null) {
         return _listeners[method](call);
      }
      return Future.value(null);
    });
  }

  static YourBridge getInstance() {
    return _instance;
  }

  register(String method, Function(MethodCall)callback) {
    _listeners[method] = callback;
  }

  deRegister(String method) {
    _listeners.remove(method);
  }

  goToNative(Map params) {
    print("----Before goToNative---");
    _bridge.invokeMethod("goToNative", params);
    print("----After goToNative---");
  }


  MethodChannel bridge() {
    return _bridge;
  }

}
