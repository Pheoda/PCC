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
				if(cityRead.getPopulation() >= 3000)
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
		// On ne prend pas toutes les villes du graphe pour raccourcir le parcours
		/*for(int i = 0; i < cities.size(); i++) {
			
				cities.remove(i);
		}*/
        cities.forEach(this::setRadiusFromCity);
    }
	
	
	//Suggestion si la ville d'arriver est une ville de plus de 15 000 hab
	//on considère qu'il y a de meilleurs routes donc trajet plus rapide
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