package zyc.com.replugin.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.qihoo360.replugin.RePlugin;
import com.qihoo360.replugin.model.PluginInfo;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import zyc.com.replugin.R;
import zyc.com.replugin.bean.HotFixInfo;
import zyc.com.replugin.bean.PublicResponseEntity;
import zyc.com.replugin.bean.SSLSocketFactoryCompat;
import zyc.com.replugin.contact.PublicContact;
import zyc.com.replugin.download.DownloadProgressHandler;
import zyc.com.replugin.download.ProgressHelper;
import zyc.com.replugin.service.DownloadApi;
import zyc.com.replugin.service.VersionService;

/**
 * Created by zhangyc on 2018/5/18.
 */

public class PluginUpdateUtil {
    private String TAG="PluginUpdateUtil";
    private Context context;
    public PluginUpdateUtil(Context context){
        this.context=context;
    }

    /**
     * 安装插件
     */
    public void installPlugins(String path,Intent intent) {
        //获取存放plugins的路径，进行安装
        File file=new File(path);
        if (file.exists()){
            Toast.makeText(context,"有木有？"+file.exists(),Toast.LENGTH_SHORT).show();
            PluginInfo info = RePlugin.install(path);
            if (null!=info){
//                RePlugin.startActivity(context,intent);
              //  Intent intent=RePlugin.createIntent("content2","me.pengtao.contentprovidertest.MainActivity");
                Log.i(">>>name",info.getName());
                RePlugin.startActivity(context,RePlugin.createIntent(info.getName(),"me.pengtao.contentprovidertest.MainActivity"));
            }
        }else {
            Toast.makeText(context,"有木有？"+file.exists(),Toast.LENGTH_SHORT).show();
        }

    }
    /**
     * 下载
     */
    public void downloadPlugin(final Context mcontext, String downloadUrl, final Intent intent, final String plugin){
        //监听下载进度
        final ProgressDialog dialog = new ProgressDialog(mcontext);
        dialog.setProgressNumberFormat("%1d KB/%2d KB");
        dialog.setTitle("下载");
        dialog.setMessage("正在下载，请稍后...");
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setCancelable(false);
        dialog.show();
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(downloadUrl+"/");
        OkHttpClient.Builder builder = ProgressHelper.addProgress(null);

        try {
            // 自定义一个信任所有证书的TrustManager，添加SSLSocketFactory的时候要用到
            final X509TrustManager trustAllCert = new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

                }

                @Override
                public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[]{};
                }
            };
            final SSLSocketFactory sslSocketFactory = new SSLSocketFactoryCompat(trustAllCert);
            builder.sslSocketFactory(sslSocketFactory, trustAllCert);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        DownloadApi retrofit = retrofitBuilder
                .client(builder.build())
                .build().create(DownloadApi.class);

        ProgressHelper.setProgressHandler(new DownloadProgressHandler() {
            @Override
            protected void onProgress(long bytesRead, long contentLength, boolean done) {
                Log.e("是否在主线程中运行", String.valueOf(Looper.getMainLooper() == Looper.myLooper()));
                Log.e("onProgress",String.format("%d%% done\n",(100 * bytesRead) / contentLength));
                Log.e("done",TAG + String.valueOf(done));
                dialog.setMax((int) (contentLength/1024));
                dialog.setProgress((int) (bytesRead/1024));

                if(done){
                    dialog.dismiss();
                }
            }
        });

        retrofit2.Call<ResponseBody> call = retrofit.retrofitDownload(downloadUrl);
        call.enqueue(new Callback<ResponseBody>() {
            public PluginInfo info;

            @Override
            public void onResponse(retrofit2.Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                try {
                    InputStream is = response.body().byteStream();
                    String appName=null;
                    try {
                        appName=mcontext.getResources().getString(R.string.app_name);
                    }catch (Exception e){
                        appName="tk";
                    }

                    File file = new File(Environment.getExternalStorageDirectory(), plugin+".apk");
                    FileOutputStream fos = new FileOutputStream(file);
                    BufferedInputStream bis = new BufferedInputStream(is);
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = bis.read(buffer)) != -1) {
                        fos.write(buffer, 0, len);
                        fos.flush();
                    }
                    fos.close();
                    bis.close();
                    is.close();
                    //下载完成，自动安装插件
                    String path=file.getAbsolutePath();
                    installPlugins(path,intent);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    /**
     * 检查插件
     */
    public void checkPlugin() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        builder.sslSocketFactory(sslSocketFactory, trustAllCert);
        builder.connectTimeout(30, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true);
//                .addInterceptor(loggingInterceptor);
        OkHttpClient okHttpClient = builder.build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PublicContact.SERVER_IP)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        VersionService tmp = retrofit.create(VersionService.class);
        tmp.getHotFix(PublicContact.APP_KEY_PLUGINUPDATE,PublicContact.FRAEWORK_VERSION).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Consumer<PublicResponseEntity<List<HotFixInfo>>>() {

            @Override
            public void accept(PublicResponseEntity<List<HotFixInfo>> hotfixs) throws Exception {
                Log.i(">>>",hotfixs.getMsg());
                List<HotFixInfo> needUpdatePlugins=new ArrayList<>();
                //本地插件信息列表
                List<PluginInfo> localPlugins = RePlugin.getPluginInfoList();
                if (0!=hotfixs.getCode()||null==hotfixs.getData()){
                    return;
                }
                for (HotFixInfo hotfit: hotfixs.getData()) {
                    //远程插件信息列表
                    int code=Integer.valueOf(hotfit.getNo());
                    Log.i(TAG,hotfit.getDownloadUrl());
                    //选出需要下载的插件,
                    //可以在这里进行相关的逻辑处理,可以根据plugin的versioncode进行判断
                    for (PluginInfo pluginInfo:localPlugins){
                        //把本地版本低于服务端版本的放在一个集合中
                        if (pluginInfo.getVersion()<code){

                            needUpdatePlugins.add(hotfit);

                        }
                    }


                }


            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i("rx------",throwable.getMessage());
            }
        });

    }

    /**
     * 安装新的插件
     * @param intent
     * @param plugin
     */
    public void installPlugin(Context context,Intent intent,String plugin){
        //
        String url="http://10.130.212.39/client/h/8e9604a40dde4a109bb485a7e7043a86/2acb1495802240398d6ac07e875e081c/plugin2-debug.apk";

        downloadPlugin(context,url,intent,plugin);

    }
}
