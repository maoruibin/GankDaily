package com.gudong.gankio.data.entity;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.NotNull;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Unique;

/**
 * Created by GuDong on 15/10/8.
 * Contact with 1252768410@qq.com
 */
public class Soul {
    @PrimaryKey(PrimaryKey.AssignType.AUTO_INCREMENT)
    @Column("_id")
    protected long id;

    @NotNull
    @Unique
    @Column("objectId")
    public String objectId;
}
