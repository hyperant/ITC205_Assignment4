package test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import game.Dice;
import game.Game;
import game.Player;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Bug2IncorrectLimitTermination {
	private Player player;
	
	private String playerName;
	private int bet;
	private int balance;
	private int limit;

	@Before
	public void setUp() throws Exception {
		//Setup the player, the player will be setup to match what is inside Main.java
		this.playerName ="Fred";
		this.bet =5;
    	this.balance = 5;
    	this.limit = 0;
    	
    	this.player = new Player(this.playerName, this.balance);
    	this.player.setLimit(this.limit);
	}

	@After
	public void tearDown() throws Exception {
		this.player =null;
	}

	@Test
	public void testLimitCheckIncorrect() {
		boolean limitReached =this.player.balanceExceedsLimitBy(this.bet);
		assertTrue(limitReached);
	}
	
	

}
