package com.hanyuluo.yuban.ui.home;

public class TimeLine {
    /*message={'id': i.id,
                 'type': 'read',
                 'userid': i.readUser.id,
                 'username': i.readUser.username,
                 'bookid':i.readBook.id,
                 'bookimgurl': i.readBook.image.url,
                 'bookname':i.readBook.bookName,
                 'commenttitle': None,
                 'comment': None,
                 'rating':i.rating,
                 'text':i.comment,
                 'time': i.createTime}
    */

    private  int  id;
    private String type;
    private  int  userid;
    private String username;
    private  int  bookid;
    private String bookimgurl;
    private String bookname;
    private String commenttitle;
    private String comment;
    private  int  rating;
    private String text;
    private String time;
    private int commentid;
    private String author;
    private String press;
    private int likenum;
    private String image;

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public int getLikenum() {
        return likenum;
    }

    public void setLikenum(int likenum) {
        this.likenum = likenum;
    }

    public String getAuthor() {
        return author;
    }

    public String getPress() {
        return press;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPress(String press) {
        this.press = press;
    }

    public int getCommentid() {
        return commentid;
    }

    public void setCommentid(int commentid) {
        this.commentid = commentid;
    }

    public int getRating() {
        return rating;
    }

    public String getBookimgurl() {
        return bookimgurl;
    }

    public void setBookimgurl(String bookimgurl) {
        this.bookimgurl = bookimgurl;
    }

    public String getComment() {
        return comment;
    }

    public String getCommenttitle() {
        return commenttitle;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setCommenttitle(String commenttitle) {
        this.commenttitle = commenttitle;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public int getUserid() {
        return userid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookid() {
        return bookid;
    }

    public String getBookname() {
        return bookname;
    }

    public String getTime() {
        return time;
    }

    public String getText() {
        return text;
    }

    public String getType() {
        return type;
    }

    public void setBookid(int bookid) {
        this.bookid = bookid;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setType(String type) {
        this.type = type;
    }

}
