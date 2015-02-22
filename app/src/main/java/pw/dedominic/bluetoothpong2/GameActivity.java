package pw.dedominic.bluetoothpong2;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.security.MessageDigestSpi;


public class GameActivity extends ActionBarActivity implements View.OnClickListener
{
	// connection threads and communication handlers
	private BluetoothService mConnectionManager;
	private WriteHandler mWriteHandler;
	private ReadHandler mReadHandler;

	// debug textview
	private TextView mDebugView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		mDebugView = (TextView) findViewById(R.id.textView);
		int connect_type = getIntent().getExtras().getInt(Constants.CONNECT_TYPE);

		if (connect_type == Constants.CONNECT_TO_HOST)
		{
			mDebugView.setText("Connecting...");
		}
		else if (connect_type == Constants.LISTEN_FOR_CONNECT)
		{
			mDebugView.setText("Listening...");
		}
		else
		{
			mDebugView.setText("Doing Nothing...");
		}
	}

	public void onClick(View v)
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
			return;
		}
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu)
//	{
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.menu_game, menu);
//		return true;
//	}
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item)
//	{
//		// Handle action bar item clicks here. The action bar will
//		// automatically handle clicks on the Home/Up button, so long
//		// as you specify a parent activity in AndroidManifest.xml.
//		int id = item.getItemId();
//
//		//noinspection SimplifiableIfStatement
//		if (id == R.id.action_settings)
//		{
//			return true;
//		}
//
//		return super.onOptionsItemSelected(item);
//	}
}
