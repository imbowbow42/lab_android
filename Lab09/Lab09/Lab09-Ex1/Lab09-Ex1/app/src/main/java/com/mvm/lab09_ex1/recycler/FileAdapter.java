package com.mvm.lab09_ex1.recycler;

import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.mvm.lab09_ex1.R;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.FileHolder> {

    private List<DownloadFile> files;
    private Handler handler = new Handler();

    public FileAdapter() {
        this.files = new ArrayList<>();
    }

    public List<DownloadFile> getFiles() {
        return files;
    }

    public void setFiles(List<DownloadFile> files) {
        this.files.clear();
        this.files.addAll(files);
        this.files.get(0).setLocalPath("/storage/sdcard/DOWNLOAD/Javascript Tutorial.mp4");
        this.notifyDataSetChanged();
    }


    @NonNull
    @Override
    public FileHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_file, parent, false);
        return new FileHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull FileHolder holder, final int position) {

        DownloadFile file = files.get(position);
        holder.bind(file, position);

        holder.btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Download
                holder.btnDownload.setEnabled(false);
                holder.btnDownload.setTextColor(Color.GRAY);

                startDownload(position, holder);
                holder.status.setEnabled(true);
            }
        });
    }
    public void startDownload(final int position, final FileHolder myViewHolder) {

        Runnable runnable = new Runnable() {
            int currentStatus = 0;

            public void run() {

                String urlDownload = files.get(position).getLocalPath();
                int count = 0;
                try {

                    URL url = new URL(urlDownload);
                    URLConnection conexion = url.openConnection();
                    conexion.connect();

                    final int lenghtOfFile = conexion.getContentLength();
                    Log.d("ANDRO_ASYNC", "Lenght of file: " + lenghtOfFile);

                    InputStream input = new BufferedInputStream(url.openStream());

                    // Get File Name from URL
                    String fileName = urlDownload
                            .substring(urlDownload.lastIndexOf('/') + 1, urlDownload.length());

                    OutputStream output = new FileOutputStream("/mnt/sdcard/" + fileName);

                    byte data[] = new byte[1024];
                    long total = 0;

                    while ((count = input.read(data)) != -1) {
                        total += count;
                        currentStatus = (int) ((total * 100) / lenghtOfFile);
                        output.write(data, 0, count);

                        // Update ProgressBar
                        handler.post(new Runnable() {
                            public void run() {
                                updateStatus(position, currentStatus, myViewHolder, lenghtOfFile);
                            }
                        });

                    }

                    output.flush();
                    output.close();
                    input.close();

                } catch (Exception e) {
                }


            }
        };
        new Thread(runnable).start();
    }
    private void updateStatus(int index, int currentStatus, FileHolder myViewHolder, int lenghtOfFile) {
        // Update ProgressBar
        myViewHolder.progressBar.setProgress(currentStatus);

        // Update Text to ColStatus
        myViewHolder.status.setText("Complete");
        myViewHolder.status.setText("Load : " + String.valueOf(currentStatus) + "%");

        // Enabled Button View
        if (currentStatus >= 100) {
            myViewHolder.status.setEnabled(true);
            myViewHolder.status.setText("Complete");
            myViewHolder.status.setTextColor(Color.GREEN);

            double fileSizeMegabytes = (lenghtOfFile / 1024);
            myViewHolder.size.setText("Size: " + fileSizeMegabytes + "MB");


        }
    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    public void add(DownloadFile downloadFile) {
        files.add(0, downloadFile);
        notifyItemInserted(0);
    }

    public static class FileHolder extends RecyclerView.ViewHolder {

        ImageView icon;
        TextView name, size, status;
        ProgressBar progressBar;
        Button btnDownload;

        public FileHolder(@NonNull View root) {
            super(root);

            icon = root.findViewById(R.id.icon);
            name = root.findViewById(R.id.name);
            size = root.findViewById(R.id.size);
            status = root.findViewById(R.id.status);
            progressBar = root.findViewById(R.id.progressBar);
            btnDownload = root.findViewById(R.id.btnDownload);
        }

        public void bind(DownloadFile file, int position) {

            if (file.getName() == null || file.getName().isEmpty()) {
                icon.setImageResource(R.drawable.icon_other);
                name.setText("Reading file name...");
                size.setText("Reading file size...");
            }else {
                icon.setImageResource(file.getIcon());
                name.setText(file.getName());
                size.setText(file.getDisplaySize());
            }


            int colorSuccess = ContextCompat.getColor(icon.getContext(), R.color.success);
            int colorFail = ContextCompat.getColor(icon.getContext(), R.color.fail);
            int colorWaiting = ContextCompat.getColor(icon.getContext(), R.color.waiting);

            if (file.isWaiting()) {
                status.setText("Waiting");
                status.setTextColor(colorWaiting);
                status.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
            else if (file.isCompleted()) {
                status.setText("Complete");
                status.setTextColor(colorSuccess);
                status.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }else if (file.isFailed()) {
                status.setText("Failed");
                status.setTextColor(colorFail);
                status.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
            else {
                progressBar.setProgress(file.getProgress());
                status.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
            }
        }
    }
}
