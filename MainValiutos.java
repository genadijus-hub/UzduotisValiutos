package Valiutos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.plaf.synth.SynthOptionPaneUI;

public class MainValiutos {

	public static int savaitesDiena;
	public static String uzklausa1 = "";
	public static String uzklausa2 = "";

	public static void main(String[] args) throws NumberFormatException, MalformedURLException {

		BufferedReader br = null;
		String line = "";
		String splitBy = ";";
		String valiutosPasirinkimas; // = "DKK";
		String data1;
		String data2;
		double kursas1 = 0;
		double kursas2 = 0;
		String urlAdresas = "https://www.lb.lt/lt/currency/daylyexport/?csv=1&class=Eu&type=day&date_day=";
		String nieko = "";
		String REGEX = "\"";
		String REGEX1 = ",";
		String taskas = ".";

		Scanner sc = new Scanner(System.in);

		do {
			System.out.println("Įveskite data1 (formatas  2019-12-15)");
			data1 = sc.nextLine();
			LocalDate d1 = LocalDate.parse(data1);
			savaitesDiena = DayOfWeek.from(d1).getValue();
		} while (savaitesDiena >= 6);

		uzklausa1 = urlAdresas + data1;

		System.out.println(savaitesDiena);

		do {
			System.out.println("Įveskite data2 (formatas  2019-12-15)");
			data2 = sc.nextLine();
			LocalDate d2 = LocalDate.parse(data2);
			savaitesDiena = DayOfWeek.from(d2).getValue();
		} while (savaitesDiena >= 6);

		uzklausa2 = urlAdresas + data2;

		System.out.println("Įeskite valiutos trumpinį");
		valiutosPasirinkimas = sc.nextLine();

		sc.close();

		try {

			URL url1 = new URL(uzklausa1);
			URLConnection urlcon1 = url1.openConnection();
			urlcon1.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
			br = new BufferedReader(new InputStreamReader(urlcon1.getInputStream()));

			while ((line = br.readLine()) != null) {
				Pattern p = Pattern.compile(REGEX);

				Matcher m = p.matcher(line);
				line = m.replaceAll(nieko);
//			      System.out.println(line);

				String[] currency = line.split(splitBy);

				if (currency[1].equals(valiutosPasirinkimas)) {

					System.out.println(currency[0] + "   " + currency[2]);
					Pattern p1 = Pattern.compile(REGEX1);

					Matcher m1 = p1.matcher(currency[2]);
					currency[2] = m1.replaceAll(taskas);
					kursas1 = Double.parseDouble(currency[2]);
				}
			}

		} catch (Exception e) {

		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		try {

			URL url1 = new URL(uzklausa2);
			URLConnection urlcon1 = url1.openConnection();
			urlcon1.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
			br = new BufferedReader(new InputStreamReader(urlcon1.getInputStream()));

			while ((line = br.readLine()) != null) {
				Pattern p = Pattern.compile(REGEX);

				Matcher m = p.matcher(line);
				line = m.replaceAll(nieko);
//			      System.out.println(line);

				String[] currency = line.split(splitBy);

				if (currency[1].equals(valiutosPasirinkimas)) {

					System.out.println(currency[0] + "   " + currency[2]);
					Pattern p1 = Pattern.compile(REGEX1);

					Matcher m1 = p1.matcher(currency[2]);
					currency[2] = m1.replaceAll(taskas);
					kursas2 = Double.parseDouble(currency[2]
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		System.out.println("Reiksme  " + kursas2);

		double pokitis = kursas1 - kursas2;
		System.out.println(
				data1 + "  " + data2 + "  " + "laikotarpiu " + valiutosPasirinkimas + " kurso pokitis yra " + pokitis);

	}

}
