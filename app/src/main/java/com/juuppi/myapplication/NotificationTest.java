package com.juuppi.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class NotificationTest extends AppCompatActivity {

    private TextView CountDown;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_test);

        CountDown = (TextView) findViewById(R.id.CountDownText);

    }

    public void GetTime(String NotifiText)
    {
        CountDown.setText(NotifiText);
    }
}