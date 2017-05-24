package app.washtime.com.washtime.view;

public class SettingItemView {

    private String mLabelName;
    private String mLabelText;

    public SettingItemView(String labelName, String labelText) {
        mLabelName = labelName;
        mLabelText = labelText;
    }

    public String getLabelName() {
        return mLabelName;
    }

    public String getLabelText() {
        return mLabelText;
    }
}
