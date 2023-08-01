import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;



public class Weather_Forcast {
	
	static String API_URL;
    
    public static String getDatafromExcel() throws EncryptedDocumentException, IOException {
    	FileInputStream fis=new FileInputStream("./src/test/resources/nimesa.xlsx");
		   Workbook book=WorkbookFactory.create(fis);
		   Sheet sh=book.getSheet("Sheet1");
		   DataFormatter format=new DataFormatter();
		   String t= format.formatCellValue(sh.getRow(0).getCell(0));
		   return t;
    	
    }
    public static void main(String[] args) throws EncryptedDocumentException, IOException {
    	API_URL = getDatafromExcel();
        Scanner scanner = new Scanner(System.in);
        int option;
        do {
            displayMenu();
            option = scanner.nextInt();
            switch (option) {
                case 1:
                    getWeatherDetails();
                    break;
                case 2:
                    getWindSpeedDetails();
                    break;
                case 3:
                    getPressureDetails();
                    break;
                case 0:
                    System.out.println("***BYE***");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } while (option != 0);

        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("Weather App Menu");
        System.out.println("1. Get weather");
        System.out.println("2. Get Wind Speed");
        System.out.println("3. Get Pressure");
        System.out.println("0. Exit");
        System.out.println("Please enter your choice:");
    }

    private static void getWeatherDetails() {
        String date = promptDate();
        String apiUrl = API_URL;
        String response = makeAPIRequest(apiUrl);

      
        System.out.println("Temperature on " + date + response); 
    }

    private static void getWindSpeedDetails() {
        String date = promptDate();
        String apiUrl = API_URL;
        String response = makeAPIRequest(apiUrl);

        
        System.out.println("Wind Speed on " + date + response); 
    }

    private static void getPressureDetails() {
        String date = promptDate();
        String apiUrl = API_URL;
        String response = makeAPIRequest(apiUrl);

        
        System.out.println("Pressure on " + date + response); 
    }

    private static String promptDate() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the date (yyyy-MM-dd HH:mm:ss format):");
        return scanner.nextLine();
    }

    private static String makeAPIRequest(String apiUrl) {
        StringBuilder response = new StringBuilder();
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response.toString();
    }

}
