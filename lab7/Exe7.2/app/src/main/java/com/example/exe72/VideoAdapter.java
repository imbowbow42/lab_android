package com.example.exe72;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {
    private Activity mActivity;
    private List<Video> mListVideo;

    public VideoAdapter(Activity mActivity){
        this.mActivity = mActivity;
    }

    public void  setData(List<Video> list){
        this.mListVideo = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video,parent,false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoAdapter.VideoViewHolder holder, int position) {
        Video video = mListVideo.get(position);
        if(video == null){
            return;
        }
        Glide.with(mActivity).load(video.getThumb()).into(holder.imgVideo);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgVideo;
        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);

            imgVideo= itemView.findViewById(R.id.img_video);
        }
    }
}
