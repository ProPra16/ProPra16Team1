// MIT License, check LICENSE.txt in the src folder for full text

package logic;

import java.io.*;
import java.io.IOException;
import java.util.ArrayList;


public class Loader{

 public static String exc_file ="Aufgabenkatalog";
 public static String line = null;
 
 private static String cur_Test = "currentTest";
 private static String cur_Class = "currentClass";

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
   line = cutter(line);
   Aufgaben_Verwaltung.get(index_Aufgabe).saveClassName(line);
  line = bufferedReader.readLine();                               //Konstruktor 
  Aufgaben_Verwaltung.get(index_Aufgabe).generateKlasse(line);   //laden
  line = bufferedReader.readLine();
  Aufgaben_Verwaltung.get(index_Aufgabe).generateKlasse(line); 
  }
  if(line.contains("Testname")){
	line = cutter(line);
    Aufgaben_Verwaltung.get(index_Aufgabe).saveTestName(line);
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
int vorne = toCut.indexOf('=')+2;
int hinten = toCut.length();
toCut = toCut.substring(vorne,hinten);
return toCut;
}

public void saveNew(int exc_nr){
Aufgabe excToSave = Aufgaben_Verwaltung.get(exc_nr);	
 String file = cur_Test;
 String line = null;
 try{                                                             //Test-Gerüst speichern
	 FileWriter writer = new FileWriter(file);
	 BufferedWriter bufferedWriter = new BufferedWriter(writer);
 for(int i=0;i<excToSave.getTest().size();i++){
  line = excToSave.getTestConstructor(i);
  bufferedWriter.write(line);
  bufferedWriter.newLine();	 
 }
 bufferedWriter.close();
 }
 catch(IOException e){
 System.out.println("ERROR:Konnte nicht in die Datei schreiben");
 }
 file ="currentClass";
 try{                                                           //Klassengerüst speichern
	 FileWriter writer = new FileWriter(file);
	 BufferedWriter bufferedWriter = new BufferedWriter(writer);
 for(int i=0;i<excToSave.getKlasse().size();i++){
  line = excToSave.getClassConstructor(i);
  bufferedWriter.write(line);
  bufferedWriter.newLine();	 
 }
 bufferedWriter.close();
 }
 catch(IOException e){
 System.out.println("ERROR:Konnte nicht in die Datei schreiben");
 }
 
}

public String TestLine(int lineToRead){
 String file = cur_Test;
 String line = null;
 try{
  FileReader reader = new FileReader(file);                     
  BufferedReader bufferedReader = new BufferedReader(reader);
  for(int i=0;i<lineToRead;i++){bufferedReader.readLine();}  
  line = bufferedReader.readLine();
  bufferedReader.close();
  }
  catch(FileNotFoundException e){                             
  System.out.println("ERROR:Findet die Datei currenTest nicht");
  }
  catch(IOException e){
  System.out.println("ERROR:Fehler beim Lesen der Datei!"); 
  } 
 return line;
 }

public void save(String File,String text){
 try{
	 FileWriter writer = new FileWriter(File,false);
	 BufferedWriter bufferedWriter = new BufferedWriter(writer);
	 bufferedWriter.write(text);
	bufferedWriter.close();
}
catch(IOException e){
System.out.println("ERROR:Konnte nicht in die Datei schreiben");
}
}

public String loadCurrentData(String file){
String line = null;
String ergebnis = "";
try{
	FileReader reader = new FileReader(file);                     
	BufferedReader bufferedReader = new BufferedReader(reader);
	while((line = bufferedReader.readLine()) != null){
	 ergebnis +=  line+"\n";
	}
	bufferedReader.close();
  }
  catch(FileNotFoundException e){                             
  System.out.println("ERROR:Findet die Datei currenTest nicht");
  }
  catch(IOException e){
  System.out.println("ERROR:Fehler beim Lesen der Datei!");
 }
return ergebnis;
}
}
