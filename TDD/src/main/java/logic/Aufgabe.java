// MIT License, check LICENSE.txt in the src folder for full text

package main.java.logic;

import java.util.ArrayList;

public class Aufgabe{                        //Datentyp zur besseren Verwaltung der Übungen

 private  String exc_Name;
 private  String exc_Beschreibung;
 private  String className;
 private  String testName;
 private  ArrayList<String> exc_Klasse = new ArrayList<String>();
 private  ArrayList<String> exc_Test = new ArrayList<String>();

 public Aufgabe(String name,String beschreibung){
 exc_Name=name;
 exc_Beschreibung=beschreibung;
 }
	 
 public String getName(){
 return exc_Name;
 }

 public String getBeschreibung(){
 return exc_Beschreibung;
 }

 public void generateKlasse(String line_geruest){
 exc_Klasse.add(line_geruest);
 }

 public void generateTest(String line_geruest){
 exc_Test.add(line_geruest);
 }
 
 public ArrayList<String> getKlasse(){
 return exc_Klasse;
 }

 public ArrayList<String> getTest(){
 return exc_Test;
 }

 public String getTestConstructor(int const_line){
 String line = exc_Test.get(const_line);	 
 return line;	 
 }

 public String getClassConstructor(int const_line){
 String line = exc_Klasse.get(const_line);	 
 return line;	 
 }
 
 public void saveClassName(String Name){
 className = Name;	 
 }

 public void saveTestName(String Name){
 testName = Name;	 
 }
 
 public String testName(){
 return testName;	 
 }
 
 public String className(){
 return className;	 
 }
 
}
