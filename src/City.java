import java.util.ArrayList;

public class City {
    private String id;
    private String name;
    private int population;
    private double latitude;
    private double longitude;
	
	private double distance; // Dijkstra distance

    private ArrayList<City> adjacent;

    public City(String id, String name, int population, double latitude, double longitude) {
        this.id = id;
        this.name = name;
        this.population = population;
        this.latitude = latitude;
        this.longitude = longitude;
        this.adjacent = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPopulation() {
        return population;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public ArrayList<City> getAdjacent() {
        return adjacent;
    }
	
	public boolean isAdjacent(City c) {
		return adjacent.contains(c);
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

    @Override
    public String toString() {
        return "City{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", population=" + population +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

    public double distance(City c) {
        final int R = 6371; // Earth radius in km
        double p1 = latitude * Math.PI / 180;
        double p2 = c.getLatitude() * Math.PI / 180;
        double d1 = (c.getLatitude() - latitude) * Math.PI / 180;
        double d2 = (c.getLongitude() - longitude) * Math.PI / 180;

        double a = Math.sin(d1 / 2) * Math.sin(d1 / 2) +
                Math.cos(p1) * Math.cos(p2) *
                        Math.sin(d2 / 2) * Math.sin(d2 / 2);
        double b = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * b;
    }

    public void addAdjacent(City c) {
        this.adjacent.add(c);
    }

}
