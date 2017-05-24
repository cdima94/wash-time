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

public class AddNewAccountFragmentFullName extends Fragment {

    TextInputLayout mFirstName;
    TextInputLayout mLastName;
    Button mNextButton;

    static String sFirstNameText;
    static String sLastNameText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_new_account_full_name, container, false);

        mFirstName = (TextInputLayout) view.findViewById(R.id.na_firstName);
        mLastName = (TextInputLayout) view.findViewById(R.id.na_lastName);

        if (sFirstNameText != null) {
            mFirstName.getEditText().setText(sFirstNameText);
        }
        if (sLastNameText != null) {
            mLastName.getEditText().setText(sLastNameText);
        }

        addListenerForNextButton(view);

        return view;
    }

    private void addListenerForNextButton(View view) {
        mNextButton = (Button) view.findViewById(R.id.na_fullName_next_button);

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkState()) {
                    sFirstNameText = mFirstName.getEditText().getText().toString();
                    sLastNameText = mLastName.getEditText().getText().toString();
                    Bundle bundle = new Bundle();
                    bundle.putString("Location name", getArguments().getString("Location name"));
                    bundle.putString("Location", getArguments().getString("Location"));
                    bundle.putString("First name", mFirstName.getEditText().getText().toString());
                    bundle.putString("Last name", mLastName.getEditText().getText().toString());
                    AddNewAccountFragmentRoom newFragment = new AddNewAccountFragmentRoom();
                    newFragment.clear();
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
        if (mFirstName.getEditText().getText().toString().isEmpty() || mLastName.getEditText().getText().toString().isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public void clear() {
        sFirstNameText = null;
        sLastNameText = null;
    }
}
