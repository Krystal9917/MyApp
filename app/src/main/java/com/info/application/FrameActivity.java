package com.info.application;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class FrameActivity extends FragmentActivity {

    private Fragment fragments[];
    private RadioGroup radioGroup;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private RadioButton btnHome, btnContact, btnFriend;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fragment);
        fragments = new Fragment[3];
        fragmentManager = getSupportFragmentManager();
        fragments[0] = fragmentManager.findFragmentById(R.id.fragment1);
        fragments[1] = fragmentManager.findFragmentById(R.id.fragment2);
        fragments[2] = fragmentManager.findFragmentById(R.id.fragment3);

        fragmentTransaction = fragmentManager.beginTransaction().hide(fragments[0]).hide(fragments[1]).hide(fragments[2]);
        fragmentTransaction.show(fragments[0]).commit();

        btnHome = findViewById(R.id.btn_home);
        btnContact = findViewById(R.id.btn_contact);
        btnFriend = findViewById(R.id.btn_other);

        radioGroup = findViewById(R.id.group_radio);
        radioGroup.setOnCheckedChangeListener(new RadioChange());
    }

    class RadioChange implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            fragmentTransaction = fragmentManager.beginTransaction().hide(fragments[0]).hide(fragments[1]).hide(fragments[2]);

            switch (i) {
                case R.id.btn_home:
                    fragmentTransaction.show(fragments[0]).commit();
                    break;
                case R.id.btn_contact:
                    fragmentTransaction.show(fragments[1]).commit();
                    break;
                case R.id.btn_other:
                    fragmentTransaction.show(fragments[2]).commit();
                    break;
                default:
                    break;
            }
        }
    }
}
