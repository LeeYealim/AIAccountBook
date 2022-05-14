package com.example.aiacountbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImageTestActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 0;

    private String mPhotoFileName = null;
    private File mPhotoFile = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_test);

        Button imageCaptureBtn = findViewById(R.id.imageCaptureBtn2);

        imageCaptureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("yelim","버튼 클릭 이벤트");
                dispatchTakePictureIntent();
            }
        });
    }

    private String currentDateFormat() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HH_mm_ss");
        String currentTimeStamp = dateFormat.format(new Date());
        return currentTimeStamp;
    }

    // 카메라 앱으로 찍은 사진 저장하기
    private void dispatchTakePictureIntent() {
        // 카메라 앱을 실행시키기 위해 ACTION_IMAGE_CAPTURE로 인텐트를 하나 만듦
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            //1. 카메라 앱으로 찍은 이미지를 저장할 파일 객체 생성
            mPhotoFileName = "IMG" + currentDateFormat() + ".jpg";
            mPhotoFile = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), mPhotoFileName);

            if (mPhotoFile != null) {
                //2. 생성된 파일 객체에 대한 Uri 객체를 얻기
                Uri imageUri = FileProvider.getUriForFile(this, "com.example.aiacountbook.fileprovider", mPhotoFile);

                //3. Uri 객체를 Extras를 통해 카메라 앱으로 전달
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                // 만든 인텐트를 startActivityForResult의 파라미터로 넣어줌
                // 이미지 캡처할 수 있는 앱을 실행시킴
                // 이 때 인텐트의 파라미터로 imageUri를 넘겨줌 : 해당 이미지 Uri에 카메라 결과 저장
                // 가리키는 파일이 곧 mPhotoFileName 을 의미
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            } else
                Toast.makeText(getApplicationContext(), "file null", Toast.LENGTH_SHORT).show();
        }
    }



    // 결과를 처리하는 방법
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("yelim","onActivityResult() 호출");


        // 이미지 캡처 실행 후 사진 결과 처리하는 방법
        // 리스트 뷰에 추가 이미지 뷰에도 보이게 추가
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            Log.d("yelim","if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK)");

            // mPhotoFileName 가 앞에서 만든 파일 이름임
            if (mPhotoFileName != null) {

                Log.d("yelim","if (mPhotoFileName != null)");

                // 이 이름을 바탕으로 하는 파일 객체를 가져와서
                mPhotoFile = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), mPhotoFileName);

                ImageView imageView = findViewById(R.id.imageView2);
                // URI를 뽑아와서 그 URI 기반으로 URI가 가리키는 파일 내용을 이미지뷰에 표시
                imageView.setImageURI(Uri.fromFile(mPhotoFile));

                // mAdapter.addItem(new MediaItem(MediaItem.SDCARD, mPhotoFileName, MediaItem.IMAGE));
            } else
                Toast.makeText(getApplicationContext(), "mPhotoFile is null", Toast.LENGTH_SHORT).show();
        }

    }
}