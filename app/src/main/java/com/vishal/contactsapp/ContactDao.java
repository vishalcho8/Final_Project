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
    @Insert (onConflict = OnConflictStrategy.IGNORE)
    void insert(Contact contact);

    @Update
    void update(Contact... contact);

    @Delete
    void deleteContact(Contact contact);

    @Query("DELETE FROM Contacts")
    void deleteAll();

    @Query("SELECT * FROM Contacts ORDER BY Name ASC")
    LiveData<List<Contact>> getAllContacts();

    @Query("SELECT * FROM Contacts LIMIT 1")
    Contact[] getAnyWord();

    /* @Query("SELECT * FROM Contacts WHERE name = :name")
    public Contact getContactWithName(String name); */

}
