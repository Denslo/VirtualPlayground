package com.example.virtualplayground.activites;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.example.virtualplayground.R;
import com.example.virtualplayground.R.id;
import com.example.virtualplayground.R.layout;
import com.example.virtualplayground.infrastructures.BTListAdapter;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.support.v4.widget.ListViewAutoScrollHelper;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class Host extends Activity {

	public static String uuid = "90e74ea0-bc15-11e3-a5e2-0800200c9a66";
	private BluetoothAdapter mBluetoothAdapter = null;
	AcceptThread trd = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.host);
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

		Button sendBtn = (Button) findViewById(R.id.startSending);
		sendBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (trd == null) {
					trd = new AcceptThread();
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

	private class AcceptThread extends Thread {
		private final BluetoothServerSocket mmServerSocket;

		public AcceptThread() {
			// Use a temporary object that is later assigned to mmServerSocket,
			// because mmServerSocket is final
			BluetoothServerSocket tmp = null;
			try {

				//Method getUuidsMethod = BluetoothAdapter.class.getDeclaredMethod("getUuids", null);

				//ParcelUuid[] uuids = (ParcelUuid[]) getUuidsMethod.invoke(mBluetoothAdapter, null);

				// MY_UUID is the app's UUID string, also used by the client code
				UUID a = UUID.fromString(uuid);
				tmp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord("VirtualPlayground", a);
			} catch (Exception e) {
			}
			mmServerSocket = tmp;
		}

		public void run() {
			BluetoothSocket socket = null;
			// Keep listening until exception occurs or a socket is returned
			while (true) {
				try {
					socket = mmServerSocket.accept();
				} catch (IOException e) {
					break;
				}
				// If a connection was accepted
				if (socket != null) {
					// Do work to manage the connection (in a separate thread)
					manageConnectedSocket(socket);
					try {
						mmServerSocket.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				}
			}
		}

		private void manageConnectedSocket(BluetoothSocket socket) {
			Log.d("CONNETION", "SDFD");

		}

		/** Will cancel the listening socket, and cause the thread to finish */
		public void cancel() {
			try {
				mmServerSocket.close();
			} catch (IOException e) {
			}
		}
	}

}
