package com.example.sqlitedemo;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class UpdateActivity extends AppCompatActivity {
    private ImageView ivSpot;
    private EditText etName;
    private EditText etWeb;
    private EditText etPhone;
    private EditText etAddress;
    private MySQLiteOpenHelper sqliteHelper;
    private Spot spot;
    private static final int REQUEST_TAKE_PICTURE = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert_activity);
        if (sqliteHelper == null){
            sqliteHelper = new MySQLiteOpenHelper(this);
        }
        finViews();
    }

    private void finViews() {
        ivSpot = (ImageView) findViewById(R.id.ivSpot);
        etName = (EditText) findViewById(R.id.etName);
        etWeb = (EditText) findViewById(R.id.etWeb);
        etPhone = (EditText) findViewById(R.id.etPhone);
        etAddress = (EditText) findViewById(R.id.etAddress);

        int id =getIntent().getExtras().getInt("id");
        spot=sqliteHelper.findById(id);
        if (spot ==null){
            Toast.makeText(this,R.string.msg_NoDataFound,Toast.LENGTH_SHORT).show();
            return;
        }
        Bitmap bitmap= BitmapFactory.decodeByteArray(spot.getImage(),0,spot.getImage().length);
        ivSpot.setImageBitmap(bitmap);
        etName.setText(spot.getName());
        etWeb.setText(spot.getWeb());
        etPhone.setText(spot.getPhone());
        etAddress.setText(spot.getAddress());
    }
    public void onTakePictureClick(View view){
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (isIntentAvailable(this,intent)){
            startActivityForResult(intent, REQUEST_TAKE_PICTURE);
        }else{
            Toast.makeText(this,R.string.msg_NoCameraAppsFound,Toast.LENGTH_SHORT).show();
        }
    }
    private boolean isIntentAvailable(Context context, Intent intent){
        PackageManager packageManager=context.getPackageManager();
        List<ResolveInfo> list=packageManager.queryIntentActivities(intent,PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() >0;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK){
            switch (requestCode){
                case REQUEST_TAKE_PICTURE:
                    Bitmap bitmap=(Bitmap) intent.getExtras().get("data");
                    if (bitmap != null){
                        ivSpot.setImageBitmap(bitmap);
                        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
                        spot.setImage(byteArrayOutputStream.toByteArray());
                    }
                    break;
                default:
                    break;
            }
        }
    }
    public void onInsertClick(View view){
        String name=etName.getText().toString().trim();
        String web =etWeb.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        if (name.length() <= 0){
            Toast.makeText(this,R.string.msg_NameIsInvalid,Toast.LENGTH_SHORT).show();
            return;
        }
        spot.setName(name);
        spot.setWeb(web);
        spot.setPhone(phone);
        spot.setAddress(address);

        int count=sqliteHelper.update(spot);
        Toast.makeText(this,count+" "+getString(R.string.msg_RowUpdated),Toast.LENGTH_SHORT).show();

        finish();
    }
    public void onCancelClick(View view){
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (sqliteHelper != null){
            sqliteHelper.close();
        }
    }
}
