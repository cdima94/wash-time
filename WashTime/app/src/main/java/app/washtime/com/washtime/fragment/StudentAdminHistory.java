package app.washtime.com.washtime.fragment;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.ExpandableListView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import app.washtime.com.washtime.R;
import app.washtime.com.washtime.activity.StudentAdminActivity;
import app.washtime.com.washtime.adapter.HistoryAdapter;
import app.washtime.com.washtime.entity.Student;
import app.washtime.com.washtime.task.ReservationTask;
import app.washtime.com.washtime.task.impl.ReservationTaskImpl;
import app.washtime.com.washtime.utils.DateParsing;

/**
 * Created by cdima on 5/31/2017.
 */

public class StudentAdminHistory extends Fragment {

    TextInputLayout mFromDate;
    TextInputLayout mToDate;
    Calendar mFromDateCalendar;
    Calendar mToDateCalendar;
    ExpandableListView mList;

    HistoryAdapter mAdapter;
    private Student mStudent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_admin_history, container, false);

        if (getArguments() != null) {
            mStudent = (Student) getArguments().getSerializable("user");
            ((StudentAdminActivity) getActivity()).setToolbarTitle(getArguments().getString("title"));
        }

        mFromDateCalendar = Calendar.getInstance();
        mToDateCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener fromDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                mFromDateCalendar.set(Calendar.YEAR, year);
                mFromDateCalendar.set(Calendar.MONTH, monthOfYear);
                mFromDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                if (mFromDateCalendar.getTime().compareTo(mToDateCalendar.getTime()) >  0) {
                    mToDateCalendar.set(Calendar.YEAR, year);
                    mToDateCalendar.set(Calendar.MONTH, monthOfYear);
                    mToDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateLabel(mToDate, mToDateCalendar);
                }
                updateLabel(mFromDate, mFromDateCalendar);

                ReservationTask task = new ReservationTaskImpl();
                mAdapter.populateHistoryList(task.getHistoryList(
                        DateParsing.formatHistoryDate(mFromDate.getEditText().getText().toString()),
                        DateParsing.formatHistoryDate(mToDate.getEditText().getText().toString()),
                        mStudent.getStudentHome().getLocationName(),
                        mStudent.getStudentHome().getName(),
                        (mStudent.getRoom() / 100)));
            }

        };

        final DatePickerDialog.OnDateSetListener toDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                mToDateCalendar.set(Calendar.YEAR, year);
                mToDateCalendar.set(Calendar.MONTH, monthOfYear);
                mToDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                if (mToDateCalendar.getTime().compareTo(mFromDateCalendar.getTime()) < 0) {
                    mFromDateCalendar.set(Calendar.YEAR, year);
                    mFromDateCalendar.set(Calendar.MONTH, monthOfYear);
                    mFromDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateLabel(mFromDate, mFromDateCalendar);
                }
                updateLabel(mToDate, mToDateCalendar);

                ReservationTask task = new ReservationTaskImpl();
                mAdapter.populateHistoryList(task.getHistoryList(
                        DateParsing.formatHistoryDate(mFromDate.getEditText().getText().toString()),
                        DateParsing.formatHistoryDate(mToDate.getEditText().getText().toString()),
                        mStudent.getStudentHome().getLocationName(),
                        mStudent.getStudentHome().getName(),
                        (mStudent.getRoom() / 100)));
            }

        };

        mFromDate = (TextInputLayout) view.findViewById(R.id.st_admin_history_from_date);
        mToDate = (TextInputLayout) view.findViewById(R.id.st_admin_history_to_date);

        updateLabel(mFromDate, mFromDateCalendar);
        updateLabel(mToDate, mToDateCalendar);

        mFromDate.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 8) {
                    Integer[] parts = DateParsing.getHistoryPartsDate(s.toString());
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(parts[2], parts[0] - 1, parts[1]);
                    if (calendar.getTime().compareTo(mToDateCalendar.getTime()) > 0) {
                        mToDate.getEditText().setText(s.toString());
                    }
                    ReservationTask task = new ReservationTaskImpl();
                    mAdapter.populateHistoryList(task.getHistoryList(
                            DateParsing.formatHistoryDate(mFromDate.getEditText().getText().toString()),
                            DateParsing.formatHistoryDate(mToDate.getEditText().getText().toString()),
                            mStudent.getStudentHome().getLocationName(),
                            mStudent.getStudentHome().getName(),
                            (mStudent.getRoom() / 100)));
                }
            }
        });

        mFromDate.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                String[] mFromDateSplit = mFromDate.getEditText().getText().toString().split("/");
                String year = mFromDateSplit[2];
                if (Integer.valueOf(mFromDateSplit[2]) < 50) {
                    year = "20" + year;
                } else {
                    year = "19" + year;
                }
                mFromDateCalendar.set(Integer.valueOf(year), Integer.valueOf(mFromDateSplit[0]) - 1, Integer.valueOf(mFromDateSplit[1]));
                new DatePickerDialog(getContext(), fromDate, mFromDateCalendar
                        .get(Calendar.YEAR), mFromDateCalendar.get(Calendar.MONTH),
                        mFromDateCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        mToDate.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 8) {
                    Integer[] parts = DateParsing.getHistoryPartsDate(s.toString());
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(parts[2], parts[0] - 1, parts[1]);
                    if (calendar.getTime().compareTo(mFromDateCalendar.getTime()) < 0) {
                        mFromDate.getEditText().setText(s.toString());
                    }
                    ReservationTask task = new ReservationTaskImpl();
                    mAdapter.populateHistoryList(task.getHistoryList(
                            DateParsing.formatHistoryDate(mFromDate.getEditText().getText().toString()),
                            DateParsing.formatHistoryDate(mToDate.getEditText().getText().toString()),
                            mStudent.getStudentHome().getLocationName(),
                            mStudent.getStudentHome().getName(),
                            (mStudent.getRoom() / 100)));
                }
            }
        });

        mToDate.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                String[] mFromDateSplit = mToDate.getEditText().getText().toString().split("/");
                String year = mFromDateSplit[2];
                if (Integer.valueOf(mFromDateSplit[2]) < 50) {
                    year = "20" + year;
                } else {
                    year = "19" + year;
                }
                mToDateCalendar.set(Integer.valueOf(year), Integer.valueOf(mFromDateSplit[0]) - 1, Integer.valueOf(mFromDateSplit[1]));
                new DatePickerDialog(getContext(), toDate, mToDateCalendar
                        .get(Calendar.YEAR), mToDateCalendar.get(Calendar.MONTH),
                        mToDateCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        mAdapter = new HistoryAdapter(getContext());
        ReservationTask task = new ReservationTaskImpl();
        mAdapter.populateHistoryList(task.getHistoryList(
                DateParsing.formatHistoryDate(mFromDate.getEditText().getText().toString()),
                DateParsing.formatHistoryDate(mToDate.getEditText().getText().toString()),
                mStudent.getStudentHome().getLocationName(),
                mStudent.getStudentHome().getName(),
                (mStudent.getRoom() / 100)));

        mList = (ExpandableListView) view.findViewById(R.id.st_admin_history_list);
        mList.setAdapter(mAdapter);
        return view;
    }

    private void updateLabel(TextInputLayout textInputLayout, Calendar calendar) {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        textInputLayout.getEditText().setText(sdf.format(calendar.getTime()));
    }
}
