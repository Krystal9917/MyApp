package com.info.application;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Main2Activity extends AppCompatActivity {
    double dollar_rate,euro_rate,won_rate;
    EditText dollar_rate_input,euro_rate_input,won_rate_input;
    Button btn_save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        Intent intent = getIntent();
        dollar_rate = intent.getDoubleExtra("dollar_rate",0.00);
        euro_rate = intent.getDoubleExtra("euro_rate",0.00);
        won_rate = intent.getDoubleExtra("won_rate",0.00);

        dollar_rate_input = findViewById(R.id.input1);
        euro_rate_input = findViewById(R.id.input2);
        won_rate_input = findViewById(R.id.input3);

        dollar_rate_input.setText(String.valueOf(dollar_rate));
        euro_rate_input.setText(String.valueOf(euro_rate));
        won_rate_input.setText(String.valueOf(won_rate));

        btn_save = findViewById(R.id.button1);
        btn_save.setOnClickListener(new BtnListener());
    }

    class BtnListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            dollar_rate = Double.parseDouble(dollar_rate_input.getText().toString());
            euro_rate = Double.parseDouble(euro_rate_input.getText().toString());
            won_rate = Double.parseDouble(won_rate_input.getText().toString());
            Intent intent = getIntent();
            Bundle bundle = new Bundle();
            bundle.putDouble("dollar_rate",dollar_rate);
            bundle.putDouble("euro_rate",euro_rate);
            bundle.putDouble("won_rate",won_rate);
            intent.putExtras(bundle);
            // resultCode用于区别带回数据的数量
            setResult(3,intent);
            finish();

        }
    }
}
