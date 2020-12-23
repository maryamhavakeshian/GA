package TSP;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
public class CityEngine {

   
    public HashMap<Integer,City> cities;
    
    //read the file, create a hash map, whose key are the IDs of city and whose value is objects of City class 
    public CityEngine(String fileName) {
		this.cities =new HashMap<Integer,City>();
    	this.fileName = fileName;
		readFile(this.fileName);
	}

	public HashMap<Integer, City> getCities() {
		return cities;
	}

	public void setCities(HashMap<Integer, City> cities) {
		this.cities = cities;
	}

	String fileName;

    public void readFile(String fileNam) {
        try {
            FileReader fileReader = new FileReader(new File(fileNam));    
            BufferedReader bufferedReader = new BufferedReader(fileReader);    
            boolean isnodeCordinate= false;
            String tspLine;
            int lineTressold= 0;
            int currentLine=1;
            
            while ((tspLine = bufferedReader.readLine()) != null ) { 
            	 String[] cityLine ;
                 if(!tspLine.startsWith("NODE_COORD_SECTION")&& !isnodeCordinate&& currentLine >= lineTressold ) {
                     isnodeCordinate= false;
                  }
                 else if (tspLine.startsWith("NODE_COORD_SECTION")&& !isnodeCordinate) {
                	 isnodeCordinate=true;
                	 ++currentLine;
                	 lineTressold = currentLine+1;
                 }else if (isnodeCordinate&&currentLine<lineTressold) {
            
             
                     cityLine=tspLine.trim().split("\\s+");

                    if (cityLine!=null && cityLine.length>1) {
                    	
                     City city = new City(Double.parseDouble(cityLine[1]),Double.parseDouble(cityLine[2]));
                     this.cities.put(Integer.valueOf(cityLine[0]),city);
                     }

                 }
           	 
            }
            if(this.cities.size()!=0)
            	System.out.println("city size "+ cities.size());
            	
            fileReader.close();      
        } catch(FileNotFoundException e) {
           System.out.println(" The file specified is not correct");
           System.exit(1);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
