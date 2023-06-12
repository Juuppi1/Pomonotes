package com.juuppi.myapplication;

public class NoteInfo {
    private String title, context;

    public NoteInfo(String title, String context){
        this.title = title;
        this.context = context;
    }
    public String getTitle(){
        return title;
    }

    public String getContext(){
        return context;
    }

    public void setTitle(String title){this.title = title;}

    public void setContext(String context){this.context = context;}

}
