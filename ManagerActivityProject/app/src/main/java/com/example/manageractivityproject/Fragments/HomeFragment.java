package com.example.manageractivityproject.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.manageractivityproject.Adapters.ActivityAdapter;
import com.example.manageractivityproject.Constants.Constant;
import com.example.manageractivityproject.EditActivity;
import com.example.manageractivityproject.HomeActivity;
import com.example.manageractivityproject.Model.Activity;
import com.example.manageractivityproject.Model.ActivityGson;
import com.example.manageractivityproject.Model.StaticClassModel;
import com.example.manageractivityproject.R;
import com.example.manageractivityproject.myInterface.IClickItemActivityListener;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeFragment extends Fragment {
    private View view;
    public static RecyclerView recyclerView;
    public static ArrayList<Activity> list;
    private SwipeRefreshLayout refreshLayout;
    private ActivityAdapter activityAdapter;
    private MaterialToolbar toolbar;
    private SharedPreferences sharedPreferences;
    //Button btnTestButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        init();

        return view;
    }

    public void init() {
        //btnTestButton = view.findViewById(R.id.btnTestButton);
        sharedPreferences = getContext().getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        recyclerView = view.findViewById(R.id.recyclerHome);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        refreshLayout = view.findViewById(R.id.swipeHome);
        toolbar = view.findViewById(R.id.toolbarHomeLoad);
        ((HomeActivity) getContext()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);

        getActivityApi();
    }

    private void MessageLoadActivity(String alert1, String alert2, int drawable) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle(alert1);
        alertDialog.setIcon(drawable);
        alertDialog.setMessage(alert2);
        alertDialog.setPositiveButton("Hoàn tác", (dialogInterface, i) -> {

        });
        alertDialog.show();
    }

    public void getActivityApi() {
        list = new ArrayList<>();
        refreshLayout.setRefreshing(true);
        StringRequest request = new StringRequest(Request.Method.GET, Constant.SELECT_FULL_ACTIVITY, response -> {
            Gson gson = new Gson();
            ActivityGson activityGson = gson.fromJson(response, ActivityGson.class);
            if (activityGson.isSuccess()) {
                list = activityGson.getActivitys();
                activityAdapter = new ActivityAdapter(list, new IClickItemActivityListener() {
                    @Override
                    public void onClickButtonDeleteActivity(int id, int position) {
                        try {
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                            alertDialog.setTitle("Thông báo!");
                            alertDialog.setIcon(R.drawable.ic_alert_remove);
                            alertDialog.setMessage("Bạn có muốn xoá hoạt động này không");
                            alertDialog.setPositiveButton("Xoá", (dialogInterface, i) -> {
                                DeleteActivity(id, position);
                            });
                            alertDialog.setNegativeButton("Không", (dialogInterface, i) -> {

                            });
                            alertDialog.show();
                        } catch (Exception btnDeleteEx) {
                            AlertDiaLogActivity("Lỗi!", "", R.drawable.ic_alert_remove);
                            btnDeleteEx.printStackTrace();
                        }
                    }

                    @Override
                    public void onClickButtonEditActivity(Activity activity, int position) {
                        try {
                            Intent intent = new Intent(((HomeActivity) getContext()), EditActivity.class);
                            StaticClassModel.setActivityModel(activity);
                            StaticClassModel.setPosition(position);
                            getContext().startActivity(intent);
                        } catch (Exception exEditActivity){
                            AlertDiaLogActivity("Lỗi!", "", R.drawable.ic_alert_remove);
                            exEditActivity.printStackTrace();
                        }
                    }

                });
                recyclerView.setAdapter(activityAdapter);
            }
            refreshLayout.setRefreshing(false);
        }, error -> {
            MessageLoadActivity("Lỗi", "Không thể load được hoạt động", R.drawable.ic_error);
            error.printStackTrace();
            refreshLayout.setRefreshing(false);
        });
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);

    }

    private void AlertDiaLogActivity(String alert1, String alert2, int drawable) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle(alert1);
        alertDialog.setIcon(drawable);
        alertDialog.setMessage(alert2);
        alertDialog.setPositiveButton("Hoàn tác", (dialogInterface, i) -> {

        });
        alertDialog.show();
    }

    protected void DeleteActivity(int id, int position) {
        StringRequest request = new StringRequest(Request.Method.POST, Constant.DELETE_ACTIVITY, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getBoolean("success")) {
                    AlertDiaLogActivity("Thông báo!", "Xoá thành công", R.drawable.ic_alert_sucess_blue);
                    list.remove(position);
                    activityAdapter.notifyItemRemoved(position);
                    activityAdapter.notifyDataSetChanged();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }, error -> {
            AlertDiaLogActivity("Thông báo", "Xoá thất bại", R.drawable.ic_error);
            error.printStackTrace();
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("id", id + "");
                return map;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);
    }

   @Override
  public void onDestroy() {
      super.onDestroy();

   }



}
