package com.vishal.contactsapp;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.Insert;
import androidx.room.PrimaryKey;

/*
    Class that holds all the required fields.
    These fields are also used to create Table in the rom database.

*/

@Entity(tableName = "Contacts")
public class Contact {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "name")
    private String name;

    @NonNull
    @ColumnInfo(name = "number")
    private String number;

    @ColumnInfo(name = "email")
    private String email;

    public Contact(@NonNull String name,@NonNull String number, String email )
    {
        this.name = name;
        this.number = number;
        this.email = email;
    }

    /*
     * This constructor is annotated using @Ignore, because Room expects only
     * one constructor by default in an entity class.
     */

    @Ignore
    public Contact(int id, @NonNull String name, @NonNull String number, String email) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.email = email;
    }

    public String getName(){return this.name;}

    public int getId() {return id;}

    public void setId(int id) {
        this.id = id;
    }


    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }
}
