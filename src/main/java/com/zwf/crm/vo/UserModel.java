package com.zwf.crm.vo;

/**
 * @author Mr Zeng
 * @version 1.0
 * @date 2023-09-09 14:45
 */

public class UserModel {
//    private Integer id;
    //加密后的id
    private String userIdStr;
    private String userName;

    private String trueName;

//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }

    public String getUserIdStr() {
        return userIdStr;
    }

    public void setUserIdStr(String userIdStr) {
        this.userIdStr = userIdStr;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }
}