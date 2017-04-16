# GankDaily
![icon](/app/src/main/res/mipmap-xxxhdpi/ic_launcher.png "")

每日提供技术干货的 App

## 下载
[fir](http://fir.im/gankdaily) 

[酷安](http://www.coolapk.com/apk/com.gudong.gankio) 

<a href="https://play.google.com/store/apps/details?id=com.gudong.gankio" target="_blank"><img src="http://www.android.com/images/brand/get_it_on_play_logo_large.png"/></a>

## 干货数据源
应用中的所有干货数据均来自[干货集中营](http://gank.io/)。

` 干货集中营是一个技术干货共享平台。每个工作日，网站都会分享一些技术干货信息，包括Android、iOS、App等技术干货，除此之外还有福利和休息视频可供你享用。
如果你是移动开发者，这个App一定很适合你。当然，如果你是一个宅男，也许你会对每天的福利更感兴趣。Enjoy it。`

## App设计
整个项目借鉴自 @drakeet 的[妹纸](https://github.com/drakeet/Meizhi)。但是在原项目基础上，
自己在代码层面和UI层面上做了自己的修改。

### UI设计
放弃使用瀑布式的妹子流做主页，转而让首页展示当天干货信息，更加注重干货内容(Content)的呈现，
如下所示，相比原妹纸项目，现在首屏只有一张`福利`图,如果觉得看的不过瘾，应用也提供了纯妹子视图入口，点击首页
右上角按钮，选择纯妹子，就可以看到所有的妹子们了，更加纯粹的妹子流。

![gank_daily_introduce](/art/gank_introduce.png "")

### 代码设计
原项目使用 Retrofit+RxJava 的组合获取网络数据，再加上使用Lambda表达式，代码已经变得很精简，所以把获取数据的代码放在 Activity 中也不显得混乱，
但是作为一个有追求的程序员，既然已经知道有 MVP 这种架构模式可以解决项目分层的问题，
那为什么不试着使用 MVP 去对代码进行进一步的优化呢，所以整个项目使用了 MVP 架构，将所有的数据请求、业务逻辑都提取到 Presenter 层中。
使用 MVP 后需要为每一个 Activity 编写一个对应一个 Presenter(控制器)类，让 Activity 担当纯粹的 View 角色，
这样 Activity 就主要负责一些界面更新操作，具体的业务逻辑都交托给 Presenter 层处理，代码结构因此也变得更加清晰。

更多关于项目中对 MVP 模式的实践，这里自己整理了一篇博客进行详细介绍。

[MVP 模式在 GankDaily 中的应用](http://gudong.name/advanced/2015/11/23/gank_mvp_introduce.html)
   
## 依赖库   
* [RxJava](https://github.com/ReactiveX/RxJava) 
* [OkHttp](https://github.com/square/okhttp)
* [Picasso](https://github.com/square/picasso)
* [Retrofit](https://github.com/square/retrofit)
* [Butterknife](https://github.com/JakeWharton/butterknife)
* [Logger](https://github.com/orhanobut/logger)

## 参考资料
* [drakeet/Meizhi](https://github.com/drakeet/Meizhi)
* [浅谈 MVP in Android](http://blog.csdn.net/lmj623565791/article/details/46596109)
* [Effective Android UI](https://github.com/pedrovgs/EffectiveAndroidUI)

## Author
* [http://gudong.name](http://gudong.name)
* [https://github.com/maoruibin](https://github.com/maoruibin)
* [http://weibo.com/maoruibin](http://weibo.com/maoruibin)

## License

    /*
     *       
     * Copyright (C) 2015 Drakeet <drakeet.me@gmail.com>
     * Copyright (C) 2015 GuDong <gudong.name@gmail.com>
     *
     * Meizhi is free software: you can redistribute it and/or modify
     * it under the terms of the GNU General Public License as published by
     * the Free Software Foundation, either version 3 of the License, or
     * (at your option) any later version.
     *
     * Meizhi is distributed in the hope that it will be useful,
     * but WITHOUT ANY WARRANTY; without even the implied warranty of
     * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
     * GNU General Public License for more details.
     *
     * You should have received a copy of the GNU General Public License
     * along with Meizhi.  If not, see <http://www.gnu.org/licenses/>.
     */

