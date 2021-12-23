package com.example.manageractivityproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.manageractivityproject.Adapters.ActivityAdapter;
import com.example.manageractivityproject.Adapters.UserAdapter;
import com.example.manageractivityproject.Convert.ConvertBitMap;
import com.example.manageractivityproject.Model.StaticClassModel;
import com.example.manageractivityproject.Model.User;
import com.google.android.material.appbar.MaterialToolbar;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import gun0912.tedbottompicker.TedBottomPicker;

public class StudentManager extends AppCompatActivity {
    private RecyclerView recyclerStudentManager;
    private ActivityAdapter activityAdapter;
    private MaterialToolbar toolbarStudentManager;
    private ImageButton btnEditBackStudent, btnPDF;
    private ArrayList<User> userArrayList;
    private UserAdapter userAdapter;
    private SharedPreferences sharedPreferences;
    private TextView txtRegisterStudent;
    Date dateTime;
    DateFormat dateFormat;
    Bitmap bitmap, scaleBitmap;
    int pageWidth = 1200;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_manager);
        init();
        btnEditBackStudent.setOnClickListener(view -> {
            //StaticClassModel.clearListUser();
            finish();
        });


        // get list user

//        Gson gson = new Gson();
//        String users = sharedPreferences.getString("userz", "");
//        Type listUserGson = new TypeToken<ArrayList<User>>(){}.getType();
        userArrayList = null;
        if(StaticClassModel.getListUserStaticClassModel().size() > 0){
            userArrayList = StaticClassModel.getListUserStaticClassModel();
           // txtRegisterStudent.setText(userArrayList.get(0).getFullname());
        }
        userAdapter = new UserAdapter(userArrayList);
        recyclerStudentManager.setAdapter(userAdapter);
        createPDF(userArrayList);

    }

    private void createPDF(ArrayList<User> userArr) {
        btnPDF.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SimpleDateFormat")
            @Override
            public void onClick(View view) {
                requestPermissions();
                try {
                    dateTime = new Date();
                    //get input
                    PdfDocument pdfDocument = new PdfDocument();
                    Paint paint = new Paint();
                    Paint titlePaint = new Paint();

                    PdfDocument.PageInfo pageInfo
                            = new PdfDocument.PageInfo.Builder(1200, 2010, 1).create();
                    PdfDocument.Page page = pdfDocument.startPage(pageInfo);

                    Canvas canvas = page.getCanvas();
                    canvas.drawBitmap(scaleBitmap, 0, 0, paint);


                    paint.setColor(Color.WHITE);
                    paint.setTextSize(30f);
                    paint.setTextAlign(Paint.Align.RIGHT);
                    canvas.drawText("Đại Học Sư Phạm Kĩ Thuật", 1160, 40, paint);
                    canvas.drawText("Địa chỉ: ", 1160, 80, paint);

                    titlePaint.setTextAlign(Paint.Align.CENTER);
                    titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                    titlePaint.setColor(Color.WHITE);
                    titlePaint.setTextSize(70);
                    canvas.drawText(StaticClassModel.getActivityName(), pageWidth / 2, 500, titlePaint);

                    paint.setTextAlign(Paint.Align.LEFT);
                    paint.setColor(Color.BLACK);
                    paint.setTextSize(35f);
                    canvas.drawText("Đơn vị:  " + "Phòng Đào tạo", 20, 590, paint);
                    canvas.drawText("02 Thanh Sơn, Thanh Bình, Hải Châu, Đà Nẵng" + "Hello2", 20, 640, paint);

                    paint.setTextAlign(Paint.Align.RIGHT);
                    canvas.drawText("No. Mã số: " + "12345", pageWidth - 20, 590, paint);

                    dateFormat = new SimpleDateFormat("dd/MM/yy");
                    canvas.drawText("Ngày in: " + dateFormat.format(dateTime), pageWidth - 20, 640, paint);

                    dateFormat = new SimpleDateFormat("HH:mm:ss");
                    canvas.drawText("Giờ in: " + dateFormat.format(dateTime), pageWidth - 20, 690, paint);

                    paint.setStyle(Paint.Style.STROKE);
                    paint.setStrokeWidth(2);
                    canvas.drawRect(20, 780, pageWidth - 20, 860, paint);

                    paint.setTextAlign(Paint.Align.LEFT);
                    paint.setStyle(Paint.Style.FILL);
                    canvas.drawText("STT", 40, 830, paint);
                    canvas.drawText("Tên", 200, 830, paint);
                    canvas.drawText("Hoạt động", 500, 830, paint);
                    canvas.drawText("Ngày đăng kí", 700, 830, paint);
                    canvas.drawText("Chú thích", 1020, 830, paint);

                    canvas.drawLine(180, 790, 180, 840, paint);
                    canvas.drawLine(490, 790, 490, 840, paint);
                    canvas.drawLine(680, 790, 680, 840, paint);
                    canvas.drawLine(990, 790, 990, 840, paint);

                    float totalOne = 0, totalTwo = 0;
                    // edit
                    if(userArr != null || userArr.size() > 0){
                        int y = 950;
                        for(int i = 0; i < userArr.size(); i++){
                            canvas.drawText("" + (i + 1), 40, y, paint);
                            canvas.drawText(userArr.get(i).getFullname(), 200, y, paint);
                            canvas.drawText(StaticClassModel.getActivityName(), 500, y, paint);
                            canvas.drawText(userArr.get(i).getTimeregistration(), 700, y, paint);
                            paint.setTextAlign(Paint.Align.RIGHT);
                            canvas.drawText(String.valueOf(""), pageWidth - 40, y, paint);
                            paint.setTextAlign(Paint.Align.LEFT);
                            y += 100;
                        }
                    }







                    canvas.drawLine(400, 1200, pageWidth - 20, 1200, paint);
                    canvas.drawText("Số lượng đăng kí", 700, 1250, paint);
                    canvas.drawText(":", 900, 1250, paint);
                    paint.setTextAlign(Paint.Align.RIGHT);
                    // edit
                    canvas.drawText(String.valueOf(userArr.size()), pageWidth - 40, 1250, paint);

                    paint.setColor(Color.rgb(247, 147, 30));
                    canvas.drawRect(680, 1350, pageWidth - 20, 1450, paint);

                    paint.setColor(Color.BLACK);
                    paint.setTextSize(50f);
                    paint.setTextAlign(Paint.Align.LEFT);
                    canvas.drawText("No. Mã số:  ", 700, 1415, paint);
                    paint.setTextAlign(Paint.Align.RIGHT);
                    canvas.drawText("12345", pageWidth - 40, 1415, paint);

                    pdfDocument.finishPage(page);

                    File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "/danhsachdangkihoatdong.pdf");
                    try {
                        pdfDocument.writeTo(new FileOutputStream(file));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    pdfDocument.close();
                    Toast.makeText(StudentManager.this, "PDF Success", Toast.LENGTH_LONG).show();
                } catch (Exception PDFex){
                    Toast.makeText(StudentManager.this, "Không thể xuất PDF", Toast.LENGTH_LONG).show();
                    PDFex.printStackTrace();
                }
            }

        });

    }

    private void init(){
        sharedPreferences = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        recyclerStudentManager = findViewById(R.id.recyclerStudentManager);
        recyclerStudentManager.setLayoutManager(new LinearLayoutManager(this));
        toolbarStudentManager = findViewById(R.id.toolbarStudentManager);
        btnEditBackStudent = findViewById(R.id.btnEditBackStudent);
        btnPDF = findViewById(R.id.btnPDF);
        txtRegisterStudent = findViewById(R.id.txtRegisterStudent);

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.suphamkithuat);
        scaleBitmap = Bitmap.createScaledBitmap(bitmap, 1200, 518, false);
    }

    public void requestPermissions() {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(StudentManager.this, "Thiết lập thành công", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(StudentManager.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }
        };
        TedPermission.create()
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();
    }






}