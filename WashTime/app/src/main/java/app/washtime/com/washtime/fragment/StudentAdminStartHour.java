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
import app.washtime.com.washtime.entity.Rule;

public class StudentAdminStartHour extends Fragment {

    TextInputLayout mStartHour;
    Button mButton;
    static String mStartHourText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_admin_start_hour, container, false);

        mStartHour = (TextInputLayout) view.findViewById(R.id.st_admin_start_hour);

        if (mStartHourText == null) {
            if (getArguments() != null) {
                mStartHour.getEditText().setText(((Rule) getArguments().getSerializable("rule")).getStartHour());
            }
        } else {
            mStartHour.getEditText().setText(mStartHourText);
        }

        addListenerForButton(view);

        return view;
    }

    private void addListenerForButton(View view) {
        mButton = (Button) view.findViewById(R.id.st_admin_start_hour_button);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkState()) {
                    Rule rule = (Rule) getArguments().getSerializable("rule");
                    mStartHourText = mStartHour.getEditText().getText().toString();
                    rule.setStartHour(mStartHourText);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("rule", rule);
                    StudentAdminEndHour newFragment = new StudentAdminEndHour();
                    newFragment.clear();
                    newFragment.setArguments(bundle);
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.st_admin_frame_layout, newFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }
        });
    }

    private boolean checkState() {
        if (mStartHour.getEditText().getText().toString().isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public void clear() {
        mStartHourText = null;
    }
}
