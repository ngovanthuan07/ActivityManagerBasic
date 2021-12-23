package com.example.manageractivityproject.Adapters;

import android.app.AlertDialog;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.VectorEnabledTintResources;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.manageractivityproject.Constants.Constant;
import com.example.manageractivityproject.Convert.ConvertBitMap;
import com.example.manageractivityproject.Model.Activity;
import com.example.manageractivityproject.R;
import com.example.manageractivityproject.myInterface.IClickItemActivityListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ActivityViewHolder> {

    private ArrayList<Activity> listActivity;
    private IClickItemActivityListener iClickItemActivityListener;

    public ActivityAdapter(ArrayList<Activity> listActivity, IClickItemActivityListener listener) {
        this.iClickItemActivityListener = listener;
        this.listActivity = listActivity;
    }


    public void setData(ArrayList<Activity> list) {
        this.listActivity = list;
    }
    public class ActivityViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgActivityPhoto;
        private CircleImageView imgActivityProfile;
        private TextView txtActivityName, txtActivityDate, txtActivityTitle;
        private ImageButton btnActivityEdit, btnActivityDelete;

        public ActivityViewHolder(@NonNull View itemView) {
            super(itemView);
            imgActivityPhoto = itemView.findViewById(R.id.imgActivityPhoto);
            imgActivityProfile = itemView.findViewById(R.id.imgActivityProfile);
            txtActivityName = itemView.findViewById(R.id.txtActivityName);
            txtActivityTitle = itemView.findViewById(R.id.txtActivityTitle);
            txtActivityDate = itemView.findViewById(R.id.txtActivityDate);
            btnActivityEdit = itemView.findViewById(R.id.btnActivityEdit);
            btnActivityDelete = itemView.findViewById(R.id.btnActivityDelete);
        }
    }

    @NonNull
    @Override
    public ActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_activity, parent, false);
        return new ActivityViewHolder(view);
    }
    @Override
    public int getItemCount() {
        return listActivity.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityViewHolder holder, int position) {
       final Activity activity = listActivity.get(position);
        if(activity == null){
            return;
        }
        try
        {
            holder.imgActivityPhoto.setImageBitmap(ConvertBitMap.getConvertBitMap().stringToBitmap(activity.getPhoto()));
            if(!activity.getUser().getPhoto().equals("")){
                holder.imgActivityProfile.setImageBitmap(ConvertBitMap.getConvertBitMap().stringToBitmap(activity.getUser().getPhoto()));
            } else{
                holder.imgActivityProfile.setImageResource(R.drawable.suphamkithuat);
            }
            holder.txtActivityTitle.setText(activity.getTitle());
            holder.txtActivityDate.setText(activity.getDatestart());
            holder.txtActivityName.setText(activity.getCreatedby());

            holder.btnActivityDelete.setOnClickListener(view -> {
                iClickItemActivityListener.onClickButtonDeleteActivity(activity.getId(), position);
            });
            holder.btnActivityEdit.setOnClickListener(view -> {
                iClickItemActivityListener.onClickButtonEditActivity(activity, position);
            });
        }
        catch (Exception err){
            err.printStackTrace();
        }

    }






}
