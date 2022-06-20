package com.example.society.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mycourse.bean.ChatBean;
import com.example.mycourse.bean.UserBean;
import com.example.mycourse.bean.VideoBean;

import java.util.ArrayList;
import java.util.List;

public class DBUtils {
    private static DBUtils instance = null;
    private static SQLiteHelper helper;
    private static SQLiteDatabase db;

    /**
     * 构造方法，只有当类被实例化时候调用
     * 实例化SQLiteHelper类，从中得到一个课读写的数据库
     **/
    public DBUtils(Context context) {
        helper = new SQLiteHelper(context);
        db = helper.getWritableDatabase();
    }

    /**
     * 得到这个类的实例
     **/
    public static DBUtils getInstance(Context context) {
        if (instance == null) {
            instance = new DBUtils(context);
        }
        return instance;
    }

    /**
     * 保存个人资料信息
     **/
    public void saveUserInfo(UserBean bean) {
        ContentValues cv = new ContentValues();
        cv.put("userName", bean.userName);
        cv.put("nickName", bean.nickName);
        cv.put("sex", bean.sex);
        cv.put("signature", bean.signature);
        cv.put("qq",bean.qq);
        //Convenience method for inserting a row into the database.
        //注意，我们是从数据库使用插入方法，传入表名和数据集完成插入
        db.insert(SQLiteHelper.U_USER_INFO, null, cv);
    }




    public void saveVideoPlayList(VideoBean videoBean, String userName){
        if (hasVideoPlay(videoBean.chapterId, videoBean.videoId, userName)){
            boolean isDelete = delVideoPlay(videoBean.chapterId, videoBean.videoId, userName);
            if (!isDelete){
                return;
            }
        }
        ContentValues cv = new ContentValues();
        cv.put("userName", userName);
        cv.put("chapterId", videoBean.chapterId);
        cv.put("videoId", videoBean.videoId);
        cv.put("videoPath", videoBean.videoPath);
        cv.put("title", videoBean.title);
        cv.put("secondTitle", videoBean.secondTitle);
        db.insert(SQLiteHelper.U_VIDEO_PLAY_LIST, null, cv);
    }
    private boolean delVideoPlay(int chapterId, int videoId, String userName){
        boolean delSuccess = false;
        int row = db.delete(SQLiteHelper.U_VIDEO_PLAY_LIST,
                " chapterId=? AND videoId=? AND userName=?",
                new String[]{chapterId + "", videoId + "", userName});
        if (row > 0){
            delSuccess = true;
        }
        return delSuccess;
    }
    private boolean hasVideoPlay(int chapterId, int videoId, String userName){
        boolean hasVideo = false;
        String sql = "SELECT * FROM " + SQLiteHelper.U_VIDEO_PLAY_LIST +
                " WHERE chapterId=? AND videoId=? AND userName=?";
        Cursor cursor = db.rawQuery(sql, new String[]{chapterId + "", videoId + "", userName});
        if (cursor.moveToNext()){
            hasVideo = true;
        }
        cursor.close();
        return hasVideo;
    }
    public List<VideoBean> getVideoHistory(String s){
        String sql = "SELECT * FROM " + SQLiteHelper.U_VIDEO_PLAY_LIST + " WHERE userName=?";
        Cursor cursor = db.rawQuery(sql, new String[]{s});
        List<VideoBean> vbl = new ArrayList<>();
        VideoBean bean = null;
        while (cursor.moveToNext()){
            bean = new VideoBean();
            bean.chapterId = cursor.getInt(cursor.getColumnIndex("chapterId"));
            bean.videoId = cursor.getInt(cursor.getColumnIndex("videoId"));
            bean.videoPath = cursor.getString(cursor.getColumnIndex("videoPath"));
            bean.title = cursor.getString(cursor.getColumnIndex("title"));
            bean.secondTitle = cursor.getString(cursor.getColumnIndex("secondTitle"));
            vbl.add(bean);
            bean = null;
        }
        cursor.close();
        return vbl;
    }

    /**
     * 保存反馈信息
     **/
    public void saveUserChat(ChatBean bean) {
        ContentValues cv = new ContentValues();
        cv.put("userName", bean.username);
        cv.put("message", bean.message);
        //Convenience method for inserting a row into the database.
        //注意，我们是从数据库使用插入方法，传入表名和数据集完成插入
        db.insert(SQLiteHelper.U_USER_CHAT, null, cv);
    }
}
