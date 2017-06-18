import java.util.ArrayList;

public class Graph {

    private final int MAX_DISTANCE = 30;
	private final int MIN_POPULATION = 1800;
	private final double INFINITE = Double.POSITIVE_INFINITY;

    private ArrayList<City> cities;

    public Graph(Reader r) {
        this.cities = new ArrayList<>();

        City cityRead;

        do {
            cityRead = r.read();
            if (cityRead != null) {
				//System.out.println("sdf");
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
	

	public void dijkstra(City cityStart, City cityEnd) {
		ArrayList<Distance> result = new ArrayList<>();
		
		//System.out.println("Starting Dijkstra");
		
		build(); // Remplit le graphe avec les villes adjacentes
		
		//System.out.println("Build finished");
		
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
		
		System.out.println("Distance entre " + cityStart.getName() + " et " + cityEnd.getName() + " : " + getDistanceFromDijkstra(result, cityEnd));
		
		
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
	
	
	//Suggestion si la ville d'arrivée est une ville de plus de 15 000 hab
	//on considère qu'il y a de meilleurs routes donc trajet plus rapide
	public void a_star(City cityStart, City cityEnd) {
		int nbArc = 1;
		ArrayList<Node> openList = new ArrayList<>();
		ArrayList<Node> closeList = new ArrayList<>();
		this.build();
		
		Node currentNode = new Node(null, cityStart, 0);
		currentNode.setCost((int) currentNode.getCurrent().distance(cityEnd));
		
		while (currentNode.getCurrent().getId() != cityEnd.getId()) {
			closeList.add(currentNode);
			openList.remove(currentNode);
			
			//On ajoute les successeurs à openList si ils n'y sont pas déjà
			ArrayList<Node> tempNode2Add = new ArrayList<>();
			
			for (City c : currentNode.getCurrent().getAdjacent()) {
				int val = nbArc * 10 + (int)c.distance(cityEnd);
				tempNode2Add.add(new Node(currentNode.getCurrent(), c, val));
			}
			concat(openList, tempNode2Add);
			tempNode2Add = null;
			
			currentNode = calculLeastCost(openList);
			
			if (closeList.size() > cities.size() * 100) {
				System.err.println("Algo coincé au dans un trou perdu, reduire la restriction sur la population");
				System.exit(1);
			}
			

			nbArc++;
		}
		closeList.add(currentNode);
		
		//affichage des villes traversées
		for (City c : buildPath(closeList))
			System.out.println(c.getName());
		
	}
	
	private static void concat(ArrayList<Node> list1, ArrayList<Node> list2) {
		if (list1.isEmpty() || list2.isEmpty())
			list1.addAll(list2);
		else {
			for (Node n2 : list2) {
				if (n2.isNotIn(list1))
					list1.add(n2);
			}
		}
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

	private static Node calculLeastCost(ArrayList<Node> openList) {
		Node node2Select = null;
		
		for (Node n : openList) {
			if (node2Select == null || node2Select.getCost() > n.getCost())
				node2Select = n;
		}
		
		return node2Select;
	}
	
	
	
	
}