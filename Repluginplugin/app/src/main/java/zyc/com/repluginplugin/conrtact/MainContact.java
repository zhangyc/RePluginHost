package zyc.com.repluginplugin.conrtact;

import zyc.com.repluginplugin.BaseContract;

/**
 * Created by zhangyc on 2018/5/29.
 * 纽带，用来联系presenter和view
 */

public interface MainContact {
    interface Presenter extends BaseContract.Presenter<View>{
       String getData();



    }
    interface View extends BaseContract.View{
        @Override
        void showToast(String string);
    }

}
