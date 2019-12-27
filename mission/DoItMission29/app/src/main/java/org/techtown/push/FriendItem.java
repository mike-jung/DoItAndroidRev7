package org.techtown.push;


public class FriendItem {

    String name;
    String mobile;
    String regId;

    public FriendItem(String name, String mobile) {
        this.name = name;
        this.mobile = mobile;
    }

    public FriendItem(String name, String mobile, String regId) {
        this.name = name;
        this.mobile = mobile;
        this.regId = regId;
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public String toString() {
        return "FriendItem{" +
                "name='" + name + '\'' +
                ", mobile='" + mobile + '\'' +
                ", regId='" + regId + '\'' +
                '}';
    }
}
