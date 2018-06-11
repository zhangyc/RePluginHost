package zyc.com.replugin.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.qihoo360.replugin.RePlugin;
import com.qihoo360.replugin.model.PluginInfo;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.List;

import io.reactivex.functions.Consumer;
import zyc.com.replugin.R;
import zyc.com.replugin.util.PluginUpdateUtil;


public class MainActivity extends AppCompatActivity {
    String url="http://10.130.212.39/client/h/8e9604a40dde4a109bb485a7e7043a86/2acb1495802240398d6ac07e875e081c/plugin2-debug.apk";

    private PluginUpdateUtil pluginUpdateUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initPremiss();
         pluginUpdateUtil=new PluginUpdateUtil(this);
        //加载本地的
        findViewById(R.id.main_replugin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path= Environment.getExternalStorageDirectory().getPath().concat("/test/app-debug.apk");
                //第一个参数为插件名称，第二个参数为目标activity
                Intent intent=RePlugin.createIntent("content2","me.pengtao.contentprovidertest.MainActivity");
                pluginUpdateUtil.installPlugins(path,intent);
            }
        });
        //加载服务器的
        findViewById(R.id.main_replugin2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击加载plugin2插件，如果还未加载则下载安装
                Intent intent=RePlugin.createIntent("plugin2","zyc.com.myapplication.Plugin2Activity");
                RePlugin.startActivity(MainActivity.this,intent);

//                toServerPlugin();
            }
        });
        //输出本地的plugin的信息
        findViewById(R.id.main_replugin3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<PluginInfo> list = RePlugin.getPluginInfoList();
                for (PluginInfo info:list){
                    String name=info.getName();
                    int version=info.getVersion();
                    info.getJSON();
                    Log.i(">>>name",name);
                    Log.i(">>>json", info.getJSON()+"");

                }
            }
        });
        //更新plugin
        findViewById(R.id.main_replugin4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pluginUpdateUtil.checkPlugin();
            }
        });
    }

    private void initPremiss() {
        RxPermissions permissions=new RxPermissions(this);
        permissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        Toast.makeText(MainActivity.this,aBoolean+"",Toast.LENGTH_SHORT).show();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

}
