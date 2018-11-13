package com.hfad.getcontact;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "jdnbhjdbhbhsdfbh";
    private String[] permission = {Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS};
    private boolean isPermissionGranted;
    private int CONTACT_PERMISSION_REQUEST_CODE = 129;

    private List<ContactListModel> list;
    // listener
    private ObserveContact observeContact;
    // RecyclerView
    private RecyclerView recyclerView;
    private RecyclerviewAdapter adapter;
    private GridLayoutManager gridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        checkContactPermission();

    }

    private void initViews() {
        recyclerView = findViewById(R.id.recyclerViewID);
    }

    private void initAdapter(List<ContactListModel> list) {
        gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new RecyclerviewAdapter(this, list);
        recyclerView.setAdapter(adapter);
    }

    @SuppressLint("NewApi")
    private void checkContactPermission() {
        if (ActivityCompat.checkSelfPermission(this, permission[0]) == PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.checkSelfPermission(this, permission[1]) == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show();
                isPermissionGranted = true;
                getPhoneContactData();
            } else {
                requestPermissions(permission, CONTACT_PERMISSION_REQUEST_CODE);
            }
        } else {
            requestPermissions(permission, CONTACT_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        isPermissionGranted = false;
        if (requestCode == CONTACT_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        isPermissionGranted = false;
                        return;
                    }

                }
            }
            isPermissionGranted = true;
            getPhoneContactData();


        }


    }

    private void getPhoneContactData() {
        list = new LinkedList<>();
        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                null,
                null,
                null,
                null);
        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            String image = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_URI));
            Cursor phoneCursor = resolver.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                    new String[]{id},
                    null);
            while (phoneCursor.moveToNext()) {
                // get Phone Number
                String phone = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                if (image != null) {
                    list.add(new ContactListModel(id, image, name, phone));
                } else {
                    list.add(new ContactListModel(id, "NULL", name, phone));
                    // sbsnjnsjkn

                }
            }
        }
        observeContact = new ObserveContact() {
            @Override
            public void success(List<ContactListModel> list) {
                if (list != null) {
                    initAdapter(list);
                }
            }
        };
        observeContact.success(list);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
