import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class ExpressionTree<T> implements Iterable<T>{
	protected ExpressionTreeNode<T> root;
	StackNode top;
	int size = 0;
	
	public ExpressionTree(){
		root = null;
	}
	
	public ExpressionTree(T element){
		root = new ExpressionTreeNode<T>(element);
	}
	
	public void clear(){
		root = null;
	}
	
	public int size(){
		return size(root);
	}
	 protected int size(ExpressionTreeNode<T> node){
		 if(node == null){
			 return 0;
		 }
		 else{
			 return 1 + size(node.left) + size(node.right);
		 }
	 }
	 
	 public int height(){
		 return height(root);
	 }
	 
	 protected int height(ExpressionTreeNode<T> node){
		 if(node == null){
			 return 0;
		 }
		 else{
			 return 1 + Math.max(height(node.left), height(node.right));
		 }
	 }
	 
	 /** class StackNode **/
	 class StackNode
	 {
	     ExpressionTreeNode<T> treeNode;
	     StackNode next;

	     /** constructor **/
	     public StackNode(ExpressionTreeNode<T> treeNode)
	     {
	         this.treeNode = treeNode;
	         next = null;
	     }
	 }
	 
	 public void build(ArrayList<T> treeArray){
		 for(int i = treeArray.size() - 1; i >= 0; i--){
			 add(treeArray.get(i));
		 }
	 }
	
	 public void add(T elem){
		 try
	        {
	            if (isDigit(elem))
	            {
	                ExpressionTreeNode<T> nptr = new ExpressionTreeNode<T>(elem);
	                //System.out.println(elem.toString());
	                push(nptr);
	            }
	            else if (!isDigit(elem))
	            {
	                ExpressionTreeNode<T> nptr = new ExpressionTreeNode<T>(elem);
	                //System.out.println(elem.toString());
	                nptr.left = pop();
	                nptr.right = pop();
	                push(nptr);
	            }
	            
	        }
	        catch (Exception e)
	        {
	            //System.out.println("Invalid Expression");
	        }
	 }
	 
	 private ExpressionTreeNode peek()
	    {
	        return top.treeNode;
	    }
	 
	 /** function to evaluate tree */
	    public double evaluate()
	    {
	    	
	        return evaluate(peek());
	    }
	 
	    /** function to evaluate tree */
	    public double evaluate(ExpressionTreeNode ptr)
	    {
	        if (ptr.left == null && ptr.right == null)
	        {
	            return Double.parseDouble(ptr.element.toString());
	        }
	        else
	        {
	            double result = 0.0;
	            double left = evaluate(ptr.left);
	            double right = evaluate(ptr.right);
	            Character operator = ptr.element.toString().charAt(0);
	            switch (operator)
	            {
	            case '+' : result = left + right; break;
	            case '-' : result = left - right; break;
	            case '*' : result = left * right; break;
	            case '/' : result = left / right; break;
	            default  : result = left + right; break;
	            }
	            return result;
	        }
	    }
	 
	 public boolean isDigit(T elem){
		 return elem instanceof Operand;
	 }
	 
	 private void push(ExpressionTreeNode<T> ptr){
	  if (top == null)
	            top = new StackNode(ptr);
	        else
	        {
	            StackNode nptr = new StackNode(ptr);
	            nptr.next = top;
	            top = nptr;
	        }
	    }
	 
	    /** function to pop a node **/
	    private ExpressionTreeNode<T> pop()
	    {
	        if (top == null)
	            throw new RuntimeException("Underflow");
	        else
	        {
	            ExpressionTreeNode<T> ptr = top.treeNode;
	            top = top.next;
	            return ptr;
	        }
	    }
	 
	
	protected static class ExpressionTreeNode<E>{
		public E element;
		public ExpressionTreeNode<E> left;
		public ExpressionTreeNode<E> right;
		
		//Leaf node constructor
		public ExpressionTreeNode(E element){
			this.element = element;
			left = null;
			right = null;
		}
		
		//Parent node constructor
		public ExpressionTreeNode(E element, ExpressionTreeNode<E> left, ExpressionTreeNode<E> right){
		
			this.element = element;
			this.left = left;
			this.right = right;
		}
		
		public boolean isLeaf(){
			return (left == null && right == null);
		}
		
		public String toString(){
			String rtn = "";
			rtn += "\n Parent: " + element.toString();
			if(right != null){
				rtn +=  "\n left child: " + right.toString();
			}
			if(left != null){
				rtn +=  "\n left child: " + right.toString();
			}
			return element.toString();
		}
	}



	@Override
	public Iterator<T> iterator() {
		// TODO Auto-generated method stub
		return null;
	}
}
