package main.java.pw.dedominic.bluetoothpong2;

/**
 * Created by qemu on 2/22/2015.
 * Edited by Spratt on 4/19/2015
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

	// handler messages - reading
	public final int DISCONNECTED = 0;
	public final int CONNECTED = 1;
	public final int READY = 'r';
	public final int SHELLX_READ = 'x';
	public final int SHELLY_READ = 'y';
	public final int SHELL_ANG_READ = 'a';
	public final int ARTILLERY_ANG_READ = 'p';
	public final int GAME_OVER_READ = 'g';

	// handler messages - writing
	public final int GAME_OVER = -1;
	public final int SEND_GAME_STATE = 0;
	public final int SEND_ARTILLERY_ANGLE = 1;
	public final int SEND_SHELL_ANGLE = 2;

	// GAME LAYOUT CONSTANTS - CHANGED VALUES AS THE ARTILLERY JUST SHIFT VIA A ROOT
	// THEY DON'T REALLY NEED TO MOVE AT ALL FOR CURRENT VERSION
	public static final float ARTILLERY_SPACE_FRACT = 100;
	public static final float ARTILLERY_WIDTH_FRACT = 200;
	public static final float ARTILLERY_HEIGHT_FRACT = 16;
	public static final float SHELL_RADIUS_FRACT = 100;
	public static final int PAINT_COLOR = 0xff666666; // grey

	// GAME FRAMES PER SECOND
	public static final int FPS = 60;

	// GAME OTHER
	public static final int SHELL_ROTATE_ACCEL = 2;
}
