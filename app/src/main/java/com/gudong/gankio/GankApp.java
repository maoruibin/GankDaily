package com.gudong.gankio;

import android.app.Application;
import android.content.Context;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

/**
 * Created by GuDong on 10/14/15 23:42.
 * Contact with 1252768410@qq.com.
 */
public class GankApp extends Application {
    public static Context sContext;
    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
        //只有调试模式下 才启用日志输出
        if(BuildConfig.DEBUG){
            Logger.init("Gank").hideThreadInfo().setMethodCount(0);
        }else{
            Logger.init("Gank").setLogLevel(LogLevel.NONE);
        }
    }
}
