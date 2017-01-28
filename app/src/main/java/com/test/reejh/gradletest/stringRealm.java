package com.test.reejh.gradletest;

/**
 * Created by 15-AB032TX on 28-01-2017.
 */
import io.realm.RealmObject;
public class stringRealm extends RealmObject{
    private String entry;

    public String getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }

    public stringRealm() {
        this.entry="";

    }
    public stringRealm(String a){
        this.entry=a;
    }
}
