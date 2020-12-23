package TSP;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;




public class GeneticAlgorithm {
	
	private double mutationRate;
	private double crossoverRate;
	private int elitismCount;
	private int populationSize;
	private int tournamentSize;


	
	public GeneticAlgorithm(int populationSize, double mutationRate, double crossoverRate, int elitismCount, int tournamentSize) {
		this.populationSize = populationSize;
		this.mutationRate = mutationRate;
		this.crossoverRate = crossoverRate;
		this.elitismCount = elitismCount;
		this.tournamentSize = tournamentSize;
	}
	public Individual tournamentSelectParent(Population population) {
		//create a population of individuals; the size of population is tournamentSize 
		Population tournament = new Population(this.tournamentSize);
		// not to choose the same individual twice
		population.shuffle();
		for (int i = 0; i < this.tournamentSize; i++) {
			//randomly select the individulas of a population
			int individualIdx= 	new Random().nextInt(population.size());
		    Individual tournamentIndividual = population.getIndividual(individualIdx);
			tournament.setIndividual(i, tournamentIndividual);
		}
		// the getFittest() function compares the fitness of the individuals and sorts them
		// the first elemet is the fittest
		return tournament.getFittest(0);
	}
	
	public void evaluatePopulation(Population population, City cities[]) {
		//calculate and set fittness value of each individual
		for (Individual individual : population.getIndividuals()) {
			 this.calcFitness(individual, cities);
		}

		
	}

	public double calcFitness(Individual individual, City cities[]) {
		// create a tour of each individual
		Tour tour = new Tour(individual, cities);
		//convert a minimization to maximization
		double fitness = 1 / tour.getDistance();
		individual.setFitness(fitness);
		return fitness;
	}

	public Population intitializePopulation(Set<Integer> cityMapKeys) {
		//initializing randomized individuals in the constructor of population
		Population population = new Population(this.populationSize, cityMapKeys);
		return population;
	}


	   public Population crossoverPopulation(Population population){
		   
	        Population newPopulation = new Population(population.size());

			for (int populationIndex = 0; populationIndex < population.size()  ; populationIndex += 2) {
				//initialize empty individuals
				Individual frstParent= new Individual(population.getIndividual(populationIndex).getChromosomeLength());
				Individual scndParent= new Individual(population.getIndividual(populationIndex).getChromosomeLength());
				//control number of parents should be considered as elite
				boolean isElitFrstParent=false;
				boolean isElitScndParent=false;
				

									
				if (populationIndex < this.elitismCount - 1) {
					frstParent = population.getFittest(populationIndex);
					scndParent = population.getFittest(populationIndex + 1);
					isElitFrstParent = true;
					isElitScndParent = true;
				} else if (populationIndex == this.elitismCount - 1) {
					//only first parent can be considered as elite
					frstParent = population.getFittest(populationIndex);
					scndParent = this.tournamentSelectParent(population);
					isElitFrstParent=true;
					isElitScndParent=false;
				} else if (populationIndex >= this.elitismCount) { 
					//the two parents participate in recombination process
					frstParent = this.tournamentSelectParent(population);
					scndParent = this.tournamentSelectParent(population);
					isElitFrstParent=false;
					isElitScndParent=false;
										
				}

				if (this.crossoverRate > Math.random()) {
					
					List<Individual> newOffsprings= crossoverParent(frstParent, scndParent, population);
					//add elite or crossover parents
					Individual frstOffspring = isElitFrstParent? frstParent:  newOffsprings.get(0);
					Individual scndOffSpring = isElitScndParent? scndParent:  newOffsprings.get(1);
					
					//add crossovered offspring
					newPopulation.setIndividual(populationIndex, frstOffspring);
					//handle last roud of odd populations
					if ( populationIndex<population.size()-1) {
						newPopulation.setIndividual(populationIndex+1, scndOffSpring);
					}
				} else {
					//crossover possibility is not high enough for crossover operation,
					// so the selected parent add to the next populatio
					newPopulation.setIndividual(populationIndex, frstParent);
					if ( populationIndex<population.size()-1) {
					newPopulation.setIndividual(populationIndex+1, scndParent);
					}
				}
				
			}

			return newPopulation;
		}
	   

	   
	   private List<Individual> crossoverParent(Individual frstParent,Individual scndParent, Population population) {
		   List<Individual>  offSpringList = new ArrayList<Individual>();
		   int offspring1Chromosome[] = new int[frstParent.getChromosomeLength()];
		   int offspring2Chromosome[] = new int[scndParent.getChromosomeLength()];
           Arrays.fill(offspring1Chromosome, -1);
           Arrays.fill(offspring2Chromosome, -1);

           Individual offspring1 = new Individual(offspring1Chromosome);
           Individual offspring2 = new Individual(offspring2Chromosome);

           int substrPos1 = (int) (Math.random() * frstParent.getChromosomeLength());
           int substrPos2 = (int) (Math.random() * frstParent.getChromosomeLength());

           final int startSubstr = Math.min(substrPos1, substrPos2);
           final int endSubstr = Math.max(substrPos1, substrPos2);

			for (int i = startSubstr; i < endSubstr; i++) {
				// filling the base section of offspring1 with selected parts of scndParent
				offspring1.setGene(i, scndParent.getGene(i));
				// filling the base section of offspring2 with selected parts of fisrParent
				offspring2.setGene(i, frstParent.getGene(i));
			}

						
			int offspring1Gene=0;
			int offspring2Gene=0;

			for (int i = 0; i < frstParent.getChromosomeLength(); i++) {
				// set the index of  genes of first parent from the end position of the base section
				int parent1Gene = i + endSubstr;
				//set the index of the first offspring based on the number of genes are transfered from the first parents
	            offspring1Gene= (i==0)? i + endSubstr:offspring1Gene;
	            //check the end of array for first parent
		        if (parent1Gene >= frstParent.getChromosomeLength()) {
					parent1Gene -= frstParent.getChromosomeLength();
				}
		        //check the end of array for first offspring
		        if (offspring1Gene >= frstParent.getChromosomeLength()) {
		        	offspring1Gene -= frstParent.getChromosomeLength();
				}
		        
		     // set the index of  genes of second parent from the end position of the base section
		        int parent2Gene = i + endSubstr;
	            //set the index of the second offspring based on the number of genes are transfered from the second parents
				offspring2Gene= (i==0)? i + endSubstr:offspring2Gene;
	            //check the end of array for second parent
		        if (parent2Gene >= scndParent.getChromosomeLength()) {
					parent2Gene -= scndParent.getChromosomeLength();
				}
		        //check the end of array for second offspring
		        if (offspring2Gene >= scndParent.getChromosomeLength()) {
		        	offspring2Gene -= scndParent.getChromosomeLength();
				}
		        
				// filling the remaining chromosomes of offspring1 first parent
				if (offspring1.containsGene(frstParent.getGene(parent1Gene)) == false) {
					if (offspring1.getGene(offspring1Gene) == -1) { // prevent duplicate genes
						offspring1.setGene(offspring1Gene, frstParent.getGene(parent1Gene));
					}
					offspring1Gene= offspring1Gene+1;// when a gene is added the index of the first offspring increases
				}
				// filling the remaining chromosomes of offspring2 with the second parent
				if (offspring2.containsGene(scndParent.getGene(parent2Gene)) == false) {
					if (offspring2.getGene(offspring2Gene) == -1) { // prevent duplicate genes
						offspring2.setGene(offspring2Gene, scndParent.getGene(parent2Gene));
					}
					offspring2Gene= offspring2Gene+1;// when a gene is added the index of the second offspring increases
				}

			}
			offSpringList.add(offspring1);
			offSpringList.add(offspring2);
		   return offSpringList;
	   }

