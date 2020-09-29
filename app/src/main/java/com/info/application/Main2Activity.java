package com.info.application;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Main2Activity extends AppCompatActivity {
    float dollar_rate, euro_rate, won_rate;
    EditText dollar_rate_input, euro_rate_input, won_rate_input;
    Button btn_save, btn_realtime;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        Intent intent = getIntent();
        dollar_rate = intent.getFloatExtra("dollar_rate", 0.0f);
        euro_rate = intent.getFloatExtra("euro_rate", 0.0f);
        won_rate = intent.getFloatExtra("won_rate", 0.0f);

        dollar_rate_input = findViewById(R.id.input1);
        euro_rate_input = findViewById(R.id.input2);
        won_rate_input = findViewById(R.id.input3);

        dollar_rate_input.setText(String.valueOf(dollar_rate));
        euro_rate_input.setText(String.valueOf(euro_rate));
        won_rate_input.setText(String.valueOf(won_rate));

        btn_save = findViewById(R.id.button1);
        btn_save.setOnClickListener(new BtnListener());

        btn_realtime = findViewById(R.id.button2);
        btn_realtime.setOnClickListener(new RealtimeBtnListener());
    }

    class RealtimeBtnListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String url = "https://www.boc.cn/sourcedb/whpj/";
                        Document document = Jsoup.parse(new URL(url).openStream(), "UTF-8", url);
                        Elements elements = document.select("div.wrapper div.BOC_main div.publish div table tbody tr");
                        // 获得美元汇率
                        Element element1 = elements.get(27);
                        Elements elements11 = element1.getAllElements();
                        float dollar_rate = 100 / Float.parseFloat(elements11.get(2).text());
                        // 获得欧元汇率
                        Element element2 = elements.get(8);
                        Elements elements22 = element2.getAllElements();
                        float euro_rate = 100 / Float.parseFloat(elements22.get(2).text());
                        // 获得韩元汇率
                        Element element3 = elements.get(14);
                        Elements elements33 = element3.getAllElements();
                        float won_rate = 100 / Float.parseFloat(elements33.get(2).text());

                        SharedPreferences sp = getSharedPreferences("RateFile", Activity.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putFloat("dollar_rate", dollar_rate);
                        editor.putFloat("euro_rate", euro_rate);
                        editor.putFloat("won_rate", won_rate);
                        editor.apply();

                        Log.i("Fetch Rates", dollar_rate + " " + euro_rate + " " + won_rate);

                        Intent intent = getIntent();
                        Bundle bundle = new Bundle();
                        bundle.putFloat("dollar_rate", dollar_rate);
                        bundle.putFloat("euro_rate", euro_rate);
                        bundle.putFloat("won_rate", won_rate);
                        intent.putExtras(bundle);
                        // resultCode用于区别带回数据的数量
                        setResult(3, intent);
                        finish();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

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
                    }
                    super.handleMessage(message);
                }
            };
        }
    }


        class BtnListener implements View.OnClickListener {

            @Override
            public void onClick(View view) {
                dollar_rate = Float.parseFloat(dollar_rate_input.getText().toString());
                euro_rate = Float.parseFloat(euro_rate_input.getText().toString());
                won_rate = Float.parseFloat(won_rate_input.getText().toString());

                SharedPreferences sp = getSharedPreferences("RateFile", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putFloat("dollar_rate", dollar_rate);
                editor.putFloat("euro_rate", euro_rate);
                editor.putFloat("won_rate", won_rate);
                editor.apply();

                Intent intent = getIntent();
                Bundle bundle = new Bundle();
                bundle.putFloat("dollar_rate", dollar_rate);
                bundle.putFloat("euro_rate", euro_rate);
                bundle.putFloat("won_rate", won_rate);
                intent.putExtras(bundle);
                // resultCode用于区别带回数据的数量
                setResult(3, intent);
                finish();

            }
        }

    }
