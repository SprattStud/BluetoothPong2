package pw.dedominic.bluetoothpong2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;
import java.util.jar.Attributes;

/**
 * Created by qemu on 2/21/2015.
 */
public class PongView extends View
{
	private final Random random = new Random();

	// to communicate back to game activity
	private Handler mHandler;

	// to redraw frame every 15 milliseconds
	private DrawHandler mDrawHandler = new DrawHandler();

	// booleans that control game initialization
	// this one also defines side of the player paddle
	private boolean isStateController = false; // who does the game logic?
	private boolean isGameRunning = false;

	// game pieces
	private Ball ball;
	private Paddle paddle_player;
	private Paddle paddle_enemy;
	private Paint color;


	public PongView(Context context, AttributeSet attrs)
	{
		super(context, attrs);

		color = new Paint();
		color.setColor(Constants.PAINT_COLOR);
	}

	private void newGame()
	{

	}

	public void setConstants(Handler wHandler, boolean stateController)
	{
		mHandler = wHandler;
		isStateController = stateController;
	}

	private void setLayoutConstants()
	{

	}

	private void checkBallCollision()
	{

	}

	public void update()
	{
		if (getHeight() == 0 || getWidth() == 0)
		{
			mDrawHandler.sleep(1);
			return;
		}

		if (!isGameRunning)
		{
			setLayoutConstants();
			newGame();
			mDrawHandler.sleep(1);
			return;
		}

		if (isStateController)
		{
			checkBallCollision();
		}

		mDrawHandler.sleep(1000 / Constants.FPS);
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);

		if (!isGameRunning)
		{
			return;
		}

		ball.draw(canvas);
		paddle_enemy.draw(canvas);
		paddle_player.draw(canvas);
	}

	private class DrawHandler extends Handler
	{
		@Override
		public void handleMessage(Message msg)
		{
			PongView.this.update();
			PongView.this.invalidate();
		}

		public void sleep(long time)
		{
			removeMessages(0);
			sendMessageDelayed(obtainMessage(0), time);
		}
	}

	private class Ball
	{
		//defines ball size
		private float radius;

		// x and y coords
		private float x;
		private float y;

		// amount balls move along x and y every frame
		private float x_vel;
		private float y_vel;

		public Ball(float x_, float y_, float rad)
		{
			x = x_;
			y = y_;
			radius = rad;
		}

		public void setVel(float ang, float speed, float ratio)
		{
			x_vel = (float) Math.cos(ang) * speed * ratio;
			y_vel = (float) Math.sin(ang) * speed;
		}

		public double getX_vel()
		{
			return x_vel;
		}

		public double getY_vel()
		{
			return y_vel;
		}

		public double getLeft()
		{
			return x-radius;
		}

		public double getRight()
		{
			return x+radius;
		}

		public double getTop()
		{
			return y-radius;
		}

		public double getBottom()
		{
			return y+radius;
		}

		// when bounces off ceiling/floor
		public void yDeflect()
		{
			y_vel = -y_vel;
		}

		// when bounces off paddle
		public void xDeflect()
		{
			//speed_mult += .1;
			//x_vel *= speed_mult;
			x_vel = -x_vel;
		}

		public void draw(Canvas screen)
		{
			screen.drawCircle(x, y, radius, color);
			x += x_vel;
			y += y_vel;
		}
	}

	private class Paddle
	{
		private float x;
		private float y;
		private float paddle_half_width;
		private float paddle_half_height;
		private float paddle_vel;

		public Paddle(float pos1, float pos2, float h, float w)
		{
			x = pos1;
			y = pos2;
			paddle_half_width = w;
			paddle_half_height = h;
			paddle_vel = 0;
		}

		public void setY(float newY)
		{
			y = newY;
		}

		public void paddleMove(float tilt_val)
		{
			paddle_vel = tilt_val;
		}

		public float getY()
		{
			return y;
		}

		public float getX()
		{
			return x;
		}

		public float getBottom()
		{
			return y + paddle_half_height;
		}

		public float getTop()
		{
			return y - paddle_half_height;
		}

		public float getLeft()
		{
			return x - paddle_half_width;
		}

		public float getRight()
		{
			return x + paddle_half_width;
		}

		public void draw(Canvas screen)
		{
			screen.drawRect(getLeft(),
				getTop(),
				getRight(),
				getBottom(),
				color);

			y += paddle_vel;
		}
	}
}
