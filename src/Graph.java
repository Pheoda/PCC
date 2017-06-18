import java.util.ArrayList;

public class Graph {
    private final int MAX_DISTANCE = 30;

    private ArrayList<City> cities;

    public Graph(Reader r) {
        this.cities = new ArrayList<>();

        City cityRead;

        do {
            cityRead = r.read();
            if (cityRead != null) {
				if(cityRead.getPopulation() >= 1800)
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
	public ArrayList<City> a_star(City cityStart, City cityEnd) {
		int nbArc = 1;
		ArrayList<Node> openList = new ArrayList<>();
		ArrayList<Node> closeList = new ArrayList<>();
		
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
		return buildPath(closeList);		
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