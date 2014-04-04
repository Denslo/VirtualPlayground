package com.example.virtualplayground.infrastructures;

import java.util.UUID;

import android.bluetooth.BluetoothDevice;

public class BluetoohItem {
	public BluetoothDevice mDevice = null;
	
	public BluetoohItem  (BluetoothDevice device) {
		mDevice = device;
	}
}
