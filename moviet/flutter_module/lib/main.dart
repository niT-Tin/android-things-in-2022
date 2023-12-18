import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_module/core/your_bridge.dart';

void main() => runApp(const MyApp());

@pragma("vm:entry-point")
void recommend() => runApp(const Recommend());

class Recommend extends StatefulWidget {
  const Recommend({Key? key}) : super(key: key);

  @override
  State<Recommend> createState() => _RecommendState();
}

class _RecommendState extends State<Recommend> {
  @override
  void initState() {
    super.initState();
    YourBridge.getInstance().register("onRefresh", (MethodCall call) {
      // 回信
      return Future.value("Flutter Recommend received. ${call.arguments}");
    });
  }

  @override
  void dispose() {
    super.dispose();
    YourBridge.getInstance().deRegister("onRefresh");
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: "Flutter Another Entry-Point",
        theme: ThemeData(
          primarySwatch: Colors.deepPurple
        ),
        home: const Scaffold(
      body: Center(
        child: Text("Flutter Another Entry-Point"),
      ),
    ));
  }
}


class MyApp extends StatelessWidget {
  const MyApp({super.key});

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: const MyHomePage(title: 'Flutter Demo Home Page'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({super.key, required this.title});

  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  int _counter = 0;

  @override
  void initState() {
    super.initState();
    YourBridge.getInstance().register("onRefresh", (MethodCall call) {
      // 回信
      return Future.value("Flutter Main received. ${call.arguments}");
    });
  }

  @override
  void dispose() {
    super.dispose();
    YourBridge.getInstance().deRegister("onRefresh");
  }

  void _incrementCounter() {
    setState(() {
      _counter++;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            const Text(
              'You have pushed the button this many times:',
            ),
            Text(
              '$_counter',
              style: Theme.of(context).textTheme.headline4,
            ),
            OutlinedButton(
                onPressed: () {
                  YourBridge.getInstance().goToNative({
                    "action": "jump", "jump": "true"
                  });
                  SystemChannels.platform.invokeMethod<void>('SystemNavigator.pop');
                // Navigator.pop(context);
            },
                child: const Text("点击我转到 React Native 页面")),
            OutlinedButton(
                onPressed: () {
                  YourBridge.getInstance().goToNative(
                      {"action": "goToMovie", "movieId": "awef1324123413"});
                  // https://api.flutter.dev/flutter/services/SystemNavigator/pop.html
                  SystemChannels.platform.invokeMethod<void>('SystemNavigator.pop');
                },
                child: const Text("点击我转到 Jetpack Compose 页面"))
          ],
        ),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: _incrementCounter,
        tooltip: 'Increment',
        child: const Icon(Icons.add),
      ), // This trailing comma makes auto-formatting nicer for build methods.
    );
  }
}
