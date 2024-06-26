package com.example.ea3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class KcalAdapter extends ArrayAdapter<KcalRecord> {

    private Context context;
    private int layoutResourceID;
    private KcalRecord[] kcalData = null;

    public KcalAdapter(Context context, int layoutResourceID, KcalRecord[] kcalData) {
        super(context, layoutResourceID, kcalData);

        this.context = context;
        this.layoutResourceID = layoutResourceID;
        this.kcalData = kcalData;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        KcalHolder kcalHolder = null;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (row == null) {
            row = inflater.inflate(layoutResourceID, parent, false);

            kcalHolder = new KcalHolder();
            kcalHolder.date = row.findViewById(R.id.date);
            kcalHolder.kcal = row.findViewById(R.id.kcal);

            row.setTag(kcalHolder);
        } else {
            kcalHolder = (KcalHolder) row.getTag();
        }

        KcalRecord record = kcalData[position];

        kcalHolder.date.setText(record.date);
        kcalHolder.kcal.setText(String.valueOf(record.kcal));

        return row;
    }

    private class KcalHolder {
        TextView date;
        TextView kcal;
    }
}