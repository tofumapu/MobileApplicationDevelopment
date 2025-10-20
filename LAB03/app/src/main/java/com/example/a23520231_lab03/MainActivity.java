package com.example.a23520231_lab03;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvStudents;
    private StudentAdapter adapter;
    private List<Student> studentList;
    private DatabaseHandler dbHandler;
    private EditText etID, etName, etClass;
    private Button btnAdd;
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
        etID = findViewById(R.id.et_ID);
        etName = findViewById(R.id.et_Name);
        etClass = findViewById(R.id.et_Class);
        btnAdd = findViewById(R.id.btn_AddStudent);
        rvStudents = findViewById(R.id.rv_Student);
        Button btnAdd = findViewById(R.id.btn_AddStudent);
        dbHandler = new DatabaseHandler(this);

        // Nạp dữ liệu
        studentList = dbHandler.getAllStudents();
        adapter = new StudentAdapter(this, studentList, dbHandler);

        rvStudents.setLayoutManager(new LinearLayoutManager(this));
        rvStudents.setAdapter(adapter);

        btnAdd.setOnClickListener(v -> addStudent());
    }
    private void addStudent() {
        String idText = etID.getText().toString().trim();
        if (idText.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập ID!", Toast.LENGTH_SHORT).show();
            return;
        }
        int id = Integer.parseInt(idText);
        String name = etName.getText().toString().trim();
        String cls = etClass.getText().toString().trim();

        if (name.isEmpty() || cls.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        Student s = new Student();
        s.setId(id);
        s.setName(name);
        s.setStudentClass(cls);
        dbHandler.addStudent(s);

        // Cập nhật lại danh sách
        studentList.clear();
        studentList.addAll(dbHandler.getAllStudents());
        adapter.notifyDataSetChanged();

        // Xóa text input
        etID.setText("");
        etName.setText("");
        etClass.setText("");

        Toast.makeText(this, "Đã thêm sinh viên!", Toast.LENGTH_SHORT).show();
    }
}