package game;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Game {

	private List<Dice> dice;
	private List<DiceValue> values;
	
	private int playerWin;
	private int playerLose;
	
	public Game(Dice die1, Dice die2, Dice die3) {
		if (die1 == null || die2 == null || die3 == null) throw new IllegalArgumentException("Dice cannot be null.");
		dice = new ArrayList<Dice>();
		dice.add(die1);
		dice.add(die2);
		dice.add(die3);
		values = new ArrayList<DiceValue>();
		
		this.playerWin =0;
		this.playerLose =0;
	}

	public List<DiceValue> getDiceValues() {
		values.clear();
		for (Dice d : dice) {
			values.add(d.getValue());
		}
		return Collections.unmodifiableList(values);
	}	
	
	public int playRound(Player player, DiceValue pick, int bet ) {		
		if (player == null) throw new IllegalArgumentException("Player cannot be null.");
		if (pick == null) throw new IllegalArgumentException("Pick cannot be negative.");
		if (bet < 0) throw new IllegalArgumentException("Bet cannot be negative.");
		float winRatio =(float) this.playerWin/(this.playerWin +this.playerLose);
		
		player.takeBet(bet);
		    
		int matches = 0;
		for ( Dice d : dice) {
			d.roll();
			if (d.getValue().equals(pick)) { 
				matches += 1;
			}
		}
		
		//Add the bias
		
		//Player needs to win, but only if they havent already won at least one match
		if(winRatio <0.42f && matches ==0) {
			//In order to make it look like the game isnt rigged, we will pick a random dice to change its value
			Random r = new Random();
			this.dice.get(r.nextInt(this.dice.size())).roll(pick);
			matches++;
		}
		
		//Player must lose
		if(winRatio >0.42f && matches >0) {
			//Find out what dice match the players dice and change it
			for ( Dice d : dice) {
				if (d.getValue().equals(pick)) {
					
					int dValue =pick.ordinal();
					dValue++;
					if(dValue >DiceValue.values().length -1)
						dValue =0;
					
					
					d.roll(DiceValue.values()[dValue]);
				}
			}
			matches =0;
		}
		
		int winnings =matches * bet;

		if (matches > 0) {			
			player.receiveWinnings(winnings +bet);
			this.playerWin++;
		} else {
			this.playerLose++;
		}
        return winnings;		
	}
	
}
