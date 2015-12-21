package com.gudong.gankio.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.gudong.gankio.R;
import com.gudong.gankio.presenter.BasePresenter;
import com.umeng.analytics.MobclickAgent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by GuDong on 10/14/15 23:05.
 * Contact with 1252768410@qq.com.
 */
public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity {
    private final String TAG = "BaseActivity";
    @Bind(R.id.toolbar)
    protected Toolbar mToolbar;
    /**
     * the presenter of this Activity
     */
    protected P mPresenter;

    /**
     * TODO use Dagger2 instance Presenter
     * use reflect instance Presenter
     */
    protected void initPresenter() throws InvocationTargetException, NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        generatePresenter(getClass());
        if (mPresenter == null) {
            generatePresenter(getClass().getSuperclass());
        }
    }

    private void generatePresenter(Class clazz) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, ClassNotFoundException {
        Class<P> tClass = getActualTypeArguments(clazz);
        if (tClass != null && tClass.getSuperclass().equals(BasePresenter.class)
                || tClass.equals(BasePresenter.class)) {
            Class<P> clz = getActualTypeArguments(tClass);
            Class<?>[] parameterTypes = new Class<?>[2];
            parameterTypes[0] = Activity.class;
            parameterTypes[1] = clz;
            //根据参数类型获取相应的构造函数
            java.lang.reflect.Constructor constructor = tClass.getConstructor(parameterTypes);
            //参数数组
            Object[] parameters = {this, this};
            //根据获取的构造函数和参数，创建实例
            mPresenter = (P) constructor.newInstance(parameters);

            if (mPresenter == null) {
                Log.d(TAG, "BaseActivity Presenter is null!");
            } else {
                Log.d(TAG, "BaseActivity Presenter is not null!");
            }
        }
    }

    private Class<P> getActualTypeArguments(Class<P> tClass) {
        Type type = tClass.getGenericSuperclass();

        if (type instanceof ParameterizedType) {
            ParameterizedType p = (ParameterizedType) type;
            Type[] arrayClasses = p.getActualTypeArguments();
            if (arrayClasses != null) {
                for (Type item : arrayClasses) {
                    if (item instanceof Class) {
                        Class<P> clz = (Class<P>) item;
                        return clz;
                    }
                }
            }
        }
        return null;
    }


    /**
     * set layout of this activity
     *
     * @return the id of layout
     */
    protected abstract int getLayout();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        ButterKnife.bind(this);
        try {
            initPresenter();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            Log.d(TAG, "InvocationTargetException--->" + e.getMessage());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            Log.d(TAG, "NoSuchMethodException--->" + e.getMessage());
        } catch (InstantiationException e) {
            e.printStackTrace();
            Log.d(TAG, "InstantiationException--->" + e.getMessage());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            Log.d(TAG, "IllegalAccessException--->" + e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Log.d(TAG, "ClassNotFoundException--->" + e.getMessage());
        }
        checkPresenterIsNull();
        initToolBar();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    private void checkPresenterIsNull() {
        if (mPresenter == null) {
            throw new IllegalStateException("please init mPresenter in initPresenter() method ");
        }
    }

    /**
     * set the id of menu
     *
     * @return if values is less then zero ,and the activity will not show menu
     */
    protected int getMenuRes() {
        return -1;
    }

    ;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (getMenuRes() < 0) return true;
        getMenuInflater().inflate(getMenuRes(), menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //don't use finish() and use onBackPressed() will be a good practice , trust me !
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    final private void initToolBar() {
        if (mToolbar == null) {
            throw new NullPointerException("please add a Toolbar in your layout.");
        }
        setSupportActionBar(mToolbar);
    }

    public void setTitle(String strTitle, boolean showHome) {
        setTitle(strTitle);
        getSupportActionBar().setDisplayShowHomeEnabled(showHome);
        getSupportActionBar().setDisplayHomeAsUpEnabled(showHome);
    }
}
