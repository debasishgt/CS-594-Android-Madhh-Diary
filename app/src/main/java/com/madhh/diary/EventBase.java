package com.madhh.diary;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by debasish on 6/8/2015.
 */
@ParseClassName("EventBase")
public class EventBase extends ParseObject {

    public EventBase(){}

    private BaseTable parent;
    private String title;
    private String memo;

    public void saveMemo(String memo){
        this.memo = memo;
        put("memo", this.memo);
    }
    public String getMemo(){
        return this.memo;
    }
    public void saveTitle(String title){
        this.title = title;
        put("title", this.title);
    }
    public String getTitle(){
        return this.title;
    }

    public void setParent(BaseTable parent){
        this.parent = parent;
        put("parent", this.parent);
    }

    public BaseTable getParent(){
        return this.parent;
    }
}
