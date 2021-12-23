package com.example.manageractivityproject.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.manageractivityproject.Convert.ConvertBitMap;
import com.example.manageractivityproject.Model.User;
import com.example.manageractivityproject.R;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHolder>{
    private ArrayList<User> userArrayList;

    public UserAdapter(ArrayList<User> userArrayList){
        this.userArrayList = userArrayList;
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student,parent,false);
        return new UserHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {
        final User user = userArrayList.get(position);
        if(user == null)
        {
            return;
        }
       try {
           if(!user.getPhoto().equals("")){
               holder.imgUserImage.setImageBitmap(ConvertBitMap.getConvertBitMap().stringToBitmap(user.getPhoto()));
           }
           holder.tvNameUser.setText(user.getFullname());
           holder.tvtimeregistration.setText("Thời gian đăng kí: " + user.getTimeregistration());
       } catch (Exception err){
           err.printStackTrace();
       }
    }

    @Override
    public int getItemCount() {
        if(userArrayList == null){
            return 0;
        }
        return userArrayList.size();
    }

    public class UserHolder extends RecyclerView.ViewHolder {
        private ImageView imgUserImage;
        private TextView tvNameUser,tvtimeregistration;

        public UserHolder(@NonNull View itemView) {
            super(itemView);
            imgUserImage = itemView.findViewById(R.id.imgUserImage);
            tvNameUser = itemView.findViewById(R.id.tvNameUser);
            tvtimeregistration = itemView.findViewById(R.id.tvtimeregistration);
        }
    }
}
