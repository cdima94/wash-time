package app.washtime.com.washtime.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import app.washtime.com.washtime.R;
import app.washtime.com.washtime.entity.Student;
import app.washtime.com.washtime.entity.StudentHome;
import app.washtime.com.washtime.task.StudentHomeTask;
import app.washtime.com.washtime.task.impl.StudentHomeTaskImpl;

public class AddNewLocationFragment extends Fragment {

    TextInputLayout mLocationName;
    TextInputLayout mName;

    Button mCreateButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_new_location, container, false);

        mLocationName = (TextInputLayout) view.findViewById(R.id.nl_location_name);
        mName = (TextInputLayout) view.findViewById(R.id.nl_location);

        addListenerForCreateButton(view);

        return view;
    }

    private void addListenerForCreateButton(View view) {
        mCreateButton = (Button) view.findViewById(R.id.nl_create);

        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkState()) {
                    StudentHomeTask task = new StudentHomeTaskImpl();
                    StudentHome home = new StudentHome();
                    home.setName(mName.getEditText().getText().toString().toUpperCase());
                    home.setLocationName(mLocationName.getEditText().getText().toString().toUpperCase());
                    task.saveStudentHome(getContext(), home, (Student) getArguments().getSerializable("User"));
                }
            }
        });
    }

    private boolean checkState() {
        if (mLocationName.getEditText().getText().toString().isEmpty() && mName.getEditText().getText().toString().isEmpty()) {
            return false;
        } else {
            return true;
        }
    }
}
