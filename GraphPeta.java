import java.util.*;

public class GraphPeta {
    Map<Node, List<Edge>> graph;
    Map<String, Node> nodeMap;

    public GraphPeta() {
        graph = new HashMap<>();
        nodeMap = new HashMap<>();
    }

    public Node tambahNode(String nama) {
        Node n = new Node(nama);
        nodeMap.put(nama, n);
        graph.put(n, new ArrayList<>());
        return n;
    }

    public void tambahJalur(String a, String b, double jarak, int waktu) {
        Node nodeA = nodeMap.get(a);
        Node nodeB = nodeMap.get(b);

        if (nodeA == null) nodeA = tambahNode(a);
        if (nodeB == null) nodeB = tambahNode(b);

        graph.get(nodeA).add(new Edge(nodeB, jarak, waktu));
        graph.get(nodeB).add(new Edge(nodeA, jarak, waktu)); // dua arah
    }

    public boolean bisaPergiBFS(String asal, String tujuan) {
        Node start = nodeMap.get(asal);
        Node end = nodeMap.get(tujuan);

        Queue<Node> q = new LinkedList<>();
        Set<Node> visited = new HashSet<>();

        q.add(start);
        visited.add(start);

        while (!q.isEmpty()) {
            Node now = q.poll();

            if (now == end) return true;

            for (Edge e : graph.get(now)) {
                if (!visited.contains(e.tujuan)) {
                    visited.add(e.tujuan);
                    q.add(e.tujuan);
                }
            }
        }
        return false;
    }

    public boolean bisaPergiDFS(String asal, String tujuan) {
        return dfsCheck(nodeMap.get(asal), nodeMap.get(tujuan), new HashSet<>());
    }

    private boolean dfsCheck(Node now, Node tujuan, Set<Node> visited) {
        if (now == tujuan) return true;
        visited.add(now);

        for (Edge e : graph.get(now)) {
            if (!visited.contains(e.tujuan)) {
                if (dfsCheck(e.tujuan, tujuan, visited)) return true;
            }
        }
        return false;
    }

    public void pergiBFS(String asal, String tujuan) {
        Node start = nodeMap.get(asal);
        Node end = nodeMap.get(tujuan);

        Map<Node, Node> parent = new HashMap<>();
        Map<Node, Double> dist = new HashMap<>();
        Map<Node, Integer> time = new HashMap<>();
        Set<Node> visited = new HashSet<>();
        Queue<Node> q = new LinkedList<>();

        for (Node n : graph.keySet()) {
            dist.put(n, Double.POSITIVE_INFINITY);
            time.put(n, Integer.MAX_VALUE);
        }

        q.add(start);
        visited.add(start);
        dist.put(start, 0.0);
        time.put(start, 0);

        while (!q.isEmpty()) {
            Node now = q.poll();

            if (now == end) break;

            for (Edge e : graph.get(now)) {
                if (!visited.contains(e.tujuan)) {
                    visited.add(e.tujuan);
                    q.add(e.tujuan);

                    parent.put(e.tujuan, now);
                    dist.put(e.tujuan, dist.get(now) + e.jarak);
                    time.put(e.tujuan, time.get(now) + e.waktu);
                }
            }
        }

        if (!parent.containsKey(end) && start != end) {
            System.out.println("Jalur tidak dapat dijangkau");
            return;
        }

        List<String> path = new ArrayList<>();
        Node curr = end;
        path.add(curr.nama);

        while (curr != start) {
            curr = parent.get(curr);
            path.add(curr.nama);
        }

        Collections.reverse(path);

        System.out.println("\nMenggunakan BFS:");
        System.out.println("Total jarak: " + dist.get(end) + " km");
        System.out.println("Total waktu: " + time.get(end) + " menit");
        System.out.println("Jalur: " + path);
    }

    public void pergiDFS(String asal, String tujuan) {
        Set<Node> visited = new HashSet<>();
        List<String> path = new ArrayList<>();
        double[] jarak = {0};
        int[] waktu = {0};

        boolean bisa = dfsPath(nodeMap.get(asal), nodeMap.get(tujuan), visited, path, jarak, waktu);

        if (!bisa) {
            System.out.println("Jalur tidak dapat dijangkau");
            return;
        }

        System.out.println("\nMenggunakan DFS:");
        System.out.println("Total jarak: " + jarak[0] + " km");
        System.out.println("Total waktu: " + waktu[0] + " menit");
        System.out.println("Jalur: " + path);
    }

    private boolean dfsPath(Node now, Node tujuan, Set<Node> visited,
                            List<String> path, double[] jarak, int[] waktu) {

        visited.add(now);
        path.add(now.nama);

        if (now == tujuan) return true;

        for (Edge e : graph.get(now)) {
            if (!visited.contains(e.tujuan)) {

                jarak[0] += e.jarak;
                waktu[0] += e.waktu;

                if (dfsPath(e.tujuan, tujuan, visited, path, jarak, waktu)) {
                    return true;
                }

                jarak[0] -= e.jarak;
                waktu[0] -= e.waktu;
                path.remove(path.size() - 1);
            }
        }
        return false;
    }

    public void jalurTerpendek(String asal, String tujuan) {
        Node start = nodeMap.get(asal);
        Node end = nodeMap.get(tujuan);

        Map<Node, Double> dist = new HashMap<>();
        Map<Node, Node> parent = new HashMap<>();

        for (Node n : graph.keySet()) dist.put(n, Double.POSITIVE_INFINITY);

        dist.put(start, 0.0);

        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingDouble(dist::get));
        pq.add(start);

        while (!pq.isEmpty()) {
            Node now = pq.poll();

            for (Edge e : graph.get(now)) {
                double baru = dist.get(now) + e.jarak;

                if (baru < dist.get(e.tujuan)) {
                    dist.put(e.tujuan, baru);
                    parent.put(e.tujuan, now);
                    pq.add(e.tujuan);
                }
            }
        }

        if (dist.get(end) == Double.POSITIVE_INFINITY) {
            System.out.println("Jalur tidak dapat dijangkau");
            return;
        }

        List<String> path = new ArrayList<>();
        Node curr = end;
        path.add(curr.nama);

        while (curr != start) {
            curr = parent.get(curr);
            path.add(curr.nama);
        }

        Collections.reverse(path);

        System.out.println("\n=== Dijkstra ===");
        System.out.println("Jarak terpendek = " + dist.get(end) + " km");
        System.out.println("Path: " + path);
    }
}
