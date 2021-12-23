package com.example.manageractivityproject.Fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.manageractivityproject.Constants.Constant;
import com.example.manageractivityproject.Convert.ConvertBitMap;
import com.example.manageractivityproject.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddActivityFragment extends Fragment {
    private View view;
    private Bitmap bitmap = null;
    private TextInputLayout txtInputLayoutTitle,txtInputLayoutContent,txtInputLayoutCostactivitie,txtInputLayoutQuantity,txtInputLayoutDateStart,txtInputLayoutDateEnd;
    private TextInputEditText txtInputTitle,txtInputContent,txtInputCostactivitie,txtInputQuantity,txtInputDateStart,txtInputDateEnd;
    private Button btnAddCreateActivity;
    private TextView txtChangerPhotoActivity;
    private RadioButton rgStatusOne, rgStatusTwo;
    private ImageView imgAddPhoToActivity;
    private static final  int GALLERY_CHANGE_ACTIVITY = 3;
    Calendar calendarDateStart, calendarDateEnd;
    SimpleDateFormat simpleDateFormat;
    private SharedPreferences userPref;

    private void SelectDateStart(){
        calendarDateStart = Calendar.getInstance();
        int day = calendarDateStart.get(Calendar.DATE);
        int month = calendarDateStart.get(Calendar.MONTH);
        int year = calendarDateStart.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),(datePicker, i, i1, i2) -> {
            calendarDateStart.set(i,i1,i2);
            txtInputDateStart.setText(simpleDateFormat.format(calendarDateStart.getTime()));
        },year,month,day);
        datePickerDialog.show();
    }
    private void SelectDateEnd(){
        calendarDateEnd = Calendar.getInstance();
        int day = calendarDateEnd.get(Calendar.DATE);
        int month = calendarDateEnd.get(Calendar.MONTH);
        int year = calendarDateEnd.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),(datePicker, i, i1, i2) -> {
            calendarDateEnd.set(i,i1,i2);
            txtInputDateEnd.setText(simpleDateFormat.format(calendarDateEnd.getTime()));
        },year,month,day);
        datePickerDialog.show();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_activity, container, false);
        init();
        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        txtInputDateStart.setOnClickListener(v ->{
            SelectDateStart();

        });
        txtInputDateEnd.setOnClickListener(v->{
            SelectDateEnd();
        });
        return view;
    }
    public void init(){
        userPref = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        txtInputLayoutTitle = view.findViewById(R.id.txtInputLayoutTitle);
        txtInputTitle = view.findViewById(R.id.txtInputTitle);
        txtInputLayoutContent = view.findViewById(R.id.txtInputLayoutContent);
        txtInputContent = view.findViewById(R.id.txtInputContent);
        txtInputLayoutCostactivitie = view.findViewById(R.id.txtInputLayoutCostactivitie);
        txtInputCostactivitie = view.findViewById(R.id.txtInputCostactivitie);
        txtInputLayoutQuantity = view.findViewById(R.id.txtInputLayoutQuantity);
        txtInputQuantity = view.findViewById(R.id.txtInputQuantity);
        txtInputLayoutDateStart = view.findViewById(R.id.txtInputLayoutDateStart);
        txtInputDateStart = view.findViewById(R.id.txtInputDateStart);
        txtInputLayoutDateEnd = view.findViewById(R.id.txtInputLayoutDateEnd);
        txtInputDateEnd = view.findViewById(R.id.txtInputDateEnd);
        btnAddCreateActivity = view.findViewById(R.id.btnAddCreateActivity);
        txtChangerPhotoActivity = view.findViewById(R.id.txtChangerPhotoActivity);
        rgStatusOne = view.findViewById(R.id.rgStatusOne);
        // handle img
        imgAddPhoToActivity = view.findViewById(R.id.imgAddPhoToActivity);
        txtChangerPhotoActivity.setOnClickListener(v->{
            Intent i = new Intent(Intent.ACTION_PICK);
            i.setType("image/*");
            startActivityForResult(i,GALLERY_CHANGE_ACTIVITY);
        });
        AddTextValidate();
        // handle btn
        btnAddCreateActivity.setOnClickListener(v->{
            if(validate()){
                AddValueActivity();
            };
        });


    }
    private void MessageSaveActivity(String alert1, String alert2, int drawable){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle(alert1);
        alertDialog.setIcon(drawable);
        alertDialog.setMessage(alert2);
        alertDialog.setPositiveButton("Hoàn tác", (dialogInterface, i) -> {

        });
        alertDialog.show();
    }
    public void AddValueActivity(){
        StringRequest request = new StringRequest(Request.Method.POST, Constant.CREATE_ACTIVITY, response->{
            try {
                JSONObject object = new JSONObject(response);
                if (object.getBoolean("success")){
                    MessageSaveActivity("Thêm thành công!","Bạn hãy ra trang chủ để xem kết quả vừa thêm", R.drawable.ic_success_save);
                    txtInputTitle.setText("");
                    txtInputContent.setText("");
                    txtInputCostactivitie.setText("");
                    txtInputQuantity.setText("");
                    txtInputDateStart.setText("");
                    txtInputDateEnd.setText("");
                    // imgAddPhoToActivity.invalidate();
                    imgAddPhoToActivity.setImageBitmap(null);


                }
            } catch (JSONException e) {
                MessageSaveActivity("Thất bại!", "Đã có vấn đề ở phần nào đó", R.drawable.ic_error);
                e.printStackTrace();
            }



        },error ->{
            MessageSaveActivity("Thất bại!", "Đã có vấn đề ở phần nào đó", R.drawable.ic_error);
            error.printStackTrace();

        } ){
            //add params
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put("title", txtInputTitle.getText().toString());
                map.put("content",txtInputContent.getText().toString());
                map.put("costactivities",txtInputCostactivitie.getText().toString());
                map.put("quantity",txtInputQuantity.getText().toString());
                map.put("status", rgStatusOne.isChecked() ? "Đang diễn ra" : "Kết thúc");
                map.put("photo", ConvertBitMap.getConvertBitMap().bitmapToString(bitmap));
                map.put("datestart", txtInputDateStart.getText().toString());
                map.put("dateend", txtInputDateEnd.getText().toString());
                map.put("createdby", userPref.getString("username", ""));
                map.put("modifiedby", userPref.getString("username", ""));
                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(request);
    }


    // handle phto
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== GALLERY_CHANGE_ACTIVITY && resultCode==getActivity().RESULT_OK){
            Uri imgUri = data.getData();
            imgAddPhoToActivity.setImageURI(imgUri);
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),imgUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void AddTextValidate(){
        // title
        txtInputTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!txtInputTitle.getText().toString().isEmpty()){
                    txtInputLayoutTitle.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        // content
        txtInputContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!txtInputContent.getText().toString().isEmpty()){
                    txtInputLayoutContent.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        // Costactivitie
        txtInputCostactivitie.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!txtInputCostactivitie.getText().toString().isEmpty()){
                    txtInputLayoutCostactivitie.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        // Quantity
        txtInputQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!txtInputQuantity.getText().toString().isEmpty()){
                    txtInputLayoutQuantity.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        // Date Start
        txtInputDateStart.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!txtInputDateStart.getText().toString().isEmpty()){
                    txtInputLayoutDateStart.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        // Date End
        txtInputDateEnd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!txtInputDateEnd.getText().toString().isEmpty()){
                    txtInputLayoutDateEnd.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                Date date = Calendar.getInstance().getTime();
                int StartWithCurrent = (int) ((calendarDateStart.getTimeInMillis() - date.getTime()) / (1000*60*60*24));
                if(StartWithCurrent >= 0){
                    txtInputLayoutDateStart.setErrorEnabled(false);
                }
            }
        });
    }

    private boolean validate(){
        if (txtInputTitle.getText().toString().isEmpty()){
            txtInputLayoutTitle.setErrorEnabled(true);
            txtInputTitle.setError("Không được để trống");
            return false;
        }
        if (txtInputContent.getText().toString().isEmpty()){
            txtInputLayoutContent.setErrorEnabled(true);
            txtInputContent.setError("Không được để trống");
            return false;
        }
        if (txtInputCostactivitie.getText().toString().isEmpty()){
            txtInputLayoutCostactivitie.setErrorEnabled(true);
            txtInputCostactivitie.setError("Không được để trống");
            return false;
        }
        if (txtInputQuantity.getText().toString().isEmpty()){
            txtInputLayoutQuantity.setErrorEnabled(true);
            txtInputQuantity.setError("Không được để trống");
            return false;
        }
        if (txtInputDateStart.getText().toString().isEmpty()){
            txtInputLayoutDateStart.setErrorEnabled(true);
            txtInputDateStart.setError("Không được để trống");
            return false;
        }
        if (txtInputDateEnd.getText().toString().isEmpty()){
            txtInputLayoutDateEnd.setErrorEnabled(true);
            txtInputDateEnd.setError("Không được để trống");
            return false;
        }
        if (bitmap == null){
            Toast.makeText(getContext(), "Vui lòng chọn hình ảnh", Toast.LENGTH_SHORT).show();
            return false;
        }
        Date date = Calendar.getInstance().getTime();
        int StartWithCurrent = (int) ((calendarDateStart.getTimeInMillis() - date.getTime()) / (1000*60*60*24));
        if(StartWithCurrent < 0){
            txtInputLayoutDateStart.setErrorEnabled(true);
            txtInputDateStart.setError("Ngày bắt đầu phải lớn hơn ngày hiện tại");
            return false;
        }
        int EndWithStart = (int) ((calendarDateEnd.getTimeInMillis() - calendarDateStart.getTimeInMillis()) / (1000*60*60*24));
        if(EndWithStart < 0){
            txtInputLayoutDateEnd.setErrorEnabled(true);
            txtInputDateEnd.setError("Ngày kết thúc phải lớn hơn ngày bắt đầu");
            return false;
        }
        return true;
    }

}
