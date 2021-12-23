package com.example.manageractivityproject.myInterface;

import com.example.manageractivityproject.Model.Activity;

public interface IClickItemActivityListener {
    void onClickButtonDeleteActivity(int id, int position);
    void onClickButtonEditActivity(Activity activity, int position);
}
