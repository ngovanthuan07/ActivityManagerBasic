package com.example.manageractivityproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.manageractivityproject.Constants.Constant;
import com.example.manageractivityproject.Convert.ConvertBitMap;
import com.example.manageractivityproject.Fragments.HomeFragment;
import com.example.manageractivityproject.Model.Activity;
import com.example.manageractivityproject.Model.ActivityGson;
import com.example.manageractivityproject.Model.StaticClassModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class EditActivity extends AppCompatActivity {
    private View view;
    private Bitmap bitmap = null;
    private TextInputLayout txtEditInputLayoutTitle, txtEditInputLayoutContent, txtEditInputLayoutCostactivitie,
            txtEditInputLayoutQuantity, txtEditInputLayoutDateStart, txtEditInputLayoutDateEnd;
    private TextInputEditText txtEditInputTitle, txtEditInputContent, txtEditInputCostactivitie,
            txtEditInputQuantity, txtEditInputDateStart, txtEditInputDateEnd;
    private Button btnEditActivity;
    private ImageButton btnEditBack;
    private TextView txtEditChangerPhotoActivity,txtEditTextDate;
    private RadioButton rgEditStatusOne, rgEditStatusTwo;
    private ImageView imgEditPhoToActivity;
    private static final int GALLERY_CHANGE_ACTIVITY = 3;
    Calendar calendarDateStart, calendarDateEnd;
    SimpleDateFormat simpleDateFormat;
    private int position = 0;
    private SharedPreferences userPref;

    private void SelectDateStart() {
        calendarDateStart = Calendar.getInstance();
        int day = calendarDateStart.get(Calendar.DATE);
        int month = calendarDateStart.get(Calendar.MONTH);
        int year = calendarDateStart.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (datePicker, i, i1, i2) -> {
            calendarDateStart.set(i, i1, i2);
            txtEditInputDateStart.setText(simpleDateFormat.format(calendarDateStart.getTime()));
        }, year, month, day);
        datePickerDialog.show();
    }

    private void SelectDateEnd() {
        calendarDateEnd = Calendar.getInstance();
        int day = calendarDateEnd.get(Calendar.DATE);
        int month = calendarDateEnd.get(Calendar.MONTH);
        int year = calendarDateEnd.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (datePicker, i, i1, i2) -> {
            calendarDateEnd.set(i, i1, i2);
            txtEditInputDateEnd.setText(simpleDateFormat.format(calendarDateEnd.getTime()));
        }, year, month, day);
        datePickerDialog.show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_acivity);
        init();
        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        txtEditInputDateStart.setOnClickListener(v -> {
            SelectDateStart();

        });
        txtEditInputDateEnd.setOnClickListener(v -> {
            SelectDateEnd();
        });
    }

    // handle phto
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_CHANGE_ACTIVITY && resultCode == RESULT_OK) {
            Uri imgUri = data.getData();
            imgEditPhoToActivity.setImageURI(imgUri);
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imgUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void EditTextValidate() {
        // title
        txtEditInputTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!txtEditInputTitle.getText().toString().isEmpty()) {
                    txtEditInputLayoutTitle.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        // content
        txtEditInputContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!txtEditInputContent.getText().toString().isEmpty()) {
                    txtEditInputLayoutContent.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        // Costactivitie
        txtEditInputCostactivitie.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!txtEditInputCostactivitie.getText().toString().isEmpty()) {
                    txtEditInputLayoutCostactivitie.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        // Quantity
        txtEditInputQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!txtEditInputQuantity.getText().toString().isEmpty()) {
                    txtEditInputLayoutQuantity.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        // Date Start
        txtEditInputDateStart.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!txtEditInputDateStart.getText().toString().isEmpty()) {
                    txtEditInputLayoutDateStart.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        // Date End
        txtEditInputDateEnd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!txtEditInputDateEnd.getText().toString().isEmpty()) {
                    txtEditInputLayoutDateEnd.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                Date date = Calendar.getInstance().getTime();
                int StartWithCurrent = (int) ((calendarDateStart.getTimeInMillis() - date.getTime()) / (1000 * 60 * 60 * 24));
                if (StartWithCurrent >= 0) {
                    txtEditInputLayoutDateStart.setErrorEnabled(false);
                }
            }
        });
    }

    private void init() {
        userPref = getApplication().getSharedPreferences("user", Context.MODE_PRIVATE);
        txtEditInputLayoutTitle = findViewById(R.id.txtEditInputLayoutTitle);
        txtEditInputTitle = findViewById(R.id.txtEditInputTitle);
        txtEditInputLayoutContent = findViewById(R.id.txtEditInputLayoutContent);
        txtEditInputContent = findViewById(R.id.txtEditInputContent);
        txtEditInputLayoutCostactivitie = findViewById(R.id.txtEditInputLayoutCostactivitie);
        txtEditInputCostactivitie = findViewById(R.id.txtEditInputCostactivitie);
        txtEditInputLayoutQuantity = findViewById(R.id.txtEditInputLayoutQuantity);
        txtEditInputQuantity = findViewById(R.id.txtEditInputQuantity);
        txtEditInputLayoutDateStart = findViewById(R.id.txtEditInputLayoutDateStart);
        txtEditInputDateStart = findViewById(R.id.txtEditInputDateStart);
        txtEditInputLayoutDateEnd = findViewById(R.id.txtEditInputLayoutDateEnd);
        txtEditInputDateEnd = findViewById(R.id.txtEditInputDateEnd);
        btnEditActivity = findViewById(R.id.btnEditCreateActivity);
        btnEditBack = findViewById(R.id.btnEditBack);
        txtEditChangerPhotoActivity = findViewById(R.id.txtEditChangerPhotoActivity);
        txtEditTextDate = findViewById(R.id.txtEditTextDate);
        rgEditStatusOne = findViewById(R.id.rgEditStatusOne);
        rgEditStatusTwo = findViewById(R.id.rgEditStatusTwo);
        bitmap = ConvertBitMap.getConvertBitMap().stringToBitmap(StaticClassModel.getActivityModel().getPhoto());
        // handle img
        imgEditPhoToActivity = findViewById(R.id.imgEditPhoToActivity);
        txtEditChangerPhotoActivity.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_PICK);
            i.setType("image/*");
            startActivityForResult(i, GALLERY_CHANGE_ACTIVITY);
        });
        initValueEdit();
        EditTextValidate();

        // handle btn
        btnEditActivity.setOnClickListener(v -> {
            if (validate()) {

                EditValueActivity();
            }

        });
        btnEditBack.setOnClickListener(v -> {
            finish();
        });


    }

    private void EditValueActivity() {
        StringRequest request = new StringRequest(Request.Method.POST, Constant.UPDATE_ACTIVITY, response->{
            try {
                Gson gson = new Gson();
                ActivityGson edit = gson.fromJson(response, ActivityGson.class);
                if (edit.isSuccess()){
                    Activity acti = edit.getActivity();
                    HomeFragment.list.set(position, acti);
                    HomeFragment.recyclerView.getAdapter().notifyItemChanged(position);
                    HomeFragment.recyclerView.getAdapter().notifyDataSetChanged();
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                    alertDialog.setTitle("Thêm thành công");
                    alertDialog.setIcon(R.drawable.ic_alert_sucess_blue);
                    alertDialog.setMessage("Vào trang chủ để xem lại kết quả");
                    alertDialog.setPositiveButton("Có", (dialogInterface, i) -> {
                        finish();
                    });
                    alertDialog.setNegativeButton("Hoàn tác", ((dialogInterface, i) -> {}));
                    alertDialog.show();
                }
            } catch (Exception e) {
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
                map.put("id", String.valueOf(StaticClassModel.getActivityModel().getId()));
                map.put("title", txtEditInputTitle.getText().toString());
                map.put("content",txtEditInputContent.getText().toString());
                map.put("costactivities",txtEditInputCostactivitie.getText().toString());
                map.put("quantity",txtEditInputQuantity.getText().toString());
                map.put("status", rgEditStatusOne.isChecked() ? "Đang diễn ra" : "Kết thúc");
                String photo = "";
                        if(bitmap == null){
                            photo = StaticClassModel.getActivityModel().getPhoto();
                        }
                        else {
                            photo = ConvertBitMap.getConvertBitMap().bitmapToString(bitmap);
                        }
                map.put("photo", photo);
                map.put("datestart", txtEditTextDate.getText().toString());
                map.put("dateend", txtEditInputDateEnd.getText().toString());
                map.put("createdby", userPref.getString("username", ""));
                map.put("modifiedby", userPref.getString("username", ""));
                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(EditActivity.this);
        queue.add(request);
    }


    private void initValueEdit() {
        if(StaticClassModel.getActivityModel() == null){
            return;
        }
        txtEditInputTitle.setText(StaticClassModel.getActivityModel().getTitle());
        txtEditInputContent.setText(StaticClassModel.getActivityModel().getContent());
        txtEditInputCostactivitie.setText(StaticClassModel.getActivityModel().getCostactivities());
        txtEditInputQuantity.setText(StaticClassModel.getActivityModel().getQuantity());
        txtEditInputDateStart.setText(StaticClassModel.getActivityModel().getDatestart());
        txtEditInputDateEnd.setText(StaticClassModel.getActivityModel().getDateend());
        imgEditPhoToActivity.setImageBitmap(ConvertBitMap
                .getConvertBitMap()
                .stringToBitmap(StaticClassModel.getActivityModel().getPhoto()));
        position = StaticClassModel.getPosition();
    }


    private void MessageSaveActivity(String alert1, String alert2, int drawable) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(alert1);
        alertDialog.setIcon(drawable);
        alertDialog.setMessage(alert2);
        alertDialog.setPositiveButton("Hoàn tác", (dialogInterface, i) -> {

        });
        alertDialog.show();
    }

    private boolean validate() {
        if (txtEditInputTitle.getText().toString().isEmpty()) {
            txtEditInputLayoutTitle.setErrorEnabled(true);
            txtEditInputTitle.setError("Không được để trống");
            return false;
        }
        if (txtEditInputContent.getText().toString().isEmpty()) {
            txtEditInputLayoutContent.setErrorEnabled(true);
            txtEditInputContent.setError("Không được để trống");
            return false;
        }
        if (txtEditInputCostactivitie.getText().toString().isEmpty()) {
            txtEditInputLayoutCostactivitie.setErrorEnabled(true);
            txtEditInputCostactivitie.setError("Không được để trống");
            return false;
        }
        if (txtEditInputQuantity.getText().toString().isEmpty()) {
            txtEditInputLayoutQuantity.setErrorEnabled(true);
            txtEditInputQuantity.setError("Không được để trống");
            return false;
        }
        if (txtEditInputDateStart.getText().toString().isEmpty()) {
            txtEditInputLayoutDateStart.setErrorEnabled(true);
            txtEditInputDateStart.setError("Không được để trống");
            return false;
        }
        if (txtEditInputDateEnd.getText().toString().isEmpty()) {
            txtEditInputLayoutDateEnd.setErrorEnabled(true);
            txtEditInputDateEnd.setError("Không được để trống");
            return false;
        }
        if(bitmap == null){
            return false;
        }
        return true;
    }


}