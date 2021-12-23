package com.example.manageractivityproject.Adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.manageractivityproject.Convert.ConvertBitMap;
import com.example.manageractivityproject.Model.Activity;
import com.example.manageractivityproject.Model.StaticClassModel;
import com.example.manageractivityproject.R;
import com.example.manageractivityproject.myInterface.IClickItemActivityListener;
import com.example.manageractivityproject.myInterface.IClickItemActivityManagerListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ManagerActivityAdapter extends RecyclerView.Adapter<ManagerActivityAdapter.ManagerActivityHolder>
        implements Filterable {
    private ArrayList<Activity> activityArrayList;
    private ArrayList<Activity> activityArrayListOld;
    private IClickItemActivityManagerListener iClickItemActivityManagerListener;

    public ManagerActivityAdapter(ArrayList<Activity> activityArrayList){
        this.activityArrayList = activityArrayList;
        this.activityArrayListOld = activityArrayList;
    }
    public ManagerActivityAdapter(ArrayList<Activity> activityArrayList, IClickItemActivityManagerListener iClickItemActivityManagerListener){
        this.activityArrayList = activityArrayList;
        this.iClickItemActivityManagerListener = iClickItemActivityManagerListener;
    }


    public void setData(ArrayList<Activity> activityArrayList){
        this.activityArrayList = activityArrayList;
    }


    @NonNull
    @Override
    public ManagerActivityHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activity,parent,false);
        return new ManagerActivityHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ManagerActivityHolder holder, int position) {
        final Activity activity = activityArrayList.get(position);
        if(activity == null){
            return;
        }

        try
        {
            holder.imgManagerActivity.setImageBitmap(ConvertBitMap.getConvertBitMap().stringToBitmap(activity.getPhoto()));
            holder.tvManagerTitleActivity.setText(activity.getTitle());
            holder.tvManagerQuantityActivity.setText("Số lượng đăng kí:" + activity.getUsers().size());
            holder.rlItemManagerActivity.setOnClickListener(view -> {
                StaticClassModel.setActivityName(activity.getTitle());
                iClickItemActivityManagerListener.onClickItemRelativeManager(activity.getUsers());
            });
        } catch (Exception ex){
            ex.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        if(!activityArrayList.isEmpty()){
            return activityArrayList.size();
        }
        return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString();
                if(strSearch.isEmpty()){
                    activityArrayList = activityArrayListOld;
                } else{
                    ArrayList<Activity> activityArr = new ArrayList<>();
                    for(Activity activity : activityArrayListOld){
                        if(activity.getTitle().toLowerCase().contains(strSearch.toLowerCase())){
                            activityArr.add(activity);
                        }
                    }
                    activityArrayList = activityArr;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = activityArrayList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                activityArrayList = (ArrayList<Activity>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ManagerActivityHolder extends RecyclerView.ViewHolder{
        private ImageView imgManagerActivity;
        private TextView tvManagerTitleActivity,tvManagerQuantityActivity;
        private RelativeLayout rlItemManagerActivity;

        public ManagerActivityHolder(@NonNull View itemView) {
            super(itemView);
            imgManagerActivity = itemView.findViewById(R.id.imgManagerActivity);
            tvManagerTitleActivity = itemView.findViewById(R.id.tvManagerTitleActivity);
            tvManagerQuantityActivity = itemView.findViewById(R.id.tvManagerQuantityActivity);
            rlItemManagerActivity = itemView.findViewById(R.id.rlItemManagerActivity);
        }
    }
}
