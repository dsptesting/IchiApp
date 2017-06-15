package com.ichi.inspection.app.models;

/**
 * Created by Mayank on 15-06-2017.
 */

public class EventBus {
    private String uri;
    private int position;
    private String ioLineId;
    public  EventBus(String uri,int position,String ioLineId){
        this.uri=uri;
        this.ioLineId = ioLineId;
        this.position=position;
    }
    public String getUri(){
        return uri;
    }
    public int getPosition(){
        return position;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getIoLineId() {
        return ioLineId;
    }

    public void setIoLineId(String ioLineId) {
        this.ioLineId = ioLineId;
    }
}
