package TSP;


public class City {
		private Double x;
		private Double y;

		public City(Double x, Double y) {
			this.x = x;
			this.y = y;
		}

        // it is called in a tour class to calculate the distance between to adjacent cities
		public double calculateDistance(City city) {
			double deltaXSq = Math.pow((city.getX() - this.getX()), 2);
			double deltaYSq = Math.pow((city.getY() - this.getY()), 2);
			double distance = Math.sqrt(Math.abs(deltaXSq + deltaYSq));
			return distance;
		}

		public Double getX() {
			return this.x;
		}

		public Double getY() {
			return this.y;
		}


		@Override
		public String toString() {
			return "City [x=" + x + ", y=" + y + "]";
		}

		
		
}
