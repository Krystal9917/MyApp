package com.info.application;


import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


public class Main5Activity extends ListActivity {
    int N = 20;
    Handler handler;
    ArrayList listItems;
    ListView listView;
    ListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        listItems = new ArrayList<HashMap<String, String>>();
        listView = findViewById(android.R.id.list);
        listView.setOnItemClickListener(new MyListClick());

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("---TAGGING---", "进入run()");
                Message message = handler.obtainMessage(5);
                try {
                    String url = "https://www.boc.cn/sourcedb/whpj/";
                    Document document = Jsoup.parse(new URL(url).openStream(), "UTF-8", url);
                    Elements elements = document.select("div.wrapper div.BOC_main div.publish div table tbody tr");
                    int i;
                    for (i = 2; i <= N + 1; i++) {
                        // 获得汇率
                        Element element1 = elements.get(i);
                        Elements elements11 = element1.getAllElements();
                        String splits[] = elements11.text().split(" ");
                        Log.i("---TAGGING---", splits[0] + " : " + splits[2]);
                        float rate = 100 / Float.parseFloat(splits[2]);
                        String name = splits[0];
                        String time = splits[7];
                        HashMap<String, String> map = new HashMap<>();
                        map.put("Currency", name);
                        map.put("Rate", String.valueOf(rate));
                        map.put("Time", time);
                        listItems.add(map);
                    }
                    handler.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        handler = new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(Message message) {
                if (listItems.size() != 0) {
                    adapter = new SimpleAdapter(
                            Main5Activity.this,
                            listItems,
                            R.layout.list_item,
                            new String[]{"Currency", "Rate"},
                            new int[]{R.id.currency, R.id.rate});
                    setListAdapter(adapter);
                } else {
                    listView.setEmptyView(findViewById(R.id.no_data));
                }
                super.handleMessage(message);
            }
        };
    }

    class MyAdapter extends BaseAdapter {
        public MyAdapter(Context context,
                         int resource,
                         ArrayList<HashMap<String, String>> list) {
            super();
        }

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            return null;
        }
    }

    class MyListClick implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            HashMap<String, String> map = (HashMap<String, String>) listView.getItemAtPosition(i);
            String title = map.get("Currency");
            String content = map.get("Rate");

            Intent intent = new Intent(Main5Activity.this, Main7Activity.class);
            Bundle bundle = new Bundle();
            float rate = Float.parseFloat(content);
            bundle.putFloat("rate",rate);
            bundle.putString("currency",title);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

}