public class Main {

    public static void main(String[] args) {
        Graph g = new Graph(new Reader("CommunesFrance.csv"));

		g.getRadiusFromCity(g.getCityFromId("anse"));
    }
}
