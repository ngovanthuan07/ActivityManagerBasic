package com.example.manageractivityproject.Fragments;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.manageractivityproject.Adapters.ManagerActivityAdapter;
import com.example.manageractivityproject.Constants.Constant;
import com.example.manageractivityproject.HomeActivity;
import com.example.manageractivityproject.MainActivity2;
import com.example.manageractivityproject.Model.Activity;
import com.example.manageractivityproject.Model.ActivityGson;
import com.example.manageractivityproject.Model.StaticClassModel;
import com.example.manageractivityproject.Model.User;
import com.example.manageractivityproject.R;
import com.example.manageractivityproject.StudentManager;
import com.example.manageractivityproject.myInterface.IClickItemActivityManagerListener;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.gson.Gson;

import java.util.ArrayList;

public class ManagerFragment extends Fragment {
    private View view;
    public static RecyclerView recyclerViewManager;
    public static ArrayList<Activity> listActivities;
    private SwipeRefreshLayout refreshLayout;
    private ManagerActivityAdapter managerActivityAdapter;
    private MaterialToolbar toolbar;
    private SharedPreferences sharedPreferences;
    private TextView txtLabelManagerAc;
    private SearchView searchView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_manager, container, false);
        init();
        return view;
    }
    private void init(){
        sharedPreferences = getContext().getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        recyclerViewManager = view.findViewById(R.id.recyclerManagerActivity);
        recyclerViewManager.setHasFixedSize(true);
        recyclerViewManager.setLayoutManager(new LinearLayoutManager(getContext()));
        toolbar = view.findViewById(R.id.toolbarManager);
        txtLabelManagerAc = view.findViewById(R.id.txtLabelManagerAc);
        ((HomeActivity) getContext()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);

        getApiManagerRegister();
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
    private void getApiManagerRegister(){
        StringRequest request = new StringRequest(Request.Method.GET, Constant.SHOW_ACTIVITY_REGISTRATIONS, response ->{
            Gson gson = new Gson();
            ActivityGson activityGson = gson.fromJson(response, ActivityGson.class);
            if(activityGson.isSuccess()){
                listActivities = activityGson.getActivitys();
                StaticClassModel.setListActivityArrayList(activityGson.getActivitys());
                managerActivityAdapter = new ManagerActivityAdapter(listActivities, new IClickItemActivityManagerListener() {
                    @Override
                    public void onClickItemRelativeManager(ArrayList<User> users) {
                        StaticClassModel.setListUserStaticClassModel(users);
                        Intent intent = new Intent(getContext(), StudentManager.class);
                        //intent.putExtra("users", userConvertJson);
                        startActivity(intent);
                    }
                });
                recyclerViewManager.setAdapter(managerActivityAdapter);


            } else{
                MessageLoadActivity("Lỗi", "Không thể load dữ liệu", R.drawable.ic_error);
            }
        }, error ->{
            MessageLoadActivity("Lỗi", "Không thể load dữ liệu", R.drawable.ic_error);
            error.printStackTrace();

        });

        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_find_activity, menu);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                managerActivityAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                managerActivityAdapter.getFilter().filter(newText);
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);

    }


}
