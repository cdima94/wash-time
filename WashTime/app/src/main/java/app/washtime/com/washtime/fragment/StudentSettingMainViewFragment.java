package app.washtime.com.washtime.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
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
import app.washtime.com.washtime.adapter.GeneralAdminSettingAdapter;
import app.washtime.com.washtime.entity.Student;
import app.washtime.com.washtime.view.SettingItemView;

public class StudentSettingMainViewFragment extends Fragment {

    ListView mListView;
    Student mUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_settings, container, false);

        if (getArguments() != null) {
            mUser = (Student) getArguments().getSerializable("user");
        }
        getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        addListenerForListView(view);

        return view;
    }

    private void addListenerForListView(View view) {
        final GeneralAdminSettingAdapter adapter = new GeneralAdminSettingAdapter(getContext(), populateSettingList());
        mListView = (ListView) view.findViewById(R.id.st_setting_list);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (adapter.getItem(position).getLabelName().equals("Name")) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("user", mUser);
                    StudentSettingChangeNameFragment newFragment = new StudentSettingChangeNameFragment();
                    newFragment.setArguments(bundle);
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.st_setting_frame_layout, newFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
                if (adapter.getItem(position).getLabelName().equals("Change password")) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("user", mUser);
                    StudentSettingChangePasswordFragment newFragment = new StudentSettingChangePasswordFragment();
                    newFragment.setArguments(bundle);
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.st_setting_frame_layout, newFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }
        });
    }

    private List<SettingItemView> populateSettingList() {
        if (mUser != null) {
            SettingItemView item = new SettingItemView("Name", mUser.getFirstName() + " " + mUser.getLastName());
            SettingItemView item1 = new SettingItemView("Change password", "It's a good idea to use a strong password that you don't use elsewhere");
            return Arrays.asList(item, item1);
        }
        return new ArrayList<>();
    }
}
