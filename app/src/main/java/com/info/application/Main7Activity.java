package com.info.application;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main7Activity extends AppCompatActivity {
    TextView title,result;
    EditText input;
    float rate;
    private final static String TAG = "---TAGGING---";
    Boolean flag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);

        title = findViewById(R.id.title);
        result = findViewById(R.id.result);
        input = findViewById(R.id.input);

        // 获取传输过来的数据
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String title_str = bundle.getString("currency");
        rate = bundle.getFloat("rate");

        // 设置标题
        title.setText(title_str);
        input.addTextChangedListener(new EditTextListener());
    }

    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("-?[0-9]+\\.?[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    class EditTextListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if(!charSequence.toString().isEmpty()){
                flag = isNumeric(charSequence.toString());
                if(flag){
                    float transfer_money = rate * Float.parseFloat(charSequence.toString());
                    result.setText(String.valueOf(transfer_money));
                }
                else{
                    result.setText("Input Invalid Character!");
                }
            }
            else{
                result.setText("");
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }

}