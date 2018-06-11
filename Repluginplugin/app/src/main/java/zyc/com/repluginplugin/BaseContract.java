package zyc.com.repluginplugin;

import android.app.Activity;

import java.util.List;

/**
 * Created by zhangyc on 2018/5/28.
 */

public interface BaseContract {
    interface View{
        Activity getActivity();
        void showToast(String string);
        void showToast(int id);
        void showLoadingDialog();
        void closeLoadingDialog();
    }
    interface Presenter<T extends BaseContract.View>{
        void takeView(T t);
        void dropView();
        void takeListView(BaseContract.ListView listView);
    }
    interface ListView<T>{

        void listViewFailed(int tabPosition);

        void listViewEmpty(int tabPosition);

        void listViewAppend(int tabPosition,List<? extends T> datas);

        void listViewInsert(int tabPosition,int listPosition,T data);

        void listViewSet(int tabPosition,List<? extends T> datas);

        void listViewUpdate(int tabPosition, int listPosition);

        void listViewDelete(int tabPosition ,int fromPosition, int deleteSize);
    }
}
