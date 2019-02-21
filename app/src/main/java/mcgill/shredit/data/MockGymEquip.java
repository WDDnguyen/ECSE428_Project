package mcgill.shredit.data
import java.io.BufferedReader;
import java.io.FileReader;

public class MockGymEquip {
	public static String[] getEquipmentID(int equipmentID) throws Exception {
		String csvFile = "./MockGymEquipmentData.csv";
		String line = "";
		BufferedReader br = new BufferedReader(new FileReader(csvFile));
		String[] cols = new String[3];
		boolean idFound = false;
		while (!idFound) {
			line = br.readLine();
			
			// use comma as separator
			cols = line.split(",");
			if(Integer.parseInt(cols[0]) == equipmentID){
				idFound = true;
			}
		}
		return cols;
	}
}
