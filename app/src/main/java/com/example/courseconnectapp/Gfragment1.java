package com.example.courseconnectapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

//public class Gfragment1 extends Fragment {
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_gfragment1, container, false);
//    }
//}


//public class fragment1 extends Fragment {
//
//
//        @Nullable
//        @Override
//        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//            View view = inflater.inflate(R.layout.fragment_fragment1, container, false);
//
//
//            return view;
//        }
//    }


public class Gfragment1 extends Fragment {

    private MyViewModel viewModel;
    private TextView coursesTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gfragment1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(MyViewModel.class);
        coursesTextView = view.findViewById(R.id.coursesTextView2);

        // Observing changes in the courses list
        viewModel.getCourses().observe(getViewLifecycleOwner(), courses -> {
            StringBuilder coursesText = new StringBuilder("<h2>Available Courses:</h2>\n");


            for (Course course : courses) {
                coursesText.append("<p><strong>Name:</strong> ").append(course.getName())
                        .append("<br><strong>Cost:</strong> ").append(course.getCost())
                        .append("<br><strong>Duration:</strong> ").append(course.getDuration())
                        .append("<br><strong>Start Date:</strong> ").append(course.getStartDate())
                        .append("<br><strong>Due Date:</strong> ").append(course.getDueDate())
                        .append("<br><strong>Branches:</strong> ").append(course.getBranches())
                        .append("</p><br>");
            }
            //coursesTextView.setText(coursesText.toString());
            coursesTextView.setText(Html.fromHtml(coursesText.toString(), Html.FROM_HTML_MODE_LEGACY));
        });
    }
}


   
    