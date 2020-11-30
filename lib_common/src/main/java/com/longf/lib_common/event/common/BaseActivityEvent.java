package com.longf.lib_common.event.common;

import com.longf.lib_common.event.BaseEvent;

public class BaseActivityEvent<T> extends BaseEvent<T> {
    public BaseActivityEvent(int code) {
        super(code);
    }
}