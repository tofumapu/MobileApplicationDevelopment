package com.example.a23520231_lab03;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    private List<Student> studentList;
    private Context context;
    private DatabaseHandler dbHandler;

    public StudentAdapter(Context context, List<Student> list, DatabaseHandler dbHandler) {
        this.context = context;
        this.studentList = list;
        this.dbHandler = dbHandler;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_student, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Student st = studentList.get(position);

        holder.tvName.setText(st.getName());
        holder.tvID.setText("ID: " + st.getId());
        holder.tvClass.setText("Lớp: " + st.getStudentClass());

        holder.itemView.setOnClickListener(v -> {
            if (holder.layoutDetails.getVisibility() == View.GONE)
                holder.layoutDetails.setVisibility(View.VISIBLE);
            else
                holder.layoutDetails.setVisibility(View.GONE);
        });
        holder.btnModify.setOnClickListener(v -> {
            // Inflate layout dialog
            View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_modify_student, null);
            EditText edtName = dialogView.findViewById(R.id.edtName);
            EditText edtClass = dialogView.findViewById(R.id.edtClass);
            Button btnCancel = dialogView.findViewById(R.id.btnCancel);
            Button btnSave = dialogView.findViewById(R.id.btnSave);

            // Gán giá trị hiện tại
            edtName.setText(st.getName());
            edtClass.setText(st.getStudentClass());

            AlertDialog dialog = new AlertDialog.Builder(context)
                    .setView(dialogView)
                    .create();

            btnCancel.setOnClickListener(cancelView -> dialog.dismiss());

            btnSave.setOnClickListener(saveView -> {
                String newName = edtName.getText().toString().trim();
                String newClass = edtClass.getText().toString().trim();

                if (newName.isEmpty() || newClass.isEmpty()) {
                    Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Cập nhật đối tượng trong database
                st.setName(newName);
                st.setStudentClass(newClass);
                dbHandler.updateStudent(st);

                // Cập nhật lại giao diện
                notifyItemChanged(holder.getBindingAdapterPosition());
                Toast.makeText(context, "Đã cập nhật sinh viên!", Toast.LENGTH_SHORT).show();

                dialog.dismiss();
            });

            dialog.show();
        });

        holder.itemView.setOnLongClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Xóa sinh viên")
                    .setMessage("Bạn có chắc muốn xóa " + st.getName() + "?")
                    .setPositiveButton("Xóa", (dialog, which) -> {
                        int currentPosition = holder.getAdapterPosition();
                        if (currentPosition != RecyclerView.NO_POSITION && currentPosition < studentList.size()) {
                            dbHandler.deleteStudent(st.getId());
                            studentList.remove(currentPosition);
                            notifyItemRemoved(currentPosition);
                            notifyItemRangeChanged(currentPosition, studentList.size());
                            Toast.makeText(context, "Đã xóa " + st.getName(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Hủy", null)
                    .show();
            return true;
        });

    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    static class StudentViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvID, tvClass;
        LinearLayout layoutDetails;
        Button btnModify;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvID = itemView.findViewById(R.id.tvID);
            tvClass = itemView.findViewById(R.id.tvClass);
            layoutDetails = itemView.findViewById(R.id.layoutDetails);
            btnModify = itemView.findViewById(R.id.btn_Modify);

        }
    }
}
