package app.washtime.com.washtime.fragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import app.washtime.com.washtime.R;
import app.washtime.com.washtime.entity.Student;
import app.washtime.com.washtime.task.StudentTask;
import app.washtime.com.washtime.task.impl.StudentTaskImpl;

public class StudentSettingChangeNameFragment extends Fragment {

    EditText mFirstName;
    EditText mLastName;
    Student mUser;
    Button mChangeButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_st_setting_name, container, false);

        mFirstName =  ((TextInputLayout) view.findViewById(R.id.st_setting_firstName)).getEditText();
        mLastName = ((TextInputLayout) view.findViewById(R.id.st_setting_lastName)).getEditText();

        if (getArguments() != null) {
            mUser = (Student) getArguments().getSerializable("user");
            mFirstName.setText(mUser.getFirstName());
            mLastName.setText(mUser.getLastName());
        }

        addListenerForButton(view);

        return view;
    }

    private void addListenerForButton(View view) {
        mChangeButton = (Button) view.findViewById(R.id.st_setting_change_name_button);

        mChangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkState()) {
                    mUser.setFirstName(mFirstName.getText().toString());
                    mUser.setLastName(mLastName.getText().toString());
                    StudentTask task = new StudentTaskImpl();
                    if (task.updateStudent(mUser).equals("200")) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("user", mUser);
                        StudentSettingMainViewFragment newFragment = new StudentSettingMainViewFragment();
                        newFragment.setArguments(bundle);
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.st_setting_frame_layout, newFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                }
            }
        });
    }

    private boolean checkState() {
        if (mFirstName.getText().toString().isEmpty() && mLastName.getText().toString().isEmpty()) {
            return false;
        } else {
            return true;
        }
    }
}
