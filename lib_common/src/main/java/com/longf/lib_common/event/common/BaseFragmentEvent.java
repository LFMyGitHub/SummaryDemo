package com.longf.lib_common.event.common;

import com.longf.lib_common.event.BaseEvent;

public class BaseFragmentEvent<T> extends BaseEvent<T> {
    public BaseFragmentEvent(int code) {
        super(code);
    }
}
