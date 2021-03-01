package debug;

import com.longf.lib_common.mvp.BaseMvpActivity;
import com.longf.module_main.R;
import com.longf.module_main.fragment.MainFragment;

public class MainActivity extends BaseMvpActivity {
    @Override
    public int onBindLayout() {
        return R.layout.debug_activity_main;
    }

    @Override
    public void initView() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fl_debug, new MainFragment())
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
