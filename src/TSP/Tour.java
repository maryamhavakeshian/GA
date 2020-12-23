package TSP;



public class Tour {
	private City cityTour[];
	private double distance = 0;

	public Tour(Individual individual, City cities[]) {
		int chromosome[] = individual.getChromosome();
		this.cityTour = new City[cities.length];
		//map the city index extracted from the input file and an array of Chromosome is started from zero 
		// index of city is started from number 1, while the index of a
		for (int geneIndex = 0; geneIndex < chromosome.length; geneIndex++) {
			this.cityTour[geneIndex] = cities[chromosome[geneIndex]-1];
		}
	}

	public double getDistance() {
		if (this.distance > 0) {
			return this.distance;
		}

		double totalDistance = 0;
		//calculate the distance between two adjacent node
		for (int cityIndex = 0; cityIndex + 1 < this.cityTour.length; cityIndex++) {
			totalDistance += this.cityTour[cityIndex].calculateDistance(this.cityTour[cityIndex + 1]);
		}

		// calculate the Hamiltonian path which connect the first and the last node
		totalDistance += this.cityTour[this.cityTour.length - 1].calculateDistance(this.cityTour[0]);
		this.distance = totalDistance;

		return totalDistance;
	}
}
