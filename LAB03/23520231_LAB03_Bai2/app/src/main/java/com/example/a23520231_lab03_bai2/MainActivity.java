package com.example.a23520231_lab03_bai2;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView lvContact;
    private DatabaseHandler dbHandler;
    private List<Contact> contactList;
    private EditText etName, etPhone;
    private Button btnAdd;
    private ArrayAdapter<String> adapter;
    private List<String> contactNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etName = findViewById(R.id.et_Name);
        etPhone = findViewById(R.id.et_ContactNumber);
        btnAdd = findViewById(R.id.btn_AddContact);
        lvContact = findViewById(R.id.lv_contact);

        dbHandler = new DatabaseHandler(this);

        // Load danh sách
        loadContacts();

        btnAdd.setOnClickListener(v -> {
            String name = etName.getText().toString();
            String phone = etPhone.getText().toString();
            if (!name.isEmpty() && !phone.isEmpty()) {
                dbHandler.addContact(new Contact(name, phone));
                loadContacts();
                etName.setText("");
                etPhone.setText("");
                Toast.makeText(this, "Đã thêm liên hệ!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
            }
        });

        // Xóa contact bằng long click
        lvContact.setOnItemLongClickListener((parent, view, position, id) -> {
            Contact contact = contactList.get(position);
            dbHandler.deleteContact(contact.getId());
            loadContacts();
            Toast.makeText(this, "Đã xóa " + contact.getName(), Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    private void loadContacts() {
        contactList = dbHandler.getAllContacts();
        contactNames = new ArrayList<>();
        for (Contact c : contactList) {
            contactNames.add(c.getName() + " - " + c.getPhoneNumber());
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contactNames);
        lvContact.setAdapter(adapter);
    }
}
