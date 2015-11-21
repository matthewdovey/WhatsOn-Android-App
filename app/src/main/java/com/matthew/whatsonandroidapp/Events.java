package com.matthew.whatsonandroidapp;

/**
 * Created by matthew on 20/11/15.
 */
public class Events {

    private int _id;
    private String _link;
    private String _eventname;
    private String _info;
    private String _desc;
    private String _image;

    public Events(String link, String event, String info, String desc, String image) {
        this._link = link;
        this._eventname = event;
        this._info = info;
        this._desc = desc;
        this._image = image;
    }

    //Maybe delete? Do these do what I need them to do?

    public void setID(int id) {
        this._id = id;
    }

    public int getID() {
        return this._id;
    }

    //I think I can delete everything below this point
    //Find out what this shit actually does

    public void setLink(String event) {
        this._link = event;
    }

    public String getLink() {
        return this._link;
    }

    public void setEventName(String event) {
        this._eventname = event;
    }

    public String getEventName() {
        return this._eventname;
    }

    public void setInfo(String info) {
        this._info = info;
    }

    public String getInfo() {
        return this._info;
    }

    public void setDesc(String event) {
        this._desc = event;
    }

    public String getDesc() {
        return this._desc;
    }

    public void setImage(String event) {
        this._image = event;
    }

    public String getImage() {
        return this._image;
    }
}

