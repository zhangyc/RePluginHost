package zyc.com.replugin.application;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.qihoo360.replugin.RePlugin;
import com.qihoo360.replugin.RePluginApplication;
import com.qihoo360.replugin.RePluginCallbacks;
import com.qihoo360.replugin.RePluginConfig;
import com.qihoo360.replugin.RePluginEventCallbacks;

import zyc.com.replugin.BuildConfig;
import zyc.com.replugin.util.PluginUpdateUtil;

/**
 * Created by zhangyc on 2018/5/7.
 */

public class MainApp extends RePluginApplication {
//    @Override
//    protected void attachBaseContext(Context base) {
//        super.attachBaseContext(base);
//
//        // FIXME 允许接收rpRunPlugin等Gradle Task，发布时请务必关掉，以免出现问题
//        RePlugin.enableDebugger(base, BuildConfig.DEBUG);
//    }

//    @Override
//    public void onCreate() {
//        super.onCreate();
//        OkGo.getInstance().init(this);
//    }

    @Override
    protected RePluginConfig createConfig() {
        RePluginConfig c = new RePluginConfig();

        // 允许“插件使用宿主类”。默认为“关闭”
        c.setUseHostClassIfNotFound(true);

        // FIXME RePlugin默认会对安装的外置插件进行签名校验，这里先关掉，避免调试时出现签名错误
        c.setVerifySign(!BuildConfig.DEBUG);

        // 针对“安装失败”等情况来做进一步的事件处理
        c.setEventCallbacks(new HostEventCallbacks(this));
        c.setMoveFileWhenInstalling(false);

        // FIXME 若宿主为Release，则此处应加上您认为"合法"的插件的签名，例如，可以写上"宿主"自己的。
        // RePlugin.addCertSignature("AAAAAAAAA");

        // 在Art上，优化第一次loadDex的速度
        // c.setOptimizeArtLoadDex(true);
        return c;
    }
    private class HostEventCallbacks extends RePluginEventCallbacks {

        private static final String TAG = "HostEventCallbacks";

        public HostEventCallbacks(Context context) {
            super(context);
        }

        @Override
        public void onInstallPluginFailed(String path, InstallResult code) {
            // FIXME 当插件安装失败时触发此逻辑。您可以在此处做“打点统计”，也可以针对安装失败情况做“特殊处理”
            // 大部分可以通过RePlugin.install的返回值来判断是否成功
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "onInstallPluginFailed: Failed! path=" + path + "; r=" + code);
            }
            super.onInstallPluginFailed(path, code);
        }

        @Override
        public void onStartActivityCompleted(String plugin, String activity, boolean result) {
            // FIXME 当打开Activity成功时触发此逻辑，可在这里做一些APM、打点统计等相关工作
            super.onStartActivityCompleted(plugin, activity, result);
        }
    }

    @Override
    protected RePluginCallbacks createCallbacks() {
        return new UpdateRePluginCallbacks(this);
    }

    public class UpdateRePluginCallbacks extends RePluginCallbacks {

        public UpdateRePluginCallbacks(Context context) {
            super(context);
        }

        public boolean onPluginNotExistsForActivity(Context context, String plugin, Intent intent, int process) {
            if("host".equals(plugin)|| RePlugin.getPluginInfo(plugin)!=null){ //已按照
                Toast.makeText(MainApp.this,"不能这个样子的",Toast.LENGTH_SHORT).show();
            }else {
                PluginUpdateUtil updateUtil=new PluginUpdateUtil(context);
                updateUtil.installPlugin(context,intent,plugin);
            }
            return true;
        }
    }
}
