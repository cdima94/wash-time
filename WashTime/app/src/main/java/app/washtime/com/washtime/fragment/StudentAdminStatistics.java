package app.washtime.com.washtime.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import app.washtime.com.washtime.R;
import app.washtime.com.washtime.activity.StudentAdminActivity;
import app.washtime.com.washtime.entity.Student;
import app.washtime.com.washtime.task.ReservationTask;
import app.washtime.com.washtime.task.impl.ReservationTaskImpl;

/**
 * Created by cdima on 6/2/2017.
 */

public class StudentAdminStatistics extends Fragment {

    Student mStudent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_admin_statistics, container, false);

        if (getArguments() != null) {
            mStudent = (Student) getArguments().getSerializable("user");
            ((StudentAdminActivity) getActivity()).setToolbarTitle(getArguments().getString("title"));
        }

        Random random = new Random();
        DataPoint[] dataPoints = new DataPoint[31];
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -30);
        GraphView graph = (GraphView) view.findViewById(R.id.graph);

        for (int i = 0 ; i <= 30; i++) {
            ReservationTask task = new ReservationTaskImpl();
            int number  = task.countReservations(formatDate(calendar.getTime()),
                    mStudent.getStudentHome().getLocationName(),
                    mStudent.getStudentHome().getName(),
                    mStudent.getRoom() / 100);
            dataPoints[i] = new DataPoint(calendar.getTime(), number);
            calendar.add(Calendar.DATE, 1);
        }

        // you can directly pass Date objects to DataPoint-Constructor
        // this will convert the Date to double via Date#getTime()
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoints);

        graph.addSeries(series);

        // set date label formatter
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
        graph.getGridLabelRenderer().setNumHorizontalLabels(5);

        // set manual x bounds to have nice steps
        graph.getViewport().setMinX(dataPoints[0].getX());
        graph.getViewport().setMaxX(dataPoints[4].getX());
        graph.getViewport().setXAxisBoundsManual(true);

        // as we use dates as labels, the human rounding to nice readable numbers
        // is not necessary
        graph.getGridLabelRenderer().setHumanRounding(false);

        graph.getViewport().setScalable(true);

        return view;
    }

    private String formatDate(Date date) {
        String myFormat = "yyyy-MM-d";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        return sdf.format(date.getTime());
    }
}
