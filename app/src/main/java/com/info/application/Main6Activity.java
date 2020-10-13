package com.info.application;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class Main6Activity extends AppCompatActivity {
    int N = 20;
    GridView gridView;
    ArrayAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);
        gridView = findViewById(android.R.id.list);
        List<String> str = new ArrayList();
        int i;
        for (i = 0; i < N; i++) {
            str.add("Str" + (i + 1));
        }
        adapter = new ArrayAdapter<>(
                Main6Activity.this, android.R.layout.simple_list_item_1, str);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new MyGridClick());
        gridView.setOnItemLongClickListener(new MyLongClick());

    }

    class MyGridClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            String str = (String) gridView.getItemAtPosition(i);
            Toast.makeText(Main6Activity.this, "Remove" + str, Toast.LENGTH_LONG).show();
            Log.i("----------", gridView.getItemAtPosition(i) + "");
            adapter.remove(gridView.getItemAtPosition(i));
        }
    }

    class MyLongClick implements AdapterView.OnItemLongClickListener {

        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
            new AlertDialog.Builder(Main6Activity.this)
                    .setTitle("Dialog")
                    .setMessage("Do you want to delete the item?")
                    .setPositiveButton("Yes", null)
                    .setNegativeButton("No",null)
                    .show();
            adapter.remove(gridView.getItemAtPosition(i));
            return true;
        }
    }

}