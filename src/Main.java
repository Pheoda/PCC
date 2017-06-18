import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        Graph g = new Graph(new Reader("CommunesFrance.csv"));

		LocalDateTime start = LocalDateTime.now();
        System.out.print("Début à ");
        System.out.println(start.getHour() + ":" + start.getMinute() + ":" + start.getSecond());
		
		///// A* /////
		/*ArrayList<City> result = g.a_star(g.getCityFromId("lyon"), g.getCityFromId("lille"));
		for (City c : result) {
			System.out.println(c.getName());
		}*/
		
		///// DIJKSTRA /////
        g.dijkstra(g.getCityFromId("brest"), g.getCityFromId("cannes"));
		

		LocalDateTime end = LocalDateTime.now();
		System.out.print("Fin à ");
        System.out.println(end.getHour() + ":" + end.getMinute() + ":" + end.getSecond());
		System.out.print("\nTemps d'exécution : ");
		Duration duration = Duration.between(start, end);
		System.out.println(duration.getSeconds() + " secondes\n");
	}

}
