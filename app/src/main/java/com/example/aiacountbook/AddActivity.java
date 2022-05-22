package com.example.aiacountbook;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferNetworkLossHandler;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.example.aiacountbook.api.PostRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddActivity extends AppCompatActivity {

    private TextView edit_date;
    private DatePickerDialog.OnDateSetListener callbackMethod;

    Button btnCamera;
    ImageView imageView;

    static final int REQUEST_IMAGE_CAPTURE = 0;

    private String mPhotoFileName = null;
    private File mPhotoFile = null;
    private Uri imageUri = null;


    private ActionBar ab;                   // 앱바

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        ab = getSupportActionBar() ;
        //ab.setDisplayHomeAsUpEnabled(true);     // 위로 버튼 활성화
        ab.setTitle("영수증 등록");               // 앱바 타이틀 설정

        // 취소 버튼 클릭 시 화면 종료
        Button Closebtn = findViewById(R.id.btn_close);
        Closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // 확인 버튼 클릭 시 화면 종료
        Button btn_ok = findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("yelim","확인 버튼 클릭");

                EditText edit_date = (EditText)findViewById(R.id.edit_date);
                String str_date = edit_date.getText().toString();

                EditText edit_place = (EditText)findViewById(R.id.edit_place);
                String str_place = edit_place.getText().toString();

                EditText edit_price = (EditText)findViewById(R.id.edit_price);
                String str_price = edit_price.getText().toString();
                
                if(str_date.equals("") && str_place.equals("") && str_price.equals("")){
                    Toast.makeText(AddActivity.this, "입력값을 확인하세요.",
                        Toast.LENGTH_SHORT).show();
                    return;
                }
                // 데이터 저장 POST API 호출
                JSONObject payload = new JSONObject();
                try {
                    payload.put("date", str_date);
                    payload.put("place", str_place);
                    payload.put("price", str_price);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String uri = "https://e866-110-14-126-182.ngrok.io/accounts";
                new PostRequest(AddActivity.this, uri, "insert").execute(payload);
            }
        });


        // 날짜 선택 버튼
        ImageButton btn_date = (ImageButton)findViewById(R.id.btn_date);
        btn_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("yelim","date 버튼 클릭 이벤트");
                callbackMethod = new DatePickerDialog.OnDateSetListener()
                {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
                    {
                        EditText edit_date = (EditText)findViewById(R.id.edit_date);
                        edit_date.setText(String.format("%d-%d-%d ", year ,monthOfYear+1,dayOfMonth));
                    }
                };

                DatePickerDialog dialog = new DatePickerDialog(AddActivity.this, callbackMethod, 2020, 12, 0);
                dialog.show();
            }
        });

        
        btnCamera = (Button) findViewById(R.id.btn_camera);
        imageView = (ImageView) findViewById(R.id.image_view);
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                // 토스트 메세지 전송
//                Toast.makeText(AddActivity.this, "영수증 촬영 버튼 클릭",
//                        Toast.LENGTH_SHORT).show();
                
                // 사진 촬영 메소드 호출
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
                imageUri = FileProvider.getUriForFile(this, "com.example.aiacountbook.fileprovider", mPhotoFile);

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

                // URI를 뽑아와서 그 URI 기반으로 URI가 가리키는 파일 내용을 이미지뷰에 표시
                imageView.setImageURI(Uri.fromFile(mPhotoFile));
                
                // AWS 버킷에 사진 업로드
                uploadWithTransferUtilty(mPhotoFileName, mPhotoFile);

                // 이미지 텍스트 인식 POST API 호출
                JSONObject payload = new JSONObject();
                try {
                    payload.put("img", mPhotoFileName);     // 이미지 파일명 바디에 넣어 전송
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String uri = "https://e866-110-14-126-182.ngrok.io/recognition";
                new PostRequest(AddActivity.this, uri, "recognition").execute(payload);

                // mAdapter.addItem(new MediaItem(MediaItem.SDCARD, mPhotoFileName, MediaItem.IMAGE));
            } else
                Toast.makeText(getApplicationContext(), "mPhotoFile is null", Toast.LENGTH_SHORT).show();
        }
    }




    // AWS 파일 전송 코드
    public void uploadWithTransferUtilty(String fileName, File file) {
        Log.d("yelim","uploadWithTransferUtilty() 호출 ...");

        // AIUser
        AWSCredentials awsCredentials = new BasicAWSCredentials("AKIA6CYUKORF5PGRSLWA", "jIz3DG5xKym6VP473xIRNZAOlABrQ+j9GSXXOhtU");	// IAM 생성하며 받은 것 입력
        AmazonS3Client s3Client = new AmazonS3Client(awsCredentials, Region.getRegion(Regions.AP_NORTHEAST_2));

        TransferUtility transferUtility = TransferUtility.builder().s3Client(s3Client).context(AddActivity.this.getApplicationContext()).build();
        TransferNetworkLossHandler.getInstance(AddActivity.this.getApplicationContext());

        TransferObserver uploadObserver = transferUtility.upload("hansung-ai-bucket", fileName, file);	// (bucket api, file이름, file객체)

        uploadObserver.setTransferListener(new TransferListener() {
            @Override
            public void onStateChanged(int id, TransferState state) {
                Log.d("yelim", "onStateChanged() ...");
                if (state == TransferState.COMPLETED) {
                    Log.d("yelim", "onStateChanged() COMPLETED ...");
                    // Handle a completed upload
                }
            }
            @Override
            public void onProgressChanged(int id, long current, long total) {
                int done = (int) (((double) current / total) * 100.0);
                Log.d("yelim", "UPLOAD - - ID: $id, percent done = $done");
            }
            @Override
            public void onError(int id, Exception ex) {
                Log.d("yelim", "UPLOAD ERROR - - ID: $id - - EX:" + ex.toString());
            }
        });
    }
}