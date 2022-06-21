import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.Image;

public class Ship{
	private int x = 10;
	private int y = 10;
	private int width = 50;
	private int height = 50;
	private int speed = 3;
	private boolean up = false;
	private boolean down = false;
	private boolean right = false;
	private boolean left = false;
	private ArrayList<Bullet> bullets;
	private int dashCount = 0;
	private boolean dash = false;
	private int dashSpeed = 3;
	private Image image;
	private Image imageDash;

	public Ship(int x, int y, int width, int height, ArrayList<Bullet> bullets){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.bullets = bullets;
	}

	public Ship(int x, int y, int width, int height, ArrayList<Bullet> bullets, String imageName){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.bullets = bullets;

		this.image = Toolkit.getDefaultToolkit().getImage(imageName);
		this.imageDash = Toolkit.getDefaultToolkit().getImage("unidash.png");
	}

	public void paint(Graphics g){
		if(image == null)
			g.fillOval(x,y,width,height);
		else
			if (dash)
				g.drawImage(imageDash,x,y,null);
			else
				g.drawImage(image,x,y,null);

		if(up)    y -= speed;
		if(down)  y += speed;
		if(right) x += speed;
		if(left)  x -= speed;

		if(dash) dashCount++;
		stopDash();
	}

	public void moveUp(){up = true;}
	public void moveDown(){down = true;}
	public void moveRight(){right = true;}
	public void moveLeft(){left = true;}

	public void stopUp(){up = false;}
	public void stopDown(){down = false;}
	public void stopRight(){right = false;}
	public void stopLeft(){left = false;}

	public int getX(){return x;}
	public int getY(){return y;}
	public int getWidth(){return width;}
	public int getHeight(){return height;}

	public void shoot(Bullet bullet, int xf, int yf){
		bullet.moveTo(xf,yf);
		bullets.add(bullet);
	}

	public void dash(){		
		dash = true;
		speed *= dashSpeed;
	} 

	public void stopDash(){
		if(dashCount == 10){
			speed /= dashSpeed;
			dash = false;
			dashCount = 0;
		}
	}

	public void stopX(int limit){
		x = limit; 
	}

	public void stopY(int limit){
		y = limit;
	}

	public void swingSword(){
		
	}
}