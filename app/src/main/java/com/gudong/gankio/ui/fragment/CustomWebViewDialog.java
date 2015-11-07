package com.gudong.gankio.ui.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;

import com.gudong.gankio.R;
import com.gudong.gankio.presenter.CustomDialogPresenter;
import com.gudong.gankio.presenter.view.ICustomDialog;

/**
 * @author mao
 */
public class CustomWebViewDialog extends DialogFragment implements ICustomDialog {
    private CustomDialogPresenter mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new CustomDialogPresenter(getContext(),this);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final View customView;
        try {
            customView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_webview, null);
        } catch (InflateException e) {
            throw new IllegalStateException("This device does not support Web Views.");
        }
        return mPresenter.makeDialog(this,customView);
    }


}
