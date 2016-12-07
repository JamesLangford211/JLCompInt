import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Engine {
	public Engine(){
		String url = "C:/Users/James/Desktop/Final Year/Computational Intelligence/Coursework/workspace/cwk_test.csv";
		//ArrayList<HashMap<String,Double>> pop = populate(url);
		
		Scanner scanner;
		ArrayList<Double> expected = new ArrayList<Double>();
		ArrayList<ArrayList<ExpressionPart>> trees = new ArrayList<ArrayList<ExpressionPart>>();
		
		try {
			scanner = new Scanner(new File(url));
			scanner.useDelimiter(",");
			
			int lineNumber = 1;
	        while(scanner.hasNextLine()){
	        	String line = scanner.nextLine();
	        	String[] lineSplit = line.split(",");
	        	expected.add(Double.parseDouble(lineSplit[0]));
	        	ArrayList<ExpressionPart> treeData = new ArrayList<ExpressionPart>();
	        	
	        	for(int i = 1; i<lineSplit.length; i++){
	        		
	        		treeData.add(new Operand(Double.parseDouble(lineSplit[i])));
	        		
	        		if(i != lineSplit.length){
	        			treeData.add(new Operator());
	        		}
	        		
	        		//fake treeData
	        		ArrayList<ExpressionPart> fake = new ArrayList<ExpressionPart>();
	        		Operand o1 = new Operand(1.0);
	        		Operator o2 = new Operator('+');
	        		Operand o3 = new Operand(2.0);
	        		Operator o4 = new Operator('+');
	        		Operand o5 = new Operand(3.0);
	        		fake.add(o1);
	        		fake.add(o2);
	        		fake.add(o3);
	        		fake.add(o4);
	        		fake.add(o5);
	        		
	        		//create tree
	        		//add treeData to a Tree
	        		//tree.build(treeData);
	        		ExpressionTree<ExpressionPart> tree = new ExpressionTree<ExpressionPart>();
	        		tree.build(fake);
	        		System.out.println(tree.evaluate());
	        		//store in trees
	        		trees.add(fake);
	        		lineNumber++;	
	        	}	
	        }
	        scanner.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		for(int i = 0; i<trees.get(0).size(); i++){
			//System.out.print(trees.get(0).get(i)+", ");
		}
		
		
		
		//for(int i = 0; i<pop.size();i++){
		//	System.out.println(pop.get(i).toString());
		//	System.out.println("**********************");
		//}
	}
	
	public ArrayList<HashMap<String,Double>> populate(String url){
		
		ArrayList<HashMap<String,Double>> population = new ArrayList<HashMap<String,Double>>();																	
		Scanner scanner;
		
			try {
				scanner = new Scanner(new File(url));
				scanner.useDelimiter(",");
		        while(scanner.hasNextLine()){
		        	String line = scanner.nextLine();
		        	String[] lineSplit = line.split(",");  
		        	ArrayList<String> lineSplitNew = new ArrayList<String>();
		        	HashMap<String,Double> rows = new HashMap<String,Double>();
		        	rows.put("expected", Double.parseDouble(lineSplit[0]));
		        	for(int i = 1; i<lineSplit.length; i++){
		        		lineSplitNew.add(lineSplit[i]);
		        	}
		        	
		        	for(int i = 0; i<lineSplitNew.size(); i++){
		        		rows.put(toAlphabetic(i), Double.parseDouble(lineSplitNew.get(i)));
		        	}        
		        	population.add(rows);
		        }
		        scanner.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
		        return population;
		    }
	
	public static String toAlphabetic(int i) {
	    if( i<0 ) {
	        return "-"+toAlphabetic(-i-1);
	    }

	    int quot = i/26;
	    int rem = i%26;
	    char letter = (char)((int)'A' + rem);
	    if( quot == 0 ) {
	        return ""+letter;
	    } else {
	        return toAlphabetic(quot-1) + letter;
	    }
	}
}