	   private Individual mutateOffspring(int populationIndx , Individual offspring) {
		   if (populationIndx >= this.elitismCount) {   
	            for (int geneIndex = 0; geneIndex < offspring.getChromosomeLength(); geneIndex++) {   
	                    if (this.mutationRate > Math.random()) {
	                    	//randomly choose a gene
	                    int newGenePos = (int) (Math.random() * offspring.getChromosomeLength());
	                    int gene1 = offspring.getGene(newGenePos);
	                    int gene2 = offspring.getGene(geneIndex);
	                    // swap the randomly choosen gene with the current gene
	                    offspring.setGene(geneIndex, gene1);
	                    offspring.setGene(newGenePos, gene2);
	                }
	            }
	        }
		   
		   return offspring;
	   } 
		public boolean isTerminationConditionMet(int generationsCount, int maxGenerations) {
			return (generationsCount > maxGenerations);
		}
	
public Population mutatePopulation(Population population){
	
	Population newPopulation = new Population(this.populationSize);
	
	for (int populationIndex = 0; populationIndex < population.size(); populationIndex += 2) {
		Individual offSpring1 = population.getFittest(populationIndex);
		offSpring1 = mutateOffspring(populationIndex, offSpring1);
		newPopulation.setIndividual(populationIndex, offSpring1);
		// handle last round of odd populations
		if (populationIndex < population.size() - 1) {
			Individual offSpring2 = population.getFittest(populationIndex + 1);
			offSpring2 = mutateOffspring(populationIndex + 1, offSpring2);
			newPopulation.setIndividual(populationIndex + 1, offSpring2);
		}

	}

    return newPopulation;
}

public double findTourDistance(Individual individual,City cityList[])  {
	Tour bestTour = new Tour(individual, cityList);
	return bestTour.getDistance();
}


// compare the fitness of the fittest individual of a population and the best individual from the past generations
public Individual findBestIndividual(Population population, Individual bestIndividual)  {
	if (bestIndividual==null)
		return population.getFittest(0);
	else{
		Individual bestIndividualInPopualtion = population.getFittest(0);
		double bestIndividualfitness= bestIndividual.getFitness();
		double bestIndividualInPopualtionFitness= bestIndividualInPopualtion.getFitness();
        return bestIndividualInPopualtionFitness>bestIndividualfitness?bestIndividualInPopualtion:bestIndividual;

		}


}


}

