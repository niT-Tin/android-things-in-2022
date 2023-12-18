## 保存一些我在2022年上Android写的无聊玩意

> 用到了flutter, sdk版本很重要，在`pubspec.yaml`里面有写，但是我自己好像用的是stable channel的3.3.7(Flutter版本),2.18.4(Dart版本)

### 骰子

我记得我在写这个的时候我用的是emacs,哈哈哈哈😀。当时刚好想要尝试更多的好玩的编辑器。(那个时候已经使用vim了)

![骰子gif](./images/flutter_dice.gif)

---

### 原生 与 Flutter 通信

我记得我当时就是很不喜欢用原来的xml来修改布局，就是喜欢声明式UI,所以就直接用Jetpack Compose和Flutter,乃至ReactNative了。在我记得原生和Flutter通信是成功了，ReactNative好像没有。但是最后期末大作业我是用的ReactNative。

我记得上课当时秉持的原则是一个作业一个技术哈哈哈哈哈(准确的说是UI架子，在上课之前使用原始的xml, 在上课开始的时候使用Jetpack Compose, 在上课中，也就是期中左右使用Flutter(中间穿插这三个UI框架同原生的通信),在期末使用ReactNative。

虽然说当时觉得没啥用，单纯觉得好玩。但是没先到在半年之后吧，我开始阅读RustDesk的源代码。它的Android端UI使用Flutter搭建的，我在看到相关Channel通信的时候还是会心一笑。(当然其中还有一些技术也是以前接触，比如一些rust binding或者共享内存存储x11捕获的图片，以及NAT类型检测[我记得它只区分了对称型NAT和其他NAT，至于其他NAT类型的细分就没了])

> 这个主要注意kotlin的版本以及相关依赖的版本

![原生channel通信gif](./images/android_channel.gif)
