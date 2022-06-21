import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.Image;

public class Enemy{
	int x, y, width, height, xi, yi, xf, yf;
	int speed = 1;
	private Image image;

	public Enemy(int x, int y, int width, int height ){
		this.x = x;
		this.y = y; 
		this.width = width;
		this.height = height;
		this.xi = x;
		this.yi = y; 
	}

	public Enemy(int x, int y, int width, int height, String imageName){
		this.x = x;
		this.y = y; 
		this.width = width;
		this.height = height;
		this.xi = x;
		this.yi = y; 

		this.image = Toolkit.getDefaultToolkit().getImage(imageName);
	}

	public void paint(Graphics g){
		
		
	}	

	public void moveTo(Graphics g, int xf, int yf){
		g.drawImage(image,x,y,null);
		double dx = xf - xi;
		double dy = yf - yi;

		double adx = Math.abs(dx);
		double ady = Math.abs(dy);

		if(dx == 0) dx = 1;
		if(dy == 0) dy = 1;

		double theta = Math.atan2(dy, dx);
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
	}

}