package com.gudong.gankio.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.gudong.gankio.R;
import com.gudong.gankio.presenter.BasePresenter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by GuDong on 10/14/15 23:05.
 * Contact with 1252768410@qq.com.
 */
public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity {
    @Bind(R.id.toolbar)
    protected Toolbar mToolbar;

    protected P mPresenter;

    /**
     * 设置Activity布局文件
     * @return 布局文件对应的资源id
     */
    protected abstract int getLayout();

    /**
     * menu res
     * @return the id of menu
     */
    protected abstract int getMenuRes();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        ButterKnife.bind(this);
        initPresenter();
        checkPresenterIsNull();
        initToolBar();
    }

    /**
     * TODO use Dagger2 instance Presenter
     */
    protected abstract void initPresenter();

    private void checkPresenterIsNull(){
        if(mPresenter == null){
            throw new IllegalStateException("please init mPresenter in initPresenter method ");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(getMenuRes(), menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    final private void initToolBar() {
        if(mToolbar == null){
            throw new NullPointerException("请在布局文件中加入Toolbar");
        }
        setSupportActionBar(mToolbar);
    }

    public void setTitle(String strTitle,boolean showHome){
        setTitle(strTitle);
        getSupportActionBar().setDisplayShowHomeEnabled(showHome);
        getSupportActionBar().setDisplayHomeAsUpEnabled(showHome);
    }
}
