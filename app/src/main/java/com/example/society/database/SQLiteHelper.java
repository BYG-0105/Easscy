package com.example.society.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import com.example.society.Bean.Eassy;
import com.example.society.Bean.Loginuser;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {


    private static SQLiteDatabase sqLiteDatabase;
    

    //创建数据库
    public SQLiteHelper(Context context)
    {
        super(context, DBinformation.DATABASE_NAME,null,DBinformation.DATABASE_VERION);
        sqLiteDatabase = this.getWritableDatabase();
    }


    //创建表
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table if not exists "+DBinformation.DATABASE_LOGINTABLE
                +"("+ DBinformation.USERS_ID+" integer primary key autoincrement,"
                + DBinformation.USERS_NAME+" varchar(64),"
                + DBinformation.USERS_PWD+" varchar(64),"
                + DBinformation.USERS_CITY+" varchar(64),"
                + DBinformation.USERS_AGE+" varchar(64),"
                + DBinformation.USERS_NUM+" varchar(64),"
                + DBinformation.USERS_GENDER+" varchar(64),"
                + DBinformation.USERS_IMGPATH+" varchar(255),"
                + DBinformation.USERS_STYLE+" varchar(64))");

        sqLiteDatabase.execSQL("create table if not exists "+DBinformation.DATABASE_EASSYTABLE
                +" ("+ DBinformation.EASSY_ID+" integer primary key autoincrement,"
                + DBinformation.EASSY_AUTHORNAME+" text,"
                + DBinformation.EASSY_IMGPATH+" text,"
                + DBinformation.EASSY_CONTENT+" text,"
                +DBinformation.EASSY_TIME+" text)");

        sqLiteDatabase.execSQL("create table if not exists "+DBinformation.DATABASE_FORUMTABLE
                +" ("+ DBinformation.FORUM_ID+" integer primary key autoincrement,"
                +  DBinformation.FORUM_NAME+" text,"
                +  DBinformation.FORUM_CONTENT+" text,"
                +  DBinformation.FORUM_AUTHOR+" text,"
                +  DBinformation.FORUM_IMGPATH+" text,"
                +DBinformation.FORUM_TIME+" text)");

        sqLiteDatabase.execSQL("create table if not exists "+DBinformation.DATABASE_FILETABLE
                +" ("+ DBinformation.FILE_ID+" integer primary key autoincrement,"
                +  DBinformation.FILE_NAME+" text,"
                +  DBinformation.FILE_AUTHOR+" text,"
                +  DBinformation.FILE_PATH+" text,"
                +DBinformation.FILE_TIME+" text)");

        sqLiteDatabase.execSQL("create table if not exists "+DBinformation.DATABASE_COMMENTTABLE
                +" ("+ DBinformation.COMMENT_ID+" integer primary key autoincrement,"
                +  DBinformation.COMMENT_AUTHORNAME+" text,"
                +  DBinformation.COMMENT_FORUMNAME+" text,"
                +  DBinformation.COMMENT_CONTENT+" text,"
                +  DBinformation.COMMENT_TIME+" text)");

        sqLiteDatabase.execSQL("create table if not exists "+DBinformation.DATABASE_PROGRAMMTABLE
                +" ("+ DBinformation.PROGRAMM_ID+" integer primary key autoincrement,"
                +  DBinformation.PROGRAMM_AUTHORNAME+" text,"
                +  DBinformation.PROGRAMM_PRONAME+" text,"
                +  DBinformation.PROGRAMM_CONTENT+" text,"
                +  DBinformation.PROGRAMM_TIME+" text)");
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    public static class User{
        public User() { }


        //查询
        public Loginuser userquery(String name)
        {
            Loginuser loginuser = null;
            Cursor cursor = sqLiteDatabase.query(DBinformation.DATABASE_LOGINTABLE,null,null,null,null,null,DBinformation.USERS_ID+" desc");
            if (cursor != null)
            {
                while (cursor.moveToNext())
                {

                    @SuppressLint("Range") String uname = cursor.getString(cursor.getColumnIndex(DBinformation.USERS_NAME));
                    if(name.equals(uname))
                    {
                        loginuser = new Loginuser();
                        @SuppressLint("Range") String id = String.valueOf(cursor.getInt(cursor.getColumnIndex(DBinformation.USERS_ID)));
                        @SuppressLint("Range") String pwd = cursor.getString(cursor.getColumnIndex(DBinformation.USERS_PWD));
                        @SuppressLint("Range") String num = cursor.getString(cursor.getColumnIndex(DBinformation.USERS_NUM));
                        @SuppressLint("Range") String city = cursor.getString(cursor.getColumnIndex(DBinformation.USERS_CITY));
                        @SuppressLint("Range") String gender = cursor.getString(cursor.getColumnIndex(DBinformation.USERS_GENDER));
                        @SuppressLint("Range") String style = cursor.getString(cursor.getColumnIndex(DBinformation.USERS_STYLE));
                        @SuppressLint("Range") String age = cursor.getString(cursor.getColumnIndex(DBinformation.USERS_AGE));
                        @SuppressLint("Range") String imgpath = cursor.getString(cursor.getColumnIndex(DBinformation.USERS_IMGPATH));
                        loginuser.setId(id);
                        loginuser.setName(uname);
                        loginuser.setPwd(pwd);
                        loginuser.setNum(num);
                        loginuser.setCity(city);
                        loginuser.setGender(gender);
                        loginuser.setStyle(style);
                        loginuser.setAge(age);
                        loginuser.setImgpath(imgpath);
                        break;
                    }
                }
                cursor.close();
            }
            //Log.i("pwd",loginuser.toString());
            return loginuser;
        }

        //添加数据
        public boolean insertData(String username,String userpwd,String city,String age,String gender,String style,String num,String imgpath)
        {

            ContentValues contentValues = new ContentValues();
            contentValues.put(DBinformation.USERS_NAME, username);
            contentValues.put(DBinformation.USERS_PWD, userpwd);
            contentValues.put(DBinformation.USERS_CITY, city);
            contentValues.put(DBinformation.USERS_AGE, age);
            contentValues.put(DBinformation.USERS_GENDER, gender);
            contentValues.put(DBinformation.USERS_STYLE, style);
            contentValues.put(DBinformation.USERS_NUM, num);
            contentValues.put(DBinformation.USERS_IMGPATH, imgpath);
            return sqLiteDatabase.insert(DBinformation.DATABASE_LOGINTABLE, null, contentValues) > 0;
        }

        //删除数据
        public boolean deleteData (String id)
        {
            String sql = DBinformation.USERS_ID+"=?";
            String[] contentValuesArray = new String[]{String.valueOf(id)};
            return sqLiteDatabase.delete(DBinformation.DATABASE_LOGINTABLE,sql,contentValuesArray)>0;
        }

        //修改密码
        public boolean updateData(String id,String username,String userpwd)
        {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DBinformation.USERS_NAME,username);
            contentValues.put(DBinformation.USERS_PWD,userpwd);
            String sql = DBinformation.USERS_ID+"=?";
            String[] strings = new String[]{id};
            return sqLiteDatabase.update(DBinformation.DATABASE_LOGINTABLE,contentValues,sql,strings)>0;
        }

        //修改个人信息
        public boolean updatemesData(String id,String username,String userpwd,String city,String age,String gender,String style,String num,String imgpath)
        {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DBinformation.USERS_NAME,username);
            contentValues.put(DBinformation.USERS_PWD,userpwd);
            contentValues.put(DBinformation.USERS_CITY, city);
            contentValues.put(DBinformation.USERS_AGE, age);
            contentValues.put(DBinformation.USERS_GENDER, gender);
            contentValues.put(DBinformation.USERS_STYLE, style);
            contentValues.put(DBinformation.USERS_NUM, num);
            contentValues.put(DBinformation.USERS_IMGPATH, imgpath);
            String sql = DBinformation.USERS_ID+"=?";
            String[] strings = new String[]{id};
            return sqLiteDatabase.update(DBinformation.DATABASE_LOGINTABLE,contentValues,sql,strings)>0;
        }

        //查询数据
        public List<Loginuser> query()
        {
            List<Loginuser> list = new ArrayList<Loginuser>();
            Cursor cursor = sqLiteDatabase.query(DBinformation.DATABASE_LOGINTABLE,null,null,null,null,null,DBinformation.USERS_ID+" desc");
            if (cursor != null)
            {
                while (cursor.moveToNext())
                {
                    Loginuser loginuser = new Loginuser();
                    @SuppressLint("Range") String id = String.valueOf(cursor.getInt(cursor.getColumnIndex(DBinformation.USERS_ID)));
                    @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(DBinformation.USERS_NAME));
                    @SuppressLint("Range") String pwd = cursor.getString(cursor.getColumnIndex(DBinformation.USERS_PWD));
                    @SuppressLint("Range") String num = cursor.getString(cursor.getColumnIndex(DBinformation.USERS_NUM));
                    @SuppressLint("Range") String city = cursor.getString(cursor.getColumnIndex(DBinformation.USERS_CITY));
                    @SuppressLint("Range") String gender = cursor.getString(cursor.getColumnIndex(DBinformation.USERS_GENDER));
                    @SuppressLint("Range") String style = cursor.getString(cursor.getColumnIndex(DBinformation.USERS_STYLE));
                    @SuppressLint("Range") String age = cursor.getString(cursor.getColumnIndex(DBinformation.USERS_AGE));
                    loginuser.setId(id);
                    loginuser.setName(name);
                    loginuser.setPwd(pwd);
                    loginuser.setPwd(num);
                    loginuser.setId(city);
                    loginuser.setName(gender);
                    loginuser.setPwd(style);
                    loginuser.setPwd(age);
                    list.add(loginuser);
                }
                cursor.close();
            }
            return list;
        }
    }

    public static class Comment {


        public Comment() {

        }

        //添加数据
        public boolean insertData(String forumname,String authorname,String content,String time)
        {

            ContentValues contentValues = new ContentValues();
            contentValues.put(DBinformation.COMMENT_FORUMNAME, forumname);
            contentValues.put(DBinformation.COMMENT_AUTHORNAME, authorname);
            contentValues.put(DBinformation.COMMENT_CONTENT, content);
            contentValues.put(DBinformation.COMMENT_TIME, time);
            return sqLiteDatabase.insert(DBinformation.DATABASE_COMMENTTABLE, null, contentValues) > 0;
        }

        //删除数据
        public boolean deleteData (String id)
        {
            String sql = DBinformation.COMMENT_ID+"=?";
            String[] contentValuesArray = new String[]{String.valueOf(id)};
            return sqLiteDatabase.delete(DBinformation.DATABASE_COMMENTTABLE,sql,contentValuesArray)>0;
        }

        //修改
        public boolean updateData(String id,String forumname,String authorname,String content,String time)
        {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DBinformation.COMMENT_FORUMNAME, forumname);
            contentValues.put(DBinformation.COMMENT_AUTHORNAME, authorname);
            contentValues.put(DBinformation.COMMENT_CONTENT, content);
            contentValues.put(DBinformation.COMMENT_TIME, time);
            String sql = DBinformation.COMMENT_ID+"=?";
            String[] strings = new String[]{id};
            return sqLiteDatabase.update(DBinformation.DATABASE_COMMENTTABLE,contentValues,sql,strings)>0;
        }

        //查询数据
        public List<com.example.society.Bean.Comment> query()
        {
            List<com.example.society.Bean.Comment> list = new ArrayList<com.example.society.Bean.Comment>();
            Cursor cursor = sqLiteDatabase.query(DBinformation.DATABASE_COMMENTTABLE,null,null,null,null,null,DBinformation.COMMENT_ID+" desc");
            if (cursor != null)
            {
                while (cursor.moveToNext())
                {
                    com.example.society.Bean.Comment  comment= new com.example.society.Bean.Comment();
                    @SuppressLint("Range") String id = String.valueOf(cursor.getColumnIndex(DBinformation.COMMENT_ID));
                    @SuppressLint("Range") String forumname = cursor.getString(cursor.getColumnIndex(DBinformation.COMMENT_FORUMNAME));
                    @SuppressLint("Range") String authorname = cursor.getString(cursor.getColumnIndex(DBinformation.COMMENT_AUTHORNAME));
                    @SuppressLint("Range") String content = cursor.getString(cursor.getColumnIndex(DBinformation.COMMENT_CONTENT));
                    @SuppressLint("Range") String time = cursor.getString(cursor.getColumnIndex(DBinformation.COMMENT_TIME));
                    comment.setId(id);
                    comment.setAuthorname(authorname);
                    comment.setForumname(forumname);
                    comment.setContent(content);
                    comment.setTime(time);
                    list.add(comment);
                }
                cursor.close();
            }
            return list;
        }


        //查询单个数据
        public com.example.society.Bean.Comment userquery(String name)
        {
            com.example.society.Bean.Comment comment = null;
            Cursor cursor = sqLiteDatabase.query(DBinformation.DATABASE_COMMENTTABLE,null,null,null,null,null,DBinformation.COMMENT_ID+" desc");
            if (cursor != null)
            {
                while (cursor.moveToNext())
                {

                    @SuppressLint("Range") String forumname = cursor.getString(cursor.getColumnIndex(DBinformation.COMMENT_FORUMNAME));

                    if(name.equals(forumname))
                    {
                        comment= new com.example.society.Bean.Comment();
                        @SuppressLint("Range") String id = String.valueOf(cursor.getInt(cursor.getColumnIndex(DBinformation.COMMENT_ID)));
                        @SuppressLint("Range") String authorname = cursor.getString(cursor.getColumnIndex(DBinformation.COMMENT_AUTHORNAME));
                        @SuppressLint("Range") String content = cursor.getString(cursor.getColumnIndex(DBinformation.COMMENT_CONTENT));
                        @SuppressLint("Range") String time = cursor.getString(cursor.getColumnIndex(DBinformation.COMMENT_TIME));
                        comment.setId(id);
                        comment.setAuthorname(authorname);
                        comment.setForumname(forumname);
                        comment.setContent(content);
                        comment.setTime(time);

                        break;
                    }
                }
                cursor.close();
            }
            //Log.i("pwd",loginuser.toString());
            return comment;
        }
    }



    public static class Eassy {


        public Eassy() {

        }

        //添加数据
        public boolean insertData(String author,String imgpath,String content,String time)
        {

            ContentValues contentValues = new ContentValues();
            contentValues.put(DBinformation.EASSY_AUTHORNAME, author);
            contentValues.put(DBinformation.EASSY_IMGPATH, imgpath);
            contentValues.put(DBinformation.EASSY_CONTENT, content);
            contentValues.put(DBinformation.EASSY_TIME, time);
            Log.d("Easssy： ", " " + author + "asdadad"+imgpath);
            return sqLiteDatabase.insert(DBinformation.DATABASE_EASSYTABLE, null, contentValues) > 0;
        }

        //删除数据
        public boolean deleteData (String id)
        {
            String sql = DBinformation.EASSY_ID+"=?";
            String[] contentValuesArray = new String[]{String.valueOf(id)};
            return sqLiteDatabase.delete(DBinformation.DATABASE_EASSYTABLE,sql,contentValuesArray)>0;
        }

        //修改
        public boolean updateData(String id,String author,String imgpath,String content,String time)
        {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DBinformation.EASSY_AUTHORNAME, author);
            contentValues.put(DBinformation.EASSY_IMGPATH, imgpath);
            contentValues.put(DBinformation.EASSY_CONTENT,content);
            contentValues.put(DBinformation.EASSY_TIME,time);
            String sql = DBinformation.EASSY_ID+"=?";
            String[] strings = new String[]{id};
            return sqLiteDatabase.update(DBinformation.DATABASE_EASSYTABLE,contentValues,sql,strings)>0;
        }

        //查询数据
        public List<com.example.society.Bean.Eassy> query()
        {
            List<com.example.society.Bean.Eassy> list = new ArrayList<com.example.society.Bean.Eassy>();
            Cursor cursor = sqLiteDatabase.query(DBinformation.DATABASE_EASSYTABLE,null,null,null,null,null,DBinformation.EASSY_ID+" desc");
            if (cursor != null)
            {
                while (cursor.moveToNext())
                {
                    com.example.society.Bean.Eassy eassy = new com.example.society.Bean.Eassy();
                    @SuppressLint("Range") String id = String.valueOf(cursor.getInt(cursor.getColumnIndex(DBinformation.EASSY_ID)));
                    @SuppressLint("Range") String author = cursor.getString(cursor.getColumnIndex(DBinformation.EASSY_AUTHORNAME));
                    @SuppressLint("Range") String imgpath = cursor.getString(cursor.getColumnIndex(DBinformation.EASSY_IMGPATH));
                    @SuppressLint("Range") String content = cursor.getString(cursor.getColumnIndex(DBinformation.EASSY_CONTENT));
                    @SuppressLint("Range") String time = cursor.getString(cursor.getColumnIndex(DBinformation.EASSY_TIME));
                    eassy.setId(id);
                    eassy.setAuthor(author);
                    eassy.setImgpath(imgpath);
                    eassy.setContent(content);
                    eassy.setTime(time);
                    list.add(eassy);
                }
                cursor.close();
            }
            return list;
        }

        //查询单个数据
        public com.example.society.Bean.Eassy userquery(String times)
        {
            com.example.society.Bean.Eassy eassy = null;
            Cursor cursor = sqLiteDatabase.query(DBinformation.DATABASE_EASSYTABLE,null,null,null,null,null,DBinformation.EASSY_ID+" desc");
            if (cursor != null)
            {
                while (cursor.moveToNext())
                {

                    @SuppressLint("Range") String time = cursor.getString(cursor.getColumnIndex(DBinformation.EASSY_TIME));

                    if(time.equals(times))
                    {
                        eassy = new com.example.society.Bean.Eassy();
                        @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex(DBinformation.EASSY_ID));
                        @SuppressLint("Range") String author = cursor.getString(cursor.getColumnIndex(DBinformation.EASSY_AUTHORNAME));
                        @SuppressLint("Range") String imgpath = cursor.getString(cursor.getColumnIndex(DBinformation.EASSY_IMGPATH));
                        @SuppressLint("Range") String content = cursor.getString(cursor.getColumnIndex(DBinformation.EASSY_CONTENT));
                        eassy.setId(id);
                        eassy.setAuthor(author);
                        eassy.setImgpath(imgpath);
                        eassy.setContent(content);
                        eassy.setTime(time);

                        break;
                    }
                }
                cursor.close();
            }
            //Log.i("pwd",loginuser.toString());
            return eassy;
        }
    }

    public static class Forum {


        public Forum() {

        }

        //添加数据
        public boolean insertData(String forumauthor,String forumname,String forumcomment,String forumimgpath,String time)
        {

            ContentValues contentValues = new ContentValues();
            contentValues.put(DBinformation.FORUM_AUTHOR, forumauthor);
            contentValues.put(DBinformation.FORUM_NAME, forumname);
            contentValues.put(DBinformation.FORUM_IMGPATH, forumimgpath);
            contentValues.put(DBinformation.FORUM_CONTENT, forumcomment);
            contentValues.put(DBinformation.FORUM_TIME, time);
            return sqLiteDatabase.insert(DBinformation.DATABASE_FORUMTABLE, null, contentValues) > 0;
        }

        //删除数据
        public boolean deleteData (String id)
        {
            String sql = DBinformation.FORUM_ID+"=?";
            String[] contentValuesArray = new String[]{String.valueOf(id)};
            return sqLiteDatabase.delete(DBinformation.DATABASE_FORUMTABLE,sql,contentValuesArray)>0;
        }

        //修改
        public boolean updateData(String id,String forumauthor,String forumname,String forumcomment,String forumimgpath,String time)
        {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DBinformation.FORUM_AUTHOR, forumauthor);
            contentValues.put(DBinformation.FORUM_NAME, forumname);
            contentValues.put(DBinformation.FORUM_IMGPATH, forumimgpath);
            contentValues.put(DBinformation.FORUM_CONTENT, forumcomment);
            contentValues.put(DBinformation.FORUM_TIME, time);
            String sql = DBinformation.FORUM_ID+"=?";
            String[] strings = new String[]{id};
            return sqLiteDatabase.update(DBinformation.DATABASE_FORUMTABLE,contentValues,sql,strings)>0;
        }

        //查询数据
        public List<com.example.society.Bean.Forum> query()
        {
            List<com.example.society.Bean.Forum> list = new ArrayList<com.example.society.Bean.Forum>();
            Cursor cursor = sqLiteDatabase.query(DBinformation.DATABASE_FORUMTABLE,null,null,null,null,null,DBinformation.FORUM_ID+" desc");
            if (cursor != null)
            {
                while (cursor.moveToNext())
                {
                    com.example.society.Bean.Forum forum = new com.example.society.Bean.Forum();
                    @SuppressLint("Range") String id = String.valueOf(cursor.getInt(cursor.getColumnIndex(DBinformation.FORUM_ID)));
                    @SuppressLint("Range") String forumname = cursor.getString(cursor.getColumnIndex(DBinformation.FORUM_NAME));
                    @SuppressLint("Range") String time = cursor.getString(cursor.getColumnIndex(DBinformation.FORUM_TIME));
                    @SuppressLint("Range") String foruming = cursor.getString(cursor.getColumnIndex(DBinformation.FORUM_IMGPATH));
                    @SuppressLint("Range") String forumcon = cursor.getString(cursor.getColumnIndex(DBinformation.FORUM_CONTENT));
                    @SuppressLint("Range") String forumauthor = cursor.getString(cursor.getColumnIndex(DBinformation.FORUM_AUTHOR));
                    forum.setId(id);
                    forum.setForumname(forumname);
                    forum.setTime(time);
                    forum.setForumcomment(forumcon);
                    forum.setForumimgpath(foruming);
                    forum.setForumauthor(forumauthor);
                    list.add(forum);
                }
                cursor.close();
            }
            return list;
        }
        //查询单个数据
        public com.example.society.Bean.Forum userquery(String name)
        {
            com.example.society.Bean.Forum forum= null;
            Cursor cursor = sqLiteDatabase.query(DBinformation.DATABASE_FORUMTABLE,null,null,null,null,null,DBinformation.FORUM_ID+" desc");
            if (cursor != null)
            {
                while (cursor.moveToNext())
                {

                    @SuppressLint("Range") String names = cursor.getString(cursor.getColumnIndex(DBinformation.FORUM_NAME));

                    if(names.equals(name))
                    {
                        forum = new com.example.society.Bean.Forum();
                        @SuppressLint("Range") String id = String.valueOf(cursor.getInt(cursor.getColumnIndex(DBinformation.FORUM_ID)));
                        @SuppressLint("Range") String time = cursor.getString(cursor.getColumnIndex(DBinformation.FORUM_TIME));
                        @SuppressLint("Range") String foruming = cursor.getString(cursor.getColumnIndex(DBinformation.FORUM_IMGPATH));
                        @SuppressLint("Range") String forumcon = cursor.getString(cursor.getColumnIndex(DBinformation.FORUM_CONTENT));
                        @SuppressLint("Range") String forumauthor = cursor.getString(cursor.getColumnIndex(DBinformation.FORUM_AUTHOR));
                        forum.setId(id);
                        forum.setForumname(name);
                        forum.setTime(time);
                        forum.setForumcomment(forumcon);
                        forum.setForumimgpath(foruming);
                        forum.setForumauthor(forumauthor);

                        break;
                    }
                }
                cursor.close();
            }
            //Log.i("pwd",loginuser.toString());
            return forum;
        }
    }

    public static class File {


        public File() {

        }

        //添加数据
        public boolean insertData(String name,String author,String path,String time)
        {

            ContentValues contentValues = new ContentValues();
            contentValues.put(DBinformation.FILE_NAME, name);
            contentValues.put(DBinformation.FILE_AUTHOR, author);
            contentValues.put(DBinformation.FILE_PATH, path);
            contentValues.put(DBinformation.FILE_TIME, time);
            return sqLiteDatabase.insert(DBinformation.DATABASE_FILETABLE, null, contentValues) > 0;
        }

        //删除数据
        public boolean deleteData (String id)
        {
            String sql = DBinformation.FILE_ID+"=?";
            String[] contentValuesArray = new String[]{String.valueOf(id)};
            return sqLiteDatabase.delete(DBinformation.DATABASE_FILETABLE,sql,contentValuesArray)>0;
        }

        //修改
        public boolean updateData(String id,String name,String author,String path,String time)
        {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DBinformation.FILE_PATH,path);
            contentValues.put(DBinformation.FILE_NAME, name);
            contentValues.put(DBinformation.FILE_AUTHOR, author);
            contentValues.put(DBinformation.FILE_TIME,time);
            String sql = DBinformation.FILE_ID+"=?";
            String[] strings = new String[]{id};
            return sqLiteDatabase.update(DBinformation.DATABASE_FILETABLE,contentValues,sql,strings)>0;
        }

        //查询数据
        public List<com.example.society.Bean.File> query()
        {
            List<com.example.society.Bean.File> list = new ArrayList<com.example.society.Bean.File>();
            Cursor cursor = sqLiteDatabase.query(DBinformation.DATABASE_FILETABLE,null,null,null,null,null,DBinformation.FILE_ID+" desc");
            if (cursor != null)
            {
                while (cursor.moveToNext())
                {
                    com.example.society.Bean.File file = new com.example.society.Bean.File();
                    @SuppressLint("Range") String id = String.valueOf(cursor.getInt(cursor.getColumnIndex(DBinformation.FILE_ID)));
                    @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(DBinformation.FILE_NAME));
                    @SuppressLint("Range") String author = cursor.getString(cursor.getColumnIndex(DBinformation.FILE_AUTHOR));
                    @SuppressLint("Range") String path = cursor.getString(cursor.getColumnIndex(DBinformation.FILE_PATH));
                    @SuppressLint("Range") String time = cursor.getString(cursor.getColumnIndex(DBinformation.FILE_TIME));
                    file.setId(id);
                    file.setPath(path);
                    file.setTime(time);
                    file.setName(name);
                    file.setAuthor(author);
                    list.add(file);
                }
                cursor.close();
            }
            return list;
        }


        //查询单个数据
        public com.example.society.Bean.File userquery(String times)
        {
            com.example.society.Bean.File file= null;
            Cursor cursor = sqLiteDatabase.query(DBinformation.DATABASE_FILETABLE,null,null,null,null,null,DBinformation.FILE_ID+" desc");
            if (cursor != null)
            {
                while (cursor.moveToNext())
                {

                    @SuppressLint("Range") String time = cursor.getString(cursor.getColumnIndex(DBinformation.FILE_TIME));

                    if(time.equals(times))
                    {
                        file = new com.example.society.Bean.File();
                        @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex(DBinformation.FILE_ID));
                        @SuppressLint("Range") String author = cursor.getString(cursor.getColumnIndex(DBinformation.FILE_AUTHOR));
                        @SuppressLint("Range") String imgpath = cursor.getString(cursor.getColumnIndex(DBinformation.FILE_PATH));
                        @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(DBinformation.FILE_NAME));
                        file.setId(id);
                        file.setAuthor(author);
                        file.setPath(imgpath);
                        file.setName(name);
                        file.setTime(time);

                        break;
                    }
                }
                cursor.close();
            }
            //Log.i("pwd",loginuser.toString());
            return file;
        }
    }

    public static class Programming {


        public Programming() {

        }

        //添加数据
        public boolean insertData(String programname, String authorname, String content, String time) {

            ContentValues contentValues = new ContentValues();
            contentValues.put(DBinformation.PROGRAMM_PRONAME, programname);
            contentValues.put(DBinformation.PROGRAMM_AUTHORNAME, authorname);
            contentValues.put(DBinformation.PROGRAMM_CONTENT, content);
            contentValues.put(DBinformation.PROGRAMM_TIME, time);
            return sqLiteDatabase.insert(DBinformation.DATABASE_PROGRAMMTABLE, null, contentValues) > 0;
        }

        //删除数据
        public boolean deleteData(String id) {
            String sql = DBinformation.PROGRAMM_ID + "=?";
            String[] contentValuesArray = new String[]{String.valueOf(id)};
            return sqLiteDatabase.delete(DBinformation.DATABASE_PROGRAMMTABLE, sql, contentValuesArray) > 0;
        }

        //修改
        public boolean updateData(String id, String programname, String authorname, String content, String time) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DBinformation.PROGRAMM_PRONAME, programname);
            contentValues.put(DBinformation.PROGRAMM_AUTHORNAME, authorname);
            contentValues.put(DBinformation.PROGRAMM_CONTENT, content);
            contentValues.put(DBinformation.PROGRAMM_TIME, time);
            String sql = DBinformation.PROGRAMM_ID + "=?";
            String[] strings = new String[]{id};
            return sqLiteDatabase.update(DBinformation.DATABASE_PROGRAMMTABLE, contentValues, sql, strings) > 0;
        }

        //查询数据
        public List<com.example.society.Bean.Programming> query() {
            List<com.example.society.Bean.Programming> list = new ArrayList<com.example.society.Bean.Programming>();
            Cursor cursor = sqLiteDatabase.query(DBinformation.DATABASE_PROGRAMMTABLE, null, null, null, null, null, DBinformation.PROGRAMM_ID + " desc");
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    com.example.society.Bean.Programming programming = new com.example.society.Bean.Programming();
                    @SuppressLint("Range") String id = String.valueOf(cursor.getColumnIndex(DBinformation.PROGRAMM_ID));
                    @SuppressLint("Range") String programmname = cursor.getString(cursor.getColumnIndex(DBinformation.PROGRAMM_PRONAME));
                    @SuppressLint("Range") String authorname = cursor.getString(cursor.getColumnIndex(DBinformation.PROGRAMM_AUTHORNAME));
                    @SuppressLint("Range") String content = cursor.getString(cursor.getColumnIndex(DBinformation.PROGRAMM_CONTENT));
                    @SuppressLint("Range") String time = cursor.getString(cursor.getColumnIndex(DBinformation.PROGRAMM_TIME));
                    programming.setId(id);
                    programming.setAuthorpro(authorname);
                    programming.setProname(programmname);
                    programming.setContent(content);
                    programming.setTime(time);
                    list.add(programming);
                }
                cursor.close();
            }
            return list;
        }


        //查询单个数据
        public com.example.society.Bean.Programming userquery(String name) {
            com.example.society.Bean.Programming programming = null;
            Cursor cursor = sqLiteDatabase.query(DBinformation.DATABASE_PROGRAMMTABLE, null, null, null, null, null, DBinformation.PROGRAMM_ID + " desc");
            if (cursor != null) {
                while (cursor.moveToNext()) {

                    @SuppressLint("Range") String programname = cursor.getString(cursor.getColumnIndex(DBinformation.PROGRAMM_PRONAME));

                    if (name.equals(programname)) {
                        programming = new com.example.society.Bean.Programming();
                        @SuppressLint("Range") String id = String.valueOf(cursor.getInt(cursor.getColumnIndex(DBinformation.PROGRAMM_ID)));
                        @SuppressLint("Range") String authorname = cursor.getString(cursor.getColumnIndex(DBinformation.PROGRAMM_AUTHORNAME));
                        @SuppressLint("Range") String content = cursor.getString(cursor.getColumnIndex(DBinformation.PROGRAMM_CONTENT));
                        @SuppressLint("Range") String time = cursor.getString(cursor.getColumnIndex(DBinformation.PROGRAMM_TIME));
                        programming.setId(id);
                        programming.setAuthorpro(authorname);
                        programming.setProname(programname);
                        programming.setContent(content);
                        programming.setTime(time);

                        break;
                    }
                }
                cursor.close();
            }
            //Log.i("pwd",loginuser.toString());
            return programming;
        }
    }



}

