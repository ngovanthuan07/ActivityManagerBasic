package com.example.manageractivityproject.Fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.manageractivityproject.Constants.Constant;
import com.example.manageractivityproject.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ChangerPasswordFragment extends Fragment {
    private View view;
    private TextInputLayout layoutPasswordChanger, layoutConfirmChanger;
    private TextInputEditText txtPasswordChanger, txtConfirmChanger;
    private Button btnPassWordChanger;
    private ProgressDialog dialog;
    private SharedPreferences userPref;


    public ChangerPasswordFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_changer_password,container,false);
        init();
        return view;
    }

    private void init() {
        userPref = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        layoutPasswordChanger = view.findViewById(R.id.txtLayoutPasswordChanger);
        layoutConfirmChanger = view.findViewById(R.id.txtLayoutConfrimChanger);
        txtPasswordChanger = view.findViewById(R.id.txtPasswordChanger);
        txtConfirmChanger = view.findViewById(R.id.txtConfirmChanger);
        btnPassWordChanger = view.findViewById(R.id.btnChangerPassword);
        dialog = new ProgressDialog(getContext());
        dialog.setCancelable(false);


        btnPassWordChanger.setOnClickListener(v->{
            //validate fields first
            if (validate()){
                register();
            }
        });


        txtPasswordChanger.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (txtPasswordChanger.getText().toString().length()>7){
                    layoutPasswordChanger.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txtConfirmChanger.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (txtConfirmChanger.getText().toString().equals(txtPasswordChanger.getText().toString())){
                    layoutConfirmChanger.setErrorEnabled(false);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    private boolean validate (){
        if (txtPasswordChanger.getText().toString().length()<8){
            layoutPasswordChanger.setErrorEnabled(true);
            layoutPasswordChanger.setError("Required at least 8 characters");
            return false;
        }
        if (!txtConfirmChanger.getText().toString().equals(txtPasswordChanger.getText().toString())){
            layoutConfirmChanger.setErrorEnabled(true);
            layoutConfirmChanger.setError("Password does not match");
            return false;
        }

        return true;
    }

    private void MessageChangerPassWord(String alert1, String alert2, int drawable) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle(alert1);
        alertDialog.setIcon(drawable);
        alertDialog.setMessage(alert2);
        alertDialog.setPositiveButton("Hoàn tác", (dialogInterface, i) -> {

        });
        alertDialog.show();
    }
    private void register(){
        dialog.setMessage("Changer Password");
        dialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, Constant.CHANGER_PASSWORD, response -> {
            //we get response if connection success
            try {
                JSONObject object = new JSONObject(response);

                if (object.getBoolean("success")){
                    SharedPreferences.Editor editor = userPref.edit();
                    editor.putString("password",object.getString("password"));
                    editor.apply();
                    //if success

                    MessageChangerPassWord("Thành công!", "Nhấn hoàn tác để quay lại", R.drawable.ic_success_save);
                }
            } catch (JSONException e) {
                MessageChangerPassWord("Thất bại!", "Nhần hoàn tác để quay lại", R.drawable.ic_error);
                e.printStackTrace();
            }
            dialog.dismiss();

        },error -> {
            MessageChangerPassWord("Thất bại!", "Nhần hoàn tác để quay lại", R.drawable.ic_error);
            // error if connection not success
            error.printStackTrace();
            dialog.dismiss();
        }){

            // add parameters
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put("username", userPref.getString("username", ""));
                map.put("password", txtPasswordChanger.getText().toString());
                return map;
            }
        };
        //add this request to requestqueue
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);
}

}
