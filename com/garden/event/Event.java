package com.garden.event;

import com.garden.constants.KeyboardConst;
import com.garden.constants.MouseConst;
import com.garden.constants.SourceConst;

import java.util.Date;

public class Event implements SourceConst, KeyboardConst, MouseConst{
    private int source;
    private long time;

    public Event(){
        Date date = new Date();
        this.time = date.getTime();
    }

    public long elapsed(){
        Date date = new Date();
        return date.getTime() - this.time;
    }

    public int getSource(){
        return this.source;
    }

    public void setSource(int source){
        this.source = source;
    }


}