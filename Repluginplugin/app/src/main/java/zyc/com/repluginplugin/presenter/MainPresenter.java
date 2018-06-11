package zyc.com.repluginplugin.presenter;

import zyc.com.repluginplugin.BaseContract;
import zyc.com.repluginplugin.conrtact.MainContact;

/**
 * Created by zhangyc on 2018/5/29.
 *
 */

public class MainPresenter implements MainContact.Presenter{

    @Override
    public void takeView(MainContact.View view) {


    }

    @Override
    public void dropView() {

    }

    @Override
    public void takeListView(BaseContract.ListView listView) {

    }

    @Override
    public String getData() {
        return null;
    }
}
