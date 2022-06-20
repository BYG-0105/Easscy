package com.example.society.database;

import java.text.SimpleDateFormat;
import java.util.Date;

//存放数据库相关信息
public class DBinformation {
    public static final String DATABASE_NAME = "PotPlayer";
    public static final String DATABASE_LOGINTABLE = "users";
    public static final int DATABASE_VERION = 1;


    public static final String USERS_ID = "id";
    public static final String USERS_NAME = "username";
    public static final String USERS_PWD = "userpwd";
    public static final String USERS_CITY = "usercity";
    public static final String USERS_AGE = "userage";
    public static final String USERS_GENDER = "usergender";
    public static final String USERS_STYLE = "userstyle";
    public static final String USERS_NUM = "usernum";


    public static final String DATABASE_ORDERTABLE = "Orders";
    public static final String ORDER_ID = "id";
    public static final String ORDER_CONTENT = "content";
    public static final String ORDER_TIME = "time";

    public static final String getTime()
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }
}
