package debug;

import com.longf.lib_common.mvp.BaseMvpActivity;
import com.longf.module_page3.R;
import com.longf.module_page3.fragment.MainPage3Fragment;

public class MainActivity extends BaseMvpActivity {
    @Override
    public int onBindLayout() {
        return R.layout.debug_activity_main;
    }

    @Override
    public void initView() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fl_debug, new MainPage3Fragment())
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
