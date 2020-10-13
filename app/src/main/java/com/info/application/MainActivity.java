package com.info.application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {
    EditText input, rmb_input;
    TextView output, other_output, real_time_rate;
    TextView teamA;
    TextView teamB;
    Button b1, b2, b3, b4, b5, b6, reset;
    float dollar_rate, euro_rate, won_rate;
    SharedPreferences.Editor editor;
    private final Timer timer = new Timer();
    private TimerTask task;
    Handler handler;
    Message message;
    SharedPreferences sharedPreferences;


    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        sharedPreferences = getSharedPreferences("RateFile", Activity.MODE_PRIVATE);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // 获得并保存汇率
                        parser();
                    }
                }).start();
                msg.what = 1;
                super.handleMessage(msg);
            }
        };

        task = new TimerTask() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                message = handler.obtainMessage(1);
                Log.i("TAGGING", "---Task Run---");
                handler.sendMessage(message);
            }
        };

        // 单位为毫秒
        timer.schedule(task, 0, 24 * 60 * 60 * 1000);

        PreferenceManager.getDefaultSharedPreferences(this);
        dollar_rate = sharedPreferences.getFloat("dollar_rate", 0.0f);
        euro_rate = sharedPreferences.getFloat("euro_rate", 0.0f);
        won_rate = sharedPreferences.getFloat("won_rate", 0.0f);

        rmb_input = findViewById(R.id.input);
        other_output = findViewById(R.id.output);
        real_time_rate = findViewById(R.id.realtime);
        String realtime_show = "Realtime Rates:\n$=" + dollar_rate + "\n€=" + euro_rate + "\n₩=" + won_rate;
        real_time_rate.setText(realtime_show);
        b1 = findViewById(R.id.button1);
        b2 = findViewById(R.id.button2);
        b3 = findViewById(R.id.button3);
        b4 = findViewById(R.id.button4);
        b1.setOnClickListener(new BtnListener());
        b2.setOnClickListener(new BtnListener());
        b3.setOnClickListener(new BtnListener());
        b4.setOnClickListener(new ConfigListener());


    }

    class ConfigListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, Main2Activity.class);
            Bundle bundle = new Bundle();
            bundle.putFloat("dollar_rate", dollar_rate);
            bundle.putFloat("euro_rate", euro_rate);
            bundle.putFloat("won_rate", won_rate);
            intent.putExtras(bundle);
            startActivityForResult(intent, 1);

        }
    }


    class BtnListener implements View.OnClickListener {
        public void onClick(View view) {

            if (rmb_input.getText().toString().length() == 0) {
                Toast.makeText(MainActivity.this, "Please Enter Value", Toast.LENGTH_SHORT).show();
            } else {
                float rmb = Float.parseFloat(rmb_input.getText().toString());
                double transfer = 0;
                String show = null;
                switch (view.getId()) {
                    case R.id.button1:
                        transfer = rmb * dollar_rate;
                        show = '$' + String.format("%.2f", transfer);
                        break;
                    case R.id.button2:
                        transfer = rmb * euro_rate;
                        show = '€' + String.format("%.2f", transfer);
                        break;
                    case R.id.button3:
                        transfer = rmb * won_rate;
                        show = '₩' + String.format("%.2f", transfer);
                        break;
                    default:
                        break;
                }
                other_output.setText(show);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.first_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.item1) {
            Intent intent = new Intent(MainActivity.this, Main0Activity.class);
            startActivity(intent);

        } else if (item.getItemId() == R.id.item2) {
            Intent intent = new Intent(MainActivity.this, Main3Activity.class);
            startActivity(intent);

        } else if (item.getItemId() == R.id.item3) {

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == 3) {
            Bundle bundle = data.getExtras();
            dollar_rate = bundle.getFloat("dollar_rate", 0.0f);
            euro_rate = bundle.getFloat("euro_rate", 0.0f);
            won_rate = bundle.getFloat("won_rate", 0.0f);
        }
        String realtime_show = "Realtime Rates:\n$=" + dollar_rate + "\n€=" + euro_rate + "\n₩=" + won_rate;
        real_time_rate.setText(realtime_show);
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void parser() {
        try {
            Log.i("TAG", "Get HTML Content!");
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


            SharedPreferences.Editor editor = sharedPreferences.edit();
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