package debug;

import com.longf.lib_common.mvp.BaseMvpActivity;
import com.longf.module_page2.R;
import com.longf.module_page2.fragment.MainPage2Fragment;

public class MainActivity extends BaseMvpActivity {
    @Override
    public int onBindLayout() {
        return R.layout.debug_activity_main;
    }

    @Override
    public void initView() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fl_debug, new MainPage2Fragment())
                .commit();
    }

    @Override
    public void initData() {

    }

    @Override
    public void injectPresenter() {

    }

    @Override
    public boolean enableToolbar() {
        return false;
    }
}
