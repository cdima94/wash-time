package app.washtime.com.washtime.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import app.washtime.com.washtime.R;
import app.washtime.com.washtime.activity.LoginActivity;
import app.washtime.com.washtime.task.StudentTask;
import app.washtime.com.washtime.task.impl.StudentTaskImpl;

public class AddNewAccountFragmentPassword extends Fragment {

    TextInputLayout mPassword;
    Button mNewAccountButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_new_account_password, container, false);

        mPassword = (TextInputLayout) view.findViewById(R.id.na_password);

        addListenerForNewAccountButton(view);

        return view;
    }

    private void addListenerForNewAccountButton(View view) {
        mNewAccountButton = (Button) view.findViewById(R.id.na_password_button);

        mNewAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkState()) {
                    StudentTask task = new StudentTaskImpl();
                    String result = task.createNewAccountStudent(getArguments().getString("First name"),
                            getArguments().getString("Last name"),
                            getArguments().getString("User name"),
                            Integer.parseInt(getArguments().getString("Room")),
                            getArguments().getString("Location name"),
                            getArguments().getString("Location"),
                            mPassword.getEditText().getText().toString());
                    if (result.equals("200")) {
                        Intent loginActivity = new Intent(getContext(), LoginActivity.class);
                        startActivity(loginActivity);
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
