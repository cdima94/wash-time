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
import app.washtime.com.washtime.activity.StudentAdminActivity;
import app.washtime.com.washtime.entity.Rule;
import app.washtime.com.washtime.task.RuleTask;
import app.washtime.com.washtime.task.impl.RuleTaskImpl;

public class StudentAdminEndHour extends Fragment {

    TextInputLayout mEndHour;
    Button mButton;
    static String mEndHourText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_admin_end_hour, container, false);

        mEndHour = (TextInputLayout) view.findViewById(R.id.st_admin_end_hour);

        if (mEndHourText == null) {
            if (getArguments() != null) {
                mEndHour.getEditText().setText(((Rule) getArguments().getSerializable("rule")).getEndHour());
            }
        } else {
            mEndHour.getEditText().setText(mEndHourText);
        }

        addListenerForButton(view);

        return view;
    }

    private void addListenerForButton(View view) {
        mButton = (Button) view.findViewById(R.id.st_admin_end_hour_button);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkState()) {
                    Rule rule = (Rule) getArguments().getSerializable("rule");
                    mEndHourText = mEndHour.getEditText().getText().toString();
                    rule.setEndHour(mEndHourText);
                    RuleTask task = new RuleTaskImpl();
                    if (task.saveRule(rule).equals("200")) {
                        ((StudentAdminActivity) getActivity()).setToolbarTitle("Rules");
                        StudentAdminMainFragment newFragment = new StudentAdminMainFragment();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.st_admin_frame_layout, newFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                }
            }
        });
    }

    private boolean checkState() {
        if (mEndHour.getEditText().getText().toString().isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public void clear() {
        mEndHourText = null;
    }
}
