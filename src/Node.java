public class Node {
	private City father;
	private City current;
	private int cost;

	public Node(City father, City current, int cost) {
		this.father = father;
		this.current = current;
		this.cost = cost;
	}

	public City getFather() {
		return father;
	}

	public void setFather(City father) {
		this.father = father;
	}

	public City getCurrent() {
		return current;
	}

	public void setCurrent(City current) {
		this.current = current;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}
}
