import 'package:android_lab_dice/dice.dart';
import 'package:flutter/material.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Dice Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: const MyHomePage(
        title: "嘛，哎哟～",
      ),
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
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: Container(
        decoration: const BoxDecoration(
          // 添加背景渐变
          gradient: LinearGradient(
              begin: Alignment.topLeft,
              end: Alignment(0.7, 1),
              colors: [
                // Color.fromRGBO(251, 171, 126, 1),
                // Color.fromRGBO(247, 206, 104, 1)
                Color.fromRGBO(98, 132, 255, 1),
                Color.fromARGB(255, 250, 41, 41),
              ],
              tileMode: TileMode.mirror),
        ),
        child: const Dices(),
      ),
    );
  }
}
