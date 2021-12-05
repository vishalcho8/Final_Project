package com.vishal.contactsapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ContactDao {
    //Insert new record in database
    @Insert (onConflict = OnConflictStrategy.IGNORE)
    void insert(Contact contact);

    //update existing record in database
    @Update
    void update(Contact... contact);

    //Delete the contact from database
    @Delete
    void deleteContact(Contact contact);

    //Delete all the contacts from database table
    @Query("DELETE FROM Contacts")
    void deleteAll();

    //Select all the contacts from table and add them in the list
    @Query("SELECT * FROM Contacts ORDER BY Name ASC")
    LiveData<List<Contact>> getAllContacts();

    //Select one contact from table
    @Query("SELECT * FROM Contacts LIMIT 1")
    Contact[] getAnyContact();

}
