package com.vishal.contactsapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import static com.vishal.contactsapp.MainActivity.EXTRA_DATA_ID;
import static com.vishal.contactsapp.MainActivity.EXTRA_DATA_UPDATE_NAME;
import static com.vishal.contactsapp.MainActivity.EXTRA_DATA_UPDATE_NUMBER;
import static com.vishal.contactsapp.MainActivity.EXTRA_DATA_UPDATE_EMAIL;
public class NewContactActivity extends AppCompatActivity {
    public static final String EXTRA_REPLY_NAME = "com.example.android.roomcontactsssample.REPLY_NAME";
    public static final String EXTRA_REPLY_ID = "com.android.example.roomcontactsssample.REPLY_ID";
    public static final String EXTRA_REPLY_NUM = "com.android.example.roomcontactsssample.REPLY_NUM";
    public static final String EXTRA_REPLY_EMAIL = "com.android.example.roomcontactssample.REPLY_EMAIL";

    private EditText mEditContactName;
    private EditText mEditContactNumber;
    private EditText mEditContactEmail;
    private Button buttonSave;
    private Button buttonCancel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);
        mEditContactName = findViewById(R.id.edit_name);
        mEditContactNumber = findViewById(R.id.edit_number);
        mEditContactEmail = findViewById(R.id.edit_email);
        buttonSave = findViewById(R.id.button_save);
        buttonCancel = findViewById(R.id.button_cancel);
        int id = -1 ;

        final Bundle extras = getIntent().getExtras();

        // If we are passed content, fill it in for the user to edit.
        if (extras != null) {
            String contact_name = extras.getString(EXTRA_DATA_UPDATE_NAME, "");
            String contact_number = extras.getString(EXTRA_DATA_UPDATE_NUMBER,"");
            String contact_email = extras.getString(EXTRA_DATA_UPDATE_EMAIL,"");
            if (!contact_name.isEmpty()) {
                mEditContactName.setText(contact_name);
                mEditContactName.setSelection(contact_name.length());
                mEditContactName.requestFocus();
            }

            if (!contact_number.isEmpty()) {
                mEditContactNumber.setText(contact_number);
            }
            if (!contact_email.isEmpty()) {
                mEditContactEmail.setText(contact_email);
            }
        } // Otherwise, start with empty fields.


        buttonSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(mEditContactName.getText()) ||
                        TextUtils.isEmpty(mEditContactNumber.getText())) {
                    Toast.makeText(view.getContext(), R.string.empty_not_saved,
                            Toast.LENGTH_LONG).show();

                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String name = mEditContactName.getText().toString();
                    replyIntent.putExtra(EXTRA_REPLY_NAME, name);
                    if (extras != null && extras.containsKey(EXTRA_DATA_ID)) {
                        int id = extras.getInt(EXTRA_DATA_ID, -1);
                        if (id != -1) {
                            replyIntent.putExtra(EXTRA_REPLY_ID, id);
                        }
                    }

                    String number = mEditContactNumber.getText().toString();
                    replyIntent.putExtra(EXTRA_REPLY_NUM, number);
                    String email = mEditContactEmail.getText().toString();
                    replyIntent.putExtra(EXTRA_REPLY_EMAIL, email);
                    setResult(RESULT_OK, replyIntent);
                    finish();
                }
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                setResult(RESULT_CANCELED, replyIntent);
                finish();
                }
            });
    }
}
