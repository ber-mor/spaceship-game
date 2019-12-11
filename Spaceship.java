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
import java.util.ArrayList;

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
		private int speed = 1;
		private boolean up = false;
		private boolean down = false;
		private boolean right = false;
		private boolean left = false;
		ArrayList<Bullet> bullets = new ArrayList<>();

		public Ship(int x, int y, int width, int height){
			this.x = x;
			this.y = y; 
			this.width = width;
			this.height = height;
		}

		public void paint(Graphics g){
			g.fillRect(x,y,width,height);

			if(up)    y -= speed;
			if(down)  y += speed;
			if(right) x += speed;
			if(left)  x -= speed;
		}

		public void drawBullets(Graphics g){
			for (Bullet b : bullets){
				b.paint(g);
			}
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
			bullets.add(bullet);
			bullet.move(xf,yf);
		}
	}

	class Bullet{
		private int speed = 10;
		private int x, y, xf, yf;
		private double theta;
		private Ship ship;

		public Bullet(Ship ship){
			this.ship = ship;
			this.x = ship.getX() + ship.getWidth()/2;
			this.y = ship.getY() + ship.getHeight()/2; 	
		}

		public void move(int xf, int yf){
			this.xf = xf;
			this.yf = yf;
		}

		public void paint(Graphics g){
			g.fillOval(x, y, 10, 10);

			double dx = xf - x; 
			double dy = yf - y;

			theta = Math.atan2(dy, dx);	

			if(x+5 > xf && y+5 > yf){
				xf += (int)(5*(Math.cos(theta)));
				yf += (int)(5*(Math.sin(theta)));
			}

			x += (int)(5*(Math.cos(theta)));
			y += (int)(5*(Math.sin(theta)));
		}
	}

	class GamePanel extends JPanel{
		Ship ship = new Ship(100,100,30,30);
		
		public GamePanel(){
			addKeyBindings();

			addMouseListener(new MouseAdapter(){
				@Override
				public void mousePressed(MouseEvent e){
					ship.shoot(new Bullet(ship), e.getX(), e.getY());				
				}
			});
		}

		@Override
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			ship.paint(g); 
			ship.drawBullets(g);

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
				ship.moveUp();
			}, (evt)->{
				ship.stopUp();
			});		

			addKeyBinding(this, KeyEvent.VK_S, "go_down_p", "go_gown_r", (evt)->{
				ship.moveDown();
			}, (evt)->{
				ship.stopDown();
			});	

			addKeyBinding(this, KeyEvent.VK_D, "go_right_p", "go_right_r", (evt)->{
				ship.moveRight();
			}, (evt)->{
				ship.stopRight(); 
			});	

			addKeyBinding(this, KeyEvent.VK_A, "go_left_p", "go_left_r", (evt)->{
				ship.moveLeft();
			}, (evt)->{
				ship.stopLeft();
			});	
		}
	}

	public static void main(String[] args) {
		new Spaceship();
	}

}