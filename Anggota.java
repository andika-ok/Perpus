/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package peminjaman;
import bagianinterface.NIMAnggota;
import koneksi.MySQLConnection;
import java.sql.*;
import java.util.Scanner;
import static peminjaman.Book.input;
/**
 *
 * @author Andika
 */
public class Anggota implements NIMAnggota{
     String idAnggota;
     String nama;
     String nim;
     String alamat;
 
    public String getID(){
         System.out.print("Masukkan ID anggota : ");
         Scanner a = new Scanner(System.in);
         return idAnggota = a.nextLine();
     }
     
     public String getNama(){
         System.out.print("Masukkan nama anggota : ");
         Scanner a = new Scanner(System.in);
         return nama = a.nextLine();
     }
     
    @Override
    public String getNIM() {
        System.out.print("Masukkan nim anggota : ");
        Scanner n = new Scanner(System.in);
        return nim = n.nextLine();
    }
    
    
    public String getAlamat(){
        System.out.print("Masukkan alamat anggota : ");
        Scanner a = new Scanner(System.in);
        return alamat = a.nextLine();
    }
     
     //insert data anggota
     public void insert(MySQLConnection m, String nama, String nim, String alamat){
         
         //melakukan koneksi kemysql
         Connection koneksi = m.conn;
         
         // query sql untuk insert data buku
        String sql = "INSERT INTO anggota (nama, nim, alamat) VALUES (?, ?, ?)";
        
        try {
            PreparedStatement statement = koneksi.prepareStatement(sql);
            
            // mapping nilai parameter dari query sql nya (sesuai urutan)
            statement.setString(1, nama);
            statement.setString(2, nim);
            statement.setString(3, alamat);

            // jalankan query (baca jumlah row affectednya)
            int rowsInserted = statement.executeUpdate();
            // jika ada row affected nya, maka status sukses
            if (rowsInserted > 0) {
                System.out.println("Insert data anggota sukses");
            }

        } catch (SQLException ex) {
            // jika query gagal
            System.out.println("Insert data anggota gagal");
        }
     }
     
     // delete data buku berdasarkan idbook
    public void delete(MySQLConnection m){
        
        getID();
        // query sql untuk hapus data buku berdasarkan idbook
        String sql = "DELETE FROM anggota WHERE idAnggota=?";
        // lakukan koneksi ke mysql
        Connection koneksi = m.conn;
        
        try {
            PreparedStatement statement;
            statement = koneksi.prepareStatement(sql);
            
            // mapping nilai parameter dari query sql nya
             statement.setString(1, String.valueOf(idAnggota));
            
            // jalankan query, dan lihat jumlah row affected nya
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Data anggota sudah berhasil dihapus");
            }
        } catch (SQLException ex) {
            System.out.println("Hapus data anggota gagal");
        }
        
    }
    
    // update data buku berdasarkan idbook
    public void update(MySQLConnection m, String idAnggota, String nama, String nim, String alamat){
        
        // query sql untuk update data buku berdasarkan idbook
        String sql = "UPDATE anggota SET nama=?, nim=?, alamat=? WHERE idAnggota=?";
        // lakukan koneksi ke mysql
        Connection koneksi = m.conn;
        
        try {
            PreparedStatement statement = koneksi.prepareStatement(sql);
            // mapping nilai parameter ke query sqlnya
            statement.setString(1, nama);
            statement.setString(2, nim);
            statement.setString(3, alamat);
            statement.setString(4, idAnggota);


            // jalankan query, dan baca jumlah row affectednya
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Update data anggota sukses");
            }
        } catch (SQLException ex) {
             System.out.println("Update data anggota gagal");
        }
    }
    
    // tampilkan semua data buku
    public void select(MySQLConnection m){
        
        // query sql untuk select all data buku
        String sql = "SELECT * FROM anggota";
        
        // lakukan koneksi ke mysql
        Connection koneksi = m.conn;
        
        try {
            Statement statement = koneksi.createStatement();
            // jalankan query
            ResultSet result = statement.executeQuery(sql);

            // membuat header table untuk output
            System.out.println("==============================================================================");
            String header = "%3s %20s %20s %4s";
            System.out.println(String.format(header, "ID", "NAMA", "NIM", "ALAMAT"));
            System.out.println("------------------------------------------------------------------------------");
            
            // looping untuk baca data per record
            while (result.next()){
                // baca data buku per record
                String idAnggota = result.getString("idAnggota");
                String nama = result.getString("nama");
                String nim = result.getString("nim");
                String alamat = result.getString("alamat");
                // tampilkan data buku per record
                String output = "%3s %20s %20s %4s";
                System.out.println(String.format(output, idAnggota, nama, nim, alamat));
            }
            
            System.out.println("==============================================================================");
            
        } catch (SQLException ex){
            System.out.println("Tampil data anggota gagal");
        }
        
                //  Tampilan menu
    System.out.println("\n========= MENU Anggota =========");
    System.out.println("1. Tambah Anggota");
    System.out.println("2. Update Anggota");
    System.out.println("3. Hapus Anggota");
    System.out.println("0. Keluar");
    System.out.println("");
    System.out.print("PILIHAN> ");

    try {
        
        MySQLConnection db1 = new MySQLConnection("localhost", "dbpeminjaman", "root", "");
        Anggota a1 = new Anggota();

        switch (input.nextInt()) {
            
            case 0:
                System.exit(0);
                break;
                
            case 1:
                a1.insert(db1, a1.getNama(), a1.getNIM(), a1.getAlamat());
                break;
        
            case 2:
               a1.update(db1, a1.getID(), a1.getNama(), a1.getNIM(), a1.getAlamat());

                break;
            
            case 3:
                a1.delete(db1);

                break;

               
            default:
                System.out.println("Pilihan salah!");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
        
    }

}

