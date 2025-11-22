import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        GraphPeta g = new GraphPeta();

        // Contoh data
        g.tambahJalur("Stasiun Kota Baru", "Alun-Alun Malang", 2.5, 10);
        g.tambahJalur("Alun-Alun Malang", "Malang City Point", 5, 15);
        g.tambahJalur("Stasiun Kota Baru", "Museum Brawijaya", 4, 12);
        g.tambahJalur("Museum Brawijaya", "Matos", 3, 10);
        g.tambahJalur("Matos", "Universitas Brawijaya", 3, 10);
        g.tambahJalur("Museum Brawijaya", "Universitas Brawijaya", 2, 10);

        System.out.print("Masukkan titik asal: ");
        String asal = in.nextLine();

        System.out.print("Masukkan titik tujuan: ");
        String tujuan = in.nextLine();

        System.out.println("\n---HASIL---");

        boolean bisa = g.bisaPergiBFS(asal, tujuan);
        System.out.println("(BFS) Dari " + asal + " ke " + tujuan + ": " + (bisa ? "Dapat dijangkau" : "Tidak dapat"));

        g.pergiBFS(asal, tujuan);
        g.pergiDFS(asal, tujuan);

        g.jalurTerpendek(asal, tujuan);

        in.close();
    }
}
