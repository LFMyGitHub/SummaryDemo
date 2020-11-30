package com.longf.lib_common.mvvm.viewmodel;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.util.Consumer;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.longf.lib_api.config.API;
import com.longf.lib_common.event.SingleLiveEvent;
import com.longf.lib_common.mvvm.model.BaseModel;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.Disposable;

public class BaseViewModel<M extends BaseModel> extends AndroidViewModel implements IBaseViewModel, Consumer<Disposable> {
    protected M mModle;
    protected UIChangeLiveData mUIChangeLiveData;
    public SharedPreferences mShared;
    public SharedPreferences.Editor mEditor;

    public BaseViewModel(@NonNull Application application, M modle) {
        super(application);
        this.mModle = modle;
        this.mShared = application.getSharedPreferences(API.SPModule.SP_APP, 0);
        this.mEditor = mShared.edit();
    }

    public UIChangeLiveData getUC() {
        if (mUIChangeLiveData == null) {
            mUIChangeLiveData = new UIChangeLiveData();
        }
        return mUIChangeLiveData;
    }

    public final class UIChangeLiveData extends SingleLiveEvent {
        private SingleLiveEvent<Boolean> showInitLoadViewEvent;
        private SingleLiveEvent<Boolean> showTransLoadingViewEvent;
        private SingleLiveEvent<Boolean> showNoDataViewEvent;
        private SingleLiveEvent<Boolean> showNetWorkErrViewEvent;
        private SingleLiveEvent<Map<String, Object>> startActivityEvent;
        private SingleLiveEvent<Void> finishActivityEvent;
        private SingleLiveEvent<Void> onBackPressedEvent;

        public SingleLiveEvent<Boolean> getShowInitLoadViewEvent() {
            return showInitLoadViewEvent = createLiveData(showInitLoadViewEvent);
        }

        public SingleLiveEvent<Boolean> getShowTransLoadingViewEvent() {
            return showTransLoadingViewEvent = createLiveData(showTransLoadingViewEvent);
        }

        public SingleLiveEvent<Boolean> getShowNoDataViewEvent() {
            return showNoDataViewEvent = createLiveData(showNoDataViewEvent);
        }

        public SingleLiveEvent<Boolean> getShowNetWorkErrViewEvent() {
            return showNetWorkErrViewEvent = createLiveData(showNetWorkErrViewEvent);
        }

        public SingleLiveEvent<Map<String, Object>> getStartActivityEvent() {
            return startActivityEvent = createLiveData(startActivityEvent);
        }

        public SingleLiveEvent<Void> getFinishActivityEvent() {
            return finishActivityEvent = createLiveData(finishActivityEvent);
        }

        public SingleLiveEvent<Void> getOnBackPressedEvent() {
            return onBackPressedEvent = createLiveData(onBackPressedEvent);
        }
    }

    protected SingleLiveEvent createLiveData(SingleLiveEvent liveData) {
        if (liveData == null) {
            liveData = new SingleLiveEvent();
        }
        return liveData;
    }

    public static final class ParameterField {
        public static String CLASS = "CLASS";
        public static String CANONICAL_NAME = "CANONICAL_NAME";
        public static String BUNDLE = "BUNDLE";
    }

    public void postShowInitLoadViewEvent(boolean show) {
        if (mUIChangeLiveData != null) {
            mUIChangeLiveData.showInitLoadViewEvent.postValue(show);
        }
    }

    public void postShowNoDataViewEvent(boolean show) {
        if (mUIChangeLiveData != null) {
            mUIChangeLiveData.showNoDataViewEvent.postValue(show);
        }
    }

    public void postShowTransLoadingViewEvent(boolean show) {
        if (mUIChangeLiveData != null) {
            mUIChangeLiveData.showTransLoadingViewEvent.postValue(show);
        }
    }

    public void postShowNetWorkErrViewEvent(boolean show) {
        if (mUIChangeLiveData != null) {
            mUIChangeLiveData.showNetWorkErrViewEvent.postValue(show);
        }
    }

    public void postStartActivityEvent(Class<?> clz, Bundle bundle) {
        Map<String, Object> params = new HashMap<>();
        params.put(ParameterField.CLASS, clz);
        if (bundle != null) {
            params.put(ParameterField.BUNDLE, bundle);
        }
        mUIChangeLiveData.startActivityEvent.postValue(params);
    }

    public void postFinishActivityEvent() {
        mUIChangeLiveData.finishActivityEvent.call();
    }

    public void postOnBackPressedEvent() {
        mUIChangeLiveData.onBackPressedEvent.call();
    }

    @Override
    public void accept(Disposable disposable) {
        if (mModle != null) {
            mModle.addSubscribe(disposable);
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if(mModle != null){
            mModle.onCleared();
        }
    }

    @Override
    public void onAny(LifecycleOwner owner, Lifecycle.Event event) {

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }
}
