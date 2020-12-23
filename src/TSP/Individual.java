package TSP;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class Individual {
	private int[] chromosome;
	private double fitness = -1;
	
	public Individual(int[] chromosome) {
		this.chromosome = chromosome;
	}
	public Individual(int chromosomeLength) {
		this.chromosome = new int[chromosomeLength];
	}
	
	
	//citymapKeys is the keys of hash map which is created in the cityEngine class when the class read
	//the tsp file. citymapkeys is the set of IDs of cities
	public Individual(Set<Integer> cityMapKeys) {
		
		//randomized permutation
		List<Integer> aList = cityMapKeys.stream().collect(Collectors.toList()); 
	    Collections.shuffle(aList, new Random()); 
	    int[] primitive = aList.stream()
				.mapToInt(Integer::intValue)
				.toArray();
	    this.chromosome = primitive; 

		
	}

	
	public int[] getChromosome() {
		return this.chromosome;
	}
	
	public int getChromosomeLength() {
		return this.chromosome.length;
	}
	public void setGene(int offset, int gene) {
		this.chromosome[offset] = gene;
	}


	public int getGene(int offset) {
		return this.chromosome[offset];
	}


	public void setFitness(double fitness) {
		this.fitness = fitness;
	}


	public double getFitness() {
		return this.fitness;
	}

	public String toString() {
		String output = "";
		for (int gene = 0; gene < this.chromosome.length; gene++) {
			output += this.chromosome[gene] +  "\n" ;
		}
		return output;
	}
	// to check duplicate city. it prevents adding duplicate cities in crossover operator
	public boolean containsGene(int gene) {
		for (int i = 0; i < this.chromosome.length; i++) {
			if (this.chromosome[i] == gene) {
				return true;
			}
		}
		return false;
	}

}
