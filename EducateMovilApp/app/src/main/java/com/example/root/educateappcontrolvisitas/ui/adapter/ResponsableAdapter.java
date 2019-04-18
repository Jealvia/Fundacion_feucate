package com.example.root.educateappcontrolvisitas.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.root.educateappcontrolvisitas.api.model.Escuela;
import com.example.root.educateappcontrolvisitas.api.model.Responsable;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ResponsableAdapter extends ArrayAdapter<Responsable> {

    private Context mContext;
    private List<Responsable> mItems;

    public ResponsableAdapter(@NonNull Context context, @NonNull List<Responsable> items) {
        super(context, 0, items);
        mContext = context;
        mItems = items;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;
        Responsable responsable = mItems.get(position);

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        } else {
            view = convertView;
        }

        TextView textView = (TextView) view.findViewById(android.R.id.text1);
        textView.setText(responsable.getResponsableNombreUsuario());

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

    public List<Responsable> getmItems() {
        return mItems;
    }

    public void setmItems(List<Responsable> mItems) {
        this.mItems = mItems;
    }
}
