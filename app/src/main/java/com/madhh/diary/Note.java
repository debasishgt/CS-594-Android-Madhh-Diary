package com.madhh.diary;

import com.parse.ParseGeoPoint;

public class Note {
	
	private String id;
	private String title;
	private String content;
    private ParseGeoPoint geoPoint;
	
	Note(String noteId, String noteTitle, String noteContent, ParseGeoPoint Point) {
		id = noteId;
		title = noteTitle;
		content = noteContent;
		geoPoint = Point;
	}

    public void setGeoPoint(ParseGeoPoint geoPoint) {
        this.geoPoint = geoPoint;
    }

    public ParseGeoPoint getGeoPoint() {

        return geoPoint;
    }

    public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@Override
	public String toString() {
		return this.getTitle();
	}

}
