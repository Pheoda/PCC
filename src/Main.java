public class Main {

    public static void main(String[] args) {
        Graph g = new Graph(new Reader("CommunesFrance.csv"));

        System.out.println("Start building.");
        g.build();
        System.out.println("Done.");
    }
}
