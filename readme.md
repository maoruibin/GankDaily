###GankDaily

GankDaily,每日提供技术干货的App。

###干货数据源

应用中的所有干货数据均来自[干货集中营](http://gank.io/)。

` 干货集中营是一个技术干货共享平台。每个工作日，网站都会分享一些技术干货信息，包括Android、iOS、App等技术干货，除此之外还有福利和休息视频可供你享用。
如果你是移动开发者，这个App一定很适合你。当然，如果你是一个宅男，也许你会对每天的福利更感兴趣。Enjoy it。`

### App设计

整个项目借鉴自 @drakeet 的[妹纸](https://github.com/drakeet/Meizhi)。但是在原项目基础上，
自己在代码层面和UI层面上做了自己的修改。具体界面设计如下

![gank_daily_introduce](/art/gank_introduce.png "")

####UI设计

放弃使用瀑布式的妹子流做主页(这点，一些朋友可能忍不了，不要急，我只是说，放弃让它做主页...)，更加注重干货信息内容(Content)的呈现，
首页展示当天的所有干货信息，相比原妹纸项目，现在首屏只有一张`福利`图,如果觉得看的不过瘾，应用也提供了纯妹子视图入口，点击首页
右上角按钮，选择纯妹子，就可以看到所有的妹子们了，包你满意，更加纯粹的妹子流。

####代码设计

原项目使用 Retrofit+RxJava 获取网络数据，再加上使用Lambda表达式，代码已经变得很精简，所以把获取数据的代码放在 Activity 中也不显得乱，
但是作为一个有追求的程序员，既然已经知道有 MVP 这种架构模式可以解决项目解耦的问题，
那为什么不试着使用 MVP 去对代码进行进一步的优化呢，所以整个项目使用了 MVP 架构，将所有的数据请求、业务逻辑都提取到 Presenter 层中的类里面，
为每一个 Activity 编写一个对应一个 Presenter(控制器)类，让 Activity 担当纯粹的 View 角色，这样 Activity 就主要负责一些界面更新操作，
具体的业务逻辑都交托给 Presenter 层处理。代码一下子就变得整洁多了。

### 下载体验 ###

[fir下载](http://fir.im/gankdaily)<br>
<br>
![扫码下载](/art/download.png "扫码下载")
   
###依赖库   

* [RxJava](https://github.com/ReactiveX/RxJava) 
* [OkHttp](https://github.com/square/okhttp)
* [Picasso](https://github.com/square/picasso)
* [Retrofit](https://github.com/square/retrofit)
* [Butterknife](https://github.com/JakeWharton/butterknife)
* [Logger](https://github.com/orhanobut/logger)

###参考资料

* [drakeet/Meizhi](https://github.com/drakeet/Meizhi)
* [浅谈 MVP in Android](http://blog.csdn.net/lmj623565791/article/details/46596109)
* [Effective Android UI](https://github.com/pedrovgs/EffectiveAndroidUI)

###一点思考

整个项目，我都在竭尽全力的优化代码，以期让代码变得更优雅、更简洁、更容易阅读。但是因为一些业务逻辑的存在，还是会有一些让人困惑的代码，
所以代码还是不够美,后续还会继续优化。

整个项目采用 MVP 的架构模式，主要是因为自己想具体使用 MVP，感受 MVP 的魅力。
其实整个重构过程还是比较顺利的，但是最终重构完成后，对 MVP 确有了新的认识，在一些方面，
其实 MVP 表现的并不好，你会发现项目中一下子多出来好多view接口，而且他们并没有什么可复用的价值。
但是 MVP 对项目解耦有很大的益处，这一点在大项目中可以看的更清楚，对于一般的小项目，如果使用MVP的话，可能并不是最明智的选择。



