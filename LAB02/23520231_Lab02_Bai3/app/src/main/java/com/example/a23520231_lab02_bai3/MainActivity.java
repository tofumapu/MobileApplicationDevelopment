package com.example.a23520231_lab02_bai3;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Các biến giao diện
    private EditText etId, etName;
    private RadioGroup rgType;
    private Button btnAdd;
    private ListView lvEmployee;

    // Các biến dữ liệu
    private ArrayList<Employee> employees;
    private ArrayAdapter<Employee> adapter;
    private Employee employee; // tạm thời để chứa nhân viên mới khi thêm

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        //  Thiết lập hệ thống viền màn hình
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Ánh xạ view
        etId = findViewById(R.id.et_MaNV);
        etName = findViewById(R.id.et_TenNV);
        rgType = findViewById(R.id.rg_type);
        btnAdd = findViewById(R.id.btn_input);
        lvEmployee = findViewById(R.id.lv_person);

        // Khởi tạo danh sách + adapter
        employees = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, employees);
        lvEmployee.setAdapter(adapter);

        // Bắt sự kiện nút "Nhập" → gọi hàm addNewEmployee()
        btnAdd.setOnClickListener(v -> addNewEmployee());
    }

    // Hàm thêm nhân viên mới (giữ nguyên theo LAB)
    public void addNewEmployee() {
        // Lấy ra đúng id của Radio Button được checked
        int radId = rgType.getCheckedRadioButtonId();
        String id = etId.getText().toString();
        String name = etName.getText().toString();

        if (radId == R.id.rd_chinhthuc) {
            // tạo instance là FullTime
            employee = new EmployeeFullTime();
        } else {
            // Tạo instance là PartTime
            employee = new EmployeePartTime();
        }

        // FullTime hay PartTime thì cũng là Employee nên có các hàm này là hiển nhiên
        employee.setId(id);
        employee.setName(name);

        // Đưa employee vào ArrayList
        employees.add(employee);

        // Cập nhật giao diện
        adapter.notifyDataSetChanged();

        // Reset lại ô nhập
        etId.setText("");
        etName.setText("");
        rgType.clearCheck();
    }
}
