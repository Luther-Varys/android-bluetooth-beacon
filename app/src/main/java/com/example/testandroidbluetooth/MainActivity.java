package com.example.testandroidbluetooth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private BluetoothAdapter mBluetoothAdapter;
    private Handler mHandler;
    private static final int REQUEST_ENABLE_BT = 1;
    // Stops scanning after 10 seconds.
    private static final long SCAN_PERIOD = 10000;


    private BluetoothAdapter.LeScanCallback mLeScanCallback =
            new BluetoothAdapter.LeScanCallback() {

                @Override
                public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.i("*** ZR ***: ", "adepter found");
                            //mLeDeviceListAdapter.addDevice(device);
                            //mLeDeviceListAdapter.notifyDataSetChanged();
                        }
                    });
                }
            };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHandler = new Handler();

        // Use this check to determine whether BLE is supported on the device.  Then you can
        // selectively disable BLE-related features.
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "BLE NOT supported", Toast.LENGTH_LONG).show();
            finish();
        }
        else{
            Toast.makeText(this, "BLE is supported", Toast.LENGTH_LONG).show();
        }




        // Initializes a Bluetooth adapter.  For API level 18 and above, get a reference to
        // BluetoothAdapter through BluetoothManager.
        final BluetoothManager bluetoothManager = (BluetoothManager)getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();

        // Ensures Bluetooth is available on the device and it is enabled. If not,
        // displays a dialog requesting user permission to enable Bluetooth.
        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

        // Checks if Bluetooth is supported on the device.
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "R.string.error_bluetooth_not_supported", Toast.LENGTH_LONG).show();
            finish();
        }
        else{
            Toast.makeText(this, "R.string.error_bluetooth is supported", Toast.LENGTH_LONG).show();
        }

//        mBluetoothAdapter.startLeScan(mLeScanCallback);
        BluetoothLeScanner bluetoothLeScanner = mBluetoothAdapter.getBluetoothLeScanner();





        // Stops scanning after a pre-defined scan period.
//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                bluetoothAdapter.stopLeScan(leScanCallback);
//            }
//        }, SCAN_PERIOD);
//
//        mScanning = true;
        mBluetoothAdapter.startLeScan(null);







        bluetoothLeScanner.startScan(new ScanCallback() {
            @Override
            public void onScanResult(int callbackType, ScanResult result) {
                super.onScanResult(callbackType, result);

                String s = "\nRssi : "+result.getRssi()+"" +
                        "\nName (Get Device) : "+result.getDevice().getName()+"" +
                        "\nBytes"+result.getScanRecord().getBytes()+"" +
                        "\nGet Device : " + result.getDevice()+"" +
                        "\nAddress : "+result.getDevice().getAddress()+"" +
                        "\nService UUIds : "+result.getScanRecord().getServiceUuids().get(0)+"" +       //Unique
                        "\nName (Scan Record) : "+result.getScanRecord().getDeviceName()+"" +
                        "\nUuids device : "+result.getDevice().getUuids()+"" +
                        "\nDescribe contents : "+result.describeContents();

                //This will show you all the data in logs.
                Log.e("All Data",s);



            }

            @Override
            public void onBatchScanResults(List<ScanResult> results) {
                super.onBatchScanResults(results);
            }

            @Override
            public void onScanFailed(int errorCode) {
                super.onScanFailed(errorCode);
            }
        });


    }



//    private void scanLeDevice(final boolean enable) {
//        if (enable) {
//            // Stops scanning after a pre-defined scan period.
//            mHandler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    mScanning = false;
//                    mBluetoothAdapter.stopLeScan(mLeScanCallback);
//                    invalidateOptionsMenu();
//                }
//            }, 10000);
//
//            mScanning = true;
//            mBluetoothAdapter.startLeScan(mLeScanCallback);
//        } else {
//            mScanning = false;
//            mBluetoothAdapter.stopLeScan(mLeScanCallback);
//        }
//        invalidateOptionsMenu();
//    }





}




//public class MyTest{
//    BluetoothAdapter bluetoothAdapter;
//    BluetoothLeScanner bluetoothLeScanner;
//
//
//
//    bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//    bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();
//
//
//
//    bluetoothLeScanner.startScan(new ScanCallback() {
//        @Override
//        public void onScanResult(int callbackType, ScanResult result) {
//            super.onScanResult(callbackType, result);
//
//            String s = "\nRssi : "+result.getRssi()+"" +
//                    "\nName (Get Device) : "+result.getDevice().getName()+"" +
//                    "\nBytes"+result.getScanRecord().getBytes()+"" +
//                    "\nGet Device : " + result.getDevice()+"" +
//                    "\nAddress : "+result.getDevice().getAddress()+"" +
//                    "\nService UUIds : "+result.getScanRecord().getServiceUuids().get(0)+"" +       //Unique
//                    "\nName (Scan Record) : "+result.getScanRecord().getDeviceName()+"" +
//                    "\nUuids device : "+result.getDevice().getUuids()+"" +
//                    "\nDescribe contents : "+result.describeContents();
//
//            //This will show you all the data in logs.
//            Log.e("All Data",s);
//
//
//
//        }
//
//        @Override
//        public void onBatchScanResults(List<ScanResult> results) {
//            super.onBatchScanResults(results);
//        }
//
//        @Override
//        public void onScanFailed(int errorCode) {
//            super.onScanFailed(errorCode);
//        }
//    });
//
//}
//
//}






///**
// * Activity for scanning and displaying available BLE devices.
// */
//public class DeviceScanActivity extends ListActivity {
//
//    private BluetoothAdapter bluetoothAdapter;
//    private boolean mScanning;
//    private Handler handler;
//
//    // Stops scanning after 10 seconds.
//    private static final long SCAN_PERIOD = 10000;
//    private void scanLeDevice(final boolean enable) {
//        if (enable) {
//            // Stops scanning after a pre-defined scan period.
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    mScanning = false;
//                    bluetoothAdapter.stopLeScan(leScanCallback);
//                }
//            }, SCAN_PERIOD);
//
//            mScanning = true;
//            bluetoothAdapter.startLeScan(leScanCallback);
//        } else {
//            mScanning = false;
//            bluetoothAdapter.stopLeScan(leScanCallback);
//        }
//    }
//}
