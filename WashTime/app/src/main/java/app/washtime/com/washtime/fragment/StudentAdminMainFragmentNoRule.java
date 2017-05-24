package app.washtime.com.washtime.fragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import app.washtime.com.washtime.R;
import app.washtime.com.washtime.activity.StudentAdminActivity;
import app.washtime.com.washtime.entity.Rule;
import app.washtime.com.washtime.entity.Student;

import static app.washtime.com.washtime.fragment.StudentAdminMainFragment.mStudent;

public class StudentAdminMainFragmentNoRule extends Fragment {

    Button mButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_admin_main_no_rule, container, false);

        if (getArguments() != null) {
            mStudent = (Student) getArguments().getSerializable("student");
            ((StudentAdminActivity) getActivity()).setToolbarTitle("Rules");
        }

        addListenerForButton(view);

        return view;
    }

    private void addListenerForButton(View view) {
        mButton = (Button) view.findViewById(R.id.st_admin_new_rule_button);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Rule rule = new Rule();
                rule.setRuleHome(mStudent.getStudentHome());
                rule.setFloor(mStudent.getRoom() / 100);
                Bundle bundle = new Bundle();
                bundle.putSerializable("rule", rule);
                bundle.putString("title", "Add rule");
                StudentAdminDuration newFragment = new StudentAdminDuration();
                newFragment.clear();
                newFragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.st_admin_frame_layout, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

    public void clear() {
        mStudent = null;
    }
}
