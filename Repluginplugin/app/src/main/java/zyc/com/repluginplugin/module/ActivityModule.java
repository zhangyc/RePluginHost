package zyc.com.repluginplugin.module;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by zhangyc on 2018/5/28.
 * Module
 */
@Module
public class ActivityModule {
    private Activity activity;
    public ActivityModule(Activity activity){
        this.activity=activity;
    }
    @Provides
    public Activity provideActivity(){
        return activity;
    }
}
