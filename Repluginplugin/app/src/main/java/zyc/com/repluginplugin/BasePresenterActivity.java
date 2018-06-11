package zyc.com.repluginplugin;

import android.os.Bundle;

import javax.inject.Inject;

import zyc.com.repluginplugin.component.ActivityComponent;
import zyc.com.repluginplugin.component.DaggerActivityComponent;

/**
 * Created by zhangyc on 2018/5/28.
 * 抽象类，主要完成presenter和view的相互注入，以及完成dargger2的初始化
 */

public abstract class BasePresenterActivity<T extends BaseContract.Presenter> extends BaseActivity{
    @Inject
    public T mPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initInject(getInject());
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init() {
        mPresenter.takeView((BaseContract.View) this);
        super.init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.dropView();
    }

    protected ActivityComponent getInject(){
        return DaggerActivityComponent
                .builder()
                .build();
    }

    public abstract void initInject(ActivityComponent component);

}
