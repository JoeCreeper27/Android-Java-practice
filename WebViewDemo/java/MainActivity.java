package com.example.webviewdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {
    private TextView sbText;
    private TextView stText;
    private SeekBar sbSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                String text = rating + " Star(s)";
                Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
            }


        });
    }

    private void findView() {
        sbText = (TextView) findViewById(R.id.sbText);
        sbSize = (SeekBar) findViewById(R.id.sbSize);
        stText =(TextView) findViewById(R.id.stText);
        RadioGroup rgGender=(RadioGroup)findViewById(R.id.rgGender);
        Switch swWifi=(Switch)findViewById(R.id.swWifi);

        sbSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                sbText.setTextSize(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Toast.makeText(MainActivity.this, "Star size = " + seekBar.getProgress(), Toast.LENGTH_SHORT).show();
            }


            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(MainActivity.this, "Stop size = " + seekBar.getProgress(), Toast.LENGTH_SHORT).show();
            }
        });
        rgGender.setOnCheckedChangeListener((group,checkedId)->{
            RadioButton radioButton=(RadioButton) group.findViewById(checkedId);
            stText.setText(radioButton.getText());
        });
        swWifi.setOnCheckedChangeListener((buttonView, isChecked)->{
            Switch sw=(Switch) buttonView;
            String swName =sw.getText().toString();
            String message = "";
            if(isChecked){
                message += swName+" "+sw.getTextOn();
            }else{
                message += swName+" "+sw.getTextOff();
            }
                stText.setText(message);
        });
    }

    public void onAgreeClick(View view) {
        CheckBox checkBox=(CheckBox) view;
        String checkBoxName=checkBox.getText().toString();
        String message;
        if (checkBox.isChecked()){
            message="checked"+" "+checkBoxName;
        }else{
            message="unchecked"+" "+checkBoxName;
        }
        stText.setText(message);
    }

    public void onVibrateClick(View view) {
        ToggleButton toggleButton=(ToggleButton) view;
        stText.setText(toggleButton.getText());
    }
}