package com.longf.module_main.entity;

public enum  MainChannel {
    MAIN(0,"MAIN"), ME(1,"ME"),PAGE2(2,"PAGE2"), PAGE3(3,"PAGE3");
    public int id;
    public String name;
    MainChannel(int id, String name){
        this.id = id;
        this.name = name;
    }
}
