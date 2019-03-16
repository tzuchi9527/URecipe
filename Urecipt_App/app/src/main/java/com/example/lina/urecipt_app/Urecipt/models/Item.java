//package com.example.lina.urecipt_app.Urecipt.models;
//
//import android.arch.persistence.room.Entity;
//import android.arch.persistence.room.PrimaryKey;
//import android.support.annotation.NonNull;
//
//
//@Entity
//public class Item {
//    @PrimaryKey
//    @NonNull  private Long id;
//    private String name;
//    private String description;
//    private Long quantity;
//
//    public void setId(long l){this.id = l;}
//    public Long getId(){return this.id;}
//    public void setName(String n){ this.name = n; }
//    public String getName() {return this.name;}
//    public void setDescription(String d) {this.description = d;}
//    public String getDescription() {return this.description;}
//    public void setQuantity(Long i) {this.quantity = i;}
//    public void setQuantity(int i) {this.quantity = Long.valueOf(i);}
//    public Long getQuantity() {return this.quantity;}
//
//
//    public String ToString(){return name+" "+description+" "+quantity.toString();}
//}
