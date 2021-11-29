package com.android.MeralKarduz.baglanti;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.MeralKarduz.R;
import com.android.MeralKarduz.model.kisilerGet;

import java.util.List;

public class kisilerBrief extends BaseAdapter {

    private List<kisilerGet> contacts;
    private Context context;
    private LayoutInflater layoutInflater;

    public kisilerBrief(Context context, List<kisilerGet> contacts) {
        this.context = context;
        this.contacts = contacts;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return contacts.size();
    }

    @Override
    public Object getItem(int position) {
        return contacts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View customView = layoutInflater.inflate(R.layout.kisiler_brief_view, null);
        TextView name = (TextView) customView.findViewById(R.id.name);
        name.setText(contacts.get(position).getName());
        return customView;
    }

    public List<kisilerGet> getContacts() {
        return contacts;
    }
}
