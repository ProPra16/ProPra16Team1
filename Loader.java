import java.io.*;
import java.nio.file.*;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;


public class Loader{

 public static String exc_file ="Aufgabenkatalog.txt";
 public static String line = null;

 public ArrayList<Aufgabe> Aufgaben_Verwaltung = new ArrayList<Aufgabe>();

 public void read_exc(){
 String tmp_Name= null;             //Dazu da um
 String tmp_Beschreibung= null;     // Aufgabenklasse zu erstellen

 int index_Aufgabe=0;              //Um die korrekte Zuordung des Konstruktors zu ermöglichen

 try{
 FileReader reader = new FileReader(exc_file);                     
 BufferedReader bufferedReader = new BufferedReader(reader);
 bufferedReader.readLine();
 bufferedReader.readLine();    //Das Kommentar überlesen
 while((line = bufferedReader.readLine()) != null){
  if(line.contains("Aufgabenname")){
   tmp_Name = cutter(line);
   }
  if(line.contains("Beschreibung")){
   tmp_Beschreibung = cutter(line);
   Aufgaben_Verwaltung.add(index_Aufgabe,new Aufgabe(tmp_Name,tmp_Beschreibung));
   }
  if(line.contains("Klassenname")){
  line = bufferedReader.readLine();                               //Konstruktor 
  Aufgaben_Verwaltung.get(index_Aufgabe).generateKlasse(line);   //laden
  line = bufferedReader.readLine();
  Aufgaben_Verwaltung.get(index_Aufgabe).generateKlasse(line); 
  }
  if(line.contains("Testname")){
   for(int i=0;i<7;i++){  
   line = bufferedReader.readLine();
   Aufgaben_Verwaltung.get(index_Aufgabe).generateTest(line);
   }
   index_Aufgabe++;
  }
   }
  bufferedReader.close();
  }
  catch(FileNotFoundException e){                             
  System.out.println("ERROR:Findet die Datei Aufgabenkatalog nicht");
  }
  catch(IOException e){
  System.out.println("ERROR:Fehler beim Lesen der Datei!"); 
  }

}

public String cutter(String toCut){       //Entfernt den Teil mit dem Gleichheitszeichen 
int vorne = toCut.indexOf('=')+1;
int hinten = toCut.length();
toCut = toCut.substring(vorne,hinten);
return toCut;
}



}
