package com.mohitsingh.soundrecorder.controllers;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.view.View;

import com.mohitsingh.soundrecorder.AllRecordsActivity;
import com.mohitsingh.soundrecorder.databinding.AllRecordsLayoutBinding;
import com.mohitsingh.soundrecorder.utils.Utils;

import java.io.File;
import java.io.IOException;

public class AllRecordsController {

    public void setView(Context context, AllRecordsLayoutBinding binding, File file, int position) {
        binding.fileNameTv.setText(new Utils().getRecordingFileName(Long.parseLong(file.getName().replace(".3gp", ""))).toUpperCase() + "");

        binding.playSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer mediaPlayer = MediaPlayer.create(context, Uri.fromFile(file));
                // mediaPlayer.prepare();
                mediaPlayer.start();
            }
        });

        binding.deleteSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AllRecordsActivity activity = (AllRecordsActivity) context;
                activity.filesList.remove(position);
                if (file.exists()) {
                    file.delete();
                }
                activity.adapter.notifyItemRemoved(position);
            }
        });
    }

}
