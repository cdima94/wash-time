package app.washtime.com.washtime.fragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import app.washtime.com.washtime.R;

public class AddNewAccountFragmentUserName extends Fragment {

    TextInputLayout mUserName;
    Button mNextButton;

    static String sUserNameText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_new_account_username, container, false);

        mUserName = (TextInputLayout) view.findViewById(R.id.na_username);

        if (sUserNameText != null) {
            mUserName.getEditText().setText(sUserNameText);
        }

        addListenerForNextButton(view);

        return view;
    }

    private void addListenerForNextButton(View view) {
        mNextButton = (Button) view.findViewById(R.id.na_username_next_button);

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkState()) {
                    sUserNameText = mUserName.getEditText().getText().toString();
                    Bundle bundle = new Bundle();
                    bundle.putString("Location name", getArguments().getString("Location name"));
                    bundle.putString("Location", getArguments().getString("Location"));
                    bundle.putString("First name", getArguments().getString("First name"));
                    bundle.putString("Last name", getArguments().getString("Last name"));
                    bundle.putString("Room", getArguments().getString("Room"));
                    bundle.putString("User name", mUserName.getEditText().getText().toString()  );
                    Fragment newFragment = new AddNewAccountFragmentPassword();
                    newFragment.setArguments(bundle);
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.lg_layout_new_account, newFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                }
            }
        });
    }

    private boolean checkState() {
        if (mUserName.getEditText().getText().toString().isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public void clear() {
        sUserNameText = null;
    }
}
