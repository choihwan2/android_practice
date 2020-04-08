package com.example.androidlectureexamples;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Example25_PhoneAdressActivity extends AppCompatActivity {

    TextView resultTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example25_phone_adress);
        resultTv = findViewById(R.id._25_resultTv);


// 1. 사용자의 단말기 OS(안드로이드 버전) 이 마쉬멜로우 버전 이전이냐 이후 인지를 check
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 만약 우리가 사용하는 기기가 M 이상이면
            // 사용자 권한 중 주소록 읽기 권한이 설정되어 있는지를 check!!
            int permissionResult = ActivityCompat.checkSelfPermission(
                    getApplicationContext(),
                    Manifest.permission.READ_CONTACTS
            );

            if (permissionResult == PackageManager.PERMISSION_DENIED) {
                // 권한이 없으면
                // 왜 권한이 없을까?
                // 1. 앱을 처음 실행해서 아예 물어본적이 없는 경우.
                // 2. 권한 허용에 대해서 사용자에게 물어는 봤지만 사용자가 거절을 선택한 경우
                if (shouldShowRequestPermissionRationale(
                        android.Manifest.permission.READ_CONTACTS
                )) {
                    // true => 권한을 거부한적이 없는 경우.
                    // 일반적으로 dialog 같은걸 이용해서 다시 물어보기
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Example25_PhoneAdressActivity.this);
                    dialog.setTitle("권한이 필요해요");
                    dialog.setMessage("주소록 읽기 권한이 필요합니다. 수락할까요?");
                    dialog.setPositiveButton("네!!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            requestPermissions(new String[]{android.Manifest.permission.READ_CONTACTS}, 100);
                        }
                    });
                    dialog.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 권한 설정을 하지 않는다는 의미
                            // 아무런 작업도 할 필요가 없다.
                            Toast.makeText(Example25_PhoneAdressActivity.this, "권한이 있어야지만 주소록을 가져올 수 있습니다.", Toast.LENGTH_SHORT).show();
                        }
                    });

                    dialog.create().show();

                } else {
                    // false => 한번도 물어본적이 없는 경우.
                    // 사용자한테 허용할까요 묻는 함수 => requestPermissions()
                    // 여러 권한을 동시에 사용자에게 요청할 수 있끼 때문에 인자가 String 배열로 들어가요!
                    requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, 100);
                }
            } else {
                // 권한이 있으면
                Log.i("ContactTest", "3보안설정 통과!");
                processContact();
            }

        } else {
            // 만약 우리가 사용하는 기가 M 미만이면
            Log.i("ContactTest", "보안설정 통과!");
        }
        Button getContactBtn = findViewById(R.id.getContactBtn);
        getContactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("ContactTest","주소록 가져오기 버튼 클릭!");
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // 사용자가 권한을 설정하게 되면 이부분이 마지막으로 호출되게 되요!!
        // 사용자가 권한 설정을 하거나 그렇지 않거나 두가지 경우 모두 이 callBack 함수가 호출되요!
        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 사용자가 권한 허용을 눌렀을 경우.
                Log.i("ContactTest", "보안설정 통과!!");
                processContact(); //  주소록을 가져오는 메소드 호출
            }
        }
    }

    private void processContact(){
        // 주소록 가져오는 코드를 작성하면 될것같다.
        // 1. Content Resolver 를 이용해서 데이터를 가죠오면 되요!
        // select 계열을 사용해야 해요! => query() method 를 이용
        // 첫번째 인자로 URI 가 들어가야 해요
        Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
                null, // 모든 컬럼 다들고오기
                null, // 조건 없이!
                null, // 조건절 사용 없음
                null // 정렬도 안행
        );
        while (cursor.moveToNext()){
            // 각 사람의 이름과 ID 를 가져와야 해요!
            // 전화번호는 다른 테이블에 있다. 테이블 하나에 이름과 번호 모두가 들어가있는게 아님
            // ID를 통해서 각 사람의 전화번호를 다시 얻어와야한다.
            String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            Log.i("ContactTest","얻어온 ID : " + id);
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            Log.i("ContactTest","얻어온 이름 : " + name);

            Cursor mobileCursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id, null, null);

            String line = null;
            while (mobileCursor.moveToNext()){
                String mobile = mobileCursor.getString(mobileCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                line = "이름 : " + name +  ", Id : " + id + "전화 번호 : " + mobile;
            }
            resultTv.append(line + "\n");

        }
    }
}
