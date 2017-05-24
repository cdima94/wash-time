package app.washtime.com.washtime.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.washtime.com.washtime.R;
import app.washtime.com.washtime.entity.Reservation;
import app.washtime.com.washtime.view.ReservationViewContainer;

public class ReservationAdapter extends BaseAdapter {

    private Context mContext;
    private List<ReservationViewContainer> mData = new ArrayList<>();
    private LayoutInflater mInflater;

    public ReservationAdapter(Context context) {
        mContext = context;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void populateReservationList(List<Reservation> reservations) {
        clearRoomsData();
        if (reservations != null) {
            for (Reservation reservation : reservations) {
                String intervalHours = reservation.getStartHour() + "-" + reservation.getEndHour();
                for (ReservationViewContainer container: mData) {
                    if (container.getIntervalHours().equals(intervalHours)) {
                        container.setRoom(reservation.getStudent().getRoom());
                    }
                }
            }
            notifyDataSetChanged();
        }
    }

    public void populateReservation(List<ReservationViewContainer> reservationViewContainer) {
        mData.addAll(reservationViewContainer);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public ReservationViewContainer getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ReservationAdapter.ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.app_list_reservation, null);
            holder = new ReservationAdapter.ViewHolder();
            holder.hour = (TextView) convertView.findViewById(R.id.reservation_item_hour);
            holder.room = (TextView) convertView.findViewById(R.id.reservation_item_room);
            convertView.setTag(holder);
        } else {
            holder = (ReservationAdapter.ViewHolder)convertView.getTag();
        }
        holder.hour.setText(mData.get(position).getIntervalHours());
        if (mData.get(position).getRoom() != 0) {
            holder.room.setText(String.valueOf(mData.get(position).getRoom()));
        } else {
            holder.room.setText("");
        }
        return convertView;
    }

    public static class ViewHolder {
        public TextView hour;
        public TextView room;
    }

    private void clearRoomsData() {
        for (ReservationViewContainer reservationViewContainer: mData) {
            reservationViewContainer.setRoom(0);
        }
    }
}
