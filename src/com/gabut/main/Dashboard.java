/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gabut.main;
import com.gabut.controller.Koneksi;
import javax.swing.JPanel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Anjayani
 */
public class Dashboard extends javax.swing.JFrame {
        Connection conn;
	ResultSet rs = null;
	PreparedStatement pst = null;
        String id;
        String idbaju;
    /**
     * Creates new form dashboard_admin
     */
 
        
        
 public Dashboard() throws SQLException, ClassNotFoundException {
      
        this.conn = Koneksi.getKoneksi();
        initComponents();
        tampilnama();
        tabelKaryawan();
        tabelBaju();
        tabelPelanggan();
        listPelanggan();
        listBaju();
        listfaktur();
        tabelPenjualan();
        totTransaksi();
        tbltransaksi();

        id_baju.disable();
        txt_id_admin.disable();
        id_pelanggan.disable();
        namaadmin.disable();
        tanggalbeli.disable();
        totalbayar.disable();
     
       
 }        
        
 public Dashboard(String id) throws SQLException, ClassNotFoundException {
        this.conn = Koneksi.getKoneksi();
        this.id = id;
        initComponents();
        
        tampilnama();
        tabelKaryawan();
        tabelBaju();
        tabelPelanggan();
        listPelanggan();
        listBaju();
        listfaktur();
        tabelPenjualan();
        totTransaksi();
        tbltransaksi()
       ;
       
    
        
        
        txt_id_admin.disable();
        id_baju.disable();
        id_pelanggan.disable();
        namaadmin.disable();
        tanggalbeli.disable();
        totalbayar.disable();
       
        
        }
 
 
 
 
 public void tbltransaksi() throws SQLException{
     

   
       try{
			String sql = "select t.tanggal, p.nama from tb_transaksi as t join tb_pelanggan as p on t.id_pelanggan = p.id where t.no_faktur = " + idtransaksi.getSelectedItem() + " and t.bayar = 'belum' ";
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			while(rs.next()){
				tanggalbeli.setText(rs.getString(1));
				namapelanggan.setSelectedItem(rs.getString(2));
				tabelDetailPenjualan((String) idtransaksi.getSelectedItem());
			}
		}catch(SQLException ex){
			
		}	
 }
    
 public void tampilnama()throws SQLException{
     
     
       tabelDetailPenjualan((String) idtransaksi.getSelectedItem());
     
    
     try{
         
     String sql = "select nama from tb_user where id = " + id;
	System.out.println(sql);
	pst=conn.prepareStatement(sql);
	rs=pst.executeQuery();
	if(rs.next()){
	jLabel18.setText(rs.getString(1));
        namaadmin.setText(rs.getString(1));
        
        }
        
        sql = "SELECT nama FROM tb_baju";
        pst = conn.prepareStatement(sql);
	    rs = pst.executeQuery();
	    while(rs.next()){
		    namabajucuy.addItem(rs.getString(1));
	    }
       
        sql = "select sum(stok) from tb_baju";
            pst = conn.prepareStatement(sql);
	    rs = pst.executeQuery();
	    while(rs.next()){
		    jLabel47.setText(rs.getString(1));
	    }
            
        sql = "select count(no_faktur) from tb_transaksi";
            pst = conn.prepareStatement(sql);
	    rs = pst.executeQuery();
	    while(rs.next()){
		    jLabel44.setText(rs.getString(1));
	    }
        sql = "select count(*) from tb_pembayaran where tgl_bayar = curdate()";
	    pst = conn.prepareStatement(sql);
	    rs = pst.executeQuery();
	    while(rs.next()){
		    jLabel40.setText(rs.getString(1));
	    }

        }catch(SQLException ex){
		    System.out.println("gagal gan");
	         }
 }
 
 
  public void listBaju(){
	
	namabaju.removeAllItems();
	    try{
		    String sql = "select nama from tb_baju order by nama asc";
		    pst=conn.prepareStatement(sql);
		    rs=pst.executeQuery();
		    while(rs.next()){
			
			namabaju.addItem(rs.getString(1));
		    }
	    }catch(SQLException e){
	    }
    }

    public void listPelanggan(){
	namapelanggan.removeAllItems();
	    try{
		    String sql = "select nama from tb_pelanggan";
		    pst=conn.prepareStatement(sql);
		    rs=pst.executeQuery();
		    while(rs.next()){
			namapelanggan.addItem(rs.getString(1));
		    }
	    }catch(SQLException e){
	    }
    }
 
    
        public void listfaktur(){
	idtransaksi.removeAllItems();
	    try{
		    String sql = "SELECT tb_transaksi.no_faktur FROM tb_transaksi WHERE tb_transaksi.bayar = 'belum';";
		    pst=conn.prepareStatement(sql);
		    rs=pst.executeQuery();
		    while(rs.next()){
			idtransaksi.addItem(rs.getString(1));
		    }
	    }catch(SQLException e){
	    }
    }
         
  public void resetdatadmin(){
      txt_id_admin.setText("");
      txt_nama.setText("");
      txt_username.setText("");
     txt_password.setText("");
     txt_id_admin.disable();
  }
  
    public void resetdatabaju(){
      id_baju.setText("");
      nama_baju.setText("");
      stok_baju.setText("");
     harga_baju.setText("");
     id_baju.disable();
  }
    
        public void resetdatapelanggan(){
      id_pelanggan.setText("");
      nama_pelanggan.setText("");
      
     alamat_pelanggan.setText("");
     nohp_pelanngan.setText("");
     id_pelanggan.disable();
  }
   
    
    void setColor(JPanel panel){
        panel.setBackground(new Color(51,172,242));
    }
    
    void resetColor (JPanel panel){
        panel.setBackground(new Color(26,147,217));
    }

    
    
