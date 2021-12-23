package com.example.manageractivityproject.Model;

import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;

public class StaticClassModel {
    public static ArrayList<User> listUserStaticClassModel;
    public static ArrayList<Activity> listActivityArrayList;
    public static Activity activityModel;
    public static int position;
    public static String activityName;

    public static String getActivityName() {
        return activityName;
    }

    public static void setActivityName(String activityName) {
        StaticClassModel.activityName = activityName;
    }

    public static int getPosition() {
        return position;
    }

    public static void setPosition(int position) {
        StaticClassModel.position = position;
    }

    public static Activity getActivityModel() {
        return activityModel;
    }

    public static void setActivityModel(Activity activityModel) {
        StaticClassModel.activityModel = activityModel;
    }

    public static void clearActivity() {
        activityModel = null;
    }

    public static ArrayList<Activity> getListActivityArrayList() {
        return listActivityArrayList;
    }

    public static void setListActivityArrayList(ArrayList<Activity> listActivityArrayList) {
        StaticClassModel.listActivityArrayList = listActivityArrayList;
    }

    public static ArrayList<User> getListUserStaticClassModel() {
        return listUserStaticClassModel;
    }

    public static void setListUserStaticClassModel(ArrayList<User> listUserStaticClassModel) {
        StaticClassModel.listUserStaticClassModel = listUserStaticClassModel;
    }

    public static void clearListActivity() {
        listActivityArrayList.clear();
    }

    public static void clearListUser() {
        listUserStaticClassModel.clear();
    }
}
