import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;
import com.fathzer.soft.javaluator.*;

public class Engine {
	
	final String TEST_URL = "src/cwk_test.csv";
	final int STARTING_POP = 100;
	final double MUTATION_PROBABILITY = 0.05;
	final int ITERATIONS = 1;
	final ArrayList<ArrayList<Operand>> dataSet = popDataTable(TEST_URL);
	final ArrayList<Double> expected = getExpected(TEST_URL);
	DoubleEvaluator evaluator = new DoubleEvaluator();
	
	public Engine(){
		
		//Initialise()
			//Initialisation:
			//Make some random functions
		ArrayList<ArrayList<ExpressionPart>> currentFunctions = initialFunctionList(STARTING_POP);
		//System.out.println(evaluate(currentFunctions));
			
		/**
		 *      Initialise random population
		 * -->  Evaluate population
		 * |    Select parents
		 * |    Combine parents to make shell offspring
		 * |    Create new population with slight mutations
		 * --- Repeat
		 */
		 
		
		
		for(int i = 0; i<ITERATIONS; i++){
			ArrayList<ArrayList<ExpressionPart>> subPop = getTopTwoParents(currentFunctions);
			//select subsection of population
			System.out.print("\n Best for iteration "+i+" : "+subPop.get(0));
		}
		
	}

	public ArrayList<ArrayList<ExpressionPart>> getTopTwoParents(ArrayList<ArrayList<ExpressionPart>> pop){
		ArrayList<ArrayList<ExpressionPart>> parents = new ArrayList<ArrayList<ExpressionPart>>();
		//select best two
		ArrayList<ArrayList<String>> evaluatedSet = evaluate(pop);
		bestToWorst(evaluatedSet);
		
		/*for(int i = 0; i<evaluatedSet.size(); i++){
			System.out.println(evaluatedSet.get(i).toString());
		}*/
		
		ArrayList<ExpressionPart> parent1 = stringToExpression(evaluatedSet.get(0).get(1));
		ArrayList<ExpressionPart> parent2 = stringToExpression(evaluatedSet.get(1).get(1));
	
		
		parents.add(parent1);
		parents.add(parent2);
		return parents;
	}
	
	public ArrayList<ArrayList<String>> evaluate(ArrayList<ArrayList<ExpressionPart>> functions){
		ArrayList<ArrayList<String>> iterationFitnesses = new ArrayList<ArrayList<String>>();
		
		//EVAL collection
				for(int i = 0; i<functions.size(); i++){
					Double fitness = 0.0;
					// for every function
					for(int j = 0; j<dataSet.size(); j++){
						//for every data row
						//combine row of data and function
						ArrayList<ExpressionPart> expression = formExpression(functions.get(i), dataSet.get(j));
						Double result = evaluator.evaluate(listToString(expression));
						fitness += score(result, expected.get(j));	
					}
					fitness = fitness/dataSet.size();
					ArrayList<String> fitnessRow = new ArrayList<String>();
					fitnessRow.add(""+fitness);
					fitnessRow.add(listToString(functions.get(i)));
					iterationFitnesses.add(fitnessRow);
				}
				
				
				
					
				
		return iterationFitnesses;
	}
	
	public void bestToWorst(ArrayList<ArrayList<String>> results){
		Collections.sort(results, new Comparator<ArrayList<String>>() {    
	        @Override
	        public int compare(ArrayList<String> o1, ArrayList<String> o2) {
	        	Double o1D = Double.parseDouble(o1.get(0));
	        	Double o2D = Double.parseDouble(o2.get(0));
	            return o1D.compareTo(o2D);
	        }               
	});
		
		Collections.reverse(results);
			
	}
	
	public Boolean isInRange(Double number, Double expected, Double range){
		Double lowerBound = expected-(range);
		Double upperBound = expected + range;
		if (lowerBound <= number && number <= upperBound){
			return true;
		}
		return false;
	}
	public ArrayList<ArrayList<ExpressionPart>> mutatePop(ArrayList<ArrayList<ExpressionPart>> population){
		ArrayList<ArrayList<ExpressionPart>> mutatedPop = new ArrayList<ArrayList<ExpressionPart>>();
		for(int i = 0; i<population.size();i++){
			for(int j = 0; j<population.get(i).size(); j++){
				Random r = new Random();
				double randomValue = r.nextDouble();
				if(randomValue<=MUTATION_PROBABILITY){
					//mutate
					population.get(i).get(j).changeRandomly();
				}
			}
		}
		return mutatedPop;
	}
	public Double score(Double number, Double expected){
		Double score = 100.0;
		if(number == expected){
			return score;
		}
		else{
			for(int i = 1; i<201; i++){
				Double range = (number*0.005) * i;
				if(!isInRange(number, expected, range)){
					score -= 1;
				}
			}
		}
		return score;
	}
	public String expressionText(ArrayList<ExpressionPart> exp){
		String expression = "";
		for(ExpressionPart ep : exp){
			expression+= ep.toString() + " ";
		}
		
		return expression;
	}
	public ArrayList<ExpressionPart> formExpression(ArrayList<ExpressionPart> ops, ArrayList<Operand> data){
		ArrayList<ExpressionPart> expression = new ArrayList<ExpressionPart>();
		//for every data but the last, add the operator to it
		for(int i = 0; i<data.size(); i++){
			expression.add(data.get(i));
			if(i<data.size()-1){
				expression.add(ops.get(i));
			}
			
		}		
		return expression;
	}
	public ArrayList<ArrayList<Operand>> popDataTable(String url){
		ArrayList<ArrayList<Operand>> dataTable = new ArrayList<ArrayList<Operand>>();
		Scanner scanner;
		try {
			scanner = new Scanner(new File(url));
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
				//System.out.print(op.toString());
				subList.add(op);
			}
			System.out.print("\n");
			list.add(subList);
		}
		return list;
	}
	public ArrayList<ExpressionPart> stringToExpression(String str){
		//ArrayList<ExpressionPart> 
		//String[] strArr = str.split(" ");
		//for(String s : strArr){
		//	if(s.charAt(0) == '+'){
		//		
		//	}
		//}
		return null;
	}
	public void listsToString(ArrayList<ArrayList<ExpressionPart>> functions){
		for(int i = 0; i<functions.size();i++){
			System.out.println("Function " + i + ":");
			for(int j = 0; j<functions.get(i).size(); j++){
				System.out.print(functions.get(i).get(j).toString());
			}
		}
	}
	public String listToString(ArrayList<ExpressionPart> function){
		String returnStr = "";
		for(int i = 0; i<function.size(); i++){
			returnStr += function.get(i).toString() + " ";
		}
		
		return returnStr;
	}
	public ArrayList<Double> getExpected(String url){
		ArrayList<Double> expected = new ArrayList<Double>();
		Scanner scanner;
		try {
			scanner = new Scanner(new File(url));
			scanner.useDelimiter(",");
			
			while(scanner.hasNextLine()){
	        	String line = scanner.nextLine();
	        	String[] lineSplit = line.split(",");
	        	
	        	expected.add(Double.parseDouble(lineSplit[0]));
	        	
	       
		}
		
		
		} catch(FileNotFoundException e){
		
		}		
		
		return expected;
	}
}

	



