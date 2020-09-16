package com.info.application;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    EditText input;
    TextView output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //获取控件
        TextView layout = findViewById(R.id.title);
        layout.setText("Set text");
        Log.i("tag","msg: say hi");

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new BtnListener());

        input = findViewById(R.id.edit);
        output = findViewById(R.id.result);

    }



    class BtnListener implements View.OnClickListener {
        public void onClick(View view) {
            float t1 = Float.parseFloat(input.getText().toString());
            double t2 = (t1-32)/1.8;
            output.setText("Result:"+String.valueOf(t2)+"℃");
        }
    }

}