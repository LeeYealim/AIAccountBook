package com.example.aiacountbook;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
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
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddActivity extends AppCompatActivity {

    private TextView edit_date;
    private DatePickerDialog.OnDateSetListener callbackMethod;

    Button btnCamera;
    ImageView imageView;
    Uri photoUri;


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

//                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                // 사진파일 변수 선언 및 경로세팅
//                File photoFile = null; try {
//                    photoFile = createImageFile();
//                } catch (IOException ex) { }
//                // 사진을 저장하고 이미지뷰에 출력
//                if(photoFile != null) {
//                    photoUri = FileProvider.getUriForFile(AddActivity.this, getPackageName() + ".fileprovider", photoFile);
//                    i.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
//                    startActivityForResult(i, 0);
//                }

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

//        if(requestCode == 0 && resultCode == RESULT_OK) { // 이미지뷰에 파일경로의 사진을 가져와 출력
//            imageView.setImageURI(photoUri);
//        }

    }
    // ImageFile의 경로를 가져올 메서드 선언
    private File createImageFile() throws IOException { // 파일이름을 세팅 및 저장경로 세팅
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File StorageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile( imageFileName, ".jpg", storageDir );
        return image;
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