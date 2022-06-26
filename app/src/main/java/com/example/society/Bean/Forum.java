package com.example.society.Bean;

public class Forum {
    private String id;
    private String forumname;//论坛名称
    private String forumauthor;//论坛作者
    private String forumcomment;//论坛简介
    private String forumimgpath;//论坛图片
    private String time;//论坛创建时间


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getForumname() {
        return forumname;
    }

    public String getForumcomment() {
        return forumcomment;
    }

    public void setForumcomment(String forumcomment) {
        this.forumcomment = forumcomment;
    }

    public String getForumimgpath() {
        return forumimgpath;
    }

    public String getForumauthor() {
        return forumauthor;
    }

    public void setForumauthor(String forumauthor) {
        this.forumauthor = forumauthor;
    }

    public void setForumimgpath(String forumimgpath) {
        this.forumimgpath = forumimgpath;
    }

    public void setForumname(String forumname) {
        this.forumname = forumname;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


}
