import java.util.ArrayList;
import java.util.Objects;


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

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Node other = (Node) obj;
		/*if (!Objects.equals(this.current, other.current)) {
			return false;
		}*/
		return other.current.getName() == this.getCurrent().getName();
	}

	public boolean isNotIn(ArrayList<Node> list) {
		for (Node n : list) {
			if (n.current.getId() == this.current.getId()) {
				return false;
			}
		}
		return true;
	}

}