     public void tabelPenjualan(){
	    DefaultTableModel model = new DefaultTableModel();
	    model.addColumn("ID");
	    model.addColumn("Pelanggan");
	    model.addColumn("Baju");
	    model.addColumn("QTY");
	    model.addColumn("Kasir");
	    try{
		    String sql = "SELECT tb_detail_transaksi.id, tb_pelanggan.nama, tb_baju.nama, tb_detail_transaksi.qty, tb_user.nama FROM tb_detail_transaksi join tb_transaksi on tb_detail_transaksi.id_transaksi = tb_transaksi.no_faktur JOIN tb_baju on tb_detail_transaksi.id_baju = tb_baju.id join tb_pelanggan on tb_transaksi.id_pelanggan = tb_pelanggan.id join tb_user on tb_detail_transaksi.id_user = tb_user.id WHERE tb_transaksi.bayar = 'belum' order by tb_detail_transaksi.id desc;";
		    pst=conn.prepareStatement(sql);
		    rs=pst.executeQuery();
		    while(rs.next()){
			    model.addRow(new Object[] {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)});
		    }
		    tbl_penjualan.setModel(model);
	    }catch(SQLException e){
		    model.addRow(new Object[] {});
		    tbl_penjualan.setModel(model);
	    }
    }

     
    public void tabelDetailPenjualan(String id){
	    DefaultTableModel model = new DefaultTableModel();
	    model.addColumn("ID");
	    model.addColumn("Baju");
            model.addColumn("QTY");
            model.addColumn("Sub Harga");
	    model.addColumn("Total Harga");
	   
	    try{
		    String sql = "SELECT tb_detail_transaksi.id, tb_baju.nama,tb_detail_transaksi.qty, tb_baju.harga, sum(tb_detail_transaksi.qty * tb_baju.harga), tb_user.nama FROM tb_detail_transaksi join tb_transaksi on tb_detail_transaksi.id_transaksi = tb_transaksi.no_faktur join tb_baju on tb_detail_transaksi.id_baju = tb_baju.id JOIN tb_pelanggan on tb_transaksi.id_pelanggan = tb_pelanggan.id join tb_user on tb_detail_transaksi.id_user = tb_user.id WHERE tb_transaksi.no_faktur = "+ idtransaksi.getSelectedItem()+" GROUP BY tb_detail_transaksi.id ORDER BY tb_transaksi.tanggal desc;";
		    pst=conn.prepareStatement(sql);
		    rs=pst.executeQuery();
		    while(rs.next()){
			    model.addRow(new Object[] {rs.getString(1), rs.getString(2),rs.getString(3), rs.getString(4), rs.getString(5)});
		    }
                    System.out.println(sql);
		    tb_detailpenjualan.setModel(model);
		    sql = "select sum(dt.qty * b.harga) from tb_detail_transaksi as dt join tb_transaksi as t on dt.id_transaksi = t.no_faktur join tb_baju as b on dt.id_baju = b.id join tb_pelanggan as p on t.id_pelanggan = p.id join tb_user as u on dt.id_user = u.id where t.no_faktur = " + id;
		    pst=conn.prepareStatement(sql);
		    rs=pst.executeQuery();
		    while(rs.next()){
			    if(rs.getString(1) == null){
				    totalbayar.setText("Rp.0,00");
			    }else{
				    Locale locale = new Locale("id", "ID");
				    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
				    totalbayar.setText(currencyFormatter.format(Integer.parseInt(rs.getString(1))));
			    }
		    }
	    }catch(SQLException e){
	    }
    }
    
    
    public void totTransaksi(){
	    DefaultTableModel model = new DefaultTableModel();
	    model.addColumn("ID");
	    model.addColumn("Nama Pelanggan");
            model.addColumn("Nama Admin");
            model.addColumn("Tanggal");
            model.addColumn("Nominal Masuk");
	    try{
		    String sql = "select distinct t.no_faktur, p.nama,u.nama, t.tanggal, pb.total from tb_detail_transaksi as dt join tb_transaksi as t on dt.id_transaksi = t.no_faktur join tb_baju as b on dt.id_baju = b.id join tb_pelanggan as p on t.id_pelanggan = p.id join tb_user as u on dt.id_user = u.id join tb_pembayaran as pb on pb.id_transaksi = t.no_faktur group by dt.id order by t.tanggal desc";
		    pst=conn.prepareStatement(sql);
		    rs=pst.executeQuery();
                 
		    while(rs.next()){
			    model.addRow(new Object[] {rs.getString(1),rs.getString(2), rs.getString(3),rs.getString(4), rs.getString(5)});
		    }
		    tbl_totaltransaksi.setModel(model);
	    }catch(SQLException e){
	    }
        }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Pane_Utama = new javax.swing.JPanel();
        menu = new javax.swing.JPanel();
        btn_dashboard = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btn_menu1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btn_menu2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        btn_menu3 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        btn_menu4 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        btn_menu5 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        btn_menu7 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        btn_menu6 = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        main = new javax.swing.JPanel();
        dashboard = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel40 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel43 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jPanel23 = new javax.swing.JPanel();
        jLabel46 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jPanel24 = new javax.swing.JPanel();
        jLabel49 = new javax.swing.JLabel();
        menu1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tb_detailpenjualan = new javax.swing.JTable();
        jLabel69 = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        totalbayar = new javax.swing.JTextField();
        jPanel27 = new javax.swing.JPanel();
        jLabel72 = new javax.swing.JLabel();
        jLabel75 = new javax.swing.JLabel();
        qty = new javax.swing.JTextField();
        jLabel77 = new javax.swing.JLabel();
        namapelanggan = new javax.swing.JComboBox<>();
        jLabel78 = new javax.swing.JLabel();
        namaadmin = new javax.swing.JTextField();
        namabaju = new javax.swing.JComboBox<>();
        caripelanggan = new javax.swing.JTextField();
        jLabel79 = new javax.swing.JLabel();
        jLabel80 = new javax.swing.JLabel();
        caribaju = new javax.swing.JTextField();
        jLabel81 = new javax.swing.JLabel();
        tanggalbeli = new javax.swing.JTextField();
        jPanel30 = new javax.swing.JPanel();
        jLabel82 = new javax.swing.JLabel();
        jPanel31 = new javax.swing.JPanel();
        jLabel83 = new javax.swing.JLabel();
        idtransaksi = new javax.swing.JComboBox<>();
        menu2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_admin = new javax.swing.JTable();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        txt_nama = new javax.swing.JTextField();
        txt_username = new javax.swing.JTextField();
        txt_password = new javax.swing.JPasswordField();
        jPanel9 = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        txt_id_admin = new javax.swing.JTextField();
        jPanel20 = new javax.swing.JPanel();
        jLabel58 = new javax.swing.JLabel();
        menu3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_baju = new javax.swing.JTable();
        jLabel50 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        harga_baju = new javax.swing.JTextField();
        jPanel16 = new javax.swing.JPanel();
        jLabel53 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jLabel54 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        id_baju = new javax.swing.JTextField();
        stok_baju = new javax.swing.JTextField();
        jPanel19 = new javax.swing.JPanel();
        jLabel57 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        nama_baju = new javax.swing.JTextField();
        menu4 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbl_pelanggan = new javax.swing.JTable();
        jLabel60 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        nohp_pelanngan = new javax.swing.JTextField();
        jPanel21 = new javax.swing.JPanel();
        jLabel63 = new javax.swing.JLabel();
        jPanel22 = new javax.swing.JPanel();
        jLabel64 = new javax.swing.JLabel();
        jPanel25 = new javax.swing.JPanel();
        jLabel65 = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        id_pelanggan = new javax.swing.JTextField();
        alamat_pelanggan = new javax.swing.JTextField();
        jPanel26 = new javax.swing.JPanel();
        jLabel67 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        nama_pelanggan = new javax.swing.JTextField();
        menu5 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tbl_totaltransaksi = new javax.swing.JTable();
        menu6 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel51 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tbl_penjualan = new javax.swing.JTable();
        jLabel84 = new javax.swing.JLabel();
        jLabel85 = new javax.swing.JLabel();
        kasiir = new javax.swing.JTextField();
        jPanel33 = new javax.swing.JPanel();
        jLabel87 = new javax.swing.JLabel();
        jLabel89 = new javax.swing.JLabel();
        id_transaksi = new javax.swing.JTextField();
        qtyy = new javax.swing.JTextField();
        jLabel91 = new javax.swing.JLabel();
        namaplg = new javax.swing.JTextField();
        jLabel92 = new javax.swing.JLabel();
        sembunyi_anjay = new javax.swing.JLabel();
        namabajucuy = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Pane_Utama.setBackground(new java.awt.Color(255, 255, 255));
        Pane_Utama.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        menu.setBackground(new java.awt.Color(0, 121, 191));
        menu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_dashboard.setBackground(new java.awt.Color(51, 172, 242));
        btn_dashboard.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_dashboardMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btn_dashboardMousePressed(evt);
            }
        });

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/gabut/icons/icons8_home_25px.png"))); // NOI18N

        jLabel4.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Dashboard");

        javax.swing.GroupLayout btn_dashboardLayout = new javax.swing.GroupLayout(btn_dashboard);
        btn_dashboard.setLayout(btn_dashboardLayout);
        btn_dashboardLayout.setHorizontalGroup(
            btn_dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_dashboardLayout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addContainerGap(114, Short.MAX_VALUE))
        );
        btn_dashboardLayout.setVerticalGroup(
            btn_dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btn_dashboardLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        menu.add(btn_dashboard, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 270, 50));

        btn_menu1.setBackground(new java.awt.Color(26, 147, 217));
        btn_menu1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btn_menu1MousePressed(evt);
            }
        });

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/gabut/icons/icons8_new_copy_25px.png"))); // NOI18N

        jLabel6.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Transaksi");

        javax.swing.GroupLayout btn_menu1Layout = new javax.swing.GroupLayout(btn_menu1);
        btn_menu1.setLayout(btn_menu1Layout);
        btn_menu1Layout.setHorizontalGroup(
            btn_menu1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_menu1Layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addContainerGap(120, Short.MAX_VALUE))
        );
        btn_menu1Layout.setVerticalGroup(
            btn_menu1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btn_menu1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        menu.add(btn_menu1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, 270, 50));

        btn_menu2.setBackground(new java.awt.Color(26, 147, 217));
        btn_menu2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btn_menu2MousePressed(evt);
            }
        });

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/gabut/icons/icons8_user_25px_4.png"))); // NOI18N

        jLabel8.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Admin");

        javax.swing.GroupLayout btn_menu2Layout = new javax.swing.GroupLayout(btn_menu2);
        btn_menu2.setLayout(btn_menu2Layout);
        btn_menu2Layout.setHorizontalGroup(
            btn_menu2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_menu2Layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addContainerGap(140, Short.MAX_VALUE))
        );
        btn_menu2Layout.setVerticalGroup(
            btn_menu2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btn_menu2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        menu.add(btn_menu2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 250, 270, 50));

        btn_menu3.setBackground(new java.awt.Color(26, 147, 217));
        btn_menu3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_menu3MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btn_menu3MousePressed(evt);
            }
        });

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/gabut/icons/icons8_view_25px.png"))); // NOI18N

        jLabel10.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Stok Baju");

        javax.swing.GroupLayout btn_menu3Layout = new javax.swing.GroupLayout(btn_menu3);
        btn_menu3.setLayout(btn_menu3Layout);
        btn_menu3Layout.setHorizontalGroup(
            btn_menu3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_menu3Layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addContainerGap(121, Short.MAX_VALUE))
        );
        btn_menu3Layout.setVerticalGroup(
            btn_menu3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btn_menu3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        menu.add(btn_menu3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 300, 270, 50));

        btn_menu4.setBackground(new java.awt.Color(26, 147, 217));
        btn_menu4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btn_menu4MousePressed(evt);
            }
        });

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/gabut/icons/icons8-user-25.png"))); // NOI18N

        jLabel12.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Pelanggan");

        javax.swing.GroupLayout btn_menu4Layout = new javax.swing.GroupLayout(btn_menu4);
        btn_menu4.setLayout(btn_menu4Layout);
        btn_menu4Layout.setHorizontalGroup(
            btn_menu4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_menu4Layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addContainerGap(118, Short.MAX_VALUE))
        );
        btn_menu4Layout.setVerticalGroup(
            btn_menu4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btn_menu4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        menu.add(btn_menu4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 350, 270, 50));

        btn_menu5.setBackground(new java.awt.Color(26, 147, 217));
        btn_menu5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btn_menu5MousePressed(evt);
            }
        });

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/gabut/icons/icons8_document_25px.png"))); // NOI18N

        jLabel14.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Laporan");

        javax.swing.GroupLayout btn_menu5Layout = new javax.swing.GroupLayout(btn_menu5);
        btn_menu5.setLayout(btn_menu5Layout);
        btn_menu5Layout.setHorizontalGroup(
            btn_menu5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_menu5Layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel14)
                .addContainerGap(130, Short.MAX_VALUE))
        );
        btn_menu5Layout.setVerticalGroup(
            btn_menu5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btn_menu5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        menu.add(btn_menu5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 400, 270, 50));

        btn_menu7.setBackground(new java.awt.Color(26, 147, 217));
        btn_menu7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btn_menu7MousePressed(evt);
            }
        });

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/gabut/icons/icons8_shutdown_25px.png"))); // NOI18N

        jLabel16.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Log Out");

        javax.swing.GroupLayout btn_menu7Layout = new javax.swing.GroupLayout(btn_menu7);
        btn_menu7.setLayout(btn_menu7Layout);
        btn_menu7Layout.setHorizontalGroup(
            btn_menu7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_menu7Layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel16)
                .addContainerGap(131, Short.MAX_VALUE))
        );
        btn_menu7Layout.setVerticalGroup(
            btn_menu7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btn_menu7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addContainerGap())
        );

        menu.add(btn_menu7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 450, 270, 50));

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/gabut/icons/icons8_person_64px.png"))); // NOI18N
        menu.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 70, 70));

        jLabel18.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("P");
        menu.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 30, 140, -1));

        jLabel19.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Admin");
        menu.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 50, 90, -1));

        btn_menu6.setBackground(new java.awt.Color(26, 147, 217));
        btn_menu6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btn_menu6MousePressed(evt);
            }
        });

        jLabel32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/gabut/icons/icons8-detail-25.png"))); // NOI18N

        jLabel33.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(255, 255, 255));
        jLabel33.setText("Transaksi On Going");

        javax.swing.GroupLayout btn_menu6Layout = new javax.swing.GroupLayout(btn_menu6);
        btn_menu6.setLayout(btn_menu6Layout);
        btn_menu6Layout.setHorizontalGroup(
            btn_menu6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_menu6Layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel33)
                .addContainerGap(58, Short.MAX_VALUE))
        );
        btn_menu6Layout.setVerticalGroup(
            btn_menu6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btn_menu6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel33, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addContainerGap())
        );

        menu.add(btn_menu6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 200, 270, 50));

        Pane_Utama.add(menu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 270, 560));

        main.setBackground(new java.awt.Color(255, 255, 255));
        main.setLayout(new java.awt.CardLayout());

        dashboard.setBackground(new java.awt.Color(255, 255, 255));
        dashboard.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(26, 147, 217));

        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/gabut/icons/icons8_home_25px.png"))); // NOI18N

        jLabel21.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Dashboard");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(570, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(34, Short.MAX_VALUE))
        );

        dashboard.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 710, 100));

        jPanel12.setBackground(new java.awt.Color(51, 172, 242));

        jLabel40.setFont(new java.awt.Font("Trebuchet MS", 0, 24)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(255, 255, 255));
        jLabel40.setText("-");
        jLabel40.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel40MouseClicked(evt);
            }
        });

        jLabel42.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/gabut/icons/icons8-transaction-48.png"))); // NOI18N

        jPanel13.setBackground(new java.awt.Color(26, 147, 217));

        jLabel43.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(255, 255, 255));
        jLabel43.setText("Transaksi Hari Ini");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel43, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel42)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel40, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel40, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel42, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        dashboard.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 210, 120));

        jPanel14.setBackground(new java.awt.Color(51, 172, 242));

        jLabel44.setFont(new java.awt.Font("Trebuchet MS", 0, 24)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(255, 255, 255));
        jLabel44.setText("-");

        jLabel45.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/gabut/icons/icons8-bill-48.png"))); // NOI18N

        jPanel23.setBackground(new java.awt.Color(26, 147, 217));
        jPanel23.setToolTipText("");

        jLabel46.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(255, 255, 255));
        jLabel46.setText("Total Transaksi");

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel23Layout.createSequentialGroup()
                .addContainerGap(37, Short.MAX_VALUE)
                .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel46, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel45)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel44, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        dashboard.add(jPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 140, 210, 120));

        jPanel15.setBackground(new java.awt.Color(51, 172, 242));

        jLabel47.setFont(new java.awt.Font("Trebuchet MS", 0, 24)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(255, 255, 255));
        jLabel47.setText("-");

        jLabel48.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/gabut/icons/icons8-t-shirt-48.png"))); // NOI18N

        jPanel24.setBackground(new java.awt.Color(26, 147, 217));

        jLabel49.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(255, 255, 255));
        jLabel49.setText("Stok Baju");

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jLabel49)
                .addContainerGap(112, Short.MAX_VALUE))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel49, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel48)
                .addGap(18, 18, 18)
                .addComponent(jLabel47, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        dashboard.add(jPanel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 140, 210, 120));

        main.add(dashboard, "card2");

        menu1.setBackground(new java.awt.Color(255, 255, 255));
        menu1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(26, 147, 217));

        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/gabut/icons/icons8_new_copy_25px.png"))); // NOI18N

        jLabel23.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("Transaksi");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(570, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(34, Short.MAX_VALUE))
        );

        menu1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 710, 100));

        tb_detailpenjualan.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        tb_detailpenjualan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tb_detailpenjualan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_detailpenjualanMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tb_detailpenjualan);

        menu1.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 310, 420, 170));

        jLabel69.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel69.setText("Total :");
        menu1.add(jLabel69, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 360, -1, -1));

        jLabel70.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel70.setText("Jenis Baju :");
        menu1.add(jLabel70, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 120, -1, -1));

        jLabel71.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel71.setText("Qty :");
        menu1.add(jLabel71, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 240, -1, -1));

        totalbayar.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        menu1.add(totalbayar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 380, 190, 30));

        jPanel27.setBackground(new java.awt.Color(116, 198, 94));
        jPanel27.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel27.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel27MouseClicked(evt);
            }
        });
        jPanel27.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel72.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel72.setForeground(new java.awt.Color(255, 255, 255));
        jLabel72.setText("Buat Transaksi ");
        jPanel27.add(jLabel72, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 90, 30));

        menu1.add(jPanel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 260, 100, 30));

        jLabel75.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel75.setText("ID Transaksi :");
        menu1.add(jLabel75, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, -1, -1));

        qty.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        menu1.add(qty, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 260, 190, 30));

        jLabel77.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel77.setText("Cari Pelanggan :");
        menu1.add(jLabel77, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 180, -1, -1));

        namapelanggan.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        menu1.add(namapelanggan, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 140, 190, 30));

        jLabel78.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel78.setText("Admin :");
        menu1.add(jLabel78, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, -1, -1));

        namaadmin.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        menu1.add(namaadmin, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, 190, 30));

        namabaju.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        menu1.add(namabaju, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 140, 190, 30));

        caripelanggan.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        caripelanggan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                caripelangganActionPerformed(evt);
            }
        });
        caripelanggan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                caripelangganKeyReleased(evt);
            }
        });
        menu1.add(caripelanggan, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 200, 190, 30));

        jLabel79.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel79.setText("Nama Pelanggan :");
        menu1.add(jLabel79, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 120, -1, -1));

        jLabel80.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel80.setText("Cari Baju :");
        menu1.add(jLabel80, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 180, -1, -1));

        caribaju.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        caribaju.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                caribajuKeyReleased(evt);
            }
        });
        menu1.add(caribaju, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 200, 190, 30));

        jLabel81.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel81.setText("Tanggal :");
        menu1.add(jLabel81, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 240, -1, -1));

        tanggalbeli.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        menu1.add(tanggalbeli, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 260, 190, 30));

        jPanel30.setBackground(new java.awt.Color(116, 198, 94));
        jPanel30.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel30.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel30MouseClicked(evt);
            }
        });
        jPanel30.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel82.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel82.setForeground(new java.awt.Color(255, 255, 255));
        jLabel82.setText("Tambah Baju");
        jPanel30.add(jLabel82, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, 30));

        menu1.add(jPanel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 260, 90, 30));

        jPanel31.setBackground(new java.awt.Color(116, 198, 94));
        jPanel31.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel31.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel31MouseClicked(evt);
            }
        });
        jPanel31.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel83.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel83.setForeground(new java.awt.Color(255, 255, 255));
        jLabel83.setText("Bayar");
        jPanel31.add(jLabel83, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 5, -1, 20));

        menu1.add(jPanel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 490, 90, 30));

        idtransaksi.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        idtransaksi.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                idtransaksiPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        menu1.add(idtransaksi, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 190, 30));

        main.add(menu1, "card2");

        menu2.setBackground(new java.awt.Color(255, 255, 255));
        menu2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(26, 147, 217));

        jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/gabut/icons/icons8_user_25px_4.png"))); // NOI18N

        jLabel25.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setText("Admin");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel24)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(570, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(34, Short.MAX_VALUE))
        );

        menu2.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 710, 100));

        tbl_admin.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        tbl_admin.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbl_admin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_adminMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_admin);

        menu2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 117, 460, 430));

        jLabel34.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel34.setText("Password :");
        menu2.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 300, -1, -1));

        jLabel35.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel35.setText("Nama :");
        menu2.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 180, -1, -1));

        jLabel36.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel36.setText("Username :");
        menu2.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 240, -1, -1));

        txt_nama.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        menu2.add(txt_nama, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 200, 190, 30));

        txt_username.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        menu2.add(txt_username, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 260, 190, 30));

        txt_password.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        menu2.add(txt_password, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 320, 190, 30));

        jPanel9.setBackground(new java.awt.Color(116, 198, 94));
        jPanel9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel9MouseClicked(evt);
            }
        });
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel37.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(255, 255, 255));
        jLabel37.setText("Add");
        jPanel9.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 5, -1, 20));

        menu2.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 370, 90, 30));

        jPanel10.setBackground(new java.awt.Color(253, 91, 79));
        jPanel10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel10MouseClicked(evt);
            }
        });
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel38.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(255, 255, 255));
        jLabel38.setText("Delete");
        jPanel10.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 5, 70, 20));

        menu2.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 370, 90, 30));

        jPanel11.setBackground(new java.awt.Color(64, 182, 215));
        jPanel11.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel11MouseClicked(evt);
            }
        });
        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel39.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(255, 255, 255));
        jLabel39.setText("Update");
        jPanel11.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 5, 50, 20));

        menu2.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 420, 90, 30));

        jLabel41.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel41.setText("ID :");
        menu2.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 120, -1, -1));

        txt_id_admin.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        menu2.add(txt_id_admin, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 140, 190, 30));

        jPanel20.setBackground(new java.awt.Color(83, 83, 83));
        jPanel20.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel20.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel20MouseClicked(evt);
            }
        });
        jPanel20.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel58.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel58.setForeground(new java.awt.Color(255, 255, 255));
        jLabel58.setText("Reset");
        jPanel20.add(jLabel58, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 5, 40, 20));

        menu2.add(jPanel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 420, 90, 30));

        main.add(menu2, "card2");

        menu3.setBackground(new java.awt.Color(255, 255, 255));
        menu3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel5.setBackground(new java.awt.Color(26, 147, 217));

        jLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/gabut/icons/icons8_view_25px.png"))); // NOI18N

        jLabel27.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setText("Stok Baju");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel26)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(570, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(34, Short.MAX_VALUE))
        );

        menu3.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 710, 100));

        tbl_baju.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        tbl_baju.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbl_baju.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_bajuMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_baju);

        menu3.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 117, 460, 430));

        jLabel50.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel50.setText("Harga");
        menu3.add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 300, -1, -1));

        jLabel52.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel52.setText("Stok");
        menu3.add(jLabel52, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 240, -1, -1));

        harga_baju.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        menu3.add(harga_baju, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 320, 190, 30));

        jPanel16.setBackground(new java.awt.Color(116, 198, 94));
        jPanel16.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel16MouseClicked(evt);
            }
        });
        jPanel16.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel53.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel53.setForeground(new java.awt.Color(255, 255, 255));
        jLabel53.setText("Add");
        jPanel16.add(jLabel53, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 5, -1, 20));

        menu3.add(jPanel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 370, 90, 30));

        jPanel17.setBackground(new java.awt.Color(253, 91, 79));
        jPanel17.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel17MouseClicked(evt);
            }
        });
        jPanel17.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel54.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel54.setForeground(new java.awt.Color(255, 255, 255));
        jLabel54.setText("Delete");
        jPanel17.add(jLabel54, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 5, 70, 20));

        menu3.add(jPanel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 370, 90, 30));

        jPanel18.setBackground(new java.awt.Color(64, 182, 215));
        jPanel18.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel18MouseClicked(evt);
            }
        });
        jPanel18.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel55.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(255, 255, 255));
        jLabel55.setText("Update");
        jPanel18.add(jLabel55, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 5, 50, 20));

        menu3.add(jPanel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 420, 90, 30));

        jLabel56.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel56.setText("ID :");
        menu3.add(jLabel56, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 120, -1, -1));

        id_baju.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        menu3.add(id_baju, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 140, 190, 30));

        stok_baju.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        menu3.add(stok_baju, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 260, 190, 30));

        jPanel19.setBackground(new java.awt.Color(83, 83, 83));
        jPanel19.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel19.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel19MouseClicked(evt);
            }
        });
        jPanel19.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel57.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel57.setForeground(new java.awt.Color(255, 255, 255));
        jLabel57.setText("Reset");
        jPanel19.add(jLabel57, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 5, 50, 20));

        menu3.add(jPanel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 420, 90, 30));

        jLabel59.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel59.setText("Nama :");
        menu3.add(jLabel59, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 180, -1, -1));

        nama_baju.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        menu3.add(nama_baju, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 200, 190, 30));

        main.add(menu3, "card2");

        menu4.setBackground(new java.awt.Color(255, 255, 255));
        menu4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel6.setBackground(new java.awt.Color(26, 147, 217));

        jLabel28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/gabut/icons/icons8-user-25.png"))); // NOI18N

        jLabel29.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setText("Data Pelanggan");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel28)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(551, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(34, Short.MAX_VALUE))
        );

        menu4.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 710, 100));

        tbl_pelanggan.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        tbl_pelanggan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbl_pelanggan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_pelangganMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tbl_pelanggan);

        menu4.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 117, 460, 430));

        jLabel60.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel60.setText("No Hp : ");
        menu4.add(jLabel60, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 300, -1, -1));

        jLabel62.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel62.setText("Alamat :");
        menu4.add(jLabel62, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 240, -1, -1));

        nohp_pelanngan.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        menu4.add(nohp_pelanngan, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 320, 190, 30));

        jPanel21.setBackground(new java.awt.Color(116, 198, 94));
        jPanel21.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel21.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel21MouseClicked(evt);
            }
        });
        jPanel21.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel63.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel63.setForeground(new java.awt.Color(255, 255, 255));
        jLabel63.setText("Add");
        jPanel21.add(jLabel63, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 5, -1, 20));

        menu4.add(jPanel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 370, 90, 30));

        jPanel22.setBackground(new java.awt.Color(253, 91, 79));
        jPanel22.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel22.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel22MouseClicked(evt);
            }
        });
        jPanel22.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel64.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel64.setForeground(new java.awt.Color(255, 255, 255));
        jLabel64.setText("Delete");
        jPanel22.add(jLabel64, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 5, 70, 20));

        menu4.add(jPanel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 370, 90, 30));

        jPanel25.setBackground(new java.awt.Color(64, 182, 215));
        jPanel25.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel25.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel25MouseClicked(evt);
            }
        });
        jPanel25.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel65.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel65.setForeground(new java.awt.Color(255, 255, 255));
        jLabel65.setText("Update");
        jPanel25.add(jLabel65, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 5, 50, 20));

        menu4.add(jPanel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 420, 90, 30));

        jLabel66.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel66.setText("ID :");
        menu4.add(jLabel66, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 120, -1, -1));

        id_pelanggan.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        menu4.add(id_pelanggan, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 140, 190, 30));

        alamat_pelanggan.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        menu4.add(alamat_pelanggan, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 260, 190, 30));

        jPanel26.setBackground(new java.awt.Color(83, 83, 83));
        jPanel26.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel26.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel26MouseClicked(evt);
            }
        });
        jPanel26.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel67.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel67.setForeground(new java.awt.Color(255, 255, 255));
        jLabel67.setText("Reset");
        jPanel26.add(jLabel67, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 5, 50, 20));

        menu4.add(jPanel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 420, 90, 30));

        jLabel68.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel68.setText("Nama :");
        menu4.add(jLabel68, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 180, -1, -1));

        nama_pelanggan.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        menu4.add(nama_pelanggan, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 200, 190, 30));

        main.add(menu4, "card2");

        menu5.setBackground(new java.awt.Color(255, 255, 255));
        menu5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel7.setBackground(new java.awt.Color(26, 147, 217));

        jLabel30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/gabut/icons/icons8_document_25px.png"))); // NOI18N

        jLabel31.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 255, 255));
        jLabel31.setText("Laporan");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel30)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(570, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(34, Short.MAX_VALUE))
        );

        menu5.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 710, 100));

        tbl_totaltransaksi.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        tbl_totaltransaksi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbl_totaltransaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_totaltransaksiMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tbl_totaltransaksi);

        menu5.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 117, 690, 430));

        main.add(menu5, "card2");

        menu6.setBackground(new java.awt.Color(255, 255, 255));
        menu6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel8.setBackground(new java.awt.Color(26, 147, 217));

        jLabel51.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/gabut/icons/icons8-detail-25.png"))); // NOI18N

        jLabel61.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel61.setForeground(new java.awt.Color(255, 255, 255));
        jLabel61.setText("Transaksi On Going");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel51)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel61, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(524, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel61, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(34, Short.MAX_VALUE))
        );

        menu6.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 710, 100));

        tbl_penjualan.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        tbl_penjualan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbl_penjualan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_penjualanMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(tbl_penjualan);

        menu6.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 117, 460, 430));

        jLabel84.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel84.setText("Kasir :");
        menu6.add(jLabel84, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 360, -1, -1));

        jLabel85.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel85.setText("Qty :");
        menu6.add(jLabel85, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 300, -1, -1));

        kasiir.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        menu6.add(kasiir, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 380, 190, 30));

        jPanel33.setBackground(new java.awt.Color(253, 91, 79));
        jPanel33.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel33.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel33MouseClicked(evt);
            }
        });
        jPanel33.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel87.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel87.setForeground(new java.awt.Color(255, 255, 255));
        jLabel87.setText("Delete");
        jPanel33.add(jLabel87, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 5, 90, 20));

        menu6.add(jPanel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 430, 150, 30));

        jLabel89.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel89.setText("ID :");
        menu6.add(jLabel89, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 120, -1, -1));

        id_transaksi.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        menu6.add(id_transaksi, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 140, 190, 30));

        qtyy.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        menu6.add(qtyy, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 320, 190, 30));

        jLabel91.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel91.setText("Pelanggan :");
        menu6.add(jLabel91, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 180, -1, -1));

        namaplg.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        menu6.add(namaplg, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 200, 190, 30));

        jLabel92.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel92.setText("Baju :");
        menu6.add(jLabel92, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 240, -1, -1));

        sembunyi_anjay.setText("jLabel1");
        menu6.add(sembunyi_anjay, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 480, -1, -1));

        namabajucuy.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        namabajucuy.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                namabajucuyPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        menu6.add(namabajucuy, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 260, 190, 30));

        main.add(menu6, "card2");

        Pane_Utama.add(main, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 0, 710, 560));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Pane_Utama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Pane_Utama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_dashboardMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_dashboardMousePressed
            try {
                tampilnama();
            } catch (SQLException ex) {
                Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        
        setColor(btn_dashboard);
        
        resetColor(btn_menu1);
        resetColor(btn_menu2);
        resetColor(btn_menu3);
        resetColor(btn_menu4);
        resetColor(btn_menu5);
        resetColor(btn_menu6);
        resetColor(btn_menu7);
        
        main.removeAll();
        main.repaint();
        main.revalidate();
        
        main.add(dashboard);
        main.repaint();
        main.revalidate();
        
        
    }//GEN-LAST:event_btn_dashboardMousePressed

    private void btn_menu1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_menu1MousePressed
         setColor(btn_menu1);
        
        resetColor(btn_dashboard);
        resetColor(btn_menu2);
        resetColor(btn_menu3);
        resetColor(btn_menu4);
        resetColor(btn_menu5);
        resetColor(btn_menu6);      
        resetColor(btn_menu7);
        
         main.removeAll();
        main.repaint();
        main.revalidate();
        
        main.add(menu1);
        main.repaint();
        main.revalidate();
    }//GEN-LAST:event_btn_menu1MousePressed

    private void btn_menu2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_menu2MousePressed
         setColor(btn_menu2);
        
        resetColor(btn_dashboard);
        resetColor(btn_menu1);
        resetColor(btn_menu3);
        resetColor(btn_menu4);
        resetColor(btn_menu5);
   resetColor(btn_menu6);     
        resetColor(btn_menu7);
        
        main.removeAll();
        main.repaint();
        main.revalidate();
        
        main.add(menu2);
        main.repaint();
        main.revalidate();
    }//GEN-LAST:event_btn_menu2MousePressed

    private void btn_menu3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_menu3MousePressed
        tabelBaju();
        
        setColor(btn_menu3);
        
        resetColor(btn_dashboard);
        resetColor(btn_menu1);
        resetColor(btn_menu2);
        resetColor(btn_menu4);
        resetColor(btn_menu5);
   resetColor(btn_menu6);    
        resetColor(btn_menu7);
        
           main.removeAll();
        main.repaint();
        main.revalidate();
        
        main.add(menu3);
        main.repaint();
        main.revalidate();
    }//GEN-LAST:event_btn_menu3MousePressed

    private void btn_menu4MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_menu4MousePressed
           setColor(btn_menu4);
        
        resetColor(btn_dashboard);
        resetColor(btn_menu1);
        resetColor(btn_menu2);
        resetColor(btn_menu3);
        resetColor(btn_menu5);
   resetColor(btn_menu6);    
        resetColor(btn_menu7);
        
           main.removeAll();
        main.repaint();
        main.revalidate();
        
        main.add(menu4);
        main.repaint();
        main.revalidate();
    }//GEN-LAST:event_btn_menu4MousePressed

    private void btn_menu5MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_menu5MousePressed
              setColor(btn_menu5);
        
        resetColor(btn_dashboard);
        resetColor(btn_menu1);
        resetColor(btn_menu2);
        resetColor(btn_menu3);
        resetColor(btn_menu4);
   resetColor(btn_menu6);   
        resetColor(btn_menu7);
        
           main.removeAll();
        main.repaint();
        main.revalidate();
        
        main.add(menu5);
        main.repaint();
        main.revalidate();
    }//GEN-LAST:event_btn_menu5MousePressed

    private void btn_menu7MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_menu7MousePressed
                 setColor(btn_menu7);
        
        resetColor(btn_dashboard);
        resetColor(btn_menu1);
        resetColor(btn_menu2);
        resetColor(btn_menu3);
        resetColor(btn_menu4);
        resetColor(btn_menu5);
   resetColor(btn_menu6);  
        
             int dialogbtn = JOptionPane.YES_NO_OPTION;
        int dialogresult = JOptionPane.showConfirmDialog(this, "Anda Yakin Ingin Keluar?", "Warning", dialogbtn);

        if (dialogresult == 0){
                     try {
                         new Login().setVisible(true);
                     } catch (SQLException ex) {
                         Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
                     } catch (ClassNotFoundException ex) {
                         Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
                     }
            this.setVisible(false);
            
            
        }
        else {

        }
    }//GEN-LAST:event_btn_menu7MousePressed

    
    public void tabelKaryawan(){
	    DefaultTableModel model = new DefaultTableModel();
	    model.addColumn("ID");
	    
	    model.addColumn("Username");
            model.addColumn("Password");
            model.addColumn("Nama");
	    try{
		    String sql = "select * from tb_user";
		    pst=conn.prepareStatement(sql);
		    rs=pst.executeQuery();
		    while(rs.next()){
			    model.addRow(new Object[] {rs.getString(1), rs.getString(2),rs.getString(3), rs.getString(4)});
		    }
                    tbl_admin.setModel(model);
		    
		    
	    }catch(SQLException e){
                
	    }
    }

    private void tbl_adminMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_adminMouseClicked
       int row = tbl_admin.rowAtPoint(evt.getPoint());
		String idAdmin = tbl_admin.getValueAt(row,0).toString();
                String username = tbl_admin.getValueAt(row,1).toString();
                String password = tbl_admin.getValueAt(row,2).toString();
		String namaAdmin = tbl_admin.getValueAt(row,3).toString();
		


		if(tbl_admin.getValueAt(row, 0) == null){
			txt_id_admin.setText("");
		}else{
			txt_id_admin.setText(idAdmin);
			txt_id_admin.disable();
		}
                if(tbl_admin.getValueAt(row, 1) == null){
			txt_username.setText("");
		}else{
			txt_username.setText(username);
		}
                if(tbl_admin.getValueAt(row, 2) == null){
			txt_password.setText("");
		}else{
			txt_password.setText(password);
		
                }
                
		if(tbl_admin.getValueAt(row, 3) == null){
			txt_nama.setText("");
		}else{
			txt_nama.setText(namaAdmin);
		}
		
    }//GEN-LAST:event_tbl_adminMouseClicked

    private void jPanel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel9MouseClicked
                String id = txt_id_admin.getText();
		String nama = txt_nama.getText();
		String username = txt_username.getText();
                String password = txt_password.getText();

		if(id.equals("")){
			if(!nama.equals("")){
				String sql = "insert into tb_user values(null, '" + username + "','" + password + "','" + nama  + "')";
				System.out.println(sql);
				try{
				pst = conn.prepareStatement(sql);
				pst.executeUpdate();
                                JOptionPane.showMessageDialog(null,"Data berhasil dimasukkan");
                               resetdatadmin();
				tabelKaryawan();
                                
				}catch(SQLException ex){
					JOptionPane.showMessageDialog(null,"Data gagal dimasukkan" + ex.getMessage());
				}
			}
		}else{
			JOptionPane.showMessageDialog(null,"Data gagal dimasukkan");
		}
    }//GEN-LAST:event_jPanel9MouseClicked

    private void jPanel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel10MouseClicked
        String id = txt_id_admin.getText();
			
		String sql = "delete from tb_user where id = " + id;
		try{
			pst = conn.prepareStatement(sql);
			pst.executeUpdate();
			JOptionPane.showMessageDialog(null,"Data berhasil dihapus");
                        resetdatadmin();
			tabelKaryawan();
		}catch(SQLException ex){
			JOptionPane.showMessageDialog(null,"Data gagal dihapus");
		}
    }//GEN-LAST:event_jPanel10MouseClicked

    private void jPanel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel11MouseClicked
       String id = txt_id_admin.getText();
		String nama = txt_nama.getText();
                String username = txt_username.getText();
		String password = txt_password.getText();
                
			
		String sql = "update tb_user set nama = '" + nama + "', username = '" + username + "', password = '" + password + "' where id = " + id;
		try{
			pst = conn.prepareStatement(sql);
			pst.executeUpdate();
			JOptionPane.showMessageDialog(null,"Data berhasil diupdate");
                        resetdatadmin();
			tabelKaryawan();
                        tampilnama();
		}catch(SQLException ex){
			JOptionPane.showMessageDialog(null,"Data gagal diupdate" + ex.getMessage());
		}
    }//GEN-LAST:event_jPanel11MouseClicked

    private void jLabel40MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel40MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel40MouseClicked
    
    public void tabelBaju(){
	    DefaultTableModel model = new DefaultTableModel();
	    model.addColumn("ID");
	    model.addColumn("Nama");
	    model.addColumn("Stok");
            model.addColumn("Harga");
	    try{
		    String sql = "select * from tb_baju";
		    pst=conn.prepareStatement(sql);
		    rs=pst.executeQuery();
		    while(rs.next()){
			    model.addRow(new Object[] {rs.getString(1), rs.getString(2),rs.getString(3),rs.getString(4)});
		    }
                    tbl_baju.setModel(model);
		    
		    
	    }catch(SQLException e){
                
	    }
    }
    
    private void tbl_bajuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_bajuMouseClicked
        int row = tbl_baju.rowAtPoint(evt.getPoint());
		String idbaju = tbl_baju.getValueAt(row,0).toString();
		String namaBaju = tbl_baju.getValueAt(row,1).toString();
		String stok = tbl_baju.getValueAt(row,2).toString();
                String harga = tbl_baju.getValueAt(row,3).toString();


		if(tbl_baju.getValueAt(row, 0) == null){
			id_baju.setText("");
		}else{
			id_baju.setText(idbaju);
			id_baju.disable();
		}
		if(tbl_baju.getValueAt(row, 1) == null){
		        nama_baju.setText("");
		}else{
			nama_baju.setText(namaBaju);
		}
		if(tbl_baju.getValueAt(row, 2) == null){
			stok_baju.setText("");
		}else{
			stok_baju.setText(stok);
		}
                if(tbl_baju.getValueAt(row, 3) == null){
			harga_baju.setText("");
		}else{
			harga_baju.setText(harga);
		}
    }//GEN-LAST:event_tbl_bajuMouseClicked

    private void jPanel16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel16MouseClicked
        String id = id_baju.getText();
		String nama = nama_baju.getText();
                
		String stok = stok_baju.getText();
                String harga = harga_baju.getText();

		if(id.equals("")){
			if(!nama.equals("")){
				String sql = "insert into tb_baju values(null, '" + nama + "','" + stok + "','" + harga + "')";
				System.out.println(sql);
				try{
				pst = conn.prepareStatement(sql);
				pst.executeUpdate();
                                JOptionPane.showMessageDialog(null,"Data berhasil dimasukkan");
                                resetdatabaju();
				tabelBaju();
                                tampilnama();
                                listPelanggan();
                                listBaju();
				}catch(SQLException ex){
					JOptionPane.showMessageDialog(null,"Data gagal dimasukkan" + ex.getMessage());
				}
			}
		}else{
			JOptionPane.showMessageDialog(null,"Data gagal dimasukkan");
		}
    }//GEN-LAST:event_jPanel16MouseClicked

    private void jPanel17MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel17MouseClicked
        String id = id_baju.getText();
			
		String sql = "delete from tb_baju where id = " + id;
		try{
			pst = conn.prepareStatement(sql);
			pst.executeUpdate();
			JOptionPane.showMessageDialog(null,"Data berhasil dihapus");
                        resetdatabaju();
				tabelBaju();
                                tampilnama();
                                listPelanggan();
        listBaju();
		}catch(SQLException ex){
			JOptionPane.showMessageDialog(null,"Data gagal dihapus");
		}
    }//GEN-LAST:event_jPanel17MouseClicked

    private void jPanel18MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel18MouseClicked
         String id = id_baju.getText();
		String nama = nama_baju.getText();
                
		String stok = stok_baju.getText();
                String harga = harga_baju.getText();
                
			
		String sql = "update tb_baju set nama = '" + nama + "', stok = '" + stok + "', harga = '" + harga + "' where id = " + id;
		try{
			pst = conn.prepareStatement(sql);
			pst.executeUpdate();
			JOptionPane.showMessageDialog(null,"Data berhasil diupdate");
                        resetdatabaju();
				tabelBaju();
                                tampilnama();
                                listPelanggan();
        listBaju();
		}catch(SQLException ex){
			JOptionPane.showMessageDialog(null,"Data gagal diupdate" + ex.getMessage());
		}
    }//GEN-LAST:event_jPanel18MouseClicked

    private void jPanel19MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel19MouseClicked
      resetdatabaju();
    }//GEN-LAST:event_jPanel19MouseClicked

    private void jPanel20MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel20MouseClicked
       resetdatadmin();
    }//GEN-LAST:event_jPanel20MouseClicked

        public void tabelPelanggan(){
	    DefaultTableModel model = new DefaultTableModel();
	    model.addColumn("ID");
	    model.addColumn("Nama");
            
	    model.addColumn("Alamat");
            model.addColumn("No Hp");
	    try{
		    String sql = "select * from tb_pelanggan";
		    pst=conn.prepareStatement(sql);
		    rs=pst.executeQuery();
		    while(rs.next()){
			    model.addRow(new Object[] {rs.getString(1), rs.getString(2),rs.getString(3),rs.getString(4)});
		    }
                    tbl_pelanggan.setModel(model);
		    
		    
	    }catch(SQLException e){
                
	    }
    }
    
    private void tbl_pelangganMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_pelangganMouseClicked
           int row = tbl_pelanggan.rowAtPoint(evt.getPoint());
		String idpel = tbl_pelanggan.getValueAt(row,0).toString();
		String namapel = tbl_pelanggan.getValueAt(row,1).toString();
                
		String alamatpel = tbl_pelanggan.getValueAt(row,2).toString();
                String nopel = tbl_pelanggan.getValueAt(row,3).toString();


		if(tbl_pelanggan.getValueAt(row, 0) == null){
			id_pelanggan.setText("");
		}else{
			id_pelanggan.setText(idpel);
			id_pelanggan.disable();
		}
		if(tbl_pelanggan.getValueAt(row, 1) == null){
		        nama_pelanggan.setText("");
		}else{
			nama_pelanggan.setText(namapel);
		}
		if(tbl_pelanggan.getValueAt(row, 2) == null){
			alamat_pelanggan.setText("");
		}else{
			alamat_pelanggan.setText(alamatpel);
		}
                if(tbl_pelanggan.getValueAt(row, 3) == null){
			nohp_pelanngan.setText("");
		}else{
			nohp_pelanngan.setText(nopel);
		}
    }//GEN-LAST:event_tbl_pelangganMouseClicked

    private void jPanel21MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel21MouseClicked
        String id = id_pelanggan.getText();
		String namapel = nama_pelanggan.getText();
               
		String alamatpel = alamat_pelanggan.getText();
                String hppel = nohp_pelanngan.getText();

		if(id.equals("")){
			if(!namapel.equals("")){
				String sql = "insert into tb_pelanggan values(null, '" + namapel + "','"+ alamatpel + "','" + hppel + "')";
				System.out.println(sql);
				try{
				pst = conn.prepareStatement(sql);
				pst.executeUpdate();
                                JOptionPane.showMessageDialog(null,"Data berhasil dimasukkan");
                                resetdatapelanggan();
				tabelPelanggan();
                                listPelanggan();
        listBaju();
				}catch(SQLException ex){
					JOptionPane.showMessageDialog(null,"Data gagal dimasukkan" + ex.getMessage());
				}
			}
		}else{
			JOptionPane.showMessageDialog(null,"Data gagal dimasukkan");
		}
    }//GEN-LAST:event_jPanel21MouseClicked

    private void jPanel22MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel22MouseClicked
        String id = id_pelanggan.getText();
			
		String sql = "delete from tb_pelanggan where id = " + id;
		try{
			pst = conn.prepareStatement(sql);
			pst.executeUpdate();
			JOptionPane.showMessageDialog(null,"Data berhasil dihapus");
                        resetdatapelanggan();
				tabelPelanggan();
                                listPelanggan();
        listBaju();
		}catch(SQLException ex){
			JOptionPane.showMessageDialog(null,"Data gagal dihapus");
		}
    }//GEN-LAST:event_jPanel22MouseClicked

    private void jPanel25MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel25MouseClicked
        String id = id_pelanggan.getText();
		String namapel = nama_pelanggan.getText();
             
		String alamatpel = alamat_pelanggan.getText();
                String hppel = nohp_pelanngan.getText();
                
			
		String sql = "update tb_pelanggan set nama = '" + namapel + "', alamat = '" + alamatpel + "', no_hp = '" + hppel + "' where id = " + id;
		try{
			pst = conn.prepareStatement(sql);
			pst.executeUpdate();
			JOptionPane.showMessageDialog(null,"Data berhasil diupdate");
                        resetdatapelanggan();
				tabelPelanggan();
                                listPelanggan();
        listBaju();
		}catch(SQLException ex){
			JOptionPane.showMessageDialog(null,"Data gagal diupdate" + ex.getMessage());
		}
    }//GEN-LAST:event_jPanel25MouseClicked

    private void jPanel26MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel26MouseClicked
          resetdatapelanggan();
    }//GEN-LAST:event_jPanel26MouseClicked

    private void tb_detailpenjualanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_detailpenjualanMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tb_detailpenjualanMouseClicked

    private void jPanel27MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel27MouseClicked
      		try{
		String sql = "select id from tb_pelanggan where nama = '" + namapelanggan.getSelectedItem() + "'";
		pst = conn.prepareStatement(sql);
		rs = pst.executeQuery();
		System.out.println(sql);
		while(rs.next()){
			sql = "insert into tb_transaksi values(null, " + rs.getString(1) + ", CURDATE(), default)";
			pst = conn.prepareStatement(sql);
			pst.executeUpdate();
			System.out.println(sql);
			JOptionPane.showMessageDialog(null,"Pembayaran baru atas nama " + namapelanggan.getSelectedItem() + " telah dibuat");
			 tampilnama();
                         listfaktur();
                         tabelPenjualan();
                         totTransaksi();
                         tbltransaksi();
		}
		}catch(SQLException ex){
		}
   
    }//GEN-LAST:event_jPanel27MouseClicked
    
    private void tbl_totaltransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_totaltransaksiMouseClicked
      
    }//GEN-LAST:event_tbl_totaltransaksiMouseClicked

    private void jPanel30MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel30MouseClicked
       			try{
			String sql = "select (stok - " + qty.getText() + ") from tb_baju where nama = '" + namabaju.getSelectedItem() + "'";
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			while(rs.next()){
				if(rs.getInt(1) >= 0){
					int um = Integer.valueOf(qty.getText());
					System.out.println(um);
					if( um > 0 ){
					sql = "select id from tb_baju where nama = '" + namabaju.getSelectedItem() + "'";
					pst = conn.prepareStatement(sql);
					rs = pst.executeQuery();
					while(rs.next()){
						idbaju = rs.getString(1);
					} 
					sql = "insert into tb_detail_transaksi values(null, '" + idtransaksi.getSelectedItem() + "','" + idbaju + "','" + qty.getText() + "','" + id + "')";
					pst = conn.prepareStatement(sql);
					pst.executeUpdate();
                                        
					sql = "update tb_baju set stok = (stok - " + qty.getText() + ") where id = " + idbaju;
					pst = conn.prepareStatement(sql);
					pst.executeUpdate();
					tabelDetailPenjualan((String) idtransaksi.getSelectedItem());
                                        
                                         
                                        
                                        tabelPenjualan();
                                        totTransaksi();
                                        tbltransaksi();
				} else {
				}
			}else{
					sql = "select stok from tb_baju where nama = '" + namabaju.getSelectedItem() + "'"; 
					pst = conn.prepareStatement(sql);
					rs = pst.executeQuery();
					while(rs.next()){
						JOptionPane.showMessageDialog(null,"Stok baju untuk " + namabaju.getSelectedItem() + " tidak mencukupi");
						JOptionPane.showMessageDialog(null,"Sisa stok untuk " + namabaju.getSelectedItem() + " adalah " + rs.getString(1));
					}
				}
			}
		}catch(SQLException ex){
			System.out.println("gabisa gan");
		}
	
    }//GEN-LAST:event_jPanel30MouseClicked

    private void jPanel31MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel31MouseClicked
           		try {
                        
			int bayar;
                        int kembalian;
                        
                        
			try{
				bayar = Integer.parseInt(
				JOptionPane.showInputDialog(this, "Nominal :"));

				if(bayar > 20000000){
					JOptionPane.showMessageDialog(null,"Batas pembayaran telah mencapai limit");
					bayar = 0;
				}
			System.out.println(bayar);
			}catch(NumberFormatException ex){
				bayar = 0;
				JOptionPane.showMessageDialog(null,"Tolong input nominal dengan benar");
			}
			// TODO add your handling code here:    
			String sql = "select sum(dt.qty * b.harga) from tb_detail_transaksi as dt join tb_transaksi as t on dt.id_transaksi = t.no_faktur join tb_baju as b on dt.id_baju = b.id join tb_pelanggan as p on t.id_pelanggan = p.id join tb_user as u on dt.id_user = u.id where t.no_faktur = " + idtransaksi.getSelectedItem();
			pst=conn.prepareStatement(sql);
			rs=pst.executeQuery();
			while(rs.next()){
				int total = Integer.parseInt(rs.getString(1)) - bayar;
				int hargaTot = Integer.parseInt(rs.getString(1));
                                
				if(total <= 0){

					sql = "update tb_transaksi set bayar = 'sudah' where no_faktur = " + idtransaksi.getSelectedItem();
					pst=conn.prepareStatement(sql);
					pst.executeUpdate();
					String idTran = (String) idtransaksi.getSelectedItem();
                                         tabelKaryawan();
                                         tabelBaju();
                                         tabelPelanggan();
                                         listPelanggan();
                                         listBaju();
                                         listfaktur();
       
                                         tabelPenjualan();
                                         totTransaksi();
                                         tbltransaksi();
                                         tampilnama();
                                        
                                       
					// insert ke pembayaran
					sql = "insert into tb_pembayaran values (null, " + idTran + ", " + hargaTot + ", CURDATE())";
					pst=conn.prepareStatement(sql);
					pst.executeUpdate();

					System.out.println("idtran = " +idTran+ " idkasir = " +id+ " bayar = " +bayar);
					  tabelKaryawan();
                                          tabelBaju();
                                          tabelPelanggan();
                                          listPelanggan();
                                          listBaju();
                                          listfaktur();
       
                                          tabelPenjualan();
                                          totTransaksi();
                                          tbltransaksi();
                                          tampilnama();
                                          
                                          kembalian = bayar - hargaTot;
                                          
                                         JOptionPane.showMessageDialog(this, "Total Kembalian Rp. "+kembalian);
                                          
    
    
				}else{
					JOptionPane.showMessageDialog(null,"Nominal tidak mencukupi");
				}
			}
		} catch (SQLException ex) {
			Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
		}

    }//GEN-LAST:event_jPanel31MouseClicked

    private void caribajuKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_caribajuKeyReleased
        namabaju.removeAllItems();
	    try{
		    String sql = "select nama from tb_baju where nama like '%" + caribaju.getText() + "%' order by nama asc";
		    pst=conn.prepareStatement(sql);
		    rs=pst.executeQuery();
		    while(rs.next()){
			namabaju.addItem(rs.getString(1));
		    }
	    }catch(SQLException e){
	    }
    }//GEN-LAST:event_caribajuKeyReleased

    private void caripelangganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_caripelangganActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_caripelangganActionPerformed

    private void caripelangganKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_caripelangganKeyReleased
       namapelanggan.removeAllItems();
	    try{
		    String sql = "select distinct nama from tb_pelanggan where nama like '%" + caripelanggan.getText() + "%' order by nama asc";
		    pst=conn.prepareStatement(sql);
		    rs=pst.executeQuery();
		    
		    while(rs.next()){
			namapelanggan.addItem(rs.getString(1));
		    }
	    }catch(SQLException e){
	    }
    }//GEN-LAST:event_caripelangganKeyReleased

    private void btn_menu6MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_menu6MousePressed
            try {
                tampilnama();
            } catch (SQLException ex) {
                Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        setColor(btn_menu6);
        
        resetColor(btn_menu1);
        resetColor(btn_menu2);
        resetColor(btn_menu3);
        resetColor(btn_menu4);
        resetColor(btn_menu5);
        resetColor(btn_dashboard);
        resetColor(btn_menu7);
        
         
        
        main.removeAll();
        main.repaint();
        main.revalidate();
        
        main.add(menu6);
        main.repaint();
        main.revalidate();
        
    }//GEN-LAST:event_btn_menu6MousePressed

    private void tbl_penjualanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_penjualanMouseClicked
       	int row = tbl_penjualan.rowAtPoint(evt.getPoint());
		String idTran = tbl_penjualan.getValueAt(row,0).toString();
		String pelanggan = tbl_penjualan.getValueAt(row,1).toString();
		String baju = tbl_penjualan.getValueAt(row,2).toString();
		String qty = tbl_penjualan.getValueAt(row,3).toString();
		String kasir = tbl_penjualan.getValueAt(row,4).toString();


		if(tbl_penjualan.getValueAt(row, 0) == null){
			id_transaksi.setText("");
		}else{
			id_transaksi.setText(idTran);
			id_transaksi.disable();
		}
		if(tbl_penjualan.getValueAt(row, 1) == null){
			namaplg.setText("");
		}else{
			namaplg.setText(pelanggan);
			namaplg.disable();
		}
		if(tbl_penjualan.getValueAt(row, 2) == null){
			namabajucuy.setSelectedItem(this);
		}else{
			namabajucuy.setSelectedItem(baju);
                        namabajucuy.disable();
		String sql = "select id from tb_baju where nama = '" + namabajucuy.getSelectedItem() + "'";
		try{
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			while(rs.next()){
				sembunyi_anjay.setText(rs.getString(1));
			}
		}catch(SQLException ex){
		}
		}
		if(tbl_penjualan.getValueAt(row, 3) == null){
			qtyy.setText("");
		}else{
			qtyy.setText(qty);
                        qtyy.disable();
		}
		if(tbl_penjualan.getValueAt(row, 4) == null){
			kasiir.setText("");
		}else{
			kasiir.setText(kasir);
			kasiir.disable();
		}
    }//GEN-LAST:event_tbl_penjualanMouseClicked

    private void jPanel33MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel33MouseClicked
       		String sql = "delete from tb_detail_transaksi where id = '" + id_transaksi.getText() + "'";
		try{
			pst = conn.prepareStatement(sql);
			pst.executeUpdate();
			sql = "update tb_baju set stok = (stok + " + qtyy.getText() + ") where id = " + sembunyi_anjay.getText();
			pst = conn.prepareStatement(sql);
			pst.executeUpdate();
			System.out.println(sql);
			   JOptionPane.showMessageDialog(null,"Data telah dihapus");
			   tabelPenjualan();
                           tampilnama();
                           
                           tabelBaju();
                           listBaju();
		}catch(SQLException ex){
			   JOptionPane.showMessageDialog(null,"Data gagal dihapus" + ex.getMessage());
		}
                
    }//GEN-LAST:event_jPanel33MouseClicked

    private void idtransaksiPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_idtransaksiPopupMenuWillBecomeInvisible

       	        try{
			String sql = "select t.tanggal, p.nama from tb_transaksi as t join tb_pelanggan as p on t.id_pelanggan = p.id where t.no_faktur = " + idtransaksi.getSelectedItem() + " and t.bayar = 'belum' ";
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			while(rs.next()){
				tanggalbeli.setText(rs.getString(1));
				namapelanggan.setSelectedItem(rs.getString(2));
				tabelDetailPenjualan((String) idtransaksi.getSelectedItem());
			}
		}catch(SQLException ex){
			
		}	
    }//GEN-LAST:event_idtransaksiPopupMenuWillBecomeInvisible

    private void btn_dashboardMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_dashboardMouseClicked
            try {
                tampilnama();
            } catch (SQLException ex) {
                Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
            }
     
        
    }//GEN-LAST:event_btn_dashboardMouseClicked

    private void btn_menu3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_menu3MouseClicked
       tabelBaju();
       
    }//GEN-LAST:event_btn_menu3MouseClicked

    private void namabajucuyPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_namabajucuyPopupMenuWillBecomeInvisible
       String sql = "select id from tb_baju where nama = '" + namabajucuy.getSelectedItem() + "'";
		try{
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			while(rs.next()){
				sembunyi_anjay.setText(rs.getString(1));
			}
		}catch(SQLException ex){
		}
    }//GEN-LAST:event_namabajucuyPopupMenuWillBecomeInvisible
        

        
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Dashboard().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Pane_Utama;
    private javax.swing.JTextField alamat_pelanggan;
    private javax.swing.JPanel btn_dashboard;
    private javax.swing.JPanel btn_menu1;
    private javax.swing.JPanel btn_menu2;
    private javax.swing.JPanel btn_menu3;
    private javax.swing.JPanel btn_menu4;
    private javax.swing.JPanel btn_menu5;
    private javax.swing.JPanel btn_menu6;
    private javax.swing.JPanel btn_menu7;
    private javax.swing.JTextField caribaju;
    private javax.swing.JTextField caripelanggan;
    private javax.swing.JPanel dashboard;
    private javax.swing.JTextField harga_baju;
    private javax.swing.JTextField id_baju;
    private javax.swing.JTextField id_pelanggan;
    private javax.swing.JTextField id_transaksi;
    private javax.swing.JComboBox<String> idtransaksi;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTextField kasiir;
    private javax.swing.JPanel main;
    private javax.swing.JPanel menu;
    private javax.swing.JPanel menu1;
    private javax.swing.JPanel menu2;
    private javax.swing.JPanel menu3;
    private javax.swing.JPanel menu4;
    private javax.swing.JPanel menu5;
    private javax.swing.JPanel menu6;
    private javax.swing.JTextField nama_baju;
    private javax.swing.JTextField nama_pelanggan;
    private javax.swing.JTextField namaadmin;
    private javax.swing.JComboBox<String> namabaju;
    private javax.swing.JComboBox<String> namabajucuy;
    private javax.swing.JComboBox<String> namapelanggan;
    private javax.swing.JTextField namaplg;
    private javax.swing.JTextField nohp_pelanngan;
    private javax.swing.JTextField qty;
    private javax.swing.JTextField qtyy;
    private javax.swing.JLabel sembunyi_anjay;
    private javax.swing.JTextField stok_baju;
    private javax.swing.JTextField tanggalbeli;
    private javax.swing.JTable tb_detailpenjualan;
    private javax.swing.JTable tbl_admin;
    private javax.swing.JTable tbl_baju;
    private javax.swing.JTable tbl_pelanggan;
    private javax.swing.JTable tbl_penjualan;
    private javax.swing.JTable tbl_totaltransaksi;
    private javax.swing.JTextField totalbayar;
    private javax.swing.JTextField txt_id_admin;
    private javax.swing.JTextField txt_nama;
    private javax.swing.JPasswordField txt_password;
    private javax.swing.JTextField txt_username;
    // End of variables declaration//GEN-END:variables

    private void tbl_baju() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
