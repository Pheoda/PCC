public class Distance {
	private City c;
	private double distance;

	public Distance(City c, double distance) {
		this.c = c;
		this.distance = distance;
	}
	
	public double getDistance() {
		return distance;
	}
	
	public City getCity() {
		return c;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}
}
