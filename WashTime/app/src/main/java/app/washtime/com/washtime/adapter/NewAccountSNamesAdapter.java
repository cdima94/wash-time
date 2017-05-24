package app.washtime.com.washtime.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.washtime.com.washtime.R;

public class NewAccountSNamesAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> mData = new ArrayList<>();
    private LayoutInflater mInflater;
    private String mSelectedName;

    public NewAccountSNamesAdapter(Context context, String selectedName) {
        mContext = context;
        mSelectedName = selectedName;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void populateStudentHomeAdapter(List<String> studentHome) {
        mData.addAll(studentHome);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public String getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NewAccountSNamesAdapter.ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.app_list_new_account_location_name, null);
            holder = new NewAccountSNamesAdapter.ViewHolder();
            holder.locationName = (TextView) convertView.findViewById(R.id.lg_new_account_location_name_item);
            holder.imageView = (ImageView) convertView.findViewById(R.id.lg_new_account_location_name_image);
            convertView.setTag(holder);
        } else {
            holder = (NewAccountSNamesAdapter.ViewHolder)convertView.getTag();
        }
        holder.locationName.setText(mData.get(position));
        holder.imageView.setVisibility(View.INVISIBLE);
        if (mData.get(position).equals(mSelectedName)) {
            holder.imageView.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    public static class ViewHolder {
        public TextView locationName;
        public ImageView imageView;
    }
}
