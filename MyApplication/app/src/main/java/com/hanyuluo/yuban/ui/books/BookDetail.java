package com.hanyuluo.yuban.ui.books;

public class BookDetail {

    private String authorName;
    private String bookName;
    private String imgurl;
    private String introduction;
    private String pressName;
    private int id;
    private int pageNum;
    private int pricingNum;

    public int getId() {
        return id;
    }

    public int getPageNum() {
        return pageNum;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getBookName() {
        return bookName;
    }

    public int getPricingNum() {
        return pricingNum;
    }

    public String getImgurl() {
        return imgurl;
    }

    public String getIntroduction() {
        return introduction;
    }

    public String getPressName() {
        return pressName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
    public void setId(int id) {
        this.id = id;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public void setPressName(String pressName) {
        this.pressName = pressName;
    }

    public void setPricingNum(int pricingNum) {
        this.pricingNum = pricingNum;
    }

}
