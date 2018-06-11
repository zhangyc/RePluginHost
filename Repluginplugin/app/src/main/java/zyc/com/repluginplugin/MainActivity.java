package zyc.com.repluginplugin;
import android.os.Bundle;

import zyc.com.repluginplugin.component.ActivityComponent;

/**
 * view
 */
public class MainActivity extends BasePresenterActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void initInject(ActivityComponent component) {
        component.inject(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        super.init();

    }
}
