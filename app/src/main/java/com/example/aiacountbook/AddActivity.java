package com.example.aiacountbook;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class AddActivity extends AppCompatActivity {

    private TextView edit_date;
    private DatePickerDialog.OnDateSetListener callbackMethod;

    Button btnCamera;
    ImageView imageView;


    private ActionBar ab;                   // 앱바

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        ab = getSupportActionBar() ;
        ab.setDisplayHomeAsUpEnabled(true);     // 위로 버튼 활성화
        ab.setTitle("영수증 등록");               // 앱바 타이틀 설정


        btnCamera = (Button) findViewById(R.id.btn_camera);
        imageView = (ImageView) findViewById(R.id.image_view);
        //btnCamera.setOnClickListener(this);


        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 토스트 메세지 전송
                Toast.makeText(AddActivity.this, "영수증 촬영 버튼 클릭",
                        Toast.LENGTH_SHORT).show();

                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i, 0);
            }
        });

    }

    // 사진 촬영 후 돌아와서 이미지뷰에 표시
    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);  // 카메라 촬영을 하면 이미지뷰에 사진 삽입
        if(requestCode == 0 && resultCode == RESULT_OK) {       // Bundle로 데이터를 입력
            Bundle extras = data.getExtras();                   // Bitmap으로 컨버전
            Bitmap imageBitmap = (Bitmap) extras.get("data");   // 이미지뷰에 Bitmap으로 이미지를 입력
            imageView.setImageBitmap(imageBitmap);
        }
    }








    public void InitializeView()
    {
        edit_date = (TextView) findViewById(R.id.edit_date);
    }

    public void InitializeListener()
    {
        callbackMethod = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                edit_date.setText(year + "년" + monthOfYear + "월" + dayOfMonth + "일");
            }
        };
    }

    public void OnClickHandler(View view)
    {
        DatePickerDialog dialog = new DatePickerDialog(this, callbackMethod, 2019, 5, 24);
        dialog.show();
    }
}