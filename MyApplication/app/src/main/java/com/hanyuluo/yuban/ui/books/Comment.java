package com.hanyuluo.yuban.ui.books;

public class Comment {
   /*  'id':comment.id,
            'bookid':comment.commentBook.id,
            'bookName':comment.commentBook.bookName,
            'authorName': comment.commentBook.authorName,
            'introduction':comment.commentBook.introduction,
            'imgurl': comment.commentBook.image.url,
            'title':comment.commentTitle,
            'text':comment.comment,
            'time':comment.createTime,
            'username':comment.commentUser.username,
            'userid':comment.commentUser.id
*/
    private int id;
    private int bookid;
    private String bookName;
    private String authorName;
    private String introduction;
    private String imgurl;
    private String title;
    private String text;
    private  String time;
    private String username;
    private int userid;


    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getBookName() {
        return bookName;
    }

    public int getBookid() {
        return bookid;
    }

    public void setBookid(int bookid) {
        this.bookid = bookid;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getIntroduction() {
        return introduction;
    }

    public String getImgurl() {
        return imgurl;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getUserid() {
        return userid;
    }

    public String getText() {
        return text;
    }

    public String getTime() {
        return time;
    }

    public String getUsername() {
        return username;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
