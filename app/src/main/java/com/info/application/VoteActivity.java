package com.info.application;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class VoteActivity extends Activity {
    Button btn_1,btn_2,btn_3;
    TextView show;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vote_activity);

        btn_1 = findViewById(R.id.button1);
        btn_2 = findViewById(R.id.button2);
        btn_3 = findViewById(R.id.button3);
        show = findViewById(R.id.show);

        btn_1.setOnClickListener(new BtnListener());
        btn_2.setOnClickListener(new BtnListener());
        btn_3.setOnClickListener(new BtnListener());
    }

    class BtnListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.button1:
                    new VoteTask().execute("agree");
                    show.setText("You Agreed!");
                    break;
                case R.id.button2:
                    new VoteTask().execute("disagree");
                    show.setText("You Disagreed!");
                    break;
                case R.id.button3:
                    new VoteTask().execute("abstain");
                    show.setText("You abstained to VOTE!");
                    break;
                default:
                    break;
            }
        }
    }
}
