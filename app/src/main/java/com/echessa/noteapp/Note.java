package com.echessa.noteapp;

import com.parse.ParseGeoPoint;

public class Note {
	
	private String id;
	private String title;
	private String content;
    private ParseGeoPoint parseGeoPoint;
	
	Note(String noteId, String noteTitle, String noteContent) {
		id = noteId;
		title = noteTitle;
		content = noteContent;
		
	}

    public void setParseGeoPoint(ParseGeoPoint parseGeoPoint) {
        this.parseGeoPoint = parseGeoPoint;
    }

    public ParseGeoPoint getParseGeoPoint() {

        return parseGeoPoint;
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
