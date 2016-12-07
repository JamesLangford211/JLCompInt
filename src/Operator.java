import java.util.Random;

public class Operator extends ExpressionPart<Character> {
	private final Character[] finalChars = {'+', '-','*','/'};
	
	public Operator(){
		super();
		value = null;
	}
	
	public Operator(Character value){
		super();
		this.value = value;
	}
		
	public void changeRandomly(){
		Random r = new Random();
		value = finalChars[r.nextInt(4)];
	}
	
}
