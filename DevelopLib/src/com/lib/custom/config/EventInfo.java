package com.lib.custom.config;

/**
 * Created by chiely on 15/3/30.
 */
public class EventInfo {

    public EventType type;
    public int       arg1;
    public int       arg2;
    public Object    obj;

    public EventInfo(EventType eventType) {
        this.type = eventType;
    }

    public EventInfo(EventType eventType, int arg1) {
        this.type = eventType;
        this.arg1 = arg1;
    }

    public EventInfo(EventType eventType, int arg1, int arg2) {
        this.type = eventType;
        this.arg1 = arg1;
        this.arg2 = arg2;
    }

    public EventInfo(EventType eventType, int arg1, Object obj) {
        this.type = eventType;
        this.arg1 = arg1;
        this.obj = obj;
    }

    public EventInfo(EventType eventType, int arg1, int arg2, Object obj) {
        this.type = eventType;
        this.arg1 = arg1;
        this.arg2 = arg2;
        this.obj = obj;
    }

    public EventInfo(EventType eventType, Object obj) {
        this.type = eventType;
        this.obj = obj;
    }

    @Override
    public String toString() {
        return "Event{" +
                "type=" + type +
                ", arg1=" + arg1 +
                ", arg2=" + arg2 +
                ", obj=" + obj +
                '}';
    }
}
