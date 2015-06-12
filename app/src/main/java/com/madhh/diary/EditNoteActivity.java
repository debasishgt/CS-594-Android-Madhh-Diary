package com.madhh.diary;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.location.Location;
import android.location.LocationManager;
//>>>>>>> Stashed changes:app/src/main/java/com/madhh/diary/EditNoteActivity.java
import android.media.ExifInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EditNoteActivity extends ActionBarActivity {
	
	private Note note;
	private EditText titleEditText;
	private EditText contentEditText;
	private String postTitle;
	private String postContent;
	private ParseGeoPoint postGeoPoint;
	private Button saveNoteButton;
	private ArrayList<BaseTable> parentObj;
	private FileInputStream fis = null;

	protected LocationManager locationManager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.activity_edit_note);

		//
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

		//
		Intent intent = this.getIntent();
		
		titleEditText = (EditText) findViewById(R.id.noteTitle);
		contentEditText = (EditText) findViewById(R.id.noteContent);

		parentObj = new ArrayList<BaseTable>();

		if (intent.getExtras() != null) {
			note = new Note(intent.getStringExtra("noteId"), intent.getStringExtra("noteTitle"), intent.getStringExtra("noteContent"), getLocation());
			
			titleEditText.setText(note.getTitle());
			contentEditText.setText(note.getContent());
		}
		
		saveNoteButton = (Button)findViewById(R.id.saveNote);
		saveNoteButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				saveNote();
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_note, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
			//return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void saveNote() {
		
		postTitle = titleEditText.getText().toString();
		postContent = contentEditText.getText().toString();
		
		postTitle = postTitle.trim();
		postContent = postContent.trim();
		postGeoPoint = getLocation();
		
		// If user doesn't enter a title or content, do nothing
		// If user enters title, but no content, save
		// If user enters content with no title, give warning
		// If user enters both title and content, save
		
		if (!postTitle.isEmpty()) {
			
			// Check if post is being created or edited
			
			if (note == null) {
				// create new post
				
				final ParseObject post = new ParseObject("Post");
				post.put("title", postTitle);
				post.put("content", postContent);
				post.put("author", ParseUser.getCurrentUser());
				post.put("geoPoint",postGeoPoint);
				setProgressBarIndeterminateVisibility(true);
				post.saveInBackground(new SaveCallback() {
		            public void done(ParseException e) {
		            	setProgressBarIndeterminateVisibility(false);
		                if (e == null) {
		                    // Saved successfully.
		                	note = new Note(post.getObjectId(), postTitle, postContent,postGeoPoint);
		                	Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
		                } else {
		                    // The save failed.
		                	Toast.makeText(getApplicationContext(), "Failed to Save", Toast.LENGTH_SHORT).show();
		                    Log.d(getClass().getSimpleName(), "User update error: " + e);
		                }
		            }
		        });
				
			}
			else {
				// update post
				
				ParseQuery<ParseObject> query = ParseQuery.getQuery("Post");
				 
				// Retrieve the object by id
				query.getInBackground(note.getId(), new GetCallback<ParseObject>() {
				  public void done(ParseObject post, ParseException e) {
				    if (e == null) {
				      // Now let's update it with some new data.
				    	post.put("title", postTitle);
						post.put("content", postContent);
						setProgressBarIndeterminateVisibility(true);
						post.saveInBackground(new SaveCallback() {
				            public void done(ParseException e) {
				            	setProgressBarIndeterminateVisibility(false);
				                if (e == null) {
				                    // Saved successfully.
				                	Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
				                } else {
				                    // The save failed.
				                	Toast.makeText(getApplicationContext(), "Failed to Save", Toast.LENGTH_SHORT).show();
				                    Log.d(getClass().getSimpleName(), "User update error: " + e);
				                }
				            }
				        });
				    }
				  }
				});
			}
		} 
		else if (postTitle.isEmpty() && !postContent.isEmpty()) {
			AlertDialog.Builder builder = new AlertDialog.Builder(EditNoteActivity.this);
			builder.setMessage(R.string.edit_error_message)
				.setTitle(R.string.edit_error_title)
				.setPositiveButton(android.R.string.ok, null);
			AlertDialog dialog = builder.create();
			dialog.show();
			//Save Data
			//saveData();
			//Edit Array
			//getBaseTableData();
			//Save Relational Data - Parent Child Data
			//saveRelData();
			getParentObj();
		}
		setResult(RESULT_OK, null);
		this.finish();
	}
	public String getToday(){
		DateFormat dateFormat = new SimpleDateFormat("MMM/dd/yyyy");
		Date date = new Date();
		String dateStr = dateFormat.format(date);

		return dateStr;
	}
	public void saveData(){
		BaseTable baseTable = new BaseTable();
		//baseTable.setTime(getToday());
		//baseTable.addEvent(1);
		baseTable.saveInBackground();

	}
	public void saveRelData(BaseTable btt){
		EventBase eventBase = new EventBase();
		eventBase.saveTitle("testTitle11");
		eventBase.saveMemo("testMemo22");
		try {
			eventBase.save();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		//getParentObj();
		// now we create a book object
		//ParseObject baseTable = new ParseObject("BaseTable");

// now let�s associate the authors with the book
// remember, we created a "authors" relation on Book
		System.out.println("dw");
		if(parentObj != null && parentObj.size() > 0) {
			//BaseTable btt = parentObj.get(0);
			Toast.makeText(getApplicationContext(), "Succ", Toast.LENGTH_LONG).show();

			ParseRelation<ParseObject> relation = btt.getRelation("event_bases");
			relation.add(eventBase);
			//eventBase.setParent(btt);

			btt.saveInBackground();
		}
	}
	public void getParentObj() {

		ParseQuery<BaseTable> query = ParseQuery.getQuery(BaseTable.class);
		//query.whereEqualTo("date", ParseUser.getCurrentUser().get("date"));
		//query.whereEqualTo("date", getToday());
		query.findInBackground(new FindCallback<BaseTable>() {
			@Override
			public void done(List<BaseTable> results, ParseException e) {
			for (BaseTable a : results) {
				// ...
				//Toast.makeText(getApplicationContext(), "In getParentObj", Toast.LENGTH_LONG).show();
				String today = getToday();
				System.out.println(today);
				String dbDate = a.getString("date");
				System.out.println(dbDate);
				if (today.equals(dbDate)) {
					//BaseTable baseTable = new BaseTable(a.getObjectId(), a.getString("date"));
					//parentObj.add(baseTable);
					saveRelData(a);
					Toast.makeText(getApplicationContext(), parentObj.toString(), Toast.LENGTH_LONG).show();
				}
			}
			}
		});
	}

	public void getBaseTableData(){
		ParseQuery<BaseTable> query = ParseQuery.getQuery(BaseTable.class);
		//query.whereEqualTo("date", ParseUser.getCurrentUser().get("date"));
		query.whereEqualTo("date", getToday());
		query.findInBackground(new FindCallback<BaseTable>() {
			@Override
			public void done(List<BaseTable> results, ParseException e) {
				Toast.makeText(getApplicationContext(), "this is my Toast message!!! =)", Toast.LENGTH_LONG).show();
				for (BaseTable a : results) {
					// ...
					ArrayList<Integer> events = new ArrayList<Integer>();
					events = (ArrayList<Integer>)a.get("events");
					events.add(23);
					a.put("events", events);
					//a.addEvent(22);
					Toast.makeText(getApplicationContext(), "this is my Toast message22!!! =)", Toast.LENGTH_LONG).show();
					a.saveInBackground();
				}
			}
		});
	}
	public void picProcess(){
		try{
			String path = "/sdcard/testimage.jpg";
			Bitmap b = BitmapFactory.decodeFile(path);
			ExifInterface exif = new ExifInterface(path);
			String datetime = getTagString(ExifInterface.TAG_DATETIME, exif);
			//String flash = exif.getAttribute(ExifInterface.TAG_FLASH);
			String gps_latitude = getTagString(ExifInterface.TAG_GPS_LATITUDE, exif);
			//String gps_latitude_ref = getTagString(ExifInterface.TAG_GPS_LATITUDE_REF, exif);
			String gps_longitude = getTagString(ExifInterface.TAG_GPS_LONGITUDE, exif);
			//String gps_longitude_ref = getTagString(ExifInterface.TAG_GPS_LONGITUDE_REF, exif);
			String img_length = getTagString(ExifInterface.TAG_IMAGE_LENGTH, exif);
			String img_width = getTagString(ExifInterface.TAG_IMAGE_WIDTH, exif);
			String make = getTagString(ExifInterface.TAG_MAKE, exif);
			String model = getTagString(ExifInterface.TAG_MODEL, exif);
			String orientation = getTagString(ExifInterface.TAG_ORIENTATION, exif);
			String white_balance = getTagString(ExifInterface.TAG_WHITE_BALANCE, exif);

			fis = new FileInputStream(path);
			byte[] data = new byte[(int) path.length()];
			fis.read(data);

			ParseFile file = new ParseFile("image.jpg",data);
			file.saveInBackground();

			ParseObject imageData = new ParseObject("ImageData");
			imageData.put("datetime", datetime);
			imageData.put("gps_latitude", gps_latitude);
			imageData.put("gps_longitude", gps_longitude);
			imageData.put("img_length", img_length);
			imageData.put("img_width", img_width);
			imageData.put("make", make);
			imageData.put("model", model);
			imageData.put("orientation", orientation);
			imageData.put("white_balance", white_balance);
			imageData.put("imageFile", file);
			imageData.saveInBackground();

		}catch (IOException e){
			e.printStackTrace();
			Toast.makeText(this, "Error!",
					Toast.LENGTH_LONG).show();
		}
	}
	private String getTagString(String tag, ExifInterface exif)
	{
		return(exif.getAttribute(tag));
	}
	
		//start

	public ParseGeoPoint getLocation()
	{
		Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		postGeoPoint = new ParseGeoPoint(location.getLatitude(), location.getLongitude());
		return postGeoPoint;
	}

	//end
}
