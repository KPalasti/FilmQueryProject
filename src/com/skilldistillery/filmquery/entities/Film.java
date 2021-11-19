package com.skilldistillery.filmquery.entities;

import java.util.List;
import java.util.Objects;

public class Film {
	private List<Actor> actors;
	private int id;
	private String title;
	private String desc;
	private short releaseYear;
	private int langId;
    private int rentDur;
    private double rate;
    private int length; 
    private double repCost; 
    private String rating; 
    private String features;
    
    
	@Override
	public String toString() {
		return "Film [actors=" + actors + ", id=" + id + ", title=" + title + ", desc=" + desc + ", releaseYear="
				+ releaseYear + ", langId=" + langId + ", rentDur=" + rentDur + ", rate=" + rate + ", length=" + length
				+ ", repCost=" + repCost + ", rating=" + rating + ", features=" + features + "]";
	}
	public Film() {
		super();
	}
	public List<Actor> getActors() {
		return actors;
	}
	public void setActors(List<Actor> actors) {
		this.actors = actors;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public short getReleaseYear() {
		return releaseYear;
	}
	public void setReleaseYear(short releaseYear) {
		this.releaseYear = releaseYear;
	}
	public int getLangId() {
		return langId;
	}
	public void setLangId(int langId) {
		this.langId = langId;
	}
	public int getRentDur() {
		return rentDur;
	}
	public void setRentDur(int rentDur) {
		this.rentDur = rentDur;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public double getRepCost() {
		return repCost;
	}
	public void setRepCost(double repCost) {
		this.repCost = repCost;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public String getFeatures() {
		return features;
	}
	public void setFeatures(String features) {
		this.features = features;
	}
    
    
}
