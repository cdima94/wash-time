package app.washtime.com.washtime.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.TextView;

import app.washtime.com.washtime.R;
import app.washtime.com.washtime.entity.Rule;
import app.washtime.com.washtime.entity.Student;
import app.washtime.com.washtime.fragment.StudentAdminMainFragment;
import app.washtime.com.washtime.fragment.StudentAdminMainFragmentNoRule;
import app.washtime.com.washtime.main.MainActivity;

public class StudentAdminActivity extends MainActivity {

    ImageButton mBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_layout_student_admin);

        // fragment stuff
        if ((Rule) getIntent().getSerializableExtra("rule") != null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("student", (Student) getIntent().getSerializableExtra("student"));
            StudentAdminMainFragment fragment = new StudentAdminMainFragment();
            fragment.clear();
            fragment.setArguments(bundle);
            getFragmentManager().beginTransaction().add(R.id.st_admin_frame_layout, fragment).commit();
        } else {
            Bundle bundle = new Bundle();
            bundle.putSerializable("student", (Student) getIntent().getSerializableExtra("student"));
            StudentAdminMainFragmentNoRule fragment = new StudentAdminMainFragmentNoRule();
            fragment.clear();
            fragment.setArguments(bundle);
            getFragmentManager().beginTransaction().add(R.id.st_admin_frame_layout, fragment).commit();
        }

        addListenerForBackButton();
    }

    private void addListenerForBackButton() {
        mBackButton = (ImageButton) findViewById(R.id.sa_back_button);

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getFragmentManager().getBackStackEntryCount() > 0) {
                    if (getToolbarTitle().equals("Rules")) {
                        Intent intent = new Intent(getApplicationContext(), StudentActivity.class);
                        intent.putExtra("student", (Student) getIntent().getSerializableExtra("student"));
                        startActivity(intent);
                    } else {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(findViewById(R.id.sa_main_view).getWindowToken(), 0);
                        getFragmentManager().popBackStack();
                    }
                } else {
                    Intent intent = new Intent(getApplicationContext(), StudentActivity.class);
                    intent.putExtra("student", (Student) getIntent().getSerializableExtra("student"));
                    startActivity(intent);
                }
            }
        });
    }

    public void setToolbarTitle(String title) {
        TextView textView = (TextView) findViewById(R.id.sa_toolbar_title);
        textView.setText(title);
    }

    private String getToolbarTitle() {
        TextView textView = (TextView) findViewById(R.id.sa_toolbar_title);
        return textView.getText().toString();
    }
}
