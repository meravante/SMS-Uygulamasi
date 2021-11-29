package com.android.MeralKarduz.baglanti;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.MeralKarduz.R;
import com.android.MeralKarduz.model.kisilerGet;

import java.util.List;

public class kisilerAdaptor extends BaseAdapter {

    private List<kisilerGet> contacts;
    private Context context;
    private LayoutInflater layoutInflater;

    public kisilerAdaptor(Context context, List<kisilerGet> contacts) {
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
        View customView = layoutInflater.inflate(R.layout.kisiler_view, null);
        ImageView profile = (ImageView) customView.findViewById(R.id.profile);
        TextView name = (TextView) customView.findViewById(R.id.name);
        TextView phoneNumber = (TextView) customView.findViewById(R.id.phoneNumber);
        CheckBox selected = (CheckBox) customView.findViewById(R.id.selectedContact);

        name.setText(contacts.get(position).getName());
        phoneNumber.setText(contacts.get(position).getPhoneNumber());
        profile.setImageBitmap(contacts.get(position).getProfile());

        selected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                contacts.get(position).setSelected(isChecked);
            }
        });

        return customView;
    }

    public List<kisilerGet> getContacts() {
        return contacts;
    }
}
