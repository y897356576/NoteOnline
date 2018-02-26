package com.stone.core.model;

/**
 * Created by 石头 on 2018/2/26.
 */
public class NoteGenre {

    private String id;  //主键

    private String userId; //所属用户

    private String typeName;    //类型名称

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
