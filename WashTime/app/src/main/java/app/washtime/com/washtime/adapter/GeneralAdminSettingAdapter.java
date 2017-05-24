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
import app.washtime.com.washtime.view.SettingItemView;

public class GeneralAdminSettingAdapter extends BaseAdapter {

    private List<SettingItemView> mData = new ArrayList<>();
    private LayoutInflater mInflater;

    public GeneralAdminSettingAdapter(Context context, List<SettingItemView> data) {
        mData = data;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public SettingItemView getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GeneralAdminSettingAdapter.ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.app_list_item_ga_settings, null);
            holder = new GeneralAdminSettingAdapter.ViewHolder();
            holder.labelName = (TextView)convertView.findViewById(R.id.ga_setting_label_name);
            holder.labelText = (TextView)convertView.findViewById(R.id.ga_setting_label_text);
            convertView.setTag(holder);
        } else {
            holder = (GeneralAdminSettingAdapter.ViewHolder)convertView.getTag();
        }
        holder.labelName.setText(mData.get(position).getLabelName());
        holder.labelText.setText(mData.get(position).getLabelText());
        return convertView;
    }

    public static class ViewHolder {
        public TextView labelName;
        public TextView labelText;
    }
}
