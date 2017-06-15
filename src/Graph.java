import java.util.ArrayList;

public class Graph {
    private final int MAX_DISTANCE = 20;

    private ArrayList<City> cities;

    public Graph(Reader r) {
        this.cities = new ArrayList<>();

        City cityRead;

        do {
            cityRead = r.read();
            if (cityRead != null) {
                //if(cityRead.getPopulation() > 2000)
                cities.add(cityRead);
            } else {
                System.out.println("Lecture ville nulle, length = " + cities.size());
            }
        } while (cityRead != null);
    }

    public City getCityFromId(String id) {
        int i = 0;
        while (i < cities.size()) {
            if (cities.get(i).getId().equals(id))
                return cities.get(i);
            i++;
        }
        return null;
    }

    public void setRadiusFromCity(City c) {
        for (int i = 0; i < cities.size(); i++) {
            if (cities.get(i) != c)
                if (c.distance(cities.get(i)) < MAX_DISTANCE) {
                    c.addAdjacent(cities.get(i));
                }
        }
    }

    public void build() {
        cities.forEach(this::setRadiusFromCity);
    }
}