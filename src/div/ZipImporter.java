package div;

import classpackage.DBConnector;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by roger on 08.04.2016.
 * Download postnummerregister_ansi.txt from http://www.bring.no/radgivning/sende-noe/adressetjenester/postnummer into same folder as class and convert
 * it to UTF-8 without BOM in ie. Notepad++ before import. Depends on DBConnector class for DB connection.
 */
public class ZipImporter {

	public static void main(String[] args) {
		Importer i = new Importer();
	}
}

class Importer extends DBConnector {

	public Importer() {
		try {
			PreparedStatement q = con.prepareStatement("INSERT INTO zipcode (zipcode, place) VALUES (?, ?);");
			PreparedStatement q2 = con.prepareStatement("SELECT * FROM zipcode WHERE zipcode = ?;");
			PreparedStatement q3 = con.prepareStatement("UPDATE zipcode SET place = ? WHERE zipcode = ?;");
			Scanner s = new Scanner(new BufferedReader(new InputStreamReader(new FileInputStream(".\\src\\div\\Postnummerregister_ansi.txt"))));
			while(s.hasNextLine()) {
				String[] line = s.nextLine().split("\t");
				q2.setString(1, line[0]);
				ResultSet res = q2.executeQuery();
				if(res.next()) { // Checks if zip code already exists in the table and updates the row.
					q3.setString(1, line[1]);
					q3.setString(2, line[0]);
					q3.executeUpdate();
				} else {
					q.setString(1, line[0]);
					q.setString(2, line[1]);
					q.executeUpdate();
				}
			}
			System.out.println("Done!");
		} catch(IOException e) {
			System.out.println("File read error: " + e);
		} catch(SQLException e) {
			System.out.println("SQL error: " + e);
		} catch(NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}
}