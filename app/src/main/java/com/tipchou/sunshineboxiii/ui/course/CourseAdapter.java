package com.tipchou.sunshineboxiii.ui.course;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.bumptech.glide.Glide;
import com.tipchou.sunshineboxiii.R;

import java.util.List;

/**
 * Created by 邵励治 on 2018/3/6.
 * Perfect Code
 */

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {

    private CourseMediaPlayer courseMediaPlayer;

    private LayoutInflater layoutInflater;

    private Context activity;

    private List<Materials> data;

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;

        TextView textView;

        private Materials materials;

        void bind(Materials materials) {
            this.materials = materials;
            Log.e("CourseAdapter.bind()", materials.toString());
            loadMaterialPicture(materials);
            loadMaterialName(materials);
        }

        @SuppressLint("SetTextI18n")
        private void loadMaterialName(Materials materials) {
            if (materials != null) {
                switch (materials.getMaterialType()) {
                    case ALBUM:
                        textView.setText("图集： " + materials.getName());
                        break;
                    case AUDIO:
                        textView.setText("音频： " + materials.getName());
                        break;
                    case VIDEO:
                        textView.setText("视频： " + materials.getName());
                        break;
                    case PDF:
                        textView.setText("PDF： " + materials.getName());
                        break;
                    default:
                        break;
                }
            }
        }

        private void loadMaterialPicture(Materials materials) {
            if (materials != null) {
                switch (materials.getMaterialType()) {
                    case ALBUM:
                        Glide.with(activity).load(R.drawable.material_aublum).into(imageView);
                        break;
                    case AUDIO:
                        Glide.with(activity).load(R.drawable.material_audio).into(imageView);
                        break;
                    case VIDEO:
                        Glide.with(activity).load(R.drawable.material_video).into(imageView);
                        break;
                    case PDF:
                        Glide.with(activity).load(R.drawable.material_aublum).into(imageView);
                        break;
                    default:
                        break;
                }
            }
        }

        ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            imageView = itemView.findViewById(R.id.course_act_item_imageview1);
            textView = itemView.findViewById(R.id.course_act_item_textview1);
        }

        @Override
        public void onClick(View v) {
            Log.e("CourseAdapter.test", materials.getMaterialType().toString());
            switch (materials.getMaterialType()) {
                case ALBUM:
                    AVAnalytics.onEvent(activity, "资源被打开数", "绘本");
                    AVObject accessRecord = new AVObject("UserAction");
                    accessRecord.put("userId", AVUser.getCurrentUser().getObjectId());
                    accessRecord.put("resId", materials.getName() + "绘本");
                    accessRecord.put("behaviorType", "openRes");
                    accessRecord.put("equipment", "androidApp");
                    accessRecord.saveInBackground();
                    courseMediaPlayer.openAlbum(materials);
                    break;
                case PDF:
                    AVAnalytics.onEvent(activity, "资源被打开数", "PDF");
                    AVObject accessRecordPDF = new AVObject("UserAction");
                    accessRecordPDF.put("userId", AVUser.getCurrentUser().getObjectId());
                    accessRecordPDF.put("resId", materials.getName() + "PDF");
                    accessRecordPDF.put("behaviorType", "openRes");
                    accessRecordPDF.put("equipment", "androidApp");
                    accessRecordPDF.saveInBackground();
                    courseMediaPlayer.openPDF(materials);
                    break;
                case AUDIO:
                    AVAnalytics.onEvent(activity, "资源被打开数", "音频");
                    AVObject accessRecord1 = new AVObject("UserAction");
                    accessRecord1.put("userId", AVUser.getCurrentUser().getObjectId());
                    accessRecord1.put("resId", materials.getName() + "音频");
                    accessRecord1.put("behaviorType", "openRes");
                    accessRecord1.put("equipment", "androidApp");
                    accessRecord1.saveInBackground();
                    courseMediaPlayer.playAudio(materials);
                    break;
                case VIDEO:
                    AVAnalytics.onEvent(activity, "资源被打开数", "视频");
                    AVObject accessRecord2 = new AVObject("UserAction");
                    accessRecord2.put("userId", AVUser.getCurrentUser().getObjectId());
                    accessRecord2.put("resId", materials.getName() + "音频");
                    accessRecord2.put("behaviorType", "openRes");
                    accessRecord2.put("equipment", "androidApp");
                    accessRecord2.saveInBackground();
                    courseMediaPlayer.playVideo(materials);
                    break;
                default:
                    break;
            }
        }
    }

    CourseAdapter(Context activity) {
        this.activity = activity;
        this.layoutInflater = LayoutInflater.from(activity);
        if (activity instanceof CourseMediaPlayer) {
            this.courseMediaPlayer = (CourseMediaPlayer) activity;
        }
    }

    void setMaterialData(List<Materials> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = layoutInflater.inflate(R.layout.activity_course_item, parent, false);
        return new ViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(data.get(position));
    }


    @Override
    public int getItemCount() {
        if (data != null) {
            return data.size();
        } else {
            return 0;
        }
    }
}
