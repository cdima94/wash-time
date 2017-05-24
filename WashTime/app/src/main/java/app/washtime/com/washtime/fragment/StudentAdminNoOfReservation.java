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

public class StudentAdminNoOfReservation extends Fragment {

    TextInputLayout mReservation;
    Button mButton;
    static String mReservationText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_admin_no_reservation, container, false);

        mReservation = (TextInputLayout) view.findViewById(R.id.st_admin_no_reservation);

        if (mReservationText == null) {
            if (getArguments() != null) {
                mReservation.getEditText().setText(String.valueOf(((Rule) getArguments().getSerializable("rule")).getNoReservations()));
            }
        } else {
            mReservation.getEditText().setText(mReservationText);
        }

        addListenerForButton(view);

        return view;
    }

    private void addListenerForButton(View view) {
        mButton = (Button) view.findViewById(R.id.st_admin_no_reservation_button);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkState()) {
                    Rule rule = (Rule) getArguments().getSerializable("rule");
                    mReservationText = mReservation.getEditText().getText().toString();
                    rule.setNoReservations(Integer.valueOf(mReservationText));
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("rule", rule);
                    StudentAdminStartHour newFragment = new StudentAdminStartHour();
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
        if (mReservation.getEditText().getText().toString().isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public void clear() {
        mReservationText = null;
    }
}
