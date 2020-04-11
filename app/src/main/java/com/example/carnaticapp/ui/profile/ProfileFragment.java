package com.example.carnaticapp.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.carnaticapp.MainActivity;
import com.example.carnaticapp.R;

import org.w3c.dom.Text;


public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        System.out.println(" Sujay OncreateView" + getArguments());
        profileViewModel =
            ViewModelProviders.of(this).get(ProfileViewModel.class);
        final String currFirstName = getArguments().getString("firstName");
        final String currLastName = getArguments().getString("lastName");
        final String currContact = getArguments().getString("contact");
        final String currCategory = getArguments().getString("category");
        final String currTeacherName = getArguments().getString("teacherName");
        final String currSchoolName = getArguments().getString("schoolName");


        final String name = currFirstName + " " + currLastName;
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        final EditText nameEdit = root.findViewById(R.id.name);
        final EditText schoolEdit = root.findViewById(R.id.schoolName);
        final EditText categoryEdit = root.findViewById(R.id.category);
        final EditText contactEdit = root.findViewById(R.id.contact);
        final EditText guruEdit = root.findViewById(R.id.Guru);
        nameEdit.setText(name);
        schoolEdit.setText(currSchoolName);
        guruEdit.setText(currTeacherName);
        categoryEdit.setText(currCategory);
        contactEdit.setText(currContact);
        return root;
    }
}
