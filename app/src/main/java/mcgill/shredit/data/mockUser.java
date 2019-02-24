package mcgill.shredit.data;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.File;
import java.io.PrintWriter;

public class mockUser {
        //private final static String csvFile = "./app/src/main/java/mcgill/shreddit/data/UserData.csv";
        String[][] userData=new String[100][2];
        int arrayIndex=0;
        public boolean registerUser(String username,String password) throws IOException {
         /*   String line = "";
            BufferedReader br = new BufferedReader(new FileReader(csvFile));
            String[] cols = new String[2];
            boolean idFound = false;
            while (!idFound) { //check if username already exists
                line = br.readLine();
                if(line==null){
                    break;
                }
                // use comma as separator
                cols = line.split(",");
                if (cols[0].equals( username) ) {
                    idFound = true;
                }
            }
            if(idFound){ //if username exists
                return false; //cannot register
            }else {*/
            //FileWriter fileWriter = new FileWriter(csvFile);
            //PrintWriter printWriter = new PrintWriter(fileWriter);
            //printWriter.printf(username+","+password);
            //printWriter.close();
            // BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile));
            // writer.write(username+","+password);
            //PrintWriter writer = null;
            // writer = new PrintWriter(new File(csvFile));
            //writer.write(username+","+password);
            // writer.write(username +","+password);
            for(int i =0;i<arrayIndex+1;i++){
                if(username.equals(userData[i][0])){
                    return false;
                }
            }
            userData[arrayIndex][0]=username;
            userData[arrayIndex][1]=password;
            arrayIndex++;
            return true;


        }
}
