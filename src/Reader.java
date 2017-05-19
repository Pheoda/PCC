// Read the .csv file, and translate into City object

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Reader {
    private FileReader fReader;
    private BufferedReader bReader;

    public Reader(String filename) {
        try {
            fReader = new FileReader(filename);
            bReader = new BufferedReader(fReader);

            // Skip header
            String header = bReader.readLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public City read() {

        try {
            String line = bReader.readLine();

			if(line != null) {
				String[] array = line.split(";");

				// City parameters
				String id 			= array[0];
				String name 		= array[1];
				int population 		= Integer.parseInt(array[2]);
				double latitude 	= Double.parseDouble(array[3].replace(',', '.'));
				double longitude 	= Double.parseDouble(array[4].replace(',', '.'));

				return new City(id, name, population, latitude, longitude);
			}
			else {
				bReader.close();
				fReader.close();
			}

        } catch (IOException e) {
            e.printStackTrace();
        }
        // Error case
        return null;
	}
}