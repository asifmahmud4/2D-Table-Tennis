/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable{

	int game_width = 1000;
	int game_height = (int)(game_width * (0.5555));
	Dimension screen_size = new Dimension(game_width,game_height);
	int ball_size = 45;
	int paddle_width = 25;
	int paddle_height = 150;
	Thread gameThread;
	Image image;
	Graphics graphics;
	Random random;
	Paddle paddle1;
	Paddle paddle2;
	Ball ball;
	Score score;
	
	GamePanel(){
		newPaddles();
		newBall();
		score = new Score(game_width,game_height);
		this.setFocusable(true);
		this.addKeyListener(new AL());
		this.setPreferredSize(screen_size);
		
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	public void newBall() {
		random = new Random();
		ball = new Ball((game_width/2)-(ball_size/2),random.nextInt(game_height-ball_size),ball_size,ball_size);
	}
	public void newPaddles() {
		paddle1 = new Paddle(0,(game_height/2)-(paddle_height/2),paddle_width,paddle_height,1);
		paddle2 = new Paddle(game_width-paddle_width,(game_height/2)-(paddle_height/2),paddle_width,paddle_height,2);
	}
	public void paint(Graphics g) {
		image = createImage(getWidth(),getHeight());
		graphics = image.getGraphics();
		draw(graphics);
		g.drawImage(image,0,0,this);
	}
	public void draw(Graphics g) {
		paddle1.draw(g);
		paddle2.draw(g);
		ball.draw(g);
		score.draw(g);


	}
	public void move() {
		paddle1.move();
		paddle2.move();
		ball.move();
	}
	public void Collision() {
		
		
		if(ball.y <=0) {
			ball.setYDirection(-ball.yVelocity);
		}
		if(ball.y >= game_height-ball_size) {
			ball.setYDirection(-ball.yVelocity);
		}
		
                
		if(ball.intersects(paddle1)) {
			ball.xVelocity = Math.abs(ball.xVelocity);
			ball.xVelocity++; 
			if(ball.yVelocity>0)
				ball.yVelocity++; 
			else
				ball.yVelocity--;
			ball.setXDirection(ball.xVelocity);
			ball.setYDirection(ball.yVelocity);
		}
		if(ball.intersects(paddle2)) {
			ball.xVelocity = Math.abs(ball.xVelocity);
			ball.xVelocity++; //optional for more difficulty
			if(ball.yVelocity>0)
				ball.yVelocity++; //optional for more difficulty
			else
				ball.yVelocity--;
			ball.setXDirection(-ball.xVelocity);
			ball.setYDirection(ball.yVelocity);
		}
		
                
		if(paddle1.y<=0)
			paddle1.y=0;
		if(paddle1.y >= (game_height-paddle_height))
			paddle1.y = game_height-paddle_height;
		if(paddle2.y<=0)
			paddle2.y=0;
		if(paddle2.y >= (game_height-paddle_height))
			paddle2.y = game_height-paddle_height;
		
                
		if(ball.x <=0) {
			score.player2++;
			newPaddles();
			newBall();
			System.out.println("Player 2: "+score.player2);
		}
		if(ball.x >= game_width-ball_size) {
			score.player1++;
			newPaddles();
			newBall();
			System.out.println("Player 1: "+score.player1);
		}
	}
	public void run() {
		
		long lastTime = System.nanoTime();
		double amountOfTicks =60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		while(true) {
			long now = System.nanoTime();
			delta += (now -lastTime)/ns;
			lastTime = now;
			if(delta >=1) {
				move();
				Collision();
				repaint();
				delta--;
			}
                        
                        
                        if(score.player1>=11){
                           break;
                        }
                        
                        if(score.player2>=11){
                           break;
                        }
		}
                
                if(score.player1>=11){
                    System.out.println("player 1 wins"); 
                    JOptionPane.showMessageDialog(this, "player 1 wins");
                }
                        
                if(score.player2>=11){
                    System.out.println("player 2 wins");
                    JOptionPane.showMessageDialog(this, "player 2 wins");
                }
                
                
	}
	public class AL extends KeyAdapter{
		public void keyPressed(KeyEvent e) {
			paddle1.keyPressed(e);
			paddle2.keyPressed(e);
		}
		public void keyReleased(KeyEvent e) {
			paddle1.keyReleased(e);
			paddle2.keyReleased(e);
		}
	}
}