package com.longf.lib_common.util;

import android.os.CountDownTimer;

/**
 * Android计时器
 */
public class TimeCount extends CountDownTimer {
    private OnTimeCountListener mOnTimeCountListener;

    public TimeCount(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    @Override
    public void onTick(long millisUntilFinished) {
        if(mOnTimeCountListener != null){
            mOnTimeCountListener.onTick(millisUntilFinished);
        }
    }

    @Override
    public void onFinish() {
        if(mOnTimeCountListener != null){
            mOnTimeCountListener.onFinish();
        }
    }

    public void setOnTimeCountListener(OnTimeCountListener onTimeCountListener){
        this.mOnTimeCountListener = onTimeCountListener;
    }

    public interface OnTimeCountListener{
        void onTick(long millisUntilFinished);

        void onFinish();
    }
}
