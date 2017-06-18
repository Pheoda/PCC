import java.util.ArrayList;

public class Graph {
    private final int MAX_DISTANCE = 500;
	private final int MIN_POPULATION = 200000;
	private final double INFINITE = Double.POSITIVE_INFINITY;

    private ArrayList<City> cities;

    public Graph(Reader r) {
        this.cities = new ArrayList<>();

        City cityRead;

        do {
            cityRead = r.read();
            if (cityRead != null) {
				if(cityRead.getPopulation() >= MIN_POPULATION)
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

    private void setRadiusFromCity(City c) {
        for (int i = 0; i < cities.size(); i++) {
            if (cities.get(i) != c)
                if (c.distance(cities.get(i)) < MAX_DISTANCE) {
                    c.addAdjacent(cities.get(i));
                }
        }
    }

	
	// Construction du graphe pour Dijkstra
    private void build() {
        cities.forEach(this::setRadiusFromCity);
    }
	
	// ATTENTION : on vide la variable cities dans cette fonction !
	// Plus tard : implémenter toDo deep copy de cities (avec adjacents !)
	public void dijkstra(City cityStart, City cityEnd) {
		ArrayList<Distance> result = new ArrayList<>();
		//ArrayList<City> toDo = new ArrayList<>(); // Deep copy de cities
		
		System.out.println("Starting Dijkstra");
		
		build(); // Remplit le graphe avec les villes adjacentes
		
		System.out.println("Build finished");
		
		// Deep copy here
		
		System.out.println("Deep copy finished");
		
		result.add(new Distance(cityStart, 0));
		cities.remove(cityStart);
		
		for(City c : cities) {
			double value;
			if(cityStart.isAdjacent(c))
				value = cityStart.distance(c);
			else
				value = INFINITE;
			result.add(new Distance(c, value));
		}
		while(!cities.isEmpty()) {
			// On prend le minimum
			Distance minValue = new Distance(null, INFINITE);
			for(Distance d : result)
				if(cities.contains(d.getCity()))
					if(minValue.getDistance() >= d.getDistance())
						minValue = d;
			
			//System.out.println("MIN VALUE : " + minValue.getDistance() + "\n" + minValue.getCity());
			cities.remove(minValue.getCity());
			
			// Check des successeurs et possible maj des distances
			for(City c : minValue.getCity().getAdjacent()) {
				if(cities.contains(c)) {
					Distance distance = search(result, c);
					double calculatedValue = minValue.getDistance() + minValue.getCity().distance(c);
					if(calculatedValue < distance.getDistance())
						distance.setDistance(calculatedValue);
				}
			}
		}
		
		System.out.println("Distance entre les 2 villes : " + getDistanceFromDijkstra(result, cityEnd));
		
		
		/*System.out.println("\n\n====AFFICHAGE RESULTAT====");
		for(Distance d : result) {
			System.out.println("Ville : " + d.getCity() + "\nDistance : " + d.getDistance() + "\n\n");
		}
		System.out.println("================\n\n");*/
	}
	
	private Distance search(ArrayList<Distance> array, City c) {
		for(Distance d : array) {
			if(d.getCity() == c)
				return d;
		}
		return null;
	}
	
	private double getDistanceFromDijkstra(ArrayList<Distance> result, City city) {
		for(Distance d : result) {
			if(d.getCity() == city)
				return d.getDistance();
		}
		return -1; // Retourne -1 en cas d'erreur
	}
	
	
	/*Suggestion si la ville d'arrivée est une ville de plus de 15 000 hab
	on considère qu'il y a de meilleurs routes donc trajet plus rapide */
	public static ArrayList<City> a_star(City cityStart, City cityEnd) {
		//On vérifie la validité des IDs
		/*for (City c : cities) {
			if () {
				
			}
		}*/
		int nbArc = 1;
		ArrayList<Node> openList = new ArrayList<>();
		ArrayList<Node> closeList = new ArrayList<>();
		
		Node startNodes = new Node(null, cityStart, 0);
		
		closeList.add(startNodes);
		
		while (closeList.get(closeList.size() - 1).getCurrent() != cityEnd) {
			//On ajoute les successeurs à openList si ils n'y sont pas déjà
			City curCity = closeList.get(closeList.size() - 1).getCurrent();
			for (City c : curCity.getAdjacent()) {
				for (Node opNode : openList) {
					if (!opNode.getCurrent().equals(c)) 
						openList.add(new Node(curCity, c, nbArc + (int)c.distance(cityEnd)));
				}
			}

			//on prend le nœud ayant le cout le moins élevé
			Node node2Add = openList.get(0);
			for (Node n : openList) {
				if (node2Add.getCost() > n.getCost()) {
					node2Add = n;
				}
			}
			openList.remove(node2Add);
			closeList.add(node2Add);

			nbArc++;
		}
		
		return buildPath(closeList);		
	}
	
	private static ArrayList<City> buildPath(ArrayList<Node> listNode) {
		ArrayList<City> finalCityList = new ArrayList<>();

		int i = listNode.size() - 1;
		
		Node n = listNode.get(i);
		finalCityList.add(n.getCurrent());
		
		//Tant que le nœud traité n'est pas le nœud de départ
		while(n != listNode.get(0)) {
			for (Node next : listNode) {
				if (next.getCurrent() == n.getFather()) {
					finalCityList.add(next.getCurrent());
					n = next;
					break;
				}
			}
		}
		
		return finalCityList;
	}
	
	
	
}