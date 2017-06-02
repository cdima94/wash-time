package app.washtime.com.washtime.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.washtime.com.washtime.R;
import app.washtime.com.washtime.entity.Reservation;
import app.washtime.com.washtime.view.HistoryHeaderListView;
import app.washtime.com.washtime.view.HistoryItemsListView;

/**
 * Created by cdima on 6/1/2017.
 */

public class HistoryAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private List<HistoryHeaderListView> mListDataHeader;
    private Map<HistoryHeaderListView, List<HistoryItemsListView>> mListDataItem;

    public HistoryAdapter(Context context) {
        mContext = context;
        mListDataHeader = new ArrayList<>();
        mListDataItem = new HashMap<>();
    }

    public void populateHistoryList(List<Reservation> reservations) {
        mListDataHeader.clear();
        mListDataItem.clear();
        if (reservations != null) {
            for (Reservation reservation : reservations) {
                String intervalHours = reservation.getStartHour() + "-" + reservation.getEndHour();
                HistoryHeaderListView headerItem = new HistoryHeaderListView(intervalHours, reservation.getStudent().getRoom());
                HistoryItemsListView childItem = new HistoryItemsListView(
                        reservation.getStudent().getFirstName(),
                        reservation.getStudent().getLastName(),
                        reservation.getDate(),
                        reservation.getDay().getName());
                mListDataHeader.add(headerItem);
                mListDataItem.put(headerItem, Arrays.asList(childItem));
            }
            notifyDataSetChanged();
        }
    }

    @Override
    public int getGroupCount() {
        return mListDataHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mListDataItem.get(mListDataHeader.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mListDataHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mListDataItem.get(mListDataHeader.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        HistoryHeaderListView headerTitle = (HistoryHeaderListView) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.app_list_group_history, null);
        }

        TextView intervalHours = (TextView) convertView.findViewById(R.id.history_hour);
        TextView room = (TextView) convertView.findViewById(R.id.history_room);
        intervalHours.setText(headerTitle.getIntervalHours());
        if (headerTitle.getRoom() == 0) {
            room.setText("");
        } else {
            room.setText(String.valueOf(headerTitle.getRoom()));
        }

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        HistoryItemsListView itemText = (HistoryItemsListView) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.app_list_item_history, null);
        }

        TextView firstName = (TextView) convertView.findViewById(R.id.history_first_name);
        TextView lastName = (TextView) convertView.findViewById(R.id.history_last_name);
        TextView date = (TextView) convertView.findViewById(R.id.history_date);
        TextView dayOfMonth = (TextView) convertView.findViewById(R.id.history_day_of_month);

        firstName.setText(itemText.getFirstName());
        lastName.setText(itemText.getLastName());
        date.setText(itemText.getDate());
        dayOfMonth.setText(itemText.getDayOfMonth());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
