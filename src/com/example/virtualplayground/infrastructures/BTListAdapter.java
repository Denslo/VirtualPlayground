package com.example.virtualplayground.infrastructures;

import java.util.ArrayList;

import com.example.virtualplayground.R;
import com.example.virtualplayground.R.id;
import com.example.virtualplayground.R.layout;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class BTListAdapter extends ArrayAdapter<BluetoohItem> {
	private Context context;
	private ArrayList<BluetoohItem> items;

	public BTListAdapter(Context context, ArrayList<BluetoohItem> values) {
		super(context, R.layout.bluetooh_list_item, values);
		this.context = context;
		this.items = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.bluetooh_list_item, parent, false);
		TextView name = (TextView)rowView.findViewById(R.id.item_name);
		
		BluetoothDevice device = items.get(position).mDevice;
		name.setText(device.getName());
		TextView mac = (TextView)rowView.findViewById(R.id.item_address);
		mac.setText(device.getUuids()[0].getUuid().toString());
		return rowView;
	}
}
