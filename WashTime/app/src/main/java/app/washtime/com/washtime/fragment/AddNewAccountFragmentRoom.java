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

public class AddNewAccountFragmentRoom extends Fragment {

    TextInputLayout mRoom;
    Button mNextButton;

    static String sRoomText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_new_account_room, container, false);

        mRoom = (TextInputLayout) view.findViewById(R.id.na_room);

        if (sRoomText != null) {
            mRoom.getEditText().setText(sRoomText);
        }

        addListenerForNextButton(view);

        return view;
    }

    private void addListenerForNextButton(View view) {
        mNextButton = (Button) view.findViewById(R.id.na_room_next_button);

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkState()) {
                    sRoomText = mRoom.getEditText().getText().toString();
                    Bundle bundle = new Bundle();
                    bundle.putString("Location name", getArguments().getString("Location name"));
                    bundle.putString("Location", getArguments().getString("Location"));
                    bundle.putString("First name", getArguments().getString("First name"));
                    bundle.putString("Last name", getArguments().getString("Last name"));
                    bundle.putString("Room", mRoom.getEditText().getText().toString());
                    AddNewAccountFragmentUserName newFragment = new AddNewAccountFragmentUserName();
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
        if (mRoom.getEditText().getText().toString().isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public void clear() {
        sRoomText = null;
    }
}
