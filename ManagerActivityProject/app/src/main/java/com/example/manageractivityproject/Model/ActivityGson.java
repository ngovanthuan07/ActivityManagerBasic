package com.example.manageractivityproject.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ActivityGson{

    @SerializedName("success")
    private boolean success;

    @SerializedName("activitys")
    private ArrayList<Activity> activitys = null;

    @SerializedName("activity")
    private Activity activity = null;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ArrayList<Activity> getActivitys() {
        return activitys;
    }

    public void setActivitys(ArrayList<Activity> activitys) {
        this.activitys = activitys;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
