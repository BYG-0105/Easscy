package com.example.society.database;

import java.text.SimpleDateFormat;
import java.util.Date;

//存放数据库相关信息
public class DBinformation {
    public static final String DATABASE_NAME = "Society";
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
    public static final String USERS_IMGPATH = "imgpath";


    public static final String DATABASE_EASSYTABLE = "Eassy";
    public static final String EASSY_ID = "id";
    public static final String EASSY_AUTHORNAME = "authorname";
    public static final String EASSY_IMGPATH = "imgpath";
    public static final String EASSY_CONTENT = "content";
    public static final String EASSY_TIME = "time";


    public static final String DATABASE_FORUMTABLE = "Forum";
    public static final String FORUM_ID = "id";
    public static final String FORUM_NAME = "forumname";
    public static final String FORUM_AUTHOR = "forumauthor";
    public static final String FORUM_IMGPATH = "forumimgpath";
    public static final String FORUM_CONTENT = "content";
    public static final String FORUM_TIME = "time";

    public static final String DATABASE_FILETABLE = "File";
    public static final String FILE_ID = "id";
    public static final String FILE_NAME = "name";
    public static final String FILE_AUTHOR = "author";
    public static final String FILE_PATH = "path";
    public static final String FILE_TIME = "time";

    public static final String DATABASE_COMMENTTABLE = "Comment";
    public static final String COMMENT_ID = "id";
    public static final String COMMENT_FORUMNAME = "Forum";
    public static final String COMMENT_AUTHORNAME = "Authorname";
    public static final String COMMENT_CONTENT = "content";
    public static final String COMMENT_TIME = "time";

    public static final String DATABASE_PROGRAMMTABLE = "Program";
    public static final String PROGRAMM_ID = "id";
    public static final String PROGRAMM_PRONAME = "Programname";
    public static final String PROGRAMM_AUTHORNAME = "Authorname";
    public static final String PROGRAMM_CONTENT = "content";
    public static final String PROGRAMM_TIME = "time";

    public static final String getTime()
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }
}
