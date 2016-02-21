package com.cleveroad.slidingtutorial.sample;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by maxuejun on 2016/2/21.
 */
public class AsyncTastActivity extends Activity implements View.OnClickListener{
    private TextView textView ;
    private Button button ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asynctask);
        init();
    }

    private void init(){
        button = (Button)findViewById(R.id.btnDownload);
        textView = (TextView)findViewById(R.id.textView);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnDownload:
                //要下载的文件地址
                String[] urls = {
                        "http://blog.csdn.net/iispring/article/details/47115879",
                        "http://blog.csdn.net/iispring/article/details/47180325",
                        "http://blog.csdn.net/iispring/article/details/47300819",
                        "http://blog.csdn.net/iispring/article/details/47320407",
                        "http://blog.csdn.net/iispring/article/details/47622705",
                        "http://blog.csdn.net/iispring/article/details/50500106",
                        "http://www.open-open.com/lib/view/open1455889601808.html"
                };
                DownloadTask downloadTask = new DownloadTask();
                downloadTask.execute(urls);
        }
    }

    private class DownloadTask extends AsyncTask<String,Object,Long>{

        @Override
        protected Long doInBackground(String... params) {
            Log.i("iSpring", "DownloadTask -> doInBackground, Thread name: " + Thread.currentThread().getName());
            //totalByte表示所有下载的文件的总字节数
            long totalByte = 0;
            //params是一个string的数组
            for(String url:params){
                Object[] result = downloadStringFile(url);
                int byteCount = (int)result[0];
                totalByte += byteCount;
                publishProgress(result);
                //如果AsyncTask被调用了cancel()方法，那么任务取消，跳出for循环
                if(isCancelled()){
                    break;
                }
            }
            //将总共下载的字节数作为结果返回
            return totalByte;
        }

        //下载文件后返回一个Object数组：下载文件的字节数以及下载的博客的名字
        private Object[] downloadStringFile(String str){
            Object[] result = new Object[2];
            int byteCunt =0;
            String blogName = "";
            HttpURLConnection conn = null;
            try{
                URL url = new URL(str);
                conn = (HttpURLConnection)url.openConnection();
                InputStream in = conn.getInputStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];
                int length = -1;
                while ((length=in.read(buf))!=-1){
                    baos.write(buf,0,length);
                    byteCunt+=length;
                }
                String respone = new String(baos.toByteArray(),"utf-8");
                int startIndex = respone.indexOf("<title>");
                if(startIndex>0){
                    startIndex+=7;
                    int endIndex = respone.indexOf("</title>");
                    if(endIndex>startIndex){
                        //解析出博客中的标题
                        blogName = respone.substring(startIndex,endIndex);
                    }
                }
            }catch (MalformedURLException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }finally {
                if(conn!=null){
                    conn.disconnect();
                }
            }
            result[0] = byteCunt;
            result[1] = blogName;
            return result;
        }

        public DownloadTask() {
            super();
        }

        @Override
        protected void onPreExecute() {
            Log.i("iSpring", "DownloadTask -> onPreExecute, Thread name: " + Thread.currentThread().getName());
            super.onPreExecute();
            button.setEnabled(false);
            textView.setText("开始下载...");
        }

        @Override
        protected void onPostExecute(Long aLong) {
            Log.i("iSpring", "DownloadTask -> onPostExecute, Thread name: " + Thread.currentThread().getName());
            super.onPostExecute(aLong);
            String text = textView.getText().toString();
            text += "\n全部下载完成，总共下载了" + aLong + "个字节";
            textView.setText(text);
            button.setEnabled(true);
        }

        @Override
        protected void onProgressUpdate(Object... values) {
            Log.i("iSpring", "DownloadTask -> onProgressUpdate, Thread name: " + Thread.currentThread().getName());
            super.onProgressUpdate(values);
            int byteCount = (int)values[0];
            String blogName = (String)values[1];
            String text = textView.getText().toString();
            text += "\n博客《" + blogName + "》下载完成，共" + byteCount + "字节";
            textView.setText(text);
        }

        @Override
        protected void onCancelled(Long l) {
            Log.i("iSpring", "DownloadTask -> onCancelled, Thread name: " + Thread.currentThread().getName());
            super.onCancelled();
            textView.setText("取消下载");
            button.setEnabled(true);
        }

    }
}
