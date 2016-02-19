package com.example.maxuejun.firstapptest.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.maxuejun.firstapptest.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AsyncTaskActivity extends AppCompatActivity implements View.OnClickListener{
    private final String TAG = "maxuejun";
    private static final String imageURL = "Http://www.baidu.com";
    final ImageLoader loader = new ImageLoader();
    @Bind(R.id.asyncTaskProgress)
    ProgressBar mProgressBar;
    @Bind(R.id.asyncTaskImage)
    ImageView mImageView;
    @Bind(R.id.asyncTaskLoad)
    Button btLoadImage;
    @Bind(R.id.asyncTaskAbort)
    Button btAbortImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        btLoadImage.setOnClickListener(this);
        btAbortImage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.asyncTaskLoad:
                loader.execute(imageURL);
                break;
            case R.id.asyncTaskAbort:
                loader.cancel(true);
                break;

        }
    }

    private class ImageLoader extends AsyncTask<String, Integer, Bitmap> {

        @Override
        protected void onPreExecute() {
            //初始化
            super.onPreExecute();
            btLoadImage.setEnabled(false);
            btAbortImage.setEnabled(true);
            mProgressBar.setVisibility(View.VISIBLE);
            mImageView.setImageResource(R.mipmap.ic_launcher);
        }

        @Override
        protected Bitmap doInBackground(String... url) {
            //耗时操作,最核心的操作
            try{
                URL mURl;
                HttpURLConnection conn = null;
                InputStream in=null;
                OutputStream out =null;
                final String filename = "loacl_temp_image";
                try{
                    mURl = new URL(url[0]);
                    conn = (HttpURLConnection)mURl.openConnection();
                    Log.i(TAG,"conn="+conn);
                    in = conn.getInputStream();
                    out = openFileOutput(filename, Context.MODE_PRIVATE);
                    byte[] buf = new byte[8196];
                    int seg ;
                    final long total = conn.getContentLength();
                    long current = 0;
                    Log.i(TAG,"run here===");
                    Log.i(TAG,"total="+total);
                    while (!isCancelled()&&(seg=in.read(buf))!=-1){
                        out.write(buf,0,seg);
                        current+=seg;
                        int progress = (int)((float)current/(float)total*100f);
                        Log.i(TAG,"seg="+seg+",progress = "+progress);
                        publishProgress(progress);
                        SystemClock.sleep(100);
                    }
                }finally {
                    if(conn!=null){
                        conn.disconnect();
                    }
                    if(in!=null){
                        in.close();
                    }
                    if(out!=null){
                        out.close();
                    }
            }
                return BitmapFactory.decodeFile(getFileStreamPath(filename).getAbsolutePath());
            }catch (MalformedURLException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress[0]);
        }

        @Override
        protected void onPostExecute(Bitmap image) {
            if(image!=null){
                mImageView.setImageBitmap(image);
            }
            mProgressBar.setProgress(100);
            //mProgressBar.setVisibility(View.GONE);
            btAbortImage.setEnabled(false);
        }

        @Override
        protected void onCancelled() {
            //中断
            super.onCancelled();
        }
    }
}
