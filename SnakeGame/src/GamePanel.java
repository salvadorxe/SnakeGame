import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener{
	static final int SCREEN_WIDTH = 1300;
	static final int SCREEN_HEIGHT = 700;
	static final int UNIT_SIZE = 50;
	static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT)/(UNIT_SIZE*UNIT_SIZE);
	static final int DELAY = 75;
	final int x[] = new int[GAME_UNITS];
	final int y[] = new int[GAME_UNITS];
	int bodyParts = 3;
	int applesEaten;
	int appleX;
	int appleY;
	char direction = 'R';
	boolean running = false;
	Timer timer;
	Random random;
	
	//setting the display and beginning the game
	GamePanel(){
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.white);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
	}
	
	//beginning the game
	public void startGame() {
		addApple();
		running = true;
		timer = new Timer(DELAY, this);
		timer.start();
	}
	
	//drawing on the display
	public void paint(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	
	
	public void draw(Graphics g) {
		
		/*for (int i = 0; i < SCREEN_HEIGHT/UNIT_SIZE; i++) {
			g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
			g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
		} */
		//drawing the apples on the screen if game has started
		if (running) {
			g.setColor(Color.red);
			g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
			
			//drawing the head of snake
			for(int i = 0; i < bodyParts; i++) {
				if (i == 0) {
					g.setColor(Color.green);
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}
				//drawing the body of the snake
				else {
					g.setColor(Color.green);
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}
			
			}
			//drawing score text on top of display
			g.setColor(Color.red);
			g.setFont(new Font("Inkt Free", Font.BOLD, 40));
			FontMetrics metrics1 = getFontMetrics(g.getFont());
			g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());
		}
		else {
			gameOver(g);
		}
		
	}
	//shifting the other body parts in regards to the position of the head of the snake
	public void move() {
		for (int i = bodyParts; i>0; i--) {
			x[i] = x[i-1];
			y[i] = y[i-1];
			
		}
		switch(direction) {
		//up and down
		case'U':
			y[0] = y[0] - UNIT_SIZE;
			break;
		case'D':
			y[0] = y[0]+ UNIT_SIZE;
			break;
		//left and right
		case'L':
			x[0] = x[0] - UNIT_SIZE;
			break;
		case'R':
			x[0] = x[0]+ UNIT_SIZE;
			break;
		}
	}
	
	public void addApple() {
		//randomly adding apples within the display
		appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE)) * UNIT_SIZE;
		appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE)) * UNIT_SIZE;
	}
	
	public void checkApple() {
		//if the head touches an apple, body increaes, the score increases, and a new apple is added
		if((x[0] == appleX) && (y[0] == appleY)) {
			bodyParts++;
			applesEaten++;
			addApple();
		}
	}
	
	public void checkCollisions() {
		//head collides w body
		for (int i = bodyParts; i > 0; i--) {
			if((x[0] == x[i]) && (y[0] == y[i])) {
				running = false;
			}
		}
		//head touches the borders
		if (x[0] < 0 || x[0] > SCREEN_WIDTH || y[0] < 0 || y[0] > SCREEN_HEIGHT)
			running = false;
		
		if (!running)
			timer.stop();
	}
	
	public void gameOver(Graphics g) {
		//drawing score text
		g.setColor(Color.red);
		g.setFont(new Font("Inkt Free", Font.BOLD, 40));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics2.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());
		//game over text
		g.setColor(Color.red);
		g.setFont(new Font("Inkt Free", Font.BOLD, 75));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("GAME OVER", (SCREEN_WIDTH - metrics1.stringWidth("GAME OVER"))/2, SCREEN_HEIGHT/2);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub 
		if (running) {
			move();
			checkApple();
			checkCollisions();
			
		}
		repaint();
		
	}
	
	public class MyKeyAdapter extends KeyAdapter{
		@Override
		//arrow keys
		public void keyPressed(KeyEvent k) {
			switch(k.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if (direction != 'R') {
					direction = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if (direction != 'L') {
					direction = 'R';
				}
				break;
			case KeyEvent.VK_UP:
				if (direction != 'D') {
					direction = 'U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if (direction != 'U') {
					direction = 'D';
				}
				break;
			}
		}
		
	}

}
