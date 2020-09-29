package com.info.application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    EditText input,rmb_input;
    TextView output,other_output,real_time_rate;
    TextView teamA;
    TextView teamB;
    Button b1,b2,b3,b4,b5,b6,reset;
    float dollar_rate,euro_rate,won_rate ;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        SharedPreferences sharedPreferences = getSharedPreferences("RateFile", Activity.MODE_PRIVATE);

        PreferenceManager.getDefaultSharedPreferences(this);
        dollar_rate = sharedPreferences.getFloat("dollar_rate",0.0f);
        euro_rate = sharedPreferences.getFloat("euro_rate",0.0f);
        won_rate = sharedPreferences.getFloat("won_rate",0.0f);

        rmb_input = findViewById(R.id.input);
        other_output = findViewById(R.id.output);
        real_time_rate = findViewById(R.id.realtime);
        String realtime_show = "Realtime Rates:\n$="+dollar_rate+"\n€="+euro_rate+"\n₩="+won_rate;
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

    class ConfigListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, Main2Activity.class);
            Bundle bundle = new Bundle();
            bundle.putFloat("dollar_rate",dollar_rate);
            bundle.putFloat("euro_rate",euro_rate);
            bundle.putFloat("won_rate",won_rate);
            intent.putExtras(bundle);
            startActivityForResult(intent,1);

        }
    }


    class BtnListener implements View.OnClickListener {
        public void onClick(View view) {

            if(rmb_input.getText().toString().length() == 0){
                Toast.makeText(MainActivity.this,"Please Enter Value",Toast.LENGTH_SHORT).show();
            }
            else{
                float rmb = Float.parseFloat(rmb_input.getText().toString());
                double transfer = 0;
                String show = null;
                switch (view.getId()){
                    case R.id.button1:
//                    score_A += 3;
                        transfer = rmb * dollar_rate;
                        show = '$'+String.format("%.2f",transfer);
                        break;
                    case R.id.button2:
//                    score_A += 2;
                        transfer = rmb * euro_rate;
                        show = '€'+String.format("%.2f",transfer);
                        break;
                    case R.id.button3:
                        transfer = rmb * won_rate;
                        show = '₩'+String.format("%.2f",transfer);
//                    score_A += 1;
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
        getMenuInflater().inflate(R.menu.first_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.item1){
            Intent intent = new Intent(MainActivity.this, Main0Activity.class);
            startActivity(intent);

        }else if(item.getItemId() == R.id.item2){
            Intent intent = new Intent(MainActivity.this, Main3Activity.class);
            startActivity(intent);

        }else if(item.getItemId() == R.id.item3){

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 1 && resultCode == 3){
            Bundle bundle = data.getExtras();
            dollar_rate = bundle.getFloat("dollar_rate",0.0f);
            euro_rate = bundle.getFloat("euro_rate",0.0f);
            won_rate = bundle.getFloat("won_rate",0.0f);
        }
        String realtime_show = "Realtime Rates:\n$="+dollar_rate+"\n€="+euro_rate+"\n₩="+won_rate;
        real_time_rate.setText(realtime_show);
        super.onActivityResult(requestCode, resultCode, data);
    }
}