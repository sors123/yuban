package com.hanyuluo.yuban.ui.userpage;

public class Userpage {
    private int readnum;
    private int id;
    private int guanzhu;
    private int fensi;
    private String username;
    private String qianming;
    private String image;
    private String location;
    private String sex;

    private String readimg;
    private String wantimg;

    public String getReadimg() {
        return readimg;
    }

    public void setReadimg(String readimg) {
        this.readimg = readimg;
    }

    public String getWantimg() {
        return wantimg;
    }

    public void setWantimg(String wantimg) {
        this.wantimg = wantimg;
    }

    public String getQianming() {
        return qianming;
    }

    public String getImage() {
        return image;
    }

    public String getLocation() {
        return location;
    }

    public String getSex() {
        return sex;
    }

    public void setQianming(String qianming) {
        this.qianming = qianming;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }


    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getFensi() {
        return fensi;
    }

    public int getGuanzhu() {
        return guanzhu;
    }

    public int getReadnum() {
        return readnum;
    }

    public void setFensi(int fensi) {
        this.fensi = fensi;
    }

    public void setGuanzhu(int guanzhu) {
        this.guanzhu = guanzhu;
    }

    public void setReadnum(int readnum) {
        this.readnum = readnum;
    }

}
