package app.washtime.com.washtime.activity;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import app.washtime.com.washtime.R;
import app.washtime.com.washtime.adapter.ReservationAdapter;
import app.washtime.com.washtime.entity.DayOfWeek;
import app.washtime.com.washtime.entity.Reservation;
import app.washtime.com.washtime.entity.Role;
import app.washtime.com.washtime.entity.Rule;
import app.washtime.com.washtime.entity.Student;
import app.washtime.com.washtime.receiver.AlarmReceiver;
import app.washtime.com.washtime.task.ReservationTask;
import app.washtime.com.washtime.task.RuleTask;
import app.washtime.com.washtime.task.impl.ReservationTaskImpl;
import app.washtime.com.washtime.task.impl.RuleTaskImpl;
import app.washtime.com.washtime.view.ReservationViewContainer;


public class StudentActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Student mStudent;
    private TabLayout tabLayout;
    private ListView mListView;
    private ReservationAdapter mAdapter;
    private Rule mRule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_layout_student);
        Toolbar toolbar = (Toolbar) findViewById(R.id.student_toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.student_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.student_nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Intent intent = getIntent();
        mStudent = (Student) intent.getSerializableExtra("student");
        addNavHeader(navigationView.getHeaderView(0));

        addListenerForTabItemClick();
        addListenerForLogoutButton();

        RuleTask taskRule = new RuleTaskImpl();
        String floor = String.valueOf(mStudent.getRoom());
        mRule = taskRule.getRule(mStudent.getStudentHome().getLocationName(), mStudent.getStudentHome().getName(), floor.substring(0, 1));

        if (mRule != null) {
            mAdapter = new ReservationAdapter(getApplicationContext());
            calculateRerervationTimes(mAdapter, mRule.getStartHour(), mRule.getEndHour(), mRule.getDuration());
            ReservationTask task = new ReservationTaskImpl();
            mAdapter.populateReservationList(task.getReservationList(DayOfWeek.MONDAY, calculateReservationDate(), mStudent.getStudentHome().getLocationName(), mStudent.getStudentHome().getName(), mStudent.getRoom()));
            mListView = (ListView) findViewById(R.id.reservation_list);
            mListView.setAdapter(mAdapter);
            addListenerForListView();
        }

        // scheduleNotification();
    }

    private int parseDuration(String duration) {
        String hours = duration.substring(0, duration.lastIndexOf(":"));
        String minutes = duration.substring(duration.lastIndexOf(":") + 1, duration.length());
        return Integer.valueOf(hours) * 60 + Integer.valueOf(minutes);
    }

    private String minutes2Time(int duration) {
        int hours = duration / 60;
        int minutes = duration -  hours * 60;
        if (String.valueOf(hours).length() == 1 && String.valueOf(minutes).length() == 1) {
            return "0" + hours + ":" + "0" + minutes;
        } else if (String.valueOf(hours).length() == 1) {
            return "0" + hours + ":" + minutes;
        } else if (String.valueOf(minutes).length() == 1) {
            return hours + ":" + "0" + minutes;
        } else {
            return hours + ":" + minutes;
        }
    }

    private void calculateRerervationTimes(ReservationAdapter adapter, String startHour, String endHour, String duration) {
        int dTime = parseDuration(duration);
        int shTime = parseDuration(startHour);
        int ehTime = parseDuration(endHour);
        List<ReservationViewContainer> tempListReservation = new ArrayList<>();
        while (shTime + dTime <= ehTime) {
            ReservationViewContainer container = new ReservationViewContainer();
            container.setIntervalHours(minutes2Time(shTime) + "-" + minutes2Time(shTime+dTime));
            shTime = shTime + dTime;
            tempListReservation.add(container);
        }
        adapter.populateReservation(tempListReservation);
    }

    private void addNavHeader(View headerView) {
        TextView studentName = (TextView) headerView.findViewById(R.id.student_nav_name);
        TextView userName = (TextView) headerView.findViewById(R.id.student_nav_user_name);

        studentName.setText(mStudent.getLastName() + " " + mStudent.getFirstName());
        userName.setText(mStudent.getUserName());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.student_drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.setting_option) {
            Intent intent = new Intent(getApplicationContext(), StudentSettingActivity.class);
            intent.putExtra("user", mStudent);
            startActivity(intent);
        }
        if (id == R.id.switch_admin) {
            if (mStudent.getRole().equals(Role.ADMIN)) {
                Intent admin = new Intent(getApplicationContext(), StudentAdminActivity.class);
                admin.putExtra("student", mStudent);
                admin.putExtra("rule", mRule);
                startActivity(admin);
            } else {
                Toast toast = Toast.makeText(this, "You don't have permission to go to the admin page", Toast.LENGTH_SHORT);
                TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
                if( v != null) v.setGravity(Gravity.CENTER);
                toast.show();
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.student_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void addListenerForLogoutButton() {
        Button logOutButton = (Button) findViewById(R.id.student_logout);
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent logout = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(logout);
            }
        });
    }

    private void addListenerForTabItemClick() {
        tabLayout = (TabLayout) findViewById(R.id.student_tab_item_layout);

        TabLayout.Tab tab = tabLayout.getTabAt(0);
        tab.select();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (mRule != null) {
                    ReservationTask task = new ReservationTaskImpl();
                    mAdapter.populateReservationList(task.getReservationList(DayOfWeek.getByAbbreviation(tab.getText().toString()), calculateReservationDate(), mStudent.getStudentHome().getLocationName(), mStudent.getStudentHome().getName(), mStudent.getRoom()));

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private void addListenerForListView() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ReservationViewContainer reservationViewContainer = (ReservationViewContainer) parent.getAdapter().getItem(position);
                if (reservationViewContainer.getRoom() == 0) {
                    Reservation reservation = new Reservation();
                    reservation.setDate(calculateReservationDate());
                    reservation.setStartHour(reservationViewContainer.getIntervalHours().substring(0, reservationViewContainer.getIntervalHours().lastIndexOf("-")));
                    reservation.setEndHour(reservationViewContainer.getIntervalHours().substring(reservationViewContainer.getIntervalHours().lastIndexOf("-") + 1, reservationViewContainer.getIntervalHours().length()));
                    reservation.setDay(DayOfWeek.getByPosition(tabLayout.getSelectedTabPosition()));
                    reservation.setStudent(mStudent);
                    if (validateReservation()) {
                        ReservationTask task = new ReservationTaskImpl();
                        task.reserve(reservation);
                        mAdapter.populateReservationList(task.getReservationList(DayOfWeek.getByPosition(tabLayout.getSelectedTabPosition()), calculateReservationDate(), mStudent.getStudentHome().getLocationName(), mStudent.getStudentHome().getName(), mStudent.getRoom()));
                    } else {
                        Toast toast = Toast.makeText(getApplicationContext(), "You can reserve just " + mRule.getNoReservations() + " per week", Toast.LENGTH_SHORT);
                        TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
                        if (v != null) v.setGravity(Gravity.CENTER);
                        toast.show();
                    }
                }
            }
        });
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final ReservationViewContainer reservationViewContainer = (ReservationViewContainer) parent.getAdapter().getItem(position);
                if (reservationViewContainer.getRoom() == mStudent.getRoom()) {
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case DialogInterface.BUTTON_POSITIVE:
                                    ReservationTask task = new ReservationTaskImpl();
                                    task.delete(calculateReservationDate(),
                                            mStudent.getStudentHome().getLocationName(),
                                            mStudent.getStudentHome().getName(),
                                            mStudent.getRoom(),
                                            reservationViewContainer.getIntervalHours().substring(0, reservationViewContainer.getIntervalHours().lastIndexOf("-")),
                                            reservationViewContainer.getIntervalHours().substring(reservationViewContainer.getIntervalHours().indexOf("-") + 1, reservationViewContainer.getIntervalHours().length())
                                    );
                                    mAdapter.populateReservationList(task.getReservationList(DayOfWeek.getByPosition(tabLayout.getSelectedTabPosition()), calculateReservationDate(), mStudent.getStudentHome().getLocationName(), mStudent.getStudentHome().getName(), mStudent.getRoom()));
                                    break;
                                case DialogInterface.BUTTON_NEGATIVE:
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(StudentActivity.this);
                    builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).create().show();
                }
                return false;
            }
        });
    }

    private String calculateReservationDate() {
        String currentDayOfWeek = new SimpleDateFormat("EE", Locale.ENGLISH).format(System.currentTimeMillis());
        int tabCurrentDayOfWeekPosition = DayOfWeek.getByAbbreviation(currentDayOfWeek).getPosition();
        int tabPosition = tabLayout.getSelectedTabPosition();
        if (tabCurrentDayOfWeekPosition <= tabPosition) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH, tabPosition - tabCurrentDayOfWeekPosition);
            String reservationDate = new SimpleDateFormat("dd-MM-yyyy").format(calendar.getTime());
            String[] reservationDateSplit = reservationDate.split("-");
            return reservationDateSplit[2] + "-" + reservationDateSplit[1] + "-" + (Integer.parseInt(reservationDateSplit[0]));
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH,  7 - (tabCurrentDayOfWeekPosition - tabPosition));
            String reservationDate = new SimpleDateFormat("dd-MM-yyyy").format(calendar.getTime());
            String[] reservationDateSplit = reservationDate.split("-");
            return reservationDateSplit[2] + "-" + reservationDateSplit[1] + "-" + (Integer.parseInt(reservationDateSplit[0]));
        }
    }

    private boolean validateReservation() {
        String currentDayOfWeek = new SimpleDateFormat("EE", Locale.ENGLISH).format(System.currentTimeMillis());
        int tabCurrentDayOfWeekPosition = DayOfWeek.getByAbbreviation(currentDayOfWeek).getPosition();
        int tabPosition = tabLayout.getSelectedTabPosition();
        if (tabPosition >= tabCurrentDayOfWeekPosition) {
            ReservationTask task = new ReservationTaskImpl();
            String calculateStartDate = calculateCurrentWeekStartDate(tabCurrentDayOfWeekPosition);
            String calculateEndDate = calculateCurrentWeekEndDate(tabCurrentDayOfWeekPosition);
            List<Reservation> reservations = task.getSpecificReservationList(calculateStartDate, calculateEndDate, mStudent.getStudentHome().getLocationName(), mStudent.getStudentHome().getName(), mStudent.getRoom());
            if (reservations.size() < mRule.getNoReservations()) {
                return true;
            } else {
                return false;
            }
        } else {
            ReservationTask task = new ReservationTaskImpl();
            String calculateStartDate = calculateNextWeekStartDate(tabCurrentDayOfWeekPosition);
            String calculateEndDate = calculateNextWeekEndDate(tabCurrentDayOfWeekPosition);
            List<Reservation> reservations = task.getSpecificReservationList(calculateStartDate, calculateEndDate, mStudent.getStudentHome().getLocationName(), mStudent.getStudentHome().getName(), mStudent.getRoom());
            if (reservations.size() < mRule.getNoReservations()) {
                return true;
            } else {
                return false;
            }
        }
    }

    private String calculateCurrentWeekStartDate(int tabCurrentDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -tabCurrentDate);
        String reservationDate = new SimpleDateFormat("dd-MM-yyyy").format(calendar.getTime());
        String[] reservationDateSplit = reservationDate.split("-");
        return reservationDateSplit[2] + "-" + reservationDateSplit[1] + "-" + (Integer.parseInt(reservationDateSplit[0]));
    }

    private String calculateCurrentWeekEndDate(int tabCurrentDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 6 - tabCurrentDate);
        String reservationDate = new SimpleDateFormat("dd-MM-yyyy").format(calendar.getTime());
        String[] reservationDateSplit = reservationDate.split("-");
        return reservationDateSplit[2] + "-" + reservationDateSplit[1] + "-" + (Integer.parseInt(reservationDateSplit[0]));
    }

    private String calculateNextWeekStartDate(int tabCurrentDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 7 - tabCurrentDate);
        String reservationDate = new SimpleDateFormat("dd-MM-yyyy").format(calendar.getTime());
        String[] reservationDateSplit = reservationDate.split("-");
        return reservationDateSplit[2] + "-" + reservationDateSplit[1] + "-" + (Integer.parseInt(reservationDateSplit[0]));
    }

    private String calculateNextWeekEndDate(int tabCurrentDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 13 - tabCurrentDate);
        String reservationDate = new SimpleDateFormat("dd-MM-yyyy").format(calendar.getTime());
        String[] reservationDateSplit = reservationDate.split("-");
        return reservationDateSplit[2] + "-" + reservationDateSplit[1] + "-" + (Integer.parseInt(reservationDateSplit[0]));
    }

    // Notification work

    private void scheduleNotification() {
        Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);

        final PendingIntent pendingIntent = PendingIntent.getBroadcast(this, AlarmReceiver.REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        long firstMillis = System.currentTimeMillis();
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, firstMillis, 10000, pendingIntent);
    }
}
