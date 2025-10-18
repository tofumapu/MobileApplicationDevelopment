package com.example.a23520231_lab02_bai5;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Spinner spinnerThumbnail;
    SpinnerAdapter spinnerAdapter;
    Dishes selectedDish;
    Button btnAdd;
    GridView gvDish;
    EditText edtName;
    ArrayList<Dishes> thumbnailList;
    ArrayList<Dishes> dishList;
    DishesAdapter adapter;
    CheckBox chkPromotion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinnerThumbnail = findViewById(R.id.spinnerThumbnail);
        btnAdd = findViewById(R.id.btnAdd);
        gvDish = findViewById(R.id.gvDish);
        edtName = findViewById(R.id.edtName);
        chkPromotion = findViewById(R.id.chkPromotion);

        // danh sách hình có sẵn
        thumbnailList = new ArrayList<>();
        thumbnailList.add(new Dishes("Thumbnail 1", R.drawable.first_thumbnail));
        thumbnailList.add(new Dishes("Thumbnail 2", R.drawable.second_thumbnail));
        thumbnailList.add(new Dishes("Thumbnail 3", R.drawable.third_thumbnail));
        thumbnailList.add(new Dishes("Thumbnail 4", R.drawable.fourth_thumbnail));

        spinnerAdapter = new SpinnerAdapter(getApplicationContext(), R.layout.my_dropdown_item, thumbnailList);
        spinnerThumbnail.setAdapter(spinnerAdapter);

        spinnerThumbnail.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDish = thumbnailList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        dishList = new ArrayList<>();
        adapter = new DishesAdapter(this, R.layout.dish, dishList);
        gvDish.setAdapter(adapter);

        btnAdd.setOnClickListener(v -> {
            String name = edtName.getText().toString().trim();
            if (name.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please enter dish name", Toast.LENGTH_SHORT).show();
                return;
            }

            Dishes d = new Dishes(name, selectedDish.getThumbnail(), chkPromotion.isChecked());
            dishList.add(d);
            adapter.notifyDataSetChanged();

            Toast.makeText(MainActivity.this, "Added successfully!", Toast.LENGTH_SHORT).show();

            edtName.setText("");
            chkPromotion.setChecked(false);
            spinnerThumbnail.setSelection(0);
        });
    }
}
