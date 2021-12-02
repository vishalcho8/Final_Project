package com.vishal.contactsapp;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ContactRepository {
    private ContactDao mContactDao;
    private LiveData<List<Contact>> mAllContacts;

    ContactRepository(Application application) {
        ContactRoomDatabase db = ContactRoomDatabase.getDatabase(application);
        mContactDao = db.contactDao();
        mAllContacts = mContactDao.getAllContacts();
    }

    LiveData<List<Contact>> getAllContacts() {
        return mAllContacts;
    }

    public void insert (Contact contact) {
        new insertAsyncTask(mContactDao).execute(contact);
    }

    public void update(Contact contact)  {
        new updateContactAsyncTask(mContactDao).execute(contact);
    }

    private static class insertAsyncTask extends AsyncTask<Contact, Void, Void> {

        private ContactDao mAsyncTaskDao;

        insertAsyncTask(ContactDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Contact... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteAllContactsAsyncTask extends AsyncTask<Void, Void, Void> {
        private ContactDao mAsyncTaskDao;

        deleteAllContactsAsyncTask(ContactDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    public void deleteAll()  {
        new deleteAllContactsAsyncTask(mContactDao).execute();
    }

    private static class deleteContactAsyncTask extends AsyncTask<Contact, Void, Void> {
        private ContactDao mAsyncTaskDao;

        deleteContactAsyncTask(ContactDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Contact... params) {
            mAsyncTaskDao.deleteContact(params[0]);
            return null;
        }
    }

    public void deleteContact(Contact contact)  {
        new deleteContactAsyncTask(mContactDao).execute(contact);
    }

    /**
     *  Updates a word in the database.
     */
    private static class updateContactAsyncTask extends AsyncTask<Contact, Void, Void> {
        private ContactDao mAsyncTaskDao;

        updateContactAsyncTask(ContactDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Contact... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }

}
