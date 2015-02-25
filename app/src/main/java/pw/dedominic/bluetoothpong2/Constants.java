package pw.dedominic.bluetoothpong2;

/**
 * Created by qemu on 2/22/2015.
 */
public interface Constants
{
	// Game Activity Intent
	public final int DO_NOT_CONNECT = 0;
	public final int LISTEN_FOR_CONNECT = 1;
	public final int CONNECT_TO_HOST = 2;
	public final String CONNECT_TYPE = "CONNECT_TYPE";
	public final int GET_MAC_ADDR = 3;

	// device list activity
	public final String DEVICE_MAC = "DEVICE_MAC";

	// handler messages
	public final int DISCONNECTED = 0;
	public final int CONNECTED = 1;
	public final int READY = 2;
	public final int READING = 3;
}
