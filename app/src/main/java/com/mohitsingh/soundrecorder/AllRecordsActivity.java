package com.mohitsingh.soundrecorder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.mohitsingh.soundrecorder.adapters.AllRecordsAdapter;
import com.mohitsingh.soundrecorder.databinding.ActivityAllRecordsBinding;
import com.mohitsingh.soundrecorder.utils.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AllRecordsActivity extends AppCompatActivity {

    ActivityAllRecordsBinding binding;
    public List<File> filesList = new ArrayList<>();
    public AllRecordsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAllRecordsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getAllFiles();
    }

    private void getAllFiles() {
        String path = getExternalFilesDir(null).getPath();
        File directory = new File(path);
        File[] filesArray = directory.listFiles();
        for (int i = 0; i < filesArray.length; i++) {
            filesList.add(filesArray[i]);
        }

        // Sort Files
        Collections.sort(filesList, new Comparator<File>() {
            @Override
            public int compare(File file1, File file2) {
                long k = file2.lastModified() - file1.lastModified();
                if(k > 0){
                    return 1;
                }else if(k == 0){
                    return 0;
                }else{
                    return -1;
                }
            }
        });

        if (filesList.isEmpty()) {
            binding.noDataTv.setVisibility(View.VISIBLE);
            binding.allRecordsRecyclerView.setVisibility(View.GONE);
        } else {
            binding.noDataTv.setVisibility(View.GONE);
            binding.allRecordsRecyclerView.setVisibility(View.VISIBLE);
            setupAdapter();
        }
    }

    private void setupAdapter() {
        adapter = new AllRecordsAdapter(AllRecordsActivity.this, filesList);
        binding.allRecordsRecyclerView.setAdapter(adapter);
        binding.allRecordsRecyclerView.setLayoutManager(new LinearLayoutManager(AllRecordsActivity.this));
    }

}
