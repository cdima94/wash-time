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

public class StudentAdminDuration extends Fragment {

    TextInputLayout mDuration;
    Button mButton;
    static String mDurationText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_admin_duration, container, false);

        mDuration = (TextInputLayout) view.findViewById(R.id.st_admin_duration);

        if (mDurationText == null) {
            if (getArguments() != null) {
                ((StudentAdminActivity) getActivity()).setToolbarTitle(getArguments().getString("title"));
                mDuration.getEditText().setText(parseDuration(((Rule) getArguments().getSerializable("rule")).getDuration()));
            }
        } else {
            mDuration.getEditText().setText(parseDuration(mDurationText));
        }

        addListenerForButton(view);

        return view;
    }

    private void addListenerForButton(View view) {
        mButton = (Button) view.findViewById(R.id.st_admin_duration_next_button);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkState()) {
                    Rule rule = (Rule) getArguments().getSerializable("rule");
                    mDurationText = parseDuration(mDuration.getEditText().getText().toString());
                    rule.setDuration(mDurationText);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("rule", rule);
                    StudentAdminNoOfReservation newFragment = new StudentAdminNoOfReservation();
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

    private String parseDuration(String duration) {
        if (duration != null) {
            if (duration.contains(":")) {
                String[] splitDuration = duration.split(":");
                return String.valueOf(Integer.parseInt(splitDuration[0]) * 60 + Integer.parseInt(splitDuration[1]));
            } else {
                Integer durationInt = Integer.parseInt(duration);
                int hours = durationInt / 60;
                int minutes = durationInt - hours * 60;
                StringBuilder sb = new StringBuilder();
                if (hours > 10) {
                    sb.append(hours);
                    sb.append(":");
                } else {
                    sb.append("0" + hours);
                    sb.append(":");
                }
                if (minutes > 10) {
                    sb.append(minutes);
                } else {
                    sb.append("0" + minutes);
                }
                return sb.toString();
            }
        }
        return "";
    }

    private boolean checkState() {
        if (mDuration.getEditText().getText().toString().isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public void clear() {
        mDurationText = null;
    }
}
