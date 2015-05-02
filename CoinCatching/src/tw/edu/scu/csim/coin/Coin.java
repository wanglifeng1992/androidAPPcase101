package tw.edu.scu.csim.coin;

import java.util.Random;

import android.graphics.Bitmap;

public class Coin {
	public int		left;
	public int		top;
	public Bitmap	bitmap;
	public int		dollar;
	public int      aa;
	Random r = new Random();

	public Coin(Bitmap bitmap, int dollar, int left, int top,int aa) {
		this.bitmap = bitmap;
		this.dollar = dollar;
		this.left = left;
		this.top = top;
		this.aa=r.nextInt(6);
	}
}
