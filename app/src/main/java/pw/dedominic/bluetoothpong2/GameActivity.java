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
	public long delay = 0;

	// button to start game
	private Button button;

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
		button = (Button) findViewById(R.id.start_game_button);
		button.setVisibility(View.GONE);
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
			String MAC_ADDR = getIntent().getStringExtra(Constants.DEVICE_MAC);
			BluetoothDevice device = mBtAdapter.getRemoteDevice(MAC_ADDR);
			mConnectionManager.join(device);
			mDebugView.setText("Connecting to: " + MAC_ADDR);
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

	public void onClick(View v)
	{
		delay = System.currentTimeMillis();
		mConnectionManager.write("This is a test".getBytes());
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
{				case Constants.READING:
					String message = new String((byte[])msg.obj, 0, msg.arg1);
					if (message.equals("ACK"))
					{
						delay = System.currentTimeMillis() - delay;
						mDebugView.setText(Long.toString(delay));
					}
					else
					{
						mDebugView.setText(message);
						mConnectionManager.write("ACK".getBytes());
					}

					break;
				case Constants.DISCONNECTED:
					finish();
				case Constants.CONNECTED:
					button.setVisibility(View.VISIBLE);
					mDebugView.setText("Connected");
					break;
				case Constants.READY:
					button.setVisibility(View.GONE);
					break;

			}
		}
	}

	@Override
	public void onStop()
	{
		super.onStop();
		mConnectionManager.killAll();
		this.finish();
	}

	@Override
	public void onPause()
	{
		super.onPause();
		mConnectionManager.killAll();
		this.finish();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		mConnectionManager.killAll();
		this.finish();
	}
}
