package com.mohitsingh.soundrecorder.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mohitsingh.soundrecorder.controllers.AllRecordsController;
import com.mohitsingh.soundrecorder.databinding.AllRecordsLayoutBinding;
import com.mohitsingh.soundrecorder.utils.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AllRecordsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<File> filesList;

    public AllRecordsAdapter(Context context, List<File> filesList) {
        this.context = context;
        this.filesList = new ArrayList<>(filesList);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AllRecordsLayoutBinding binding = AllRecordsLayoutBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
        );

        return new AllRecordsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        File file = filesList.get(position);
        AllRecordsLayoutBinding binding = ((AllRecordsViewHolder) holder).binding;

        new AllRecordsController().setView(context, binding, file, position);
    }

    @Override
    public int getItemCount() {
        return filesList.size();
    }

    public static class AllRecordsViewHolder extends RecyclerView.ViewHolder {
        AllRecordsLayoutBinding binding;

        public AllRecordsViewHolder(@NonNull AllRecordsLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
