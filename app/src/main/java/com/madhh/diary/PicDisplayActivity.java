package com.madhh.diary;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;

import java.util.List;


public class PicDisplayActivity extends ActionBarActivity {
    ImageView targetImage;
    TextView textTargetUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_display);
        textTargetUri = (TextView)findViewById(R.id.image_name);
        targetImage = (ImageView)findViewById(R.id.targetimage);
        showImage();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pic_display, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        }
        if (id == R.id.action_map_retrive) {
            Intent intent = new Intent(this, MapRetrive.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
    public void showImage(){
        //Retrieve
        Intent intent = this.getIntent();
        String picObjId = "";
        ParseQuery<ImageData> query = ParseQuery.getQuery(ImageData.class);
        if (intent.getExtras() != null) {
            picObjId = intent.getStringExtra("picId");
            textTargetUri.setText(picObjId);
        }
        query.whereEqualTo("objectId", picObjId);
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
                    Toast.makeText(PicDisplayActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
