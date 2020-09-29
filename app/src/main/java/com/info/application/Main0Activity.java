package com.info.application;


import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


public class Main0Activity extends AppCompatActivity {
    TextView teamA, teamB;
    Button b1, b2, b3, b4, b5, b6, reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        teamA = findViewById(R.id.textView2);
        teamB = findViewById(R.id.textView4);

        b1 = findViewById(R.id.button1);
        b2 = findViewById(R.id.button2);
        b3 = findViewById(R.id.button3);
        b4 = findViewById(R.id.button4);
        b5 = findViewById(R.id.button5);
        b6 = findViewById(R.id.button6);
        reset = findViewById(R.id.button7);
        b1.setOnClickListener(new BtnListener());
        b2.setOnClickListener(new BtnListener());
        b3.setOnClickListener(new BtnListener());
        b4.setOnClickListener(new BtnListener());
        b5.setOnClickListener(new BtnListener());
        b6.setOnClickListener(new BtnListener());
        reset.setOnClickListener(new BtnListener());


    }


    class BtnListener implements View.OnClickListener {
        public void onClick(View view) {
            int score_A = Integer.parseInt(teamA.getText().toString());
            int score_B = Integer.parseInt(teamB.getText().toString());
            switch (view.getId()) {
                case R.id.button1:
                    score_A += 3;
                    break;
                case R.id.button2:
                    score_A += 2;
                    break;
                case R.id.button3:
                    score_A += 1;
                    break;
                case R.id.button4:
                    score_B += 3;
                    break;
                case R.id.button5:
                    score_B += 2;
                    break;
                case R.id.button6:
                    score_B += 1;
                    break;
                case R.id.button7:
                    score_A = 0;
                    score_B = 0;
                    break;
                default:
                    break;
            }
            teamA.setText(String.valueOf(score_A));
            teamB.setText(String.valueOf(score_B));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String score_A = ((TextView)findViewById(R.id.textView4)).getText().toString();
        String score_B = ((TextView)findViewById(R.id.textView2)).getText().toString();
        Log.i("SAVE", "onSaveInstanceState");
        outState.putString("team_A_score",score_A);
        outState.putString("team_B_score",score_B);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String score_A = savedInstanceState.getString("team_A_score");
        String score_B = savedInstanceState.getString("team_B_score");
        Log.i("RESTORE", "onRestoreInstanceState");
        ((TextView)findViewById(R.id.textView4)).setText(score_A);
        ((TextView)findViewById(R.id.textView2)).setText(score_B);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.first_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.item1) {

        } else if (item.getItemId() == R.id.item2) {

        } else if (item.getItemId() == R.id.item3) {

        }
        return super.onOptionsItemSelected(item);
    }

}