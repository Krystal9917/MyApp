package com.info.application;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;

public class Main3Activity extends AppCompatActivity {

    private String TAG = "Thread 1";
    Handler handler;
    Button btn;
    SharedPreferences sp;

    private String inputStream2String(InputStream inputStream) throws IOException {
        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(inputStream, "gb2312");
        while (true) {
            int rsz = in.read(buffer, 0, buffer.length);
            if (rsz < 0)
                break;
            out.append(buffer, 0, rsz);
        }
        return out.toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        sp = getSharedPreferences("RateFile", Activity.MODE_PRIVATE);

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "--- Running ---");
                // 解析页面
                parser();

                //获取Message对象
                Message message = handler.obtainMessage(5);
                message.obj = "Hello from run()";
                handler.sendMessage(message);
            }
        }).start();

        handler = new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(Message message) {
                if (message.what == 5) {
                    String str = (String) message.obj;
                    Log.i(TAG, "--- Thread 0 Handle Message: Get Message = " + str + " ---");
                }
                super.handleMessage(message);
            }
        };

    }

    public void parser(){
        try {
            Log.i(TAG, "Get HTML Content!");
            String url = "https://www.boc.cn/sourcedb/whpj/";
            Document document = Jsoup.parse(new URL(url).openStream(), "UTF-8", url);
            Elements elements = document.select("div.wrapper div.BOC_main div.publish div table tbody tr");
            // 获得美元汇率
            Element element1 = elements.get(27);
            Elements elements11 = element1.getAllElements();
            Log.i("ELEMENTS11", elements11.text());
            float dollar_rate = 100 / Float.parseFloat(elements11.get(2).text());
            // 获得欧元汇率
            Element element2 = elements.get(8);
            Elements elements22 = element2.getAllElements();
            Log.i("ELEMENTS22", elements22.text());
            float euro_rate = 100 / Float.parseFloat(elements22.get(2).text());
            // 获得韩元汇率
            Element element3 = elements.get(14);
            Elements elements33 = element3.getAllElements();
            Log.i("ELEMENTS33", elements33.text());
            float won_rate = 100 / Float.parseFloat(elements33.get(2).text());


            SharedPreferences.Editor editor = sp.edit();
            editor.putFloat("dollar_rate", dollar_rate);
            editor.putFloat("euro_rate", euro_rate);
            editor.putFloat("won_rate", won_rate);
            editor.apply();

            Log.i("DOLLAR", String.valueOf(dollar_rate));
            Log.i("EURO", String.valueOf(euro_rate));
            Log.i("WON", String.valueOf(won_rate));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
