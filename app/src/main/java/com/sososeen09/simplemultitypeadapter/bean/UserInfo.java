package com.sososeen09.simplemultitypeadapter.bean;

/**
 * @author sososeen09
 */

public class UserInfo {
    public String name;
    /**
     * 1 male
     * 0 female
     */
    public int sexuality;

    public UserInfo() {
    }

    public UserInfo(String name, int sexuality) {
        this.name = name;
        this.sexuality = sexuality;
    }
}
