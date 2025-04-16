
package nic.ame.app.test.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class InsertLogin {

	public static void main(String[] args) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5434/ame_db", "postgres", "postgres");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Opened database successfully");
		String st = "INSERT INTO login_tbl(user_name, first_login_flag, force_personal_id, "
				+ "in_active_by, in_active_by_ip," + " password, user_status)" + "VALUES (?, ?, ?, ?, ?, ?, ?);";
		ps = (PreparedStatement) con.prepareStatement(st);

		ps.setString(1, "6727727");
		ps.setString(2, "N");
		ps.setString(3, "893AR01");
		ps.setString(4, "us");
		ps.setString(5, "us");

		ps.setString(6, bCryptPasswordEncoder.encode("123456"));
		ps.setInt(7, 1);

		System.out.println(">>>>>>>>>>>>>>>>>>" + ps.execute());

	}

}
