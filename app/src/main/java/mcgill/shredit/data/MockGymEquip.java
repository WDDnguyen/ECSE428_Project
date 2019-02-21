package mcgill.shredit.data

import java.io.BufferedReader;
import java.io.FileReader;

public class MockGymEquip {
	private final static String csvFile = "./src/main/java/mcgill/shreddit/data/MockGymEquipmentData.csv";
	
	public static String[] getEquipmentByID(int equipmentID) throws Exception {
		String line = "";
		BufferedReader br = new BufferedReader(new FileReader(csvFile));
		String[] cols = new String[3];
		boolean idFound = false;
		while (!idFound) {
			line = br.readLine();

			// use comma as separator
			cols = line.split(",");
			if (Integer.parseInt(cols[0]) == equipmentID) {
				idFound = true;
			}
		}
		return cols;
	}

	public static String[] getEquipmentByEquipment(String equipmentName) throws Exception {
		String line = "";
		BufferedReader br = new BufferedReader(new FileReader(csvFile));
		String[] cols = new String[3];
		boolean idFound = false;
		while (!idFound) {
			line = br.readLine();

			// use comma as separator
			cols = line.split(",");
			if (0 == cols[1].compareTo(equipmentName)) {
				idFound = true;
			}
		}
		return cols;
	}

	public static String[] getEquipmentByMuscle(String muscleName) throws Exception {
		String line = "";
		BufferedReader br = new BufferedReader(new FileReader(csvFile));
		String[] cols = new String[3];
		boolean idFound = false;
		while (!idFound) {
			line = br.readLine();

			// use comma as separator
			cols = line.split(",");
			if (0 == cols[2].compareTo(muscleName)) {
				idFound = true;
			}
		}
		return cols;
	}
}
