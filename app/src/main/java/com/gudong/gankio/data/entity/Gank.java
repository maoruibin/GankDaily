package com.gudong.gankio.data.entity;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.Table;

import java.util.Date;

/**
 * Created by GuDong on 15/10/8.
 * Contact with 1252768410@qq.com
 */
@Table("ganks")public class Gank extends Soul{

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
}
