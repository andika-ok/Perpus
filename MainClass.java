/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import koneksi.MySQLConnection;
import peminjaman.Anggota;
import peminjaman.Book;
import peminjaman.Peminjaman;
import java.util.Scanner;
import peminjaman.Tampilan;

/**
 *
 * @author Andika
 */
public class MainClass {
    
        public static void main(String[] args) {
        MySQLConnection db1 = new MySQLConnection("localhost", "dbpeminjaman", "root", "");
        Peminjaman p1 = new Peminjaman();
//        p1.insert(db1, p1.getBukuPinjam(), p1.getTglPinjam(), p1.getTglKembali(), p1.setDendaAnggota());
//        p1.delete(db1,3);
//        p1.select(db1);
        
        Anggota a1 = new Anggota();
//        a1.insert(db1, a1.getNama(), a1.getNIM(), a1.getAlamat());
//        a1.delete(db1, 2);
//        a1.update(db1, a1.getID(), a1.getNama(), a1.getNIM(), a1.getAlamat());
//        a1.select(db1);
        
        Book b1 = new Book();
//        b1.insert(db1, b1.getNamaBuku(), b1.getNamaPenulis(), b1.getPenerbit(), b1.getTahunTerbit());
//        b1.delete(db1, 1);
//        b1.update(db1, b1.getIDBuku(), b1.getNamaBuku(), b1.getNamaPenulis(), b1.getPenerbit(), b1.getTahunTerbit());
//        b1.select(db1);
        Tampilan tampil = new Tampilan();
        tampil.showMenu();
        db1.close();
    }
}
