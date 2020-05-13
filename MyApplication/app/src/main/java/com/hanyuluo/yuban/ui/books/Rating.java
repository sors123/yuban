package com.hanyuluo.yuban.ui.books;

public class Rating {
    private int id;
    private String bookName;
    private int positive;
    private String tuijian;
    private int total;
    private int negative;
    private int moderate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public int getModerate() {
        return moderate;
    }

    public int getNegative() {
        return negative;
    }

    public int getPositive() {
        return positive;
    }

    public int getTotal() {
        return total;
    }

    public String getTuijian() {
        return tuijian;
    }

    public void setModerate(int moderate) {
        this.moderate = moderate;
    }

    public void setNegative(int negative) {
        this.negative = negative;
    }

    public void setPositive(int positive) {
        this.positive = positive;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setTuijian(String tuijian) {
        this.tuijian = tuijian;
    }
}
