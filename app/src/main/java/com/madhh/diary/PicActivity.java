package com.madhh.diary;

/**
 * Created by debasish on 6/9/2015.
 */


import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import com.madhh.diary.R;

public class PicActivity extends Activity {

    TextView textTargetUri;
    ImageView targetImage;
    Bitmap bitmap;
    FileInputStream fis;
    Uri targetUri;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic);
        Button buttonLoadImage = (Button)findViewById(R.id.loadimage);
        Button buttonUploadImage = (Button)findViewById(R.id.uploadimage);
        Button buttonShowImage = (Button)findViewById(R.id.showimage);
        textTargetUri = (TextView)findViewById(R.id.targeturi);
        targetImage = (ImageView)findViewById(R.id.targetimage);

        buttonLoadImage.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);
            }});
        buttonUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Do Upload
                uploadImage();
                Toast.makeText(getApplicationContext(), "Upload Here", Toast.LENGTH_LONG).show();
            }
        });
        buttonShowImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Do Upload
                showImage();
                Toast.makeText(getApplicationContext(), "Show Image", Toast.LENGTH_LONG).show();
            }
        });

    }
    public void showImage(){
        //Retrieve
        ParseQuery<ImageData> query = ParseQuery.getQuery(ImageData.class);
        //query.whereEqualTo("date", ParseUser.getCurrentUser().get("date"));
        //query.whereEqualTo("date", getToday());
        query.findInBackground(new FindCallback<ImageData>() {
            @Override
            public void done(List<ImageData> results, ParseException e) {
                for (ImageData a : results) {
                    ParseFile imageFile = (ParseFile) a.get("imageFile");
                    imageFile.getDataInBackground(new GetDataCallback() {
                        public void done(byte[] data, ParseException e) {
                            if (e == null) {
                                // data has the bytes for the resume
                                //ImageView image = (ImageView) findViewById(R.id.img);
                                Bitmap bMap = BitmapFactory.decodeByteArray(data, 0, data.length);
                                targetImage.setImageBitmap(bMap);

                            } else {
                                // something went wrong
                            }
                        }
                    });
                }
            }
        });
    }

            public void uploadImage() {
                try {
                    //String path = "/sdcard/testimage.jpg";
                    //Bitmap b = BitmapFactory.decodeFile(path);
//            ExifInterface exif;
//            exif = new ExifInterface(path);
//            String datetime = getTagString(ExifInterface.TAG_DATETIME, exif);
//            //String flash = exif.getAttribute(ExifInterface.TAG_FLASH);
//            String gps_latitude = getTagString(ExifInterface.TAG_GPS_LATITUDE, exif);
//            //String gps_latitude_ref = getTagString(ExifInterface.TAG_GPS_LATITUDE_REF, exif);
//            String gps_longitude = getTagString(ExifInterface.TAG_GPS_LONGITUDE, exif);
//            //String gps_longitude_ref = getTagString(ExifInterface.TAG_GPS_LONGITUDE_REF, exif);
//            String img_length = getTagString(ExifInterface.TAG_IMAGE_LENGTH, exif);
//            String img_width = getTagString(ExifInterface.TAG_IMAGE_WIDTH, exif);
//            String make = getTagString(ExifInterface.TAG_MAKE, exif);
//            String model = getTagString(ExifInterface.TAG_MODEL, exif);
//            String orientation = getTagString(ExifInterface.TAG_ORIENTATION, exif);
//            String white_balance = getTagString(ExifInterface.TAG_WHITE_BALANCE, exif);

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byte[] data = stream.toByteArray();

                    ParseFile file = new ParseFile("image.JPEG", data);


                    file.save();
                    ImageData imageData = (ImageData) new ImageData();
                    imageData.setFile(file);
                    imageData.save();

                    targetImage.setImageBitmap(null);
                    //targetImage.setImageBitmap(bMap);

                    //fis = new FileInputStream(path);
                    //byte[] data = new byte[(int) path.length()];
                    //fis.read(data);

                    //ParseFile file = new ParseFile("image.jpg",data);

//            ParseObject imageData = new ParseObject("ImageData");
//            imageData.put("datetime", datetime);
//            imageData.put("gps_latitude", gps_latitude);
//            imageData.put("gps_longitude", gps_longitude);
//            imageData.put("img_length", img_length);
//            imageData.put("img_width", img_width);
//            imageData.put("make", make);
//            imageData.put("model", model);
//            imageData.put("orientation", orientation);
//            imageData.put("white_balance", white_balance);
//            imageData.put("imageFile", file);
                    //imageData.saveInBackground();

                }
//        catch (IOException e){
//            e.printStackTrace();
//            Toast.makeText(this, "Error!",
//                    Toast.LENGTH_LONG).show();
//        }
                catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            private String getTagString(String tag, ExifInterface exif) {
                return (exif.getAttribute(tag));
            }

            @Override
            protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                // TODO Auto-generated method stub
                super.onActivityResult(requestCode, resultCode, data);

                if (resultCode == RESULT_OK) {
                    targetUri = data.getData();
                    textTargetUri.setText(targetUri.toString());

                    try {
                        bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                        targetImage.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }
