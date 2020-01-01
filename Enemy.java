import java.awt.Graphics;

public class Enemy{
	int x, y, width, height;
	int speed = 2;

	public Enemy(int x, int y, int width, int height){
		this.x = x;
		this.y = y; 
		this.width = width;
		this.height = height;
	}

	public void paint(Graphics g){
		g.fillRect(x,y,width,height);

		
	}	

}