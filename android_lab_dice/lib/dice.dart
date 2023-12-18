import 'dart:math';
import 'package:elegant_notification/elegant_notification.dart';
import 'package:flutter/material.dart';
import 'package:flutter/physics.dart';
import 'package:zflutter/zflutter.dart';

class Dices extends StatefulWidget {
  const Dices({super.key});

  @override
  // ignore: library_private_types_in_public_api
  _DicesState createState() => _DicesState();
}

class _DicesState extends State<Dices> with SingleTickerProviderStateMixin {
  late AnimationController animationController;
  int num = 1;
  int num2 = 1;
  // 游戏次数
  int times = 0;
  // z轴方向旋转角度
  double zRotation = 0;
  // 是否为初始状态
  bool isInitial = true;

  // ignore: non_constant_identifier_names
  Widget Base(CurvedAnimation curvedValue, CurvedAnimation firstHalf,
      CurvedAnimation secondHalf, double zoom) {
    // 手势检测
    return GestureDetector(
      onTap: () {
        if (animationController.isAnimating) {
          animationController.reset();
          isInitial = true;
        } else {
          animationController.forward(from: 0);
          random();
        }
      },
      child: Container(
        color: Colors.transparent,
        child: ZIllustration(
          zoom: 1,
          children: [
            ZPositioned(
              translate: ZVector.only(x: 100 * zoom),
              child: ZGroup(
                children: [
                  ZPositioned(
                    scale: ZVector.all(zoom),
                    rotate:
                        // 获取对应点最终需要旋转的旋转角度，结合动画插值，
                        // 为了获得更好的旋转效果,实际各个方向上多旋转360度
                    times != 0 && !isInitial ? getRotation(num2).multiplyScalar(curvedValue.value) -
                            ZVector.all((tau / 2) * (firstHalf.value)) -
                            ZVector.all((tau / 2) * (secondHalf.value))  : const ZVector.only(x: tau / 8, y: tau / 8) ,
                    child: ZPositioned(
                        rotate: ZVector.only(
                            // z轴(垂直屏幕)方向上任意旋转
                            z: -zRotation * 1.9 * (animationController.value)),
                        child: Dice(
                          zoom: zoom,
                          color: const Color.fromRGBO(0, 0, 255, 0.5),
                        )),
                  ),
                ],
              ),
            ),
            ZPositioned(
              translate: ZVector.only(x: -100 * zoom),
              child: ZGroup(
                children: [
                  ZPositioned(
                    scale: ZVector.all(zoom),
                    rotate: times != 0 && !isInitial ? getRotation(num).multiplyScalar(curvedValue.value) -
                        ZVector.all((tau / 2) * (firstHalf.value)) -
                        ZVector.all((tau / 2) * (secondHalf.value)) : const ZVector.only(x: - tau / 8, y: - tau / 8),
                    child: ZPositioned(
                        rotate: ZVector.only(
                            z: -zRotation * 2.1 * (animationController.value)),
                        child: Dice(zoom: zoom)),
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }

  late SpringSimulation simulation;
  // 两个骰子点数

  @override
  void initState() {
    super.initState();

    // 使用弹簧模拟落地反弹效果
    simulation = SpringSimulation(
      const SpringDescription(
        mass: 1,
        stiffness: 20,
        damping: 2,
      ),
      1, // 起始值
      0, // 终止值
      1, // 速度
    );

    animationController = AnimationController(
        vsync: this, duration: const Duration(milliseconds: 2000))
      ..addListener(() {
        setState(() {});
        if (animationController.isCompleted) {
          var score = num + num2;
          if (score == 2 || score == 3 || score == 12) {
            ElegantNotification.error(
                    title: const Text("你ba行a"),
                    description: Text("你不行阿老弟, 都转 $times 次了阿"))
                .show(context);
            // 输了之后，次数清零
            times = -1;
            isInitial = false;
          } else if (score == 7 || score == 11) {
            ElegantNotification.success(
                    title: const Text("嗨嘿嗨，来了哦老弟"),
                    description: times <= 3
                        ? Text("你转了 $times 次，赢了")
                        : Text("你转了 $times 次终于赢了"))
                .show(context);
            // 赢了之后，次数清零
            times = -1;
            isInitial = false;
          } else {
            ElegantNotification.info(
                    title: const Text("继续加油"),
                    description: const Text("运气不错，但还不够好"))
                .show(context);
          }
        }
      });
  }

  void random() {
    zRotation = Random().nextDouble() * tau;
    num = Random().nextInt(5) + 1;
    num2 = 6 - Random().nextInt(5);
    if (times == -1) {
      times += 2;
    } else {
      times++;
    }
    isInitial = false;
  }

  @override
  Widget build(BuildContext context) {
    final curvedValue = CurvedAnimation(
      curve: Curves.ease,
      parent: animationController,
    );
    final firstHalf = CurvedAnimation(
      curve: const Interval(0, 1),
      parent: animationController,
    );
    final secondHalf = CurvedAnimation(
      curve: const Interval(0, 0.3),
      parent: animationController,
    );

    // 通过缩放来模拟下落
    final zoom = (simulation.x(animationController.value)).abs() / 2 + 0.5;
    return Base(curvedValue, firstHalf, secondHalf, zoom);
  }

  @override
  void dispose() {
    animationController.dispose();
    super.dispose();
  }
}

// 获取每一个点数最终需要对应轴旋转的方向
ZVector getRotation(int num) {
  switch (num) {
    case 1:
      return ZVector.zero;
    case 2:
      return const ZVector.only(x: tau / 4);
    case 3:
      return const ZVector.only(y: tau / 4);
    case 4:
      return const ZVector.only(y: 3 * tau / 4);
    case 5:
      return const ZVector.only(x: 3 * tau / 4);
    case 6:
      return const ZVector.only(y: tau / 2);
  }
  throw ('num $num is not in the dice');
}

class Face extends StatelessWidget {
  final double zoom;
  final Color color;

  const Face({Key? key, this.zoom = 1, required this.color}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return ZRect(
      stroke: 50 * zoom,
      width: 50,
      height: 50,
      color: color,
    );
  }
}

class Dot extends StatelessWidget {
  const Dot({super.key});

  @override
  Widget build(BuildContext context) {
    return ZCircle(
      diameter: 15,
      stroke: 0,
      fill: true,
      color: Colors.white,
    );
  }
}

class TwoDots extends StatelessWidget {
  const TwoDots({super.key});

  @override
  Widget build(BuildContext context) {
    return ZGroup(
      sortMode: SortMode.update,
      children: const [
        ZPositioned(translate: ZVector.only(y: -20), child: Dot()),
        ZPositioned(translate: ZVector.only(y: 20), child: Dot()),
      ],
    );
  }
}

class FourDots extends StatelessWidget {
  const FourDots({super.key});

  @override
  Widget build(BuildContext context) {
    return ZGroup(
      sortMode: SortMode.update,
      children: const [
        ZPositioned(translate: ZVector.only(x: 20, y: 0), child: TwoDots()),
        ZPositioned(translate: ZVector.only(x: -20, y: 0), child: TwoDots()),
      ],
    );
  }
}

class Dice extends StatelessWidget {
  final Color color;
  final double zoom;

  // 默认缩放比为1
  // 默认颜色为红色
  const Dice(
      {Key? key,
      this.zoom = 1,
      this.color = const Color.fromRGBO(255, 10, 10, 0.5)})
      : super(key: key);

  // 绘制骰子
  @override
  Widget build(BuildContext context) {
    return ZGroup(
      children: [
        ZGroup(
          sortMode: SortMode.update,
          children: [
            // 初始化骰子六个面，因为Face(Rect设置stroke即可)存在厚度，所以只需要四个Face即可
            // z方向上(垂直屏幕)堆叠两个Face
            // y方向上(cross方向即竖直方向)堆叠两个Face
            // x方向上(main方向即水平方向)，y，z方向存在厚度，忽略
            ZPositioned(
                translate: const ZVector.only(z: -25),
                child: Face(zoom: zoom, color: color)),
            ZPositioned(
                translate: const ZVector.only(z: 25),
                child: Face(zoom: zoom, color: color)),
            ZPositioned(
                translate: const ZVector.only(y: 25),
                rotate: const ZVector.only(x: tau / 4),
                child: Face(zoom: zoom, color: color)),
            ZPositioned(
                translate: const ZVector.only(y: -25),
                rotate: const ZVector.only(x: tau / 4),
                child: Face(zoom: zoom, color: color)),
          ],
        ),
        // 一点
        const ZPositioned(translate: ZVector.only(z: 50), child: Dot()),
        // 二点
        ZPositioned(
          rotate: const ZVector.only(x: tau / 4),
          translate: const ZVector.only(y: 50),
          child: ZGroup(
            sortMode: SortMode.update,
            children: const [
              ZPositioned(translate: ZVector.only(y: -20), child: Dot()),
              ZPositioned(translate: ZVector.only(y: 20), child: Dot()),
            ],
          ),
        ),
        // 三点
        ZPositioned(
          rotate: const ZVector.only(y: tau / 4),
          translate: const ZVector.only(x: 50),
          child: ZGroup(
            sortMode: SortMode.update,
            children: const [
              Dot(),
              ZPositioned(translate: ZVector.only(x: 20, y: -20), child: Dot()),
              ZPositioned(translate: ZVector.only(x: -20, y: 20), child: Dot()),
            ],
          ),
        ),
        // 四点
        ZPositioned(
          rotate: const ZVector.only(y: tau / 4),
          translate: const ZVector.only(x: -50),
          child: ZGroup(
            sortMode: SortMode.update,
            children: const [
              ZPositioned(
                  translate: ZVector.only(x: 20, y: 0), child: TwoDots()),
              ZPositioned(
                  translate: ZVector.only(x: -20, y: 0), child: TwoDots()),
            ],
          ),
        ),

        // 五点
        ZPositioned(
          rotate: const ZVector.only(x: tau / 4),
          translate: const ZVector.only(y: -50),
          child: ZGroup(
            sortMode: SortMode.update,
            children: const [
              Dot(),
              ZPositioned(child: FourDots()),
            ],
          ),
        ),

        // 六点
        ZPositioned(
          translate: const ZVector.only(z: -50),
          child: ZGroup(
            sortMode: SortMode.update,
            children: const [
              // 将二点在z轴方向旋转90度，匹配四点间距
              ZPositioned(rotate: ZVector.only(z: tau / 4), child: TwoDots()),
              ZPositioned(child: FourDots()),
            ],
          ),
        ),
      ],
    );
  }
}
