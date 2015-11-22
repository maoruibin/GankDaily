package com.gudong.gankio.data.entity;

import com.gudong.gankio.core.GankCategory;
import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.Table;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by GuDong on 15/10/8.
 * Contact with 1252768410@qq.com
 */
@Table("ganks")public class Gank extends Soul implements Cloneable,Serializable{

    /**
     * createdAt : 2015-10-06T08:23:35.565Z
     * publishedAt : 2015-10-08T01:29:48.811Z
     * used : true
     * type : 休息视频
     * url : http://v.youku.com/v_show/id_XMTY4OTE3OTQ4.html
     * who : lxxself
     * desc : 耐克还有这广告，我良辰服气
     * updatedAt : 2015-10-08T01:29:49.219Z
     */

    @Column("used") public boolean used;
    @Column("type") public String type;
    @Column("url") public String url;
    @Column("who") public String who;
    @Column("desc") public String desc;
    @Column("updatedAt") public Date updatedAt;
    @Column("createdAt") public Date createdAt;
    @Column("publishedAt") public Date publishedAt;

    /**
     * this item is header type of gank or not,if true, this item will show category name
     */
    public boolean isHeader;

    public boolean is妹子(){
        return type.equals(GankCategory.福利.name());
    }

    @Override
    public Gank clone() {
        Gank gank = null;
        try{
            gank = (Gank)super.clone();
        }catch(CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return gank;
    }
}
