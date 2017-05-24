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

public class SettingChangeNameFragment extends Fragment {

    EditText mFirstName;
    EditText mLastName;
    Student mGA;
    Button mChangeButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ga_setting_change_name, container, false);

        mFirstName =  ((TextInputLayout) view.findViewById(R.id.ga_setting_firstName)).getEditText();
        mLastName = ((TextInputLayout) view.findViewById(R.id.ga_setting_lastName)).getEditText();

        if (getArguments() != null) {
            mGA = (Student) getArguments().getSerializable("user");
            mFirstName.setText(mGA.getFirstName());
            mLastName.setText(mGA.getLastName());
        }

        addListenerForButton(view);

        return view;
    }

    private void addListenerForButton(View view) {
        mChangeButton = (Button) view.findViewById(R.id.ga_setting_change_name_button);

        mChangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkState()) {
                    mGA.setFirstName(mFirstName.getText().toString());
                    mGA.setLastName(mLastName.getText().toString());
                    StudentTask task = new StudentTaskImpl();
                    if (task.updateStudent(mGA).equals("200")) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("user", mGA);
                        SettingMainViewFragment newFragment = new SettingMainViewFragment();
                        newFragment.setArguments(bundle);
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.ga_setting_frame_layout, newFragment);
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
