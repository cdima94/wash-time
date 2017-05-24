package app.washtime.com.washtime.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;

import app.washtime.com.washtime.R;
import app.washtime.com.washtime.fragment.AddNewAccountFragment;
import app.washtime.com.washtime.main.MainActivity;

public class AddNewAccountActivity extends MainActivity {

    ImageButton mBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_layout_create_new_account_location);

        // fragment stuff
        AddNewAccountFragment fragment = new AddNewAccountFragment();
        fragment.clear();
        getFragmentManager().beginTransaction().add(R.id.lg_layout_new_account, fragment).commit();

        addListenerForBackButton();
    }

    private void addListenerForBackButton() {
        mBackButton = (ImageButton) findViewById(R.id.lg_add_new_account_back_button);

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getFragmentManager().getBackStackEntryCount() > 0) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(findViewById(R.id.na_main_view).getWindowToken(), 0);
                    getFragmentManager().popBackStack();
                } else {
                    Intent loginActivity = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(loginActivity);
                }
            }
        });
    }
}
