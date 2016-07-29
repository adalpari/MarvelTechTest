package com.github.adalpari.mvpretrofitjson.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Characters {

@SerializedName("available")
@Expose
private String available;
@SerializedName("returned")
@Expose
private String returned;
@SerializedName("collectionURI")
@Expose
private String collectionURI;
@SerializedName("items")
@Expose
private List<Item_> items = new ArrayList<Item_>();

/**
* 
* @return
* The available
*/
public String getAvailable() {
return available;
}

/**
* 
* @param available
* The available
*/
public void setAvailable(String available) {
this.available = available;
}

/**
* 
* @return
* The returned
*/
public String getReturned() {
return returned;
}

/**
* 
* @param returned
* The returned
*/
public void setReturned(String returned) {
this.returned = returned;
}

/**
* 
* @return
* The collectionURI
*/
public String getCollectionURI() {
return collectionURI;
}

/**
* 
* @param collectionURI
* The collectionURI
*/
public void setCollectionURI(String collectionURI) {
this.collectionURI = collectionURI;
}

/**
* 
* @return
* The items
*/
public List<Item_> getItems() {
return items;
}

/**
* 
* @param items
* The items
*/
public void setItems(List<Item_> items) {
this.items = items;
}

}