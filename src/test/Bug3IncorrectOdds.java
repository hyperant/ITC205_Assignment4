package test;

import static org.junit.Assert.*;

import java.util.List;

import game.Dice;
import game.DiceValue;
import game.Game;
import game.Player;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Bug3IncorrectOdds {
	
	private Player player;
	private Game game;
	private Dice d1;
	private Dice d2;
	private Dice d3;
	
	private String playerName;
	private int bet;
	private int balance;
	private int limit;

	@Before
	public void setUp() throws Exception {
		//Setup the dice
		this.d1 =new Dice();
		this.d2 =new Dice();
		this.d3 =new Dice();
		
		//Setup the player, the player will be setup to match what is inside Main.java
		this.playerName ="Fred";
		this.bet =5;
    	this.balance = 100;
    	this.limit = 0;
    	
    	this.player = new Player(this.playerName, this.balance);
    	this.player.setLimit(this.limit);
    	
    	//Create a new game
    	this.game =new Game(this.d1, this.d2, this.d3);
	}

	@After
	public void tearDown() throws Exception {
		this.d1 =null;
		this.d2 =null;
		this.d3 =null;
		
		this.player =null;
		this.game =null;
	}

	@Test
	public void testIncorrectOdds() {
		//In order to test the bug we are going to keep playing the game until the win ratio is wrong
		//We will set the game up exactly as it appears in Main.java the only difference is this will be
		//place inside an endless loop. In order to demonstrate the bug and what values we are seeing we
		//are going to output the win ratio only after each game
		
        int round =0;

        for(int ii =0; ii <1000; ii++)
        {
        	round++;
            int winCount = 0;
            int loseCount = 0;
            
            for (int i = 0; i < 100; i++)
            {
            	String name = "Fred";
            	int balance = 100;
            	int limit = 0;
                player = new Player(name, balance);
                player.setLimit(limit);
                int bet = 5;

                int turn = 0;
                while (player.balanceExceedsLimitBy(bet) && player.getBalance() < 200)
                {
                    turn++;                    
                	DiceValue pick = DiceValue.getRandom();
                	
                	int winnings = game.playRound(player, pick, bet);
                    
                    if (winnings > 0) {
	                	winCount++; 
                    }
                    else {
	                	loseCount++;
                    }
                    
                } //while
            } //for
            float winRatio =(float) winCount/(winCount+loseCount);
            
            System.out.println("round " +round + ": win ratio " +winRatio);
            
            //Once this assert fails the loop will break, this is an indication of the bug
            assertTrue(winRatio >=0.40f);
            assertTrue(winRatio <=0.44f);
        }
	}

}
