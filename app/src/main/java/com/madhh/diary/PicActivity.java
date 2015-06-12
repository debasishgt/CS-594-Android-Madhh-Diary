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
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import com.madhh.diary.R;
import com.parse.ParseUser;

public class PicActivity extends Activity {

    TextView textTargetUri;
    ImageView targetImage;
    Bitmap bitmap;
    FileInputStream fis;
    Uri targetUri;
    private boolean valid = false;
    double Latitude, Longitude;
    protected LocationManager locationManager;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic);
        Button buttonLoadImage = (Button)findViewById(R.id.loadimage);
        Button buttonUploadImage = (Button)findViewById(R.id.uploadimage);
        Button buttonShowImage = (Button)findViewById(R.id.showimage);
        Latitude = Longitude = 0;
        textTargetUri = (TextView)findViewById(R.id.targeturi);
        targetImage = (ImageView)findViewById(R.id.targetimage);

        buttonLoadImage.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);
            }
        });
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
                if (results != null) {
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
                } else {
                    Toast.makeText(PicActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
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
            ExifInterface exif;
            String path = getImagePath(targetUri,this);
            exif = new ExifInterface(path);
            String gps_latitude = getTagString(ExifInterface.TAG_GPS_LATITUDE, exif);
            String gps_latitude_ref = getTagString(ExifInterface.TAG_GPS_LATITUDE_REF, exif);
            String gps_longitude = getTagString(ExifInterface.TAG_GPS_LONGITUDE, exif);
            String gps_longitude_ref = getTagString(ExifInterface.TAG_GPS_LONGITUDE_REF, exif);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] data = stream.toByteArray();

            ParseFile file = new ParseFile("image.JPEG", data);
            //file.save();
            ImageData imageData = new ImageData();
            imageData.setUser();

            if((gps_latitude !=null)
                    && (gps_latitude_ref !=null)
                    && (gps_longitude != null)
                    && (gps_longitude_ref !=null))
            {
                valid = true;
                if(gps_latitude_ref.equals("N")){
                    Latitude = convertToDegree(gps_latitude);
                }
                else{
                    Latitude = 0 - convertToDegree(gps_latitude);
                }
                if(gps_longitude_ref.equals("E")){
                    Longitude = convertToDegree(gps_longitude);
                }
                else{
                    Longitude = 0 - convertToDegree(gps_longitude);
                }
            }
            else{
                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                Latitude = location.getLatitude();
                Longitude = location.getLongitude();
            }

            imageData.setFile(file);
            imageData.setGps_latitude(Latitude);
            imageData.setGps_longitude(Longitude);
            //imageData.setGps_latitude_ref(gps_latitude_ref);
            //  imageData.setGps_longitude_ref(gps_longitude_ref);
            imageData.saveInBackground();

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
//        catch (ParseException e) {
//            e.printStackTrace();
//        }
        catch (IOException e1){
            e1.printStackTrace();
        }
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return (String.valueOf(Latitude)
                + ", "
                + String.valueOf(Longitude));
    }

    private Float convertToDegree(String stringDMS){
        Float result = null;
        String[] DMS = stringDMS.split(",", 3);

        String[] stringD = DMS[0].split("/", 2);
        Double D0 = new Double(stringD[0]);
        Double D1 = new Double(stringD[1]);
        Double FloatD = D0/D1;

        String[] stringM = DMS[1].split("/", 2);
        Double M0 = new Double(stringM[0]);
        Double M1 = new Double(stringM[1]);
        Double FloatM = M0/M1;

        String[] stringS = DMS[2].split("/", 2);
        Double S0 = new Double(stringS[0]);
        Double S1 = new Double(stringS[1]);
        Double FloatS = S0/S1;

        result = new Float(FloatD + (FloatM/60) + (FloatS/3600));

        return result;


    }

    private String getImagePath(Uri targetUri,Activity PicActivity)
    {
        Cursor cursor = PicActivity.getContentResolver()
                .query(targetUri, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file
            // path
            return targetUri.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }

    private String getTagString(String tag, ExifInterface exif) {
        return (exif.getAttribute(tag));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_note_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {

            case R.id.action_settings: {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }
    private void loadLoginView() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
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