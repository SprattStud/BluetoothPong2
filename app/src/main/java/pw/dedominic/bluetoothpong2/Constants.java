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

	// handler messages - reading
	public final int DISCONNECTED = 0;
	public final int CONNECTED = 1;
	public final int READY = 2;
	public final int READING = 3;

	// handler messages - writing
	public final int GET_PADDLE_Y = 0;
	public final int GET_BALL_X = 1;
	public final int GET_BALL_Y = 2;

	// GAME LAYOUT CONSTANTS
	public static final int PLAYER_PADDLE_LEFT = 1;
	public static final int PLAYER_PADDLE_RIGHT =-1;
	public static final int FRAMES_PER_SECOND =60;
	public static final float PADDLE_SPACE_FRACT = 100;
	public static final float PADDLE_HALFWIDTH_FRACT = 100;
	public static final float PADDLE_HALFHEIGHT_FRACT = 8;
	public static final float BALL_RADIUS_FRACT = 100;
	public static final int PAINT_COLOR = 0xff666666; // grey

	// GAME FRAMES PER SECOND
	public static final int FPS = 60;
}
