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
import javax.swing.SwingUtilities;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.Image;

public class Spaceship{
	public static final long serialVersionUID = 1L;

	public Spaceship(){
		initComponents();
	}

	private void initComponents(){
		JFrame frame = new JFrame("AAAAHHHH");
		frame.setSize(500,500);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GamePanel panel = new GamePanel();
		frame.add(panel);
		// frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.requestFocus();
	}

	class GamePanel extends JPanel{
		public static final long serialVersionUID = 1L;
		ArrayList<Bullet> bullets = new ArrayList<>();
		ArrayList<Enemy> enemies = new ArrayList<>();
		Ship ship = new Ship(100,100,50,50,bullets, "uni.png");
		private int bgMove = 0;

		public GamePanel(){
			setBackground(Color.BLACK);
			addKeyBindings();

			addMouseListener(new MouseAdapter(){
				@Override
				public void mousePressed(MouseEvent e){
					if(SwingUtilities.isLeftMouseButton(e))
						ship.shoot(new Bullet(ship,"heart.png"), e.getX(), e.getY());
					if(SwingUtilities.isRightMouseButton(e))
						System.out.println("Click derecho");
				}
			});
		}

		@Override
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			g.drawImage(Toolkit.getDefaultToolkit().getImage("clouds.png"),0,0,null);
			drawBackground(g);
			bgMove-=2;
			g.setColor(Color.WHITE);
			ship.paint(g);
			drawBullets(g);
			checkBorderCoalition();
			// drawEnemies(g);
			repaint();
			try{Thread.sleep(4);}catch(Exception e){}
		}

		public void drawBackground(Graphics g){
			g.setColor(new Color(255,255,255));
			int vLines = bgMove; 
			int hLines = 0; 

			// while(vLines < getWidth()){
			// 	g.drawLine(vLines, 0, vLines, getHeight());
			// 	vLines += 40;
			// }

			// while(hLines < getHeight()){
			// 	g.drawLine(0, hLines, getWidth(), hLines);
			// 	hLines += 40;
			// }

			/////////////////////////////////////////////////

			while(vLines < getWidth()){
				g.setColor(new Color(255,255,255));
				g.fillRect(vLines, 0, 20,20);
				g.fillRect(vLines, getHeight()-20, 20,20);
				g.setColor(new Color(255, 194, 194));
				g.fillRect(vLines+2, 0+2, 16,16);
				g.fillRect(vLines+2, getHeight()-20+2, 16,16);
				vLines += 20;
			}
		}

		public void drawBullets(Graphics g){
			// If the current element is deleted, the reference used 
			// for iteration gets deleted, so it crashes
			// for (Bullet b : bullets){
			// 	b.paint(g);
			// 	if (b.getX() < -b.getSize() || b.getX()>getWidth() ||
			// 		b.getY() < -b.getSize() || b.getY()>getHeight())
			// 		bullets.remove(0);
			// }c

			// Removes bullets out of screen from ArrayList
			// Using indexes instead, the current element can be deleted
			for (int i=0; i<bullets.size(); i++){
				Bullet b = bullets.get(i);
				b.paint(g);
				if (b.getX() < -b.getSize() || b.getX()>getWidth() ||
					b.getY() < -b.getSize() || b.getY()>getHeight())
					bullets.remove(0);
			}
		}

		public void drawEnemies(Graphics g){
			// if(enemies.size()<=5){
				for (int i=0; i<5; i++){
					enemies.add(new Enemy(getWidth(),(int)(Math.random()*getHeight()),50,50,"goat.png"));
				}
				// try{
					
				// Thread.sleep(1000);
				// }catch(Exception e){

				// }

				for (Enemy e : enemies) e.moveTo(g, ship.getX(), ship.getY());
			// }
		}

		public void checkBorderCoalition(){
			if(ship.getX() >= getWidth()-ship.getWidth()) ship.stopX(getWidth()-ship.getWidth());
			if(ship.getX() <= 0) ship.stopX(0);
			if(ship.getY() >= getHeight()-ship.getHeight()) ship.stopY(getHeight()-ship.getHeight());
			if(ship.getY() <= 0) ship.stopY(0);
		}

		public void addKeyBinding(JComponent comp, int keyCode, String id1, String id2,
				ActionListener actionPressed, ActionListener actionReleased){
			InputMap im = comp.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
			ActionMap ap = comp.getActionMap();

			im.put(KeyStroke.getKeyStroke(keyCode, 0, false), id1);
			im.put(KeyStroke.getKeyStroke(keyCode, 0, true), id2);

			ap.put(id1, new AbstractAction(){
				public static final long serialVersionUID = 1L;
				@Override
				public void actionPerformed(ActionEvent e){
					actionPressed.actionPerformed(e);
				}
			});

			ap.put(id2, new AbstractAction(){
				public static final long serialVersionUID = 1L;
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

			addKeyBinding(this, KeyEvent.VK_SPACE, "dash_p", "dash_r", (evt)->{
				ship.dash();
			}, (evt)->{
				
			});

			addKeyBinding(this, KeyEvent.VK_ENTER, "shoot_p", "shoot_r", (evt)->{
				ship.shoot(new Bullet(ship,"heart.png"), getWidth()-20, ship.getY());
			}, (evt)->{
				
			});
		}
	}

	public static void main(String[] args) {
		new Spaceship();
	}

}
