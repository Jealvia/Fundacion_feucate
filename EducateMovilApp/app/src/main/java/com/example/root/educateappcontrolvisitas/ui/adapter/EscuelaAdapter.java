package com.example.root.educateappcontrolvisitas.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.root.educateappcontrolvisitas.api.model.Escuela;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class EscuelaAdapter extends ArrayAdapter<Escuela> {

    private Context mContext;
    private List<Escuela> mItems;

    public EscuelaAdapter(@NonNull Context context, @NonNull List<Escuela> items) {
        super(context, 0, items);
        mContext = context;
        mItems = items;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;
        Escuela medicalCenter = mItems.get(position);

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        } else {
            view = convertView;
        }

        TextView textView = (TextView) view.findViewById(android.R.id.text1);
        textView.setText(medicalCenter.getNombre());

        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getView(position, convertView, parent);
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public List<Escuela> getmItems() {
        return mItems;
    }

    public void setmItems(List<Escuela> mItems) {
        this.mItems = mItems;
    }
}
