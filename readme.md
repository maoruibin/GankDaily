###GankDaily

GankDaily,每日提供技术干货的App。

###开发初衷

为学习使用 MVP、RxJava、Retrofit 等知识点，仿照 @drakeet 的[妹纸](https://github.com/drakeet/Meizhi)，
使用 MVP 模式对项目进行重构，让视图层跟业务逻辑向分离，使代码结构更清晰。

###截图   

![index](/art/gank_index.png "")
   
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

使用 MVP 对原项目进行重构，主要是因为自己想具体使用 MVP，感受 MVP 的魅力。
其实整个重构过程还是比较顺利的，但是最终重构完成后，对 MVP 确有了新的认识，在一些方面，
其实 MVP 表现的并不好，你会发现项目中一下子多出来好多view接口，而且他们并没有什么可复用的价值。
但是MVP对项目解耦有很大的益处，这一点在大项目中可以看的更清楚，对于一般的小项目，如果使用MVP的话，并不是最明智的选择。


