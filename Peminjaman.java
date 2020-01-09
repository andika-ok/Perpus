/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package peminjaman;
import bagianabstract.Denda;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import peminjaman.Anggota;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import koneksi.MySQLConnection;
import peminjaman.Book;
import static peminjaman.Book.input;
/**
 *
 * @author Andika
 */
public class Peminjaman extends Denda{
    int idPeminjaman;
    String bukuPinjam;
    String tglPinjam;
    String tglKembali;
    Integer denda;
    
    public Integer getIDPeminjaman(){
         System.out.print("Masukkan ID buku : ");
         Scanner a = new Scanner(System.in);
         return idPeminjaman = a.nextInt();
     }
    
    public String getBukuPinjam(){
        Book a1 = new Book();
        return this.bukuPinjam = a1.getNamaBuku();
    }
    
    public String getTglPinjam(){
        System.out.print("Masukkan tanggal pinjam buku : ");
        Scanner p = new Scanner(System.in);
        return this.tglPinjam = p.nextLine();
    } 
    
    public String getTglKembali(){
        System.out.print("Masukkan tanggal kembali buku : ");
        Scanner k = new Scanner(System.in);
        return this.tglKembali = k.nextLine();
    } 
    
    @Override
    public Integer setDendaAnggota(){
        String stglAwal = this.tglPinjam;
        String stglAkhir = this.tglKembali;
       
        denda = 0;
        
        DateFormat dateAwal = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat dateAkhir = new SimpleDateFormat("dd/MM/yyyy");
         
        try {
            Date tglAwal = dateAwal.parse(stglAwal);
            Date tglAkhir = dateAkhir.parse(stglAkhir);
             
            Date TGLAwal = tglAwal;
            Date TGLAkhir = tglAkhir;
            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(TGLAwal);
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(TGLAkhir);
             
            String hasil = String.valueOf(daysBetween(cal1, cal2));
             
            System.out.println("Tanggal Awal  = " +stglAwal);
            System.out.println("Tanggal Akhir = " +stglAkhir);
            System.out.println("Selisih: " +hasil+ " hari");
            
            if (Integer.parseInt(hasil) > 7 ){
                this.denda = (Integer.parseInt(hasil) - 7) * 5000;
                System.out.println("Denda : " + denda);
            }
            else{
                System.out.println("Denda " + 0);
            } 
            return denda;
             
        } catch (ParseException e) {
        }
        return denda;
    }
     
    private static long daysBetween(Calendar tanggalAwal, Calendar tanggalAkhir) {
        long lama = 0;
        Calendar tanggal = (Calendar) tanggalAwal.clone();
        while (tanggal.before(tanggalAkhir)) {
            tanggal.add(Calendar.DAY_OF_MONTH, 1);
            lama++;
        }
        return lama;
    }
    
    // insert data buku
    public void insert(MySQLConnection m, String bukuPinjam, String tglPinjam, String tglKembali, Integer denda){
        // lakukan koneksi ke mysql
        Connection koneksi = m.conn;
        
        // query sql untuk insert data buku
        String sql = "INSERT INTO peminjam (bukuPinjam, tglPinjam, tglKembali, denda) VALUES (?, ?, ?, ?)";
 
        try {
            PreparedStatement statement = koneksi.prepareStatement(sql);
            
            // mapping nilai parameter dari query sql nya (sesuai urutan)
            statement.setString(1, bukuPinjam);
            statement.setString(2, tglPinjam);
            statement.setString(3, tglKembali);
            statement.setString(4, denda.toString());

            // jalankan query (baca jumlah row affectednya)
            int rowsInserted = statement.executeUpdate();
            // jika ada row affected nya, maka status sukses
            if (rowsInserted > 0) {
                System.out.println("Insert data buku sukses");
            }

        } catch (SQLException ex) {
            // jika query gagal
            System.out.println("Insert data buku gagal");
        }
    }
    
    // delete data buku berdasarkan idbook
    public void delete(MySQLConnection m){
        
        getIDPeminjaman();
        // query sql untuk hapus data buku berdasarkan idbook
        String sql = "DELETE FROM peminjam WHERE idPeminjam=?";
        // lakukan koneksi ke mysql
        Connection koneksi = m.conn;
        
        try {
            PreparedStatement statement;
            statement = koneksi.prepareStatement(sql);
            
            // mapping nilai parameter dari query sql nya
            statement.setString(1, String.valueOf(idPeminjaman));
            
            // jalankan query, dan lihat jumlah row affected nya
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Data peminjam sudah berhasil dihapus");
            }
        } catch (SQLException ex) {
            System.out.println("Hapus data peminjam gagal");
        }
        
    }
    
    // tampilkan semua data buku
    public void select(MySQLConnection m){
        
        // query sql untuk select all data peminjam
        String sql = "SELECT * FROM peminjam";
        
        // lakukan koneksi ke mysql
        Connection koneksi = m.conn;
        
        try {
            Statement statement = koneksi.createStatement();
            // jalankan query
            ResultSet result = statement.executeQuery(sql);

            // membuat header table untuk output
            System.out.println("==============================================================================");
            String header = "%3s %20s %20s %20s %4s";
            System.out.println(String.format(header, "ID", "BUKU PINJAM", "TGL PINJAM", "TGL KEMBALI", "DENDA"));
            System.out.println("------------------------------------------------------------------------------");
            
            // looping untuk baca data per record
            while (result.next()){
                // baca data buku per record
                String idPeminjam = result.getString("idPeminjam");
                String bukuPinjam = result.getString("bukuPinjam");
                String tglPinjam = result.getString("tglPinjam");
                String tglKembali = result.getString("tglKembali");
                String denda = result.getString("denda");
                // tampilkan data buku per record
                String output = "%3s %20s %20s %20s %4s";
                System.out.println(String.format(output, idPeminjam, bukuPinjam, tglPinjam, tglKembali, denda));
            }
            
            System.out.println("==============================================================================");
            
        } catch (SQLException ex){
            System.out.println("Tampil data peminjaman gagal");
        }
        
         //  Tampilan menu
    System.out.println("\n========= MENU PEMINJAMAN =========");
    System.out.println("1. Tambah peminjaman");
    System.out.println("2. Hapus peminjaman");
    System.out.println("0. Keluar");
    System.out.println("");
    System.out.print("PILIHAN> ");

    try {
        
        MySQLConnection db1 = new MySQLConnection("localhost", "dbpeminjaman", "root", "");
        Peminjaman p1 = new Peminjaman();

        switch (input.nextInt()) {
            
            case 0:
                System.exit(0);
                break;
                
            case 1:
                p1.insert(db1, p1.getBukuPinjam(), p1.getTglPinjam(), p1.getTglKembali(), p1.setDendaAnggota());
                break;
        
            case 2:
                p1.delete(db1);
                break;
                 
            default:
                System.out.println("Pilihan salah!");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    }
}
