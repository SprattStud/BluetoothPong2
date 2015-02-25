package pw.dedominic.bluetoothpong2;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameActivity extends ActionBarActivity implements View.OnClickListener, SensorEventListener
{
	// bluetooth adapter & connection stats
	private BluetoothAdapter mBtAdapter;
	private int IS_CONNECT = 0;

	// connection threads and communication handlers
	private BluetoothService mConnectionManager;
	private WriteHandler mWriteHandler = new WriteHandler();
	private ReadHandler mReadHandler = new ReadHandler();

	// debug textview
	private TextView mDebugView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		mDebugView = (TextView) findViewById(R.id.textView);
		int connect_type = getIntent().getExtras().getInt(Constants.CONNECT_TYPE);

		// start game button, hidden at start
		Button button = (Button) findViewById(R.id.start_game_button);
		button.setOnClickListener(this);

		mBtAdapter = BluetoothAdapter.getDefaultAdapter();
		if (!mBtAdapter.isEnabled())
		{
			int REQUEST_ENABLE_BT = 1;
			Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
		}

		mConnectionManager = new BluetoothService(mReadHandler);

		if (connect_type == Constants.CONNECT_TO_HOST)
		{
			mDebugView.setText("Connecting...");
			Intent intent = new Intent(this, BtConnectActivity.class);
			startActivityForResult(intent, Constants.GET_MAC_ADDR);
		}
		else if (connect_type == Constants.LISTEN_FOR_CONNECT)
		{
			mDebugView.setText("Listening...");
			mConnectionManager.listen();
		}
		else
		{
			mDebugView.setText("Doing Nothing...");
		}
	}

	public void onActivityResult(int req_code, int result_code, Intent data)
	{
		if (result_code == Activity.RESULT_OK && req_code == Constants.GET_MAC_ADDR)
		{
			String MAC_ADDR = data.getStringExtra(Constants.DEVICE_MAC);
			mDebugView.setText(MAC_ADDR);
			BluetoothDevice device = mBtAdapter.getRemoteDevice(MAC_ADDR);
			mConnectionManager.join(device);
		}
	}

	public void onClick(View v)
	{
		return;
	}

	@Override
	public void onSensorChanged(SensorEvent e)
	{
		return;
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy)
	{
		return;
	}

	private class WriteHandler extends Handler
	{
		@Override
		public void handleMessage(Message msg)
		{
			return;
		}
	}

	private class ReadHandler extends Handler
	{
		@Override
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
				case Constants.DISCONNECTED:
					setResult(Activity.RESULT_CANCELED);
					finish();
				case Constants.CONNECTED:
					findViewById(R.id.start_game_button).setVisibility(View.VISIBLE);
				case Constants.READY:
					findViewById(R.id.start_game_button).setVisibility(View.GONE);
			}
		}
	}
}
