package com.example.a23520231_lab02_bai6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText edtId, edtName;
    CheckBox chkManager;
    Button btnAdd;
    RecyclerView rvEmployee;
    ArrayList<Employee> employees;
    EmployeeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtId = findViewById(R.id.et_MaNV);
        edtName = findViewById(R.id.et_TenNV);
        chkManager = findViewById(R.id.cb_IsManager);
        btnAdd = findViewById(R.id.btn_input);
        rvEmployee = findViewById(R.id.rvEmployee);

        employees = new ArrayList<>();
        adapter = new EmployeeAdapter(this, employees);
        rvEmployee.setAdapter(adapter);
        rvEmployee.setLayoutManager(new LinearLayoutManager(this));

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = edtId.getText().toString();
                String name = edtName.getText().toString();
                boolean isManager = chkManager.isChecked();

                employees.add(new Employee(id, name, isManager));
                adapter.notifyItemInserted(employees.size() - 1);

                edtId.setText("");
                edtName.setText("");
                chkManager.setChecked(false);
            }
        });
    }
}
