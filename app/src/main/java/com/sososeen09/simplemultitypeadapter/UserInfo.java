package com.sososeen09.simplemultitypeadapter;

/**
 * Created on 2018/5/22.
 *
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
