import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.Image;

public class Bullet{
	private int speed = 5;
	private int xi, yi, xf, yf, x, y;
	private double theta;
	private int size = 10;
	private Ship ship;
	private Image image;

	public Bullet(Ship ship, String imageName){
		this.ship = ship;
		this.xi = (int) ship.getX() + ship.getWidth()/2 - size/2;
		this.yi = (int) ship.getY() + ship.getHeight()/2 - size/2;
		this.x = xi;
		this.y = yi;

		this.image = Toolkit.getDefaultToolkit().getImage(imageName);
	}

	public Bullet(Ship ship){
		this.ship = ship;
		this.xi = (int) ship.getX() + ship.getWidth()/2 - size/2;
		this.yi = (int) ship.getY() + ship.getHeight()/2 - size/2;
		this.x = xi;
		this.y = yi;
	}

	public void moveTo(int xf, int yf){
		this.xf = xf;
		this.yf = yf;
	}

	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public int getSize(){
		return size;
	}

	public void paint(Graphics g){
		if (image == null)
			g.fillOval(x, y, size, size);
		else 
			g.drawImage(image,x,y,null);

		double dx = xf - xi;
		double dy = yf - yi;

		double adx = Math.abs(dx);
		double ady = Math.abs(dy);

		if(dx == 0) dx = 1;
		if(dy == 0) dy = 1;

		theta = Math.atan2(dy, dx);
		speed = adx >= ady?Math.abs((int)(10*(Math.cos(theta)))):Math.abs((int)(10*(Math.sin(theta))));

		if(dx >= 0 && dy > 0)
		if(adx>=ady){
			y = Math.round((yi + (x-xi)*(yf-yi)/(xf-xi)));
			x+=speed; 
		}else{
			x = Math.round((xi + (y-yi)*(xf-xi)/(yf-yi)));
			y+=speed;
		}
		else if (dx >= 0 && dy < 0)
		if(adx>=ady){
			y = Math.round((yi + (x-xi)*(yf-yi)/(xf-xi)));
			x+=speed;
		}else{
			x = Math.round((xi + (y-yi)*(xf-xi)/(yf-yi)));
			y-=speed;
		}else if (dx < 0 && dy >= 0)
		if(adx>=ady){
			y = Math.round((yi + (x-xi)*(yf-yi)/(xf-xi)));
			x-=speed;
		}else{
			x = Math.round((xi + (y-yi)*(xf-xi)/(yf-yi)));
			y+=speed;
		}else if (dx < 0 && dy < 0)
		if(adx>=ady){
			y = Math.round((yi + (x-xi)*(yf-yi)/(xf-xi)));
			x-=speed;
		}else{
			x = Math.round((xi + (y-yi)*(xf-xi)/(yf-yi)));
			y-=speed;
		}

		// theta = Math.atan2(dy, dx);
		//
		// if(x+5 > xf && y+5 > yf){
		// 	xf += (int)(5*(Math.cos(theta)));
		// 	yf += (int)(5*(Math.sin(theta)));
		// }
		//
		// x += (int)(5*(Math.cos(theta)));
		// y += (int)(5*(Math.sin(theta)));
	}
}