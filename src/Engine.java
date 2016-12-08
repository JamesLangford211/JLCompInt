import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

public class Engine {
	
	final String TEST_URL = "src/cwk_test.csv";
	final int POP = 10;
	
	public Engine(){

		//Initialisation:
			//Make some random functions
		ArrayList<ArrayList<ExpressionPart>> randomFunctions = initialFunctionList(POP);
		
		
		//EVAL collection
		for(int i = 0; i<randomFunctions.size(); i++){
			// for every function
			// combine operators from function and data
			// apply to every row and create a score
			// store function and score somewhere
			
		}
		
		//SELECT parents
		//RECOMBINE parents
		//MUTATE offspring
		//EVAL offspring
		
	}
	
	public ArrayList<ArrayList<Operand>> popDataTable(){
		ArrayList<ArrayList<Operand>> dataTable = new ArrayList<ArrayList<Operand>>();
		Scanner scanner;
		try {
			scanner = new Scanner(new File(TEST_URL));
			scanner.useDelimiter(",");
			
			while(scanner.hasNextLine()){
	        	String line = scanner.nextLine();
	        	String[] lineSplit = line.split(",");
	        	ArrayList<Operand> treeData = new ArrayList<Operand>();
	        	
	        	for(int i = 1; i<lineSplit.length; i++){
	        		treeData.add(new Operand(Double.parseDouble(lineSplit[i])));
	        		}
	        	dataTable.add(treeData);
		}
		
		
		} catch(FileNotFoundException e){
		
		}		
		
		return dataTable;
}

	
	public ArrayList<ArrayList<ExpressionPart>> initialFunctionList(int popSize){
		ArrayList<ArrayList<ExpressionPart>> list = new ArrayList<ArrayList<ExpressionPart>>();
		
		for(int index = 0; index<popSize; index++){
			ArrayList<ExpressionPart> subList = new ArrayList<ExpressionPart>();
			for(int i = 0; i<12; i++){
				Operator op = new Operator();
				op.changeRandomly();
				subList.add(op);
			}
			list.add(subList);
		}
		return list;
	}
	
	public void listToString(ArrayList<ArrayList<ExpressionPart>> functions){
		for(int i = 0; i<functions.size();i++){
			System.out.println("Function " + i + ":");
			for(int j = 0; j<functions.get(i).size(); j++){
				System.out.print(functions.get(i).get(j).toString());
			}
		}
	}
}

	



