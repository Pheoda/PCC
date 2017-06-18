import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {
        Graph g = new Graph(new Reader("CommunesFrance.csv"));

        System.out.println("Start building.");
        System.out.println(LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute() + ":" + LocalDateTime.now().getSecond());
        //g.dijkstra(g.getCityFromId("lyon"), g.getCityFromId("lille"));
        System.out.println("Done.");
        System.out.println(LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute() + ":" + LocalDateTime.now().getSecond());
    

	}
}
