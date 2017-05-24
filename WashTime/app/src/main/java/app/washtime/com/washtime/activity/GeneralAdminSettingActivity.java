package app.washtime.com.washtime.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;

import app.washtime.com.washtime.R;
import app.washtime.com.washtime.entity.Student;
import app.washtime.com.washtime.fragment.SettingMainViewFragment;

public class GeneralAdminSettingActivity extends GeneralAdminActivity {

    ImageButton mBackButton;
    Student mGA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_setting_general_admin);

        mGA = (Student) getIntent().getSerializableExtra("admin");

        // fragment stuff
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", mGA);
        SettingMainViewFragment fragment = new SettingMainViewFragment();
        fragment.setArguments(bundle);
        getFragmentManager().beginTransaction().add(R.id.ga_setting_frame_layout, fragment).commit();

        addListenerForBackButton();
    }

    private void addListenerForBackButton() {
        mBackButton = (ImageButton) findViewById(R.id.ga_setting_back_button);

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getFragmentManager().getBackStackEntryCount() > 0) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(findViewById(R.id.ga_setting_main_view).getWindowToken(), 0);
                    getFragmentManager().popBackStack();
                } else {
                    Intent intent = new Intent(getApplicationContext(), GeneralAdminActivity.class);
                    intent.putExtra("admin", mGA);
                    startActivity(intent);
                }
            }
        });
    }
}
