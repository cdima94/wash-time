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
import app.washtime.com.washtime.entity.StudentHome;
import app.washtime.com.washtime.view.StudentHomeViewContainer;

public class StudentHomeAdapter extends BaseAdapter {

    private Context mContext;
    private List<StudentHomeViewContainer> mData = new ArrayList<>();
    private LayoutInflater mInflater;

    public StudentHomeAdapter(Context context) {
        mContext = context;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void populateStudentHomeAdapter(List<StudentHome> studentHome) {
        for (StudentHome sHome: studentHome) {
            mData.add(new StudentHomeViewContainer(sHome.getLocationName(), sHome.getName()));
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public StudentHomeViewContainer getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.app_list_item_general_admin, null);
            holder = new ViewHolder();
            holder.locationName = (TextView)convertView.findViewById(R.id.ga_list_item_location_name);
            holder.name = (TextView)convertView.findViewById(R.id.ga_list_item_location);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.locationName.setText(mData.get(position).getLocationName());
        holder.name.setText(mData.get(position).getName());
        return convertView;
    }

    public static class ViewHolder {
        public TextView locationName;
        public TextView name;
    }
}
