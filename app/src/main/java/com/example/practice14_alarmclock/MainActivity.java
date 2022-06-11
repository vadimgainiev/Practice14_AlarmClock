package com.example.practice14_alarmclock;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TimePicker;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    Button btnSetAlarmClock;
    Calendar dateTime = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSetAlarmClock = findViewById(R.id.btnSetAlarmClock);

        btnSetAlarmClock.setOnClickListener(view -> new TimePickerDialog(MainActivity.this, t,
                dateTime.get(Calendar.HOUR_OF_DAY),
                dateTime.get(Calendar.MINUTE),
                true)
                .show());
    }

    TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            dateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateTime.set(Calendar.MINUTE, minute);

            setInitialDateTimeData();
        }
    };

    private void setInitialDateTimeData() {
        long timerMillis = dateTime.getTimeInMillis();
        long currentMillis = System.currentTimeMillis();
        if (timerMillis < currentMillis) {
            long dayMillis = 24 * 60 * 60 * 1000;
            timerMillis += dayMillis;
        }

        Intent alarm = new Intent(MainActivity.this, Alarm.class);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, timerMillis,
                PendingIntent.getBroadcast(getApplicationContext(), 0, alarm, 0));
    }
}