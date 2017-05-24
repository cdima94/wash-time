package app.washtime.com.washtime.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;

import app.washtime.com.washtime.R;
import app.washtime.com.washtime.entity.Student;
import app.washtime.com.washtime.fragment.StudentSettingMainViewFragment;

public class StudentSettingActivity extends AppCompatActivity {

    ImageButton mBackButton;
    Student mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_setting_student);

        mUser = (Student) getIntent().getSerializableExtra("user");

        // fragment stuff
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", mUser);
        StudentSettingMainViewFragment fragment = new StudentSettingMainViewFragment();
        fragment.setArguments(bundle);
        getFragmentManager().beginTransaction().add(R.id.st_setting_frame_layout, fragment).commit();

        addListenerForBackButton();
    }

    private void addListenerForBackButton() {
        mBackButton = (ImageButton) findViewById(R.id.st_setting_back_button);

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getFragmentManager().getBackStackEntryCount() > 0) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(findViewById(R.id.st_setting_main_view).getWindowToken(), 0);
                    getFragmentManager().popBackStack();
                } else {
                    Intent intent = new Intent(getApplicationContext(), StudentActivity.class);
                    intent.putExtra("student", mUser);
                    startActivity(intent);
                }
            }
        });
    }
}
