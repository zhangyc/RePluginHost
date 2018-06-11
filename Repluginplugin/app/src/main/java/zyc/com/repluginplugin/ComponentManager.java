package zyc.com.repluginplugin;

import android.content.Context;

import zyc.com.repluginplugin.component.ActivityComponent;
import zyc.com.repluginplugin.component.DaggerActivityComponent;

/**
 * Created by wmliang on 2018/4/25.
 */

public class ComponentManager {
    private static ActivityComponent sAppComponent;
    public static ActivityComponent getAppComposent(Context context) {
        synchronized (ComponentManager.class) {
            if (sAppComponent == null) {

                sAppComponent = DaggerActivityComponent.builder().build();
            }
        }
        return sAppComponent;
    }
}
