package app.washtime.com.washtime.fragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import app.washtime.com.washtime.R;
import app.washtime.com.washtime.adapter.NewAccountSNamesAdapter;
import app.washtime.com.washtime.task.StudentHomeTask;
import app.washtime.com.washtime.task.impl.StudentHomeTaskImpl;


public class AddNewAccountLFragment extends Fragment {

    ListView mListView;
    private static ImageView sImageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_new_account_location_list, container, false);
        addListenerForListView(view);
        return view;
    }

    private void addListenerForListView(View view) {
        NewAccountSNamesAdapter adapter = new NewAccountSNamesAdapter(getContext(), getArguments() == null ? null : getArguments().getString("Location"));
        StudentHomeTask task = new StudentHomeTaskImpl();
        task.getAllNames(adapter, getArguments() == null ? null : getArguments().getString("Location name"));
        mListView = (ListView) view.findViewById(R.id.lg_location_list);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (sImageView != null) {
                    sImageView.setVisibility(View.INVISIBLE);
                }
                ImageView imageView = (ImageView) view.findViewById(R.id.lg_new_account_location_name_image);
                imageView.setVisibility(View.VISIBLE);
                sImageView = imageView;
                Bundle bundle = new Bundle();
                bundle.putString("Location", (String) parent.getAdapter().getItem(position));
                Fragment newFragment = new AddNewAccountFragment();
                newFragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.lg_layout_new_account, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }
}
