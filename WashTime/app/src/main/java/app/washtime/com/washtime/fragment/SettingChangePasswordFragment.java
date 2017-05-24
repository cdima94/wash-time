package app.washtime.com.washtime.fragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import app.washtime.com.washtime.R;
import app.washtime.com.washtime.entity.Student;
import app.washtime.com.washtime.task.StudentTask;
import app.washtime.com.washtime.task.impl.StudentTaskImpl;

public class SettingChangePasswordFragment extends Fragment {

    TextInputLayout mPassword;
    Button mChangeButton;
    Student mGA;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ga_setting_password, container, false);

        mPassword = (TextInputLayout) view.findViewById(R.id.ga_setting_password);
        if (getArguments() != null) {
            mGA = (Student) getArguments().getSerializable("user");
        }

        addListenerForChangeButton(view);

        return view;
    }

    private void addListenerForChangeButton(View view) {
        mChangeButton = (Button) view.findViewById(R.id.ga_setting_password_button);

        mChangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkState()) {
                    Student tempGA = mGA;
                    tempGA.setPassword(mPassword.getEditText().getText().toString());
                    StudentTask task = new StudentTaskImpl();
                    if (task.updatePassword(tempGA, mPassword.getEditText().getText().toString()).equals("200")) {
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
        if (mPassword.getEditText().getText().toString().isEmpty()) {
            return false;
        } else {
            return true;
        }
    }
}
