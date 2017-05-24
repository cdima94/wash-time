package app.washtime.com.washtime.fragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import app.washtime.com.washtime.R;
import app.washtime.com.washtime.activity.StudentAdminActivity;
import app.washtime.com.washtime.adapter.GeneralAdminSettingAdapter;
import app.washtime.com.washtime.entity.Rule;
import app.washtime.com.washtime.entity.Student;
import app.washtime.com.washtime.task.RuleTask;
import app.washtime.com.washtime.task.impl.RuleTaskImpl;
import app.washtime.com.washtime.view.SettingItemView;

public class StudentAdminMainFragment extends Fragment {

    ListView mListView;
    static Student mStudent;
    Rule mRule;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_admin_main, container, false);

        if (getArguments() != null) {
            mStudent = (Student) getArguments().getSerializable("student");
            ((StudentAdminActivity) getActivity()).setToolbarTitle("Rules");
        }
        if (mStudent != null) {
            RuleTask taskRule = new RuleTaskImpl();
            String floor = String.valueOf(mStudent.getRoom());
            mRule = taskRule.getRule(mStudent.getStudentHome().getLocationName(), mStudent.getStudentHome().getName(), floor.substring(0, 1));
        }
        addListenerForListView(view);

        return view;
    }

    private void addListenerForListView(View view) {
        final GeneralAdminSettingAdapter adapter = new GeneralAdminSettingAdapter(getContext(), populateRuleList());
        mListView = (ListView) view.findViewById(R.id.st_admin_list_rule);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("rule", mRule);
                bundle.putString("title", "Change rule");
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

    private List<SettingItemView> populateRuleList() {
        if (mRule != null) {
            SettingItemView item = new SettingItemView("Rule",
                    "Number of reservation:  " + mRule.getNoReservations() + "\n" +
                    "Duration:  " + mRule.getDuration() + "\n" +
                    "Start hour:  " +  mRule.getStartHour() + "\n" +
                    "End hour:  " + mRule.getEndHour());
            return Arrays.asList(item);
        } else {
            return new ArrayList<>();
        }
    }

    public void clear() {
        mStudent = null;
    }
}
