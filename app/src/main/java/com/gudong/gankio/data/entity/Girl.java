package com.gudong.gankio.data.entity;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.Table;

import java.util.Date;

/**
 * Created by GuDong on 15/10/8.
 * Contact with 1252768410@qq.com
 */
@Table("girls")public class Girl extends Soul {

    /**
     * createdAt : 2015-10-07T05:42:23.910Z
     * publishedAt : 2015-10-08T01:29:48.812Z
     * used : true
     * type : 福利
     * url : http://ww4.sinaimg.cn/large/7a8aed7bgw1ewsip9thfoj20hs0qo774.jpg
     * who : 张涵宇
     * desc : 10.8
     * updatedAt : 2015-10-08T01:29:49.400Z
     */

    @Column("used") public boolean used;
    @Column("type") public String type;
    @Column("url") public String url;
    @Column("who") public String who;
    @Column("desc") public String desc;
    @Column("createdAt") public Date createdAt;
    @Column("publishedAt") public Date publishedAt;
    @Column("updatedAt") public Date updatedAt;
}
