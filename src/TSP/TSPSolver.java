package TSP;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.TooManyListenersException;
import TSP.CityEngine;
import TSP.Tour;


import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class TSPSolver {
	
	public static final int TARGET_GENERATION = 200;
	public static final String OUTPUT_FILE_NAME= "solution.csv";
	
	public static void main(String[] args) {
	
		HashMap<Integer,City> cityMap= new CityEngine(nextArgument(args)).getCities();
		City cityList[] = cityMap.values().toArray(new City[cityMap.size()]);
		double mutationRate = 1/cityMap.size();
		double crossOverRate = 0.8;
		int elitismCount=1;
		int populationSize=100;
		int tournamentSize=2;
		
		
		GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(populationSize,mutationRate,crossOverRate, elitismCount, tournamentSize);

		Population population = geneticAlgorithm.intitializePopulation(cityMap.keySet());
		int generation = 0;
		Individual bestIndividual =null;
		while (!geneticAlgorithm.isTerminationConditionMet(generation, TARGET_GENERATION) ) {
			geneticAlgorithm.evaluatePopulation(population, cityList);
			bestIndividual = geneticAlgorithm.findBestIndividual(population,bestIndividual);
			population = geneticAlgorithm.crossoverPopulation(population);
			population = geneticAlgorithm.mutatePopulation(population);
			generation++;
		}
		geneticAlgorithm.evaluatePopulation(population, cityList);
		bestIndividual = geneticAlgorithm.findBestIndividual(population,bestIndividual);
		System.out.println(" The Total Distance travelled: " + geneticAlgorithm.findTourDistance(bestIndividual,cityList));
		
		try {
			writeResultToFile("The Permutation travelled: "+  "\r\n" + bestIndividual.toString());
		}catch (FileNotFoundException ex)	{
			System.out.println("Destination File Cannot be Openned!  The process cannot access the file because it is being used by another process");
			System.exit(1);
		
		} catch (IOException e) {
			System.out.println("Destination File Cannot be Openned! ");
			System.exit(1);
		}
		
		
		

	}
	
	public static void writeResultToFile(String str) 
			  throws IOException {
			   
			    BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT_FILE_NAME));
			    writer.write(str);
			    
			    writer.close();
			}
	
    private static void showHelp(Options options){
        
        String syntaxStr1;
        String headerStr;
        HelpFormatter formatter = new HelpFormatter();
        PrintWriter pw = new PrintWriter(System.out, true);

            syntaxStr1 = "java [VM_Options] TSPFileName ";
            headerStr = " TSP Solver ";
  
        final int columns = 150;
       formatter.printWrapped(pw, columns, syntaxStr1);
       formatter.printWrapped(pw, columns, "");
       formatter.printWrapped(pw, columns, headerStr);
       formatter.printWrapped(pw, columns, "");
       formatter.printOptions(pw, columns, options, 4, 4);
   }
    
    
    
    private static String readTSPFile(CommandLine line, Options options) throws IOException, TooManyListenersException
    {
    	final String FILE_NAME;
  
        String[] cmdArgs = line.getArgs();
       
            if (cmdArgs.length != 1)
            {
                showHelp(options);
                System.exit(1);
            } 
             FILE_NAME= cmdArgs[0];
             return FILE_NAME;
        
}

    
	
		 public static String nextArgument(String arguments[]){

			    String strArgument = new String();
				Options options = new Options();
				options.addOption("h", "help", false, "Show this help");
				CommandLineParser parser = new GnuParser();
				CommandLine line = null;

				try {
					line = parser.parse(options, arguments);

					if (line.hasOption('h')) {
						showHelp(options);
					    Scanner scanner = new Scanner(System.in);
					    System.out.println("Please enter a file name");
					    strArgument = scanner.next();
					    scanner.close();
					    return strArgument;					
					    }
				} catch (ParseException ex) {
					ex.printStackTrace();
					System.exit(1);

				}

				String[] cmdArgs = line.getArgs();
				if (cmdArgs.length>0) {
					try {
						strArgument=readTSPFile(line, options);
					} catch (Exception ex) {
						ex.printStackTrace();
						System.exit(1);
					}
				}else {

					try {
						Scanner scanner = new Scanner(System.in);
						System.out.println("Please enter a file name");
						strArgument = scanner.next();
						scanner.close();
					} catch (Exception ex) {
						System.out.println("The file specified is not correct.");
						System.exit(1);

					}

				}

				
		 
			 				    return strArgument;

		 

			}
	

}
