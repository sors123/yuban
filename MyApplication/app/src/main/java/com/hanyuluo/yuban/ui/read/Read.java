package com.hanyuluo.yuban.ui.read;

public class Read {
    private int bookid;
    private int userid;
    private int id;
    private String text;
    private String bookname;
    private String bookimgurl;
    private String time;
    private String username;
    private int rating;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUsername() {
        return username;
    }

    public String getTime() {
        return time;
    }

    public String getText() {
        return text;
    }

    public int getUserid() {
        return userid;
    }

    public void setBookid(int bookid) {
        this.bookid = bookid;
    }

    public int getBookid() {
        return bookid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRating() {
        return rating;
    }

    public String getBookimgurl() {
        return bookimgurl;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookimgurl(String bookimgurl) {
        this.bookimgurl = bookimgurl;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

}
