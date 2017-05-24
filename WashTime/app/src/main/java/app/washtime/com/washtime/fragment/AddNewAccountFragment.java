package app.washtime.com.washtime.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.Arrays;
import java.util.List;

import app.washtime.com.washtime.R;
import app.washtime.com.washtime.adapter.NewAccountLocationViewAdaper;

public class AddNewAccountFragment extends Fragment {

    ListView mListView;
    Button mButton;
    static String sLocationName;
    static String sLocation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_new_account, container, false);
        if (getArguments() != null) {
            if (getArguments().getString("Location name") != null) {
                sLocationName = getArguments().getString("Location name");
                sLocation = null;
            }
            if (getArguments().getString("Location") != null) {
                sLocation = getArguments().getString("Location");
            }
        }
        getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        addListenerForListView(view);
        addListenerForButton(view);
        checkState();
        return view;
    }

    private void addListenerForListView(View view) {
        final NewAccountLocationViewAdaper adapter = new NewAccountLocationViewAdaper(getContext(), Arrays.asList("Location name", "Location"));
        mListView = (ListView) view.findViewById(R.id.lg_location_view);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List<String> data = adapter.getData();
                if (data.get(position).equals("Location name")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("Location name", sLocationName);
                    Fragment newFragment = new AddNewAccountLNFragment();
                    newFragment.setArguments(bundle);
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.lg_layout_new_account, newFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
                if (data.get(position).equals("Location")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("Location", sLocation);
                    bundle.putString("Location name", sLocationName);
                    Fragment newFragment = new AddNewAccountLFragment();
                    newFragment.setArguments(bundle);
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.lg_layout_new_account, newFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }
        });
    }

    private void addListenerForButton(View view) {
        mButton = (Button) view.findViewById(R.id.na_next_button);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mButton.isEnabled()) {
                    Bundle bundle = new Bundle();
                    bundle.putString("Location", sLocation);
                    bundle.putString("Location name", sLocationName);
                    AddNewAccountFragmentFullName newFragment = new AddNewAccountFragmentFullName();
                    newFragment.clear();
                    newFragment.setArguments(bundle);
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.lg_layout_new_account, newFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }
        });
    }

    private void checkState() {
        if (sLocationName != null && sLocation != null) {
            mButton.setEnabled(true);
            mButton.setTextColor(Color.WHITE);
        } else {
            mButton.setEnabled(false);
            mButton.setTextColor(Color.GRAY);
        }
    }

    public void clear() {
        sLocationName = null;
        sLocation = null;
    }
}
