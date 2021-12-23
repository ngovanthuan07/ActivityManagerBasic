package com.example.manageractivityproject.Fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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
import com.example.manageractivityproject.HomeActivity;
import com.example.manageractivityproject.R;
import com.example.manageractivityproject.UserInfoActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {
    private View view;
    private TextInputLayout layoutFullName;
    private TextInputEditText txtFullName;
    private TextView txtSelectPhoto;
    private Button btnContinue;
    private CircleImageView circleImageView;
    private static final int GALLERY_ADD_PROFILE = 1;
    private Bitmap bitmap = null;
    private SharedPreferences userPref;
    private ProgressDialog dialog;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile,container,false);
        init();
        circleImageView.setImageBitmap(ConvertBitMap.getConvertBitMap().stringToBitmap(userPref.getString("photo","")));
        txtFullName.setText(userPref.getString("fullname", ""));
        return view;

    }
    private void init() {
        dialog = new ProgressDialog(getActivity());
        dialog.setCancelable(false);
        userPref = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        layoutFullName = view.findViewById(R.id.txtLayoutNameUserInfo);
        txtFullName = view.findViewById(R.id.txtNameUserInfo);
        txtSelectPhoto = view.findViewById(R.id.txtSelectPhoto);
        btnContinue = view.findViewById(R.id.btnContinue);
        circleImageView = view.findViewById(R.id.imgUserInfo);

        //pick photo from gallery
        txtSelectPhoto.setOnClickListener(v->{
            Intent i = new Intent(Intent.ACTION_PICK);
            i.setType("image/*");
            startActivityForResult(i,GALLERY_ADD_PROFILE);
        });


        btnContinue.setOnClickListener(v->{
            // validate fields
            if(validate()){
                saveUserInfo();
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==GALLERY_ADD_PROFILE && resultCode== getActivity().RESULT_OK){
            Uri imgUri = data.getData();
            circleImageView.setImageURI(imgUri);
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),imgUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private boolean validate(){
        if (txtFullName.getText().toString().isEmpty()){
            layoutFullName.setErrorEnabled(true);
            layoutFullName.setError("Full name is Required");
            return false;
        }

        return true;
    }

    private void MessageUpdateProfile(String alert1, String alert2, int icon) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle(alert1);
        alertDialog.setIcon(icon);
        alertDialog.setMessage(alert2);
        alertDialog.setPositiveButton("Hoàn tác", (dialogInterface, i) -> {

        });
        alertDialog.show();
    }
    private void saveUserInfo(){
        dialog.setMessage("Saving");
        dialog.show();
        String fullname = txtFullName.getText().toString().trim();

        StringRequest request = new StringRequest(Request.Method.POST, Constant.SAVE_USER_INFO, response->{
            try {
                JSONObject object = new JSONObject(response);
                if (object.getBoolean("success")){
                    SharedPreferences.Editor editor = userPref.edit();
                    editor.putString("photo",object.getString("photo"));
                    editor.putString("fullname",object.getString("fullname"));
                    editor.apply();
                    MessageUpdateProfile("Thành công!", "Cập nhật tài khoản thành công", R.drawable.ic_success_save);
                }
            } catch (JSONException e) {
                MessageUpdateProfile("Thất bại!", "Cập nhật tài khoản thất bại", R.drawable.ic_error);
                e.printStackTrace();
            }

            dialog.dismiss();

        },error ->{
            MessageUpdateProfile("Thất bại!", "Cập nhật tài khoản thất bại", R.drawable.ic_error);
            error.printStackTrace();
            dialog.dismiss();
        } ){
            //add params
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put("username", userPref.getString("username", ""));
                map.put("fullname",fullname);
                map.put("photo", ConvertBitMap.getConvertBitMap().bitmapToString(bitmap));
                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(request);
    }


}
