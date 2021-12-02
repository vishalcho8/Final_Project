package com.vishal.contactsapp;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ContactViewModel extends AndroidViewModel {
    private ContactRepository mRepository;

    private LiveData<List<Contact>> mAllContacts;

    public ContactViewModel (Application application) {
        super(application);
        mRepository = new ContactRepository(application);
        mAllContacts = mRepository.getAllContacts();
    }

    LiveData<List<Contact>> getAllContacts() { return mAllContacts; }

    public void insert(Contact contact) { mRepository.insert(contact); }
    public void deleteAll() {mRepository.deleteAll();}

    public void deleteContact(Contact contact) {mRepository.deleteContact(contact);}
    public void update(Contact contact) { mRepository.update(contact);}
}
