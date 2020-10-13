package com.info.application;



import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


public class Main5Activity extends ListActivity {
    int N = 10;
    Handler handler;
    ArrayList listItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        listItems = new ArrayList<HashMap<String, String>>();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("---TAGGING---","进入run()");
                Message message = handler.obtainMessage(5);
                try {
                    String url = "https://www.boc.cn/sourcedb/whpj/";
                    Document document = Jsoup.parse(new URL(url).openStream(), "UTF-8", url);
                    Elements elements = document.select("div.wrapper div.BOC_main div.publish div table tbody tr");
                    int i;
                    for (i = 2; i <= N+1; i++) {
                        // 获得汇率
                        Element element1 = elements.get(i);
                        Elements elements11 = element1.getAllElements();
                        Log.i("---TAGGING---",elements11.text());
                        float rate = 100 / Float.parseFloat(elements11.get(2).text());
                        String name = elements11.get(0).text();
                        HashMap<String, String> map = new HashMap<>();
                        map.put("Currency", name);
                        map.put("Rate", String.valueOf(rate));
                        Log.i("TAGGING" + name, String.valueOf(rate));
                        listItems.add(map);
                    }
                    message.obj = listItems;
                    handler.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        handler = new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(Message message) {
                if (message.what == 5) {
                    ListAdapter adapter = new SimpleAdapter(
                            Main5Activity.this,
                            listItems,
                            R.layout.list_item,
                            new String[]{"Currency", "Rate"},
                            new int[]{R.id.currency, R.id.rate});

                    setListAdapter(adapter);
                }
                super.handleMessage(message);
            }
        };


    }

}