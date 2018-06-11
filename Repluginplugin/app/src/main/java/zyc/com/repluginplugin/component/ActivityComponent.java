package zyc.com.repluginplugin.component;

import dagger.Component;
import zyc.com.repluginplugin.MainActivity;
import zyc.com.repluginplugin.module.ActivityModule;

/**
 * Created by zhangyc on 2018/5/28.
 * 纽带
 */
@ActivityScope
@Component(modules = {ActivityModule.class})
public interface ActivityComponent {
    void inject(MainActivity activity);

}
