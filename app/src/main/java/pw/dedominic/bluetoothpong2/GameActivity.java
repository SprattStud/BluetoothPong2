package pw.dedominic.bluetoothpong2;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameActivity extends Activity implements View.OnClickListener, SensorEventListener
{
	// lets the game know that sensor and view is running
	private boolean isReady = false;

	// button to start game
	private Button button;

	// bluetooth adapter & connection stats
	private BluetoothAdapter mBtAdapter;
	private int IS_CONNECT = 0;

	// sensor stuff
	private SensorManager mSenMng;
	private Sensor accelerometer;

	// connection threads and communication handlers
	private BluetoothService mConnectionManager;
	private WriteHandler mWriteHandler = new WriteHandler();
	private ReadHandler mReadHandler = new ReadHandler();

	// debug textview
	private TextView mDebugView;

	// GameView
	private PongView mPongView;

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
			registerSensorListener();
		}
	}

	public void onClick(View v)
	{
		button.setVisibility(View.GONE);
		mConnectionManager.write("READY? fdjkafljdkas;l".getBytes());
		registerSensorListener();
		initGameView(true);
	}

	public void registerSensorListener()
	{
		mSenMng = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		accelerometer = mSenMng.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		mSenMng.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
	}

	public void initGameView(boolean button_pressed)
	{
		return;
	}

	@Override
	public void onSensorChanged(SensorEvent e)
	{
		if (e.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
		{
			if (e.values[0] > 1 || e.values[0] < -1)
			{
				mConnectionManager.write(Float.toString(e.values[0]).getBytes());
			}
		}

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
				case Constants.READING:
					String message = new String((byte[]) msg.obj, 0, msg.arg1);
					if (message.contains("READY?"))
					{
						button.setVisibility(View.GONE);
						registerSensorListener();
						initGameView(false);
					}
					else
					{
						GameActivity.this.mDebugView.setText(message);
					}
					break;
				case Constants.DISCONNECTED:
					if (mSenMng != null)
					{
						mSenMng.unregisterListener(GameActivity.this);
					}
					finish();
					mDebugView.setText("Disconnected");
					break;
				case Constants.CONNECTED:
					button.setVisibility(View.VISIBLE);
					mDebugView.setText("Connected");
					break;
			}
		}
	}

	@Override
	protected void onStop()
	{
		super.onStop();
		killGame();
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		killGame();
	}

	@Override
	public void onBackPressed()
	{
		super.onBackPressed();
		killGame();
	}

	public void killGame()
	{
		if (isReady)
		{
			mSenMng.unregisterListener(this);
			mSenMng = null;
		}
		mConnectionManager.killAll();
	}
}
