package com.codeknight.sjapp;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class SecondActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "download_channel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        TextView tvExclusive = findViewById(R.id.tvExclusive);
        tvExclusive.setText("KGF 3 Exclusive");

        Button btnDownload = findViewById(R.id.btnDownload);
        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDownloadDialog();
            }
        });
    }

    public void showDownloadDialog() {
        // Create a custom dialog with increased size
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_download);

        // Set width and height of the dialog (adjust as needed)
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        dialog.setTitle("Download Progress");

        final ProgressBar progressBar = dialog.findViewById(R.id.progressBar);
        final TextView tvProgress = dialog.findViewById(R.id.tvProgress);

        // Simulating download progress with a handler
        new CountDownTimer(5000, 500) {
            int progress = 0;

            @Override
            public void onTick(long millisUntilFinished) {
                progress += 10;
                progressBar.setProgress(progress);
                tvProgress.setText("Downloading... " + progress + "%");
            }

            @Override
            public void onFinish() {
                progress = 100;
                progressBar.setProgress(progress);
                tvProgress.setText("Download Complete");
                new CountDownTimer(1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        // do nothing
                    }

                    @Override
                    public void onFinish() {
                        dialog.dismiss();
                        showNotification();
                    }
                }.start();
            }
        }.start();

        dialog.show();
    }

    private void showNotification() {
        // Create a notification channel (required for Android Oreo and higher)
        createNotificationChannel();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Download Completed")
                .setContentText("Your download has been completed successfully!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1, builder.build());
    }

    private void createNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            CharSequence name = "Download Channel";
            String description = "Channel for download notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
