package test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import game.Dice;
import game.DiceValue;
import game.Game;
import game.Player;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Bug1IncorrectPayout {
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
		this.playerName ="Fred";
		this.bet =5;
		
		//Setup three dices
		this.d1 =mock(Dice.class);
		this.d2 =mock(Dice.class);
		this.d3 =mock(Dice.class);
		
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
	public void testBalanceAfterWin() {
		//The player will win by matching d3 (dice3)
		
		//Setup the return values for the three dice
		when(this.d1.getValue()).thenReturn(DiceValue.ANCHOR);
		when(this.d2.getValue()).thenReturn(DiceValue.CLUB);
		when(this.d3.getValue()).thenReturn(DiceValue.CROWN);
		
		//Play a round and make set the players pick to equal CROWN
		int winnings =this.game.playRound(this.player, DiceValue.CROWN, this.bet);
		
		//Verify that we do go inside dice.getValue
		verify(this.d1).getValue();
		verify(this.d2).getValue();
		verify(this.d3).getValue();
		
		//Assert that the dice are returning the correct value
		assertEquals(DiceValue.ANCHOR, this.d1.getValue());
		assertEquals(DiceValue.CLUB, this.d2.getValue());
		assertEquals(DiceValue.CROWN, this.d3.getValue());
		
		//Assert that the player did win the correct amount
		assertEquals(5, winnings);
		
		//Assert that the players winnings increased correctly
		assertEquals((this.balance +winnings), this.player.getBalance());		
	}

}
