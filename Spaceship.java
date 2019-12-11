import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import javax.swing.InputMap;
import javax.swing.ActionMap;
import javax.swing.KeyStroke;
import javax.swing.JComponent;
import javax.swing.AbstractAction;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.Color;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.MouseInfo;


public class Spaceship{
	public Spaceship(){
		initComponents();
	}

	private void initComponents(){
		JFrame frame = new JFrame("AAAAHHHH");
		frame.setSize(1000, 700);
		GamePanel panel = new GamePanel();
		frame.add(panel); 
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.requestFocus();
	}

	class Ship{
		private int x = 10;
		private int y = 10;
		private int width = 20;
		private int height = 20;
		private int speed = 2;
		private boolean up = false;
		private boolean down = false;
		private boolean right = false;
		private boolean left = false;

		public Ship(int x, int y, int width, int height){
			this.x = x;
			this.y = y; 
			this.width = width;
			this.height = height;
		}

		public void draw(Graphics g){
			g.fillRect(x,y,width,height);
		}

		public void moveUp(){up = true;}
		public void moveDown(){down = true;}
		public void moveRight(){right = true;}
		public void moveLeft(){left = true;}

		public void stopUp(){up = false;}
		public void stopDown(){down = false;}
		public void stopRight(){right = false;}
		public void stopLeft(){left = false;}

		public void move(){
			if(up)    y -= speed;
			if(down)  y += speed;
			if(right) x += speed;
			if(left)  x -= speed;
		}

		public int getX(){return this.x;}
		public int getY(){return this.y;}

		public void shoot(Bullet bullet){

		}
	}

	class Bullet{
		private int speed = 10;
		private int x, y;
		private double theta;
		private Ship ship;

		public Bullet(int x, int y, Ship ship){
			this.x = x;
			this.y = y; 
			this.ship = ship;
		}

		public Bullet(int x, int y){
			this.x = x;
			this.y = y; 
		}
	}

	class GamePanel extends JPanel{
		int shipX = 400; 
		int shipY = 300;
		int shipWhidht = 30;
		int shipHeight = 30;
		int shipSpeed = 1;
		boolean up = false;
		boolean down = false;
		boolean right = false;
		boolean left = false;
		int pX, pY;
		double theta;
		int xf, yf;
		Ship s = new Ship(100,100,20,20);
		
		public GamePanel(){
			addKeyBindings();

			addMouseListener(new MouseAdapter(){
				@Override
				public void mousePressed(MouseEvent e){
					pX = shipX + shipWhidht/2 ; 
					pY = shipY + shipHeight/2 ;

					xf = e.getX();
					yf = e.getY();				
				}
			});
		}

		public void moveBullet(){	
			double dx = xf - pX; 
			double dy = yf - pY;

			theta = Math.atan2(dy, dx);	
			if(pX+5 > xf && pY+5 > yf){
				xf += (int)(5*(Math.cos(theta)));
				yf += (int)(5*(Math.sin(theta)));
			}
			pX += (int)(5*(Math.cos(theta)));
			pY += (int)(5*(Math.sin(theta)));
		}

		@Override
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			g.fillRect(shipX, shipY, shipWhidht, shipHeight); //Ship.draw()

			//Ship.move
			if(up)    shipY -= shipSpeed;
			if(down)  shipY += shipSpeed;
			if(right) shipX += shipSpeed;
			if(left)  shipX -= shipSpeed;

			g.fillOval(pX, pY, 10, 10);
			moveBullet();

			repaint();
			try{Thread.sleep(1);}catch(Exception e){}
		}

		public void addKeyBinding(JComponent comp, int keyCode, String id1, String id2, 
				ActionListener actionPressed, ActionListener actionReleased){
			InputMap im = comp.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
			ActionMap ap = comp.getActionMap();

			im.put(KeyStroke.getKeyStroke(keyCode, 0, false), id1);
			im.put(KeyStroke.getKeyStroke(keyCode, 0, true), id2);

			ap.put(id1, new AbstractAction(){
				@Override
				public void actionPerformed(ActionEvent e){
					actionPressed.actionPerformed(e);
				}
			});

			ap.put(id2, new AbstractAction(){
				@Override
				public void actionPerformed(ActionEvent e){
					actionReleased.actionPerformed(e);
				}
			});
		}

		public void addKeyBindings(){
			addKeyBinding(this, KeyEvent.VK_W, "go_up_p", "go_up_r", (evt)->{
				up = true;
			}, (evt)->{
				up = false;
			});		

			addKeyBinding(this, KeyEvent.VK_S, "go_down_p", "go_gown_r", (evt)->{
				down = true;
			}, (evt)->{
				down = false;
			});	

			addKeyBinding(this, KeyEvent.VK_D, "go_right_p", "go_right_r", (evt)->{
				right = true;
			}, (evt)->{
				right = false; 
			});	

			addKeyBinding(this, KeyEvent.VK_A, "go_left_p", "go_left_r", (evt)->{
				left = true;
			}, (evt)->{
				left = false;
			});	
		}
	}

	public static void main(String[] args) {
		new Spaceship();
	}

}