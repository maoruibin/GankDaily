/*
 *
 * Copyright (C) 2015 Drakeet <drakeet.me@gmail.com>
 * Copyright (C) 2015 GuDong <maoruibin9035@gmail.com>
 *
 * Meizhi is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Meizhi is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Meizhi.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.gudong.gankio.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.gudong.gankio.R;
import com.gudong.gankio.ui.view.IGirlFaceView;
import com.gudong.gankio.util.TaskUtils;
import com.orhanobut.logger.Logger;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by GuDong on 11/2/15 18:22.
 * Contact with gudong.name@gmail.com.
 */
public class GirlFacePresenter extends BasePresenter<IGirlFaceView> {
    public GirlFacePresenter(Activity context, IGirlFaceView view) {
        super(context, view);
    }

    public void saveFace(String url) {
        if (!TextUtils.isEmpty(url)) {
            String fileName = url.substring(url.lastIndexOf("/")+1);
            saveImageToSdCard(mContext, url, fileName);
        }
    }

    private void saveImageToSdCard(final Context context,final  String url,final  String title) {
        TaskUtils.executeAsyncTask(new AsyncTask<Object, Object, Boolean>() {
            @Override
            protected Boolean doInBackground(Object... params) {
                Bitmap bmp = null;
                try {
                    bmp = Picasso.with(context).load(url).get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (bmp == null) {
                    return false;
                }

                // 首先保存图片
                File appDir = new File(getSDPath(), "Meizhi2");
                if (!appDir.exists()) {
                    boolean is = appDir.mkdir();
                    if(is){
                        Logger.i("create suc");
                    }else{
                        Logger.i("create fail");
                    }
                }
                File file = new File(appDir, title);

                try {
                    FileOutputStream fos = new FileOutputStream(file);
                    bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }

                // 其次把文件插入到系统图库
                try {
                    MediaStore.Images.Media.insertImage(context.getContentResolver(),
                            file.getAbsolutePath(), title, null);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                // 最后通知图库更新
                Intent scannerIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                        Uri.parse("file://" + file.getAbsolutePath()));
                context.sendBroadcast(scannerIntent);

                return true;
            }

            @Override
            protected void onPostExecute(Boolean result) {
                super.onPostExecute(result);
                String msg;
                if (result) {
                    File appDir = new File(Environment.getExternalStorageDirectory(), "Meizhi");
                    if (!appDir.exists()) {
                        appDir.mkdir();
                    }
                    msg = String.format(context.getString(R.string.picture_has_save_to),
                            appDir.getAbsolutePath());
                    mView.saveSuccess(msg);
                } else {
                    msg = context.getString(R.string.picture_save_fail);
                    mView.showFailInfo(msg);
                }

            }
        });
    }

    public String getSDPath(){
        return Environment.getExternalStorageDirectory().toString();
    }


}
