package com.mohitsingh.soundrecorder;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.mohitsingh.soundrecorder.databinding.ActivityMainBinding;
import com.mohitsingh.soundrecorder.utils.Utils;

import java.io.File;

@RequiresApi(api = Build.VERSION_CODES.O)
public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    MediaRecorder mediaRecorder;
    boolean isRecording;
    int RECORD_PERMISSION_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupButtons();
    }

    private void setupButtons() {
        binding.startRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.RECORD_AUDIO}, RECORD_PERMISSION_CODE);
                } else {
                    startRecording();
                }
            }
        });

        binding.stopRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopRecord();
            }
        });

        binding.allRecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AllRecordsActivity.class));
            }
        });
    }

    private void startRecording() {
        try {
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);

            File file = new File(getExternalFilesDir(null), System.currentTimeMillis() + ".3gp");
            if (!file.exists())
                file.createNewFile();

            mediaRecorder.setOutputFile(file);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

            mediaRecorder.prepare();
            mediaRecorder.start();
            isRecording = true;
            binding.chronometer.setBase(SystemClock.elapsedRealtime());
            binding.chronometer.start();
            Toast.makeText(this, "Recording Started!", Toast.LENGTH_SHORT).show();

        }
        catch (Exception e){
            Toast.makeText(this, "Some Error Occurred!", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == RECORD_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startRecording();
            } else {
                Toast.makeText(MainActivity.this, "Permission Declined!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void stopRecord() {
        if (!isRecording) {
            Toast.makeText(this, "Recording not yet started!", Toast.LENGTH_SHORT).show();
            return;
        }
        mediaRecorder.stop();
        mediaRecorder.release();
        isRecording = false;
        binding.chronometer.stop();
        binding.chronometer.setBase(SystemClock.elapsedRealtime());
        Toast.makeText(this, "Recording Stopped!", Toast.LENGTH_SHORT).show();
    }

}
