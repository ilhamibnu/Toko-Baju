/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gabut.controller;
import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author Anjayani
 */
public class Koneksi {
    
    private static Connection koneksi;

	public static Connection getKoneksi() {

		if (koneksi == null) {
			try {
				koneksi = DriverManager.getConnection("jdbc:mysql://localhost/db_trello", "root", "");
			} catch (SQLException ex) {
				JOptionPane.showMessageDialog(null, "Database Tidak Terhubung" + ex.getMessage());
			}
		}

		return koneksi;

	}

	static Object getConnection() {

		throw new UnsupportedOperationException("Tidak Dapat Terhubung");
	}

}
