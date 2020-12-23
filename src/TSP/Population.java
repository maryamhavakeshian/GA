package TSP;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.Set;

public class Population {

	private Individual population[];
	
	
	
	public Individual setIndividual(int offset, Individual individual) {
		return population[offset] = individual;
	}

	public Individual getIndividual(int offset) {
		return population[offset];
	}
	
	public Population(int populationSize) {
					this.population = new Individual[populationSize];
	} 
	//creates all the individuals of the population
	public Population(int populationSize,Set<Integer> cityMapKeys) {
		this.population = new Individual[populationSize];
		for (int IndividualCount=0; IndividualCount< populationSize ; IndividualCount++) {
		 Individual individual = new Individual(cityMapKeys);
		 this.population[IndividualCount]= individual;
		}
	}
	public Individual[] getIndividuals() {
		return this.population;
	}
	//compare individuals' fitness and sort them, so the first node of sorted array is the fittest
	public Individual getFittest(int offset) {
		// Order population by fitness
		Arrays.sort(this.population, new Comparator<Individual>() {
			@Override
			public int compare(Individual o1, Individual o2) {
				if (o1.getFitness() > o2.getFitness()) {
					return -1;
				} else if (o1.getFitness() < o2.getFitness()) {
					return 1;
				}
				return 0;
			}
		});
		return population[offset];
}
	
	//return number of individuals
	public int size() {
		return this.population.length;
	}

	// randomly shuffle the population 
	public void shuffle() {
		Random rnd = new Random();
		for (int i = population.length - 1; i > 0; i--) {
			int index = rnd.nextInt(i + 1);
			Individual a = population[index];
			population[index] = population[i];
			population[i] = a;
		}
	}
	
	
}
