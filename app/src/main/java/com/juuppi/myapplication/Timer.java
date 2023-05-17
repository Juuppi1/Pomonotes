package com.juuppi.myapplication;

import static android.Manifest.permission.POST_NOTIFICATIONS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class Timer extends AppCompatActivity implements DialogTesti.DialogListener {
    private TextView PomoCdText, BreakCdText;
    private int PomoMin, BreakMin;
    private int PomoSec, BreakSec = 0;
    private Button PomoStartPause, BreakStartPause;
    private boolean PomoTimerRunning, BreakTimerRunning;
    private long PomoTimeLeft, PomoStartTime, BreakTimeLeft, BreakStartTime;
    private CountDownTimer PomoCDTimer, BreakCDTimer;
    private boolean ongoing;
    private static final int PERMISSION_REQ_CODE = 2;
    private String NotifiText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        PomoCdText = findViewById(R.id.PomoCountDown);
        PomoStartPause = findViewById(R.id.PomoStartPause);
        BreakCdText = findViewById(R.id.BreakCountDown);
        BreakStartPause = findViewById(R.id.BreakStartPause);

        requestRuntimePermission();
        createNotificationChannel();
    }

    public void DialogOpener(View v) {
        DialogTesti dialog = new DialogTesti();
        dialog.show(getSupportFragmentManager(), "dialog testi");
    }

    public void SetTimes(int PomoInt, int BreakInt) {
        PomoMin = PomoInt;
        PomoCdText.setText(PomoMin + ":00");
        PomoStartTime = PomoMin * 60000;
        PomoTimeLeft = PomoStartTime;

        BreakMin = BreakInt;
        BreakCdText.setText(BreakMin + ":00");
        BreakStartTime = BreakMin * 60000;
        BreakTimeLeft = BreakStartTime;
    }

    public void PomoStartPauseClicked(View view) {
        if (PomoTimerRunning) {
            pausePomoTimer();
        } else if (BreakTimerRunning) {
            pauseBreakTimer();
            startPomoTimer();
            ongoing = true;
        } else {
            startPomoTimer();
            ongoing = true;
        }
    }

    public void BreakStartPauseClicked(View view) {
        if (BreakTimerRunning) {
            pauseBreakTimer();
        } else if (PomoTimerRunning) {
            pausePomoTimer();
            startBreakTimer();
            ongoing = true;
        } else {
            startBreakTimer();
            ongoing = true;
        }
    }

    private void startPomoTimer() {
        PomoCDTimer = new CountDownTimer(PomoTimeLeft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                PomoTimeLeft = millisUntilFinished;
                updatePomoCDText();
            }

            @Override
            public void onFinish() {
                ongoing = false;
                NotificationTesti("Pomodoro", "Work time ended. It's time for a break!", ongoing);
                PomoTimerRunning = false;
                PomoStartPause.setText("Start");
                PomoCDTimer.cancel();
            }
        }.start();
        PomoTimerRunning = true;
        PomoStartPause.setText("Pause");
    }

    private void pausePomoTimer() {
        PomoCDTimer.cancel();
        PomoTimerRunning = false;
        PomoStartPause.setText("Start");

        if (!PomoTimerRunning && !BreakTimerRunning)
        {
            ongoing = false;
            NotificationTesti("Pomodoro", NotifiText, ongoing);
        }
    }

    private void startBreakTimer() {
        BreakCDTimer = new CountDownTimer(BreakTimeLeft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                BreakTimeLeft = millisUntilFinished;
                updateBreakCDText();
            }

            @Override
            public void onFinish() {
                ongoing = false;
                NotificationTesti("Pomodoro", "Break time ended. It's time to continue working!", ongoing);
                BreakTimerRunning = false;
                BreakStartPause.setText("Start");
                BreakCDTimer.cancel();
            }
        }.start();
        BreakTimerRunning = true;
        BreakStartPause.setText("Pause");
    }

    private void pauseBreakTimer() {
        BreakCDTimer.cancel();
        BreakTimerRunning = false;
        BreakStartPause.setText("Start");

        if (!PomoTimerRunning && !BreakTimerRunning)
        {
            ongoing = false;
            NotificationTesti("Pomodoro", NotifiText, ongoing);
        }
    }

    private void updatePomoCDText() {
        PomoMin = (int) (PomoTimeLeft / 1000) / 60;
        PomoSec = (int) (PomoTimeLeft / 1000) % 60;

        String timeLeftFormat = String.format(Locale.getDefault(), "%02d:%02d", PomoMin, PomoSec);
        PomoCdText.setText(timeLeftFormat);
        NotifiText = "Work: " + timeLeftFormat;

        NotificationTesti("Pomodoro", NotifiText, ongoing);
    }

    private void updateBreakCDText() {
        BreakMin = (int) (BreakTimeLeft / 1000) / 60;
        BreakSec = (int) (BreakTimeLeft / 1000) % 60;

        String timeLeftFormat = String.format(Locale.getDefault(), "%02d:%02d", BreakMin, BreakSec);
        BreakCdText.setText(timeLeftFormat);
        NotifiText = "Break: " + timeLeftFormat;

        NotificationTesti("Pomodoro", NotifiText, ongoing);
    }
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_MIN;
            NotificationChannel channel = new NotificationChannel("1", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    @SuppressLint("MissingPermission")
    private void NotificationTesti(String otsikko, String notifikaatioteksti, Boolean ongoing) {
        Intent intent = new Intent(this, Timer.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        Intent testIntent = new Intent(this, BroadcastTest.class);
        testIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent testPIntent = PendingIntent.getActivity(this, 0, testIntent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(Timer.this, "1")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(otsikko)
                .setContentText(notifikaatioteksti)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setOnlyAlertOnce(true)
                .setOngoing(ongoing)
                .setContentIntent(pendingIntent)
                .addAction(R.drawable.ic_launcher_foreground, "Testi", testPIntent);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(Timer.this);
        managerCompat.notify(1, builder.build());
    }
    public void requestRuntimePermission()
    {
        if (ContextCompat.checkSelfPermission(
                this, POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED)
        {
        }
        else
        {
            ActivityCompat.requestPermissions(this, new String[] {POST_NOTIFICATIONS}, PERMISSION_REQ_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == PERMISSION_REQ_CODE)
        {
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
            }
            else
            {
                requestRuntimePermission();
            }
        }
    }
    public void Back(View view)
    {
        Intent intent = new Intent(Timer.this, MainActivity.class);
        startActivity(intent);
    }

    public void notes(View view)
    {
        Intent intent = new Intent(Timer.this, Notes.class);
        startActivity(intent);
    }
    public void settings(View view) {
    }
}