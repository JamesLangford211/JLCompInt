
public abstract class ExpressionPart<T> {
	protected T value; 
	
	public T getPart(){
		return value;
	}
	
	public void changePart(T value){
		this.value = value;
	}
	
	public String toString(){
		if(value == null){
			return "Blank";
		}
		else
			return ""+ String.valueOf(value);
	}
}
