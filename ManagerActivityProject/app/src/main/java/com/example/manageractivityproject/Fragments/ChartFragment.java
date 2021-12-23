package com.example.manageractivityproject.Fragments;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.manageractivityproject.Constants.Constant;
import com.example.manageractivityproject.Model.Activity;
import com.example.manageractivityproject.Model.ActivityGson;
import com.example.manageractivityproject.Model.StaticClassModel;
import com.example.manageractivityproject.Model.User;
import com.example.manageractivityproject.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ChartFragment extends Fragment {
    public View view;
    private PieChart pieChart;
    private  ArrayList<Activity> activityChartArr;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_chart, container, false);
        init();

        return view;
    }
    public void init(){
        pieChart = view.findViewById(R.id.PieChart);
        setupPieChart();
        loadPieChartData();
    }

    private void setupPieChart() {
        pieChart.setDrawHoleEnabled(true);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(12);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setCenterText("Hoạt động");
        pieChart.setCenterTextSize(24);
        pieChart.getDescription().setEnabled(false);

        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(true);
    }
    private void loadPieChartData() {
        ArrayList<PieEntry> entries = new ArrayList<>();
        /*getApiChartManagerRegister();*/
        activityChartArr = StaticClassModel.getListActivityArrayList();
        if(activityChartArr == null || activityChartArr.isEmpty()){
            MessageLoadActivity("Thông báo", "Lỗi database không tồn tại dữ liệu", R.drawable.ic_alert_remove);
            return;
        }
        activityChartArr.forEach(activity -> {
            entries.add(new PieEntry(activity.getUsers().size(), activity.getTitle()));
        });
        ArrayList<Integer> colors = new ArrayList<>();
        for (int color: ColorTemplate.MATERIAL_COLORS) {
            colors.add(color);
        }

        for (int color: ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color);
        }

        PieDataSet dataSet = new PieDataSet(entries, "Hoạt đông");
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter(pieChart));
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.BLACK);

        pieChart.setData(data);
        pieChart.invalidate();

        pieChart.animateY(1400, Easing.EaseInOutQuad);
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

}
