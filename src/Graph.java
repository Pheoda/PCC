import java.util.ArrayList;

public class Graph {
	private final int MAX_DISTANCE = 20;
	ArrayList<City> cities;

	public Graph(Reader r) {
		this.cities = new ArrayList<>();

		City cityRead = null;

		do {
			cityRead = r.read();
			if(cityRead != null) {
				cities.add(cityRead);
			}
			else {
				System.out.println("Lecture ville nulle, length = " + cities.size());
			}
		}while(cityRead != null);
	}

	public City getCityFromId(String id) {
		int i = 0;
		while(i < cities.size()) {
			if (cities.get(i).getId().equals(id))
				return cities.get(i);
			i++;
		}
		return null;
	}

	public void getRadiusFromCity(City c) {
		int cpt = 0;
		for (int i = 0; i < cities.size(); i++) {
			if (cities.get(i) != c)
				if (c.distance(cities.get(i)) < MAX_DISTANCE) {
					System.out.println(cities.get(i));
					cpt++;
				}
		}
		System.out.println(cpt + " results...");
	}
}