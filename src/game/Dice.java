package game;
public class Dice {
			
	private DiceValue value;
	
	public Dice() {
		value =  DiceValue.getRandom();
	}
	
	public DiceValue getValue() {
		return value;
	}

	public DiceValue roll() {
		value =DiceValue.getRandom();
		return value;
	}
	
	public DiceValue roll(DiceValue val) {
		value =val;
		return val;
	}
	
	public String toString() {
		return value.toString();
	}
}
