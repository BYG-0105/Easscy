package com.example.society.Bean;

public class Comment {
    private String id;
    private String forname;//论坛名称
    private String authorname;//发布评论作者
    private String content;//发布评论内容
    private String time;//发布评论时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getForname() {
        return forname;
    }

    public void setForname(String forname) {
        this.forname = forname;
    }

    public String getForumname() {
        return forname;
    }

    public void setForumname(String name) {
        this.forname = name;
    }

    public String getAuthorname() {
        return authorname;
    }

    public void setAuthorname(String authorname) {
        this.authorname = authorname;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
