package com.example.a23520231_lab02_bai2;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> names;
    ArrayAdapter<String> adapter;
    private EditText et_name;
    private Button btn_input;
    private ListView lv_person;
    private TextView tv_selection;
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
        et_name = findViewById(R.id.et_name);
        btn_input = findViewById(R.id.btn_input);
        lv_person = findViewById(R.id.lv_person);
        tv_selection = findViewById(R.id.tv_selection);
        // Tạo ArrayList object
        names = new ArrayList<String>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, names);
        lv_person.setAdapter(adapter);
        btn_input.setOnClickListener(v -> {
            names.add(et_name.getText().toString());
            adapter.notifyDataSetChanged();
            et_name.setText("");
        });
        lv_person.setOnItemClickListener((parent, view, pos, id) ->
                tv_selection.setText("position: " + pos + "; value: " + names.get(pos)));
        lv_person.setOnItemLongClickListener((parent, view, pos, id) -> {
            names.remove(pos);
            adapter.notifyDataSetChanged();
            return true;
        });

    }
}