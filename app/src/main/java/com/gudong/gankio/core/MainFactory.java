package com.gudong.gankio.core;

/**
 * Created by GuDong on 15/10/8.
 * Contact with 1252768410@qq.com
 */
public class MainFactory {
    /**
     * 数据主机地址
     */
    public static final String HOST = "http://gank.avosapps.com/api";

    private static GuDong mGuDong;

    protected static final Object monitor = new Object();

    public static GuDong getGuDongInstance(){
        synchronized (monitor){
            if(mGuDong==null){
                mGuDong = new MainRetrofit().getService();
            }
            return mGuDong;
        }
    }
}
