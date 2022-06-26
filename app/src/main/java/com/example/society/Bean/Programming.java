package com.example.society.Bean;

public class Programming {

    private String id;
    private String proname;//题目名称
    private String authorpro;//发布作者
    private String content;//题目内容
    private String time;//发布时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProname() {
        return proname;
    }

    public void setProname(String proname) {
        this.proname = proname;
    }

    public String getAuthorpro() {
        return authorpro;
    }

    public void setAuthorpro(String authorpro) {
        this.authorpro = authorpro;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
