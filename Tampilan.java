/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package peminjaman;

import java.util.Scanner;
import koneksi.MySQLConnection;

/**
 *
 * @author Andika
 */
public class Tampilan {
    
    public static Scanner input = new Scanner(System.in);

    public static void showMenu() {
    System.out.println("\n========= MENU UTAMA =========");
    System.out.println("1. Buku");
    System.out.println("2. Peminjaman");
    System.out.println("3. Anggota");
    System.out.println("0. Keluar");
    System.out.println("");
    System.out.print("PILIHAN> ");
    
    MySQLConnection db1 = new MySQLConnection("localhost", "dbpeminjaman", "root", "");

    try {
    
        switch (input.nextInt()) {
            case 0:
                System.exit(0);
                break;
            case 1:
                Book buku = new Book();
                buku.select(db1);
                break;
            case 2:
                Peminjaman peminjaman = new Peminjaman();
                peminjaman.select(db1);
                break;
            case 3:
                Anggota anggota = new Anggota();
                anggota.select(db1);
                break;
            case 4:
                break;
            default:
                System.out.println("Pilihan salah!");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}
}
