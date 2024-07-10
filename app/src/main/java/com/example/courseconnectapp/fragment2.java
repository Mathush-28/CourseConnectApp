package com.example.courseconnectapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

public class fragment2 extends Fragment {

    private MyViewModel viewModel;
    private EditText editTextStartDate;
    private EditText editTextRegistrationDueDate;

    private boolean[] checkedItems; // To track checked items
    private String[] branchesList;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment2, container, false);

        viewModel = new ViewModelProvider(requireActivity()).get(MyViewModel.class);

        editTextStartDate = view.findViewById(R.id.courseStartDate);
        editTextStartDate.setOnClickListener(v -> showDatePickerDialog(editTextStartDate));

        editTextRegistrationDueDate = view.findViewById(R.id.registrationDueDate);
        editTextRegistrationDueDate.setOnClickListener(v -> showDatePickerDialog(editTextRegistrationDueDate));

        EditText courseNameEditText = view.findViewById(R.id.courseName);
        EditText courseCostEditText = view.findViewById(R.id.courseCost);
        EditText courseDurationEditText = view.findViewById(R.id.courseDuration);

        branchesList = getResources().getStringArray(R.array.branches_array);
        checkedItems = new boolean[branchesList.length];

        Button selectBranchesButton = view.findViewById(R.id.selectBranchesButton);
        selectBranchesButton.setOnClickListener(v -> showBranchesDialog());

        Button addCourseButton = view.findViewById(R.id.addCourseButton);
        addCourseButton.setOnClickListener(v -> {
            if (courseNameEditText.getText().toString().isEmpty() ||
                    courseCostEditText.getText().toString().isEmpty() ||
                    courseDurationEditText.getText().toString().isEmpty() ||
                    editTextStartDate.getText().toString().isEmpty() ||
                    editTextRegistrationDueDate.getText().toString().isEmpty() ||
                    getSelectedBranches().isEmpty()) {
                Toast.makeText(getActivity(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                Course course = new Course(
                        courseNameEditText.getText().toString(),
                        courseCostEditText.getText().toString(),
                        courseDurationEditText.getText().toString(),
                        editTextStartDate.getText().toString(),
                        editTextRegistrationDueDate.getText().toString(),
                        getSelectedBranches()
                );

                viewModel.addCourse(course);
                Toast.makeText(getActivity(), "Course Added Successfully", Toast.LENGTH_SHORT).show();
                clearFields(courseNameEditText, courseCostEditText, courseDurationEditText, editTextStartDate, editTextRegistrationDueDate);
            }
           // clearFields(courseNameEditText, courseCostEditText, courseDurationEditText, editTextStartDate, editTextRegistrationDueDate);

        });


        return view;
    }

    private String getSelectedBranches() {
        StringBuilder selectedBranches = new StringBuilder();
        for (int i = 0; i < branchesList.length; i++) {
            if (checkedItems[i]) {
                selectedBranches.append(branchesList[i]).append(", ");
            }
        }
        if (selectedBranches.length() > 0) {
            selectedBranches.setLength(selectedBranches.length() - 2); // Remove last comma and space
        }
        return selectedBranches.toString();
    }

    private void clearFields(EditText courseName, EditText courseCost, EditText courseDuration, EditText startDate, EditText dueDate) {
        courseName.setText("");
        courseCost.setText("");
        courseDuration.setText("");
        startDate.setText("");
        dueDate.setText("");
        for (int i = 0; i < checkedItems.length; i++) {
            checkedItems[i] = false; // Reset branch selections
        }
    }

    private void showBranchesDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select Branches");
        builder.setMultiChoiceItems(branchesList, checkedItems, (dialog, which, isChecked) -> checkedItems[which] = isChecked);

        builder.setPositiveButton("OK", (dialog, which) -> saveSelectedBranches());
        builder.setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void saveSelectedBranches() {
        StringBuilder selectedBranches = new StringBuilder();
        for (int i = 0; i < branchesList.length; i++) {
            if (checkedItems[i]) {
                selectedBranches.append(branchesList[i]).append(", ");
            }
        }

        if (selectedBranches.length() > 0) {
            selectedBranches.setLength(selectedBranches.length() - 2); // Remove last comma and space
        }



        // Save the selected branches to a database or other storage here
    }

    private void showDatePickerDialog(final EditText editText) {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                (view, year1, monthOfYear, dayOfMonth) -> {
                    // Set the date chosen through the picker
                    editText.setText(String.format("%d/%d/%d", dayOfMonth, monthOfYear + 1, year1));
                }, year, month, day);

        datePickerDialog.show();
    }
}
