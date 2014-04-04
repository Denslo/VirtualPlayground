package com.example.virtualplayground.activites;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import com.example.virtualplayground.R;




import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class Client extends Activity {
	private BluetoothAdapter mBluetoothAdapter = null;
	private BluetoothDevice device = null;
	private UUID uuid = null;
	private ConnectThread trd = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.client);
		
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		Intent intent = getIntent();
		Bundle params = intent.getExtras();
		device = (BluetoothDevice)params.get("device");
		uuid = device.getUuids()[0].getUuid();
		
		Button sendBtn = (Button)findViewById(R.id.startListen);
		sendBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (trd == null) {
					trd = new ConnectThread(device);
					trd.start();
					v.setBackgroundColor(0xFF0000);
				} else {
					trd.cancel();
					trd = null;
					v.setBackgroundColor(0x00FF00);
				}
				
			}
		});
	}
	
	
	private class ConnectThread extends Thread {
	    private final BluetoothSocket mmSocket;
	    private final BluetoothDevice mmDevice;
	 
	    public ConnectThread(BluetoothDevice device) {
	        // Use a temporary object that is later assigned to mmSocket,
	        // because mmSocket is final
	        BluetoothSocket tmp = null;
	        mmDevice = device;
	 
	        // Get a BluetoothSocket to connect with the given BluetoothDevice
	        try {
	            // MY_UUID is the app's UUID string, also used by the server code
	            tmp = device.createRfcommSocketToServiceRecord(UUID.fromString(Host.uuid));
	        } catch (IOException e) { }
	        mmSocket = tmp;
	    }
	 
	    public void run() {
	        // Cancel discovery because it will slow down the connection
	        mBluetoothAdapter.cancelDiscovery();
	 
	        try {
	            // Connect the device through the socket. This will block
	            // until it succeeds or throws an exception
	            mmSocket.connect();
	        } catch (IOException connectException) {
	            // Unable to connect; close the socket and get out
	            try {
	                mmSocket.close();
	            } catch (IOException closeException) { }
	            return;
	        }
	 
	        // Do work to manage the connection (in a separate thread)
	        manageConnectedSocket(mmSocket);
	    }
	    
	    private void manageConnectedSocket(BluetoothSocket mSocket) {
	    	InputStream is;
	    	OutputStream os;
			try {
				is = mSocket.getInputStream();
				os = mSocket.getOutputStream();
				while(true) {
		    		byte[] buffer = new byte[8];
		    		is.read(buffer);
		    		os.write(buffer);
		    		
		    	}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
			
		}

	 
	    /** Will cancel an in-progress connection, and close the socket */
	    public void cancel() {
	        try {
	            mmSocket.close();
	        } catch (IOException e) { }
	    }
	}
}
