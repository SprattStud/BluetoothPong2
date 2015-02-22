package pw.dedominic.bluetoothpong2;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainMenuActivity extends ActionBarActivity implements View.OnClickListener
{
	public final String button1Txt = "Listen";
	public final String button2Txt = "Connect";
	public final String button3Txt = "Test";
	public String debug = "";

	private TextView mDebugText;


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);

		mDebugText = (TextView) findViewById(R.id.textView);

		Button button1 = (Button) findViewById(R.id.button);
		Button button2 = (Button) findViewById(R.id.button2);
		Button button3 = (Button) findViewById(R.id.button3);

		button1.setOnClickListener(this);
		button2.setOnClickListener(this);
		button3.setOnClickListener(this);
	}

	@Override
	public void onClick(View v)
	{
		Intent intent = new Intent(this, GameActivity.class);

		if (v.getId() == R.id.button)
		{
			debug = "Listening";
			intent.putExtra(Constants.CONNECT_TYPE, Constants.LISTEN_FOR_CONNECT);
		}
		else if (v.getId() == R.id.button2)
		{
			debug = "Connecting";
			intent.putExtra(Constants.CONNECT_TYPE, Constants.CONNECT_TO_HOST);
		}
		else
		{
			debug = "Test";
			intent.putExtra(Constants.CONNECT_TYPE, Constants.DO_NOT_CONNECT);
		}

		mDebugText.setText(debug);
		startActivity(intent);
	}

	//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
