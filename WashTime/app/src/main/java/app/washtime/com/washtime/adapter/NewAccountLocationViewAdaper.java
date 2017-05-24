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

public class NewAccountLocationViewAdaper extends BaseAdapter{

    private Context mContext;
    private List<String> mData = new ArrayList<>();
    private LayoutInflater mInflater;

    public NewAccountLocationViewAdaper(Context context, List<String> data) {
        mContext = context;
        mData = data;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        NewAccountLocationViewAdaper.ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.app_list_new_account_location_view, null);
            holder = new NewAccountLocationViewAdaper.ViewHolder();
            holder.item = (TextView)convertView.findViewById(R.id.lg_new_account_location_view_item);
            convertView.setTag(holder);
        } else {
            holder = (NewAccountLocationViewAdaper.ViewHolder)convertView.getTag();
        }
        holder.item.setText(mData.get(position));
        return convertView;
    }

    public static class ViewHolder {
        public TextView item;
    }

    public List<String> getData() {
        return mData;
    }
}
