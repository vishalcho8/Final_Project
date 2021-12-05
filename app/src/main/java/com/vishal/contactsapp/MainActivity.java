package com.vishal.contactsapp;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.lifecycle.ViewModelProvider;

import com.vishal.contactsapp.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int NEW_CONTACT_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_CONTACT_ACTIVITY_REQUEST_CODE = 2;

    public static final String EXTRA_DATA_UPDATE_NAME = "extra_name_to_be_updated";
    public static final String EXTRA_DATA_ID = "extra_data_id";
    public static final String EXTRA_DATA_UPDATE_NUMBER = "extra_data_number";
    public static final String EXTRA_DATA_UPDATE_EMAIL = "extra_data_email";

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private ContactViewModel mContactViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(binding.toolbar);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final ContactListAdapter adapter = new ContactListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Get a new or existing ViewModel from the ViewModelProvider.
        mContactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);

        mContactViewModel.getAllContacts().observe(this, new Observer<List<Contact>>() {
                    @Override
                    public void onChanged(@Nullable final List<Contact> contacts) {
                        // Update the cached copy of the contacts in the adapter.
                        adapter.setContacts(contacts);
                    }
                });

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewContactActivity.class);
                startActivityForResult(intent, NEW_CONTACT_ACTIVITY_REQUEST_CODE);
            }
        });

        // Add the functionality to swipe items in the
        // recycler view to delete that item
        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView,
                                          RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder,
                                         int direction) {
                        int position = viewHolder.getAdapterPosition();
                        Contact myContact = adapter.getContactAtPosition(position);
                        Toast.makeText(MainActivity.this, "Deleting " +
                                myContact.getName(), Toast.LENGTH_LONG).show();

                        // Delete the word
                        mContactViewModel.deleteContact(myContact);
                    }
                });

        helper.attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new ContactListAdapter.ClickListener()  {

            @Override
            public void onItemClick(View v, int position) {
                Contact contact = adapter.getContactAtPosition(position);
                launchUpdateContactActivity(contact);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Insert or Update the record based on If and else condition
        if (requestCode == NEW_CONTACT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Contact contact = new Contact(data.getStringExtra(NewContactActivity.EXTRA_REPLY_NAME), data.getStringExtra(NewContactActivity.EXTRA_REPLY_NUM), data.getStringExtra(NewContactActivity.EXTRA_REPLY_EMAIL));
            mContactViewModel.insert(contact);
        } else if (requestCode == UPDATE_CONTACT_ACTIVITY_REQUEST_CODE
                && resultCode == RESULT_OK) {
            String contact_name = data.getStringExtra(NewContactActivity.EXTRA_REPLY_NAME);
            int id = data.getIntExtra(NewContactActivity.EXTRA_REPLY_ID, -1);
            String contact_num = data.getStringExtra(NewContactActivity.EXTRA_REPLY_NUM);
            String contact_email = data.getStringExtra(NewContactActivity.EXTRA_REPLY_EMAIL);

            if (id != -1) {
                mContactViewModel.update(new Contact(id, contact_name, contact_num, contact_email));
            } else {
                Toast.makeText(this, "Failed to update contact. Please try again.",
                        Toast.LENGTH_LONG).show();
            }
        } else {
            /* Do Nothing */
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.clear_data) {
            // Add a toast just for confirmation
            Toast.makeText(this, "Deleting all the contacts..",
                    Toast.LENGTH_SHORT).show();

            // Delete the existing data
            mContactViewModel.deleteAll();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.recyclerview);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void launchUpdateContactActivity( Contact contact) {
        Intent intent = new Intent(this, NewContactActivity.class);
        intent.putExtra(EXTRA_DATA_UPDATE_NAME, contact.getName());
        intent.putExtra(EXTRA_DATA_ID, contact.getId());
        intent.putExtra(EXTRA_DATA_UPDATE_NUMBER, contact.getNumber());
        intent.putExtra(EXTRA_DATA_UPDATE_EMAIL, contact.getEmail());
        Log.i("launchUpdateContactActivity Email..", contact.getEmail());
        startActivityForResult(intent, UPDATE_CONTACT_ACTIVITY_REQUEST_CODE);
    }
}