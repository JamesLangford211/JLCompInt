import java.util.ArrayList;

public class ExpTree<T> {
	 /** class TreeNode **/
    class TreeNode
    {    
        ExpressionPart data;
        TreeNode left, right;
 
        /** constructor **/
        public TreeNode(ExpressionPart expressionPart)
        {
            this.data = expressionPart;
            this.left = null;
            this.right = null;
        }
    } 
 
    /** class StackNode **/
    class StackNode
    {
        TreeNode treeNode;
        StackNode next;
 
        /** constructor **/
        public StackNode(TreeNode treeNode)
        {
            this.treeNode = treeNode;
            next = null;
        }
    }
 
    private StackNode top;
 
    /** constructor **/
    public ExpTree()
    {
        top = null;
    }
 
    /** function to clear tree **/
    public void clear()
    {
        top = null;
    }
 
    /** function to push a node **/
    private void push(TreeNode ptr)
    {
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
    private TreeNode pop()
    {
        if (top == null)
            throw new RuntimeException("Underflow");
        else
        {
            TreeNode ptr = top.treeNode;
            top = top.next;
            return ptr;
        }
    }
 
    /** function to get top node **/
    private TreeNode peek()
    {
        return top.treeNode;
    }
 
    /** function to insert character **/
    private void insert(ExpressionPart expressionPart)
    {
        try
        {
            if (expressionPart instanceof Operand)
            {
                TreeNode nptr = new TreeNode(expressionPart);
                push(nptr);
            }
            else if (expressionPart  instanceof Operator)
            {
                TreeNode nptr = new TreeNode(expressionPart);
                nptr.left = pop();
                nptr.right = pop();
                push(nptr);
            }
        }
        catch (Exception e)
        {
            System.out.println("Invalid Expression");
        }
    }
 
    /** function to build tree from input */
    public void buildTree(ArrayList<ExpressionPart> exp)
    {
        for (int i = exp.size() - 1; i >= 0; i--){
            insert(exp.get(i));
        }
        
    }
 
    /** function to evaluate tree */
    public double evaluate()
    {
        return evaluate(peek());
    }
 
    /** function to evaluate tree */
    public double evaluate(TreeNode ptr)
    {
        if (ptr.left == null && ptr.right == null)
            return Double.parseDouble(ptr.data.toString());
        else
        {
            double result = 0.0;
            double left = evaluate(ptr.left);
            double right = evaluate(ptr.right);
            char operator = ptr.data.toString().charAt(0);
 
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
 
    /** function to get postfix expression */
    public void postfix()
    {
        postOrder(peek());
    }
 
    /** post order traversal */
    private void postOrder(TreeNode ptr)
    {
        if (ptr != null)
        {
            postOrder(ptr.left);            
            postOrder(ptr.right);
            System.out.print(ptr.data);            
        }    
    }
 
    /** function to get infix expression */
    public void infix()
    {
        inOrder(peek());
    }
 
    /** in order traversal */
    private void inOrder(TreeNode ptr)
    {
        if (ptr != null)
        {
            inOrder(ptr.left);
            System.out.print(ptr.data);
            inOrder(ptr.right);            
        }    
    }
 
    /** function to get prefix expression */
    public void prefix()
    {
        preOrder(peek());
    }
 
    /** pre order traversal */
    private void preOrder(TreeNode ptr)
    {
        if (ptr != null)
        {
            System.out.print(ptr.data);
            preOrder(ptr.left);
            preOrder(ptr.right);            
        }    
    }
}


