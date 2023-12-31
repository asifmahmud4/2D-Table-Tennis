
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.*;

public class Score extends Rectangle{

        static  int game_width;
	static int game_height;
	int player1;
	int player2;
	
	Score(int game_width, int game_height){
		Score.game_width = game_width;
		Score.game_height = game_height;
	}
	public void draw(Graphics g) {
		g.setColor(Color.white);
		g.setFont(new Font("timesnewroman",Font.PLAIN,60));
		
		g.drawLine(game_width/2, 0, game_width/2, game_height);
		
		g.drawString(String.valueOf(player1/10)+String.valueOf(player1%10), (game_width/2)-90, 50);
		g.drawString(String.valueOf(player2/10)+String.valueOf(player2%10), (game_width/2)+20, 50);
	}
}