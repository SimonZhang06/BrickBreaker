import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


import javax.swing.JPanel;
import javax.swing.Timer;

public class Game extends JPanel implements KeyListener, ActionListener {
	
	public Game() {
		map = new MapGenerator(brickrow,brickcol);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this);
		timer.start();
	}
	
	public int brickrow = 3+ (int)(Math.random()*3);
	public int brickcol = 5+ (int)(Math.random()*4);	
	private boolean play = false;
	private int score = 0;
	private int totalBricks = brickrow*brickcol;
	private Timer timer;
	private int delay = 8;
	private int playerX = 310;
	private int ballposX = 50 + (int)(Math.random()*200);
	private int ballposY = 300 + (int)(Math.random()*200);
	private int ballXDir  = -1;
	private int ballYDir  = -2;
	private MapGenerator map;
	
	public void paint(Graphics g) {
		//add background
		g.setColor(Color.black);
		g.fillRect(1,1,692,592);
		
		//add  borders
		g.setColor(Color.yellow);
		//add the border too lazy
		
		map.draw((Graphics2D) g);
		//paddle
		g.setColor(Color.green);
		g.fillRect(playerX, 550, 100, 8);
		
		g.setColor(Color.white);
		g.setFont(new Font("serif",Font.BOLD,25));
		g.drawString("" + score,  590, 30);
		
		
		
		//ball
		g.setColor(Color.YELLOW);
		g.fillOval(ballposX, ballposY, 20, 20);
		
		if(totalBricks==0) {
			play = false;
			ballXDir = 0;
			ballYDir = 0;
			g.setColor(Color.red);
			g.setFont(new Font("serif",Font.BOLD,30));
			g.drawString("Congratulations! You Won!", 190, 300);
			
			g.setFont(new Font("serif",Font.BOLD,20));
			g.drawString("press Enter to Restart" ,230, 350);
			
			
		}
		if(ballposY > 570) {
			play = false;
			ballXDir = 0;
			ballYDir = 0;
			g.setColor(Color.red);
			g.setFont(new Font("serif",Font.BOLD,30));
			g.drawString("Game Over, Score is " + score, 190, 300);
			
			g.setFont(new Font("serif",Font.BOLD,20));
			g.drawString("press Enter to Restart" ,230, 350);
		}
		g.dispose();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		
		if(play) {
			if(new Rectangle(ballposX, ballposY, 20, 20).intersects
					(new Rectangle(playerX, 550, 100, 8)))
				ballYDir = - ballYDir;
			ballposX += ballXDir ;
			ballposY += ballYDir;
		}
		A: for(int i = 0; i<map.map.length;i++) {
			for(int j = 0; j<map.map[0].length; j++) {
				if(map.map[i][j]>0) {
					int brickX = j*map.brickWidth + 80;
					int brickY = i*map.brickHeight + 50;
					int brickWidth  = map.brickWidth;
					int brickHeight = map.brickHeight;
					
					Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
					Rectangle ballRect = new Rectangle(ballposX,ballposY, 20, 20);
					Rectangle brickRect = rect;
					if(ballRect.intersects(brickRect)) {
						map.setBrickValue(0, i, j);
						totalBricks--;
						score += 5;
						if(ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width) {
							ballXDir = -ballXDir;
						}
						else {
							ballYDir = -ballYDir;
						}
						break A;
					}
				}
			}
		}
		if(ballposX < 0 ) ballXDir = -ballXDir;
		
		if(ballposY < 0 ) ballYDir = -ballYDir;
		
		if(ballposX > 670 ) ballXDir = -ballXDir;
		
		repaint();
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if(playerX >= 600) {
				playerX = 600;
			}
			else {
				moveRight();
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			if(playerX <= 10) {
				playerX = 10;
			}
			else {
				moveLeft();
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_ENTER){
			if(!play) {
				play = true;
				ballposX = 50 + (int)(Math.random()*200);
				ballposY = 300 + (int)(Math.random()*200);
				ballXDir = -1;
				ballYDir = -2;
				playerX = 310;
				score = 0;
				int x = 3+ (int)(Math.random()*3);
				int y = 5+ (int)(Math.random()*4);
				totalBricks = x*y;
				map = new MapGenerator(x,y);
				repaint();
			}
		}
	}
	
	public void moveRight() {
		play = true;
		playerX += 20;
	}
	public void moveLeft() {
		play = true;
		playerX -= 20;
	}
	
	@Override
	public void keyReleased(KeyEvent e) {}
	
}
