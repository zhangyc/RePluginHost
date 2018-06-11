package zyc.com.repluginplugin;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by zhangyc on 2018/5/28.
 * 封装view的相关基本操作
 */

public abstract class BaseActivity extends AppCompatActivity{

    // 声明变量
    public Context mContext;
    public Activity mActivity;

    protected void initBundle() {
    }

    protected void initEventOrData() {
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mContext = this;
        mActivity = this;
        initFlag();
        setContentView(getContentView());
        init();
        initBundle();
        initEventOrData();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    //获取布局
    protected abstract int getContentView();

    //设置页面Flag
    protected void initFlag() {
    }

    protected void init() {
    }

    private boolean isFirst = true;

    /**
     * 吐丝 要求是3 秒钟
     */
    public void showToast(String msg) {
        ToastUtils.show(mContext, msg, 3000);
    }

    /**
     * 吐丝，对资源信息  3秒钟
     *
     * @param resId
     */
    public void showToast(int resId) {
        ToastUtils.show(mContext, resId, 3000);
    }

    /**
     * 对吐丝进行关闭
     */
    public void cancelToast() {
        ToastUtils.cancelToast();
    }

    protected void removeFaceFragment(String tag) {
        Fragment fragment = getFragmentManager().findFragmentByTag(tag);
        if (fragment != null) {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.remove(fragment);
            transaction.commit();
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    /**
     * 带参数的activity跳转
     *
     * @param bundle
     * @param clx
     * @param <T>
     */
    public <T> void toActivity(Bundle bundle, Class<T> clx) {
        Intent intent = new Intent(this, clx);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * activity跳转 非带参数
     *
     * @param activity
     * @param <E>
     */
    public <E> void toActivity(Class<E> activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

    /**
     * activity跳转(返回结果)
     *
     * @param activity
     * @param <E>
     */
    public <E> void toActivityForResult(Class<E> activity, Bundle extras,
                                        int requestCode) {
        Intent intent = new Intent(this, activity);
        intent.putExtras(extras);
        startActivityForResult(intent, requestCode);
        //overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
    }

    /**
     * activity跳转(返回结果)
     *
     * @param activity
     * @param <E>
     */
    public <E> void toActivityForResult(Class<E> activity,
                                        int requestCode) {
        Intent intent = new Intent(this, activity);
        startActivityForResult(intent, requestCode);
    }
}
