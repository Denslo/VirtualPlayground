package com.example.virtualplayground.activites;

import java.util.ArrayList;
import java.util.Set;

import com.example.virtualplayground.R;
import com.example.virtualplayground.R.id;
import com.example.virtualplayground.R.layout;
import com.example.virtualplayground.infrastructures.BTListAdapter;
import com.example.virtualplayground.infrastructures.BluetoohItem;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.widget.ListViewAutoScrollHelper;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;


public class MainActivity extends Activity {

	private ListView userslist = null;
	private ArrayList<BluetoohItem> data = null;
	private MainActivity thisReference = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		thisReference = this;
		BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if (mBluetoothAdapter == null) {
		    // Device does not support Bluetooth
		}
		
		if (!mBluetoothAdapter.isEnabled()) {
		    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		    startActivityForResult(enableBtIntent, 1);
		}
		
		data = new ArrayList<BluetoohItem>();

		
		// Register the BroadcastReceiver
		/*
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		registerReceiver(mReceiver, filter);
		
		Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
		discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
		startActivity(discoverableIntent);
		
		mBluetoothAdapter.startDiscovery();
		*/
		
		
		Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
		// If there are paired devices
		if (pairedDevices.size() > 0) {
		    // Loop through paired devices
		    for (BluetoothDevice device : pairedDevices) {
		        // Add the name and address to an array adapter to show in a ListView
		    	data.add(new BluetoohItem(device));
		    }
		}
		
		userslist = (ListView)findViewById(R.id.bluetoothList);
		BTListAdapter adapter = new BTListAdapter(this, data);
		userslist.setAdapter(adapter);
		userslist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				CheckBox cb = (CheckBox)findViewById(R.id.ishostCB);
				
				Intent myIntent = null;
				if (cb.isChecked()) {
					myIntent = new Intent(thisReference, Host.class);
				} else {
					myIntent = new Intent(thisReference, Client.class);
				
				}
				
				myIntent.putExtra("device", data.get(position).mDevice); 
				startActivity(myIntent);
				
			}
		});
	}
	
	// Create a BroadcastReceiver for ACTION_FOUND
//	private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
//	    public void onReceive(Context context, Intent intent) {
//	        String action = intent.getAction();
//	        // When discovery finds a device
//	        if (BluetoothDevice.ACTION_FOUND.equals(action)) {
//	            // Get the BluetoothDevice object from the Intent
//	            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
//	            // Add the name and address to an array adapter to show in a ListView
//	           Log.d("LOG",device.getName() + "\n" + device.getAddress());
//	        }
//	        
//	        ((SimpleAdapter) userslist.getAdapter()).notifyDataSetChanged(); 
//	    }
//	};
}
