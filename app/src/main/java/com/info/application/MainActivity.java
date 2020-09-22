package com.info.application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
    double dollar_rate = 0.15;
    double euro_rate = 0.13;
    double won_rate = 171.39;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        //获取控件
//        TextView layout = findViewById(R.id.title);
//        layout.setText("Set text");
//        Log.i("tag","msg: say hi");
//
//        Button button = findViewById(R.id.button);
//        button.setOnClickListener(new BtnListener());
//
//        input = findViewById(R.id.edit);
//        output = findViewById(R.id.result);

//        teamA = findViewById(R.id.textView2);
//        teamB = findViewById(R.id.textView4);
//
//        b1 = findViewById(R.id.button1);
//        b2 = findViewById(R.id.button2);
//        b3 = findViewById(R.id.button3);
//        b4 = findViewById(R.id.button4);
//        b5 = findViewById(R.id.button5);
//        b6 = findViewById(R.id.button6);
//        reset = findViewById(R.id.button7);
//        b1.setOnClickListener(new BtnListener());
//        b2.setOnClickListener(new BtnListener());
//        b3.setOnClickListener(new BtnListener());
//        b4.setOnClickListener(new BtnListener());
//        b5.setOnClickListener(new BtnListener());
//        b6.setOnClickListener(new BtnListener());
//        reset.setOnClickListener(new BtnListener());

        rmb_input = findViewById(R.id.input);
        other_output = findViewById(R.id.output);
        real_time_rate = findViewById(R.id.realtime);
        String realtime_show = "Realtime Rates: $="+dollar_rate+" €="+euro_rate+" ₩="+won_rate;
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
            Intent intent = new Intent(MainActivity.this,Main2Activity.class);
//            intent.putExtra("dollar_rate",dollar_rate);
//            intent.putExtra("euro_rate",euro_rate);
//            intent.putExtra("won_rate",won_rate);
//            startActivity(intent);
            Bundle bundle = new Bundle();
            bundle.putDouble("dollar_rate",dollar_rate);
            bundle.putDouble("euro_rate",euro_rate);
            bundle.putDouble("won_rate",won_rate);
            intent.putExtras(bundle);
            startActivityForResult(intent,1);

        }
    }


    class BtnListener implements View.OnClickListener {
        public void onClick(View view) {
//            int score_A = Integer.parseInt(teamA.getText().toString());
//            int score_B = Integer.parseInt(teamB.getText().toString());

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

        }else if(item.getItemId() == R.id.item2){

        }else if(item.getItemId() == R.id.item3){

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 1 && resultCode == 3){
            Bundle bundle = data.getExtras();
            dollar_rate = bundle.getDouble("dollar_rate",0.00);
            euro_rate = bundle.getDouble("euro_rate",0.00);
            won_rate = bundle.getDouble("won_rate",0.00);
        }
        String realtime_show = "Realtime Rates: $="+dollar_rate+" €="+euro_rate+" ₩="+won_rate;
        real_time_rate.setText(realtime_show);
        super.onActivityResult(requestCode, resultCode, data);
    }
}