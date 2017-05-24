package app.washtime.com.washtime.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

import app.washtime.com.washtime.R;
import app.washtime.com.washtime.entity.Student;
import app.washtime.com.washtime.fragment.AddNewLocationFragment;
import app.washtime.com.washtime.main.MainActivity;

public class AddNewLocationActivity extends MainActivity {

    ImageButton mBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_layout_create_new_location);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        // fragment stuff
        Bundle bundle  = new Bundle();
        bundle.putSerializable("User", getIntent().getSerializableExtra("admin"));
        AddNewLocationFragment fragment = new AddNewLocationFragment();
        fragment.setArguments(bundle);
        getFragmentManager().beginTransaction().add(R.id.ga_layout_new_location, fragment).commit();

        addListenerForBackButton();
    }

    private void addListenerForBackButton() {
        mBackButton = (ImageButton) findViewById(R.id.ga_add_new_location_back_button);

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GeneralAdminActivity.class);
                intent.putExtra("admin", (Student) getIntent().getSerializableExtra("admin"));
                startActivity(intent);
            }
        });
    }
}
