package com.example.a23520231_lab02_bai4;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText edtId, edtName;
    private CheckBox chbxManager;
    private Button btnAdd;
    private ListView lvEmployee;
    private ArrayList<Employee> employees;
    private EmployeeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtId = findViewById(R.id.et_MaNV);
        edtName = findViewById(R.id.et_TenNV);
        chbxManager = findViewById(R.id.cb_IsManager);
        btnAdd = findViewById(R.id.btn_input);
        lvEmployee = findViewById(R.id.lv_person);

        employees = new ArrayList<>();
        adapter = new EmployeeAdapter(this, R.layout.item_employee, employees);
        lvEmployee.setAdapter(adapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = edtId.getText().toString().trim();
                String name = edtName.getText().toString().trim();
                boolean isManager = chbxManager.isChecked();

                if (!id.isEmpty() && !name.isEmpty()) {
                    Employee e = new Employee(id, name, isManager);
                    employees.add(e);
                    adapter.notifyDataSetChanged();

                    // Reset form
                    edtId.setText("");
                    edtName.setText("");
                    chbxManager.setChecked(false);
                }
            }
        });
    }
}
