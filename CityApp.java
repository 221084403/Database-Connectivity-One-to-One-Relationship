/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cityapp;

import java.sql.*;
import java.util.*;
import za.ac.tut.bl.*;
import za.ac.tut.entities.*;

/**
 *
 * @author MNCEDISI
 */
public class CityApp 
{
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        // TODO code application logic here
        
        //database connection 
        String dbURL = "jdbc:derby://localhost:1527/CityDataBase";
        String userName = "CityDB";
        String password = "123";
        
        //store into database statement
        String storeSQLC = "INSERT INTO CITY_TBL VALUES(? ,? , ?)";
        String storeSQLM = "INSERT INTO MAYOR_TBL VALUES(? ,? , ? , ? ,?)";
        
        //change mayor details
        String changeDetailsSQLM = "UPDATE MAYOR_TBL "+
                                   "SET NAME =? , SURNAME =? "+
                                   "WHERE IdNumber = ? ";
        
        //deleting record match id 
        String deleteSQLC = "DELETE FROM CITY_TBL "+
                            "WHERE IdNumber = ? ";
        
        String deleteSQLM = "DELETE FROM MAYOR_TBL "+
                            "WHERE IdNumber = ? ";       
        //get city
        String citySQLC = "SELECT c.Name CityName , c.Population Population , "+
                          "m.Name MayorName , m.Surname Surname , m.Gender Gender "+
                          "FROM CITY_TBL c , MAYOR_TBL m "+
                          "WHERE c.IdNumber = m.IdNumber "+
                          " AND "+
                          "c.IdNumber = ?";   
        //get cities
        String citieSQLC = "SELECT c.IdNumber IdNumber ,  c.Name CityName , c.Population Population , "+
                           "m.Name MayorName , m.Surname Surname , m.Gender Gender "+
                           "FROM CITY_TBL c , MAYOR_TBL m "+
                           "WHERE c.IdNumber = m.IdNumber ";
        
        //highest population
        String highestSQL = "SELECT MAX(Population) Population FROM CITY_TBL";
                      
        //lowest population
        String lowestSQL = "SELECT MIN(Population) Population FROM CITY_TBL";
        
        //cities with mayor's gender
        String citiesSQL = "SELECT c.Name Cities FROM CITY_TBL c , MAYOR_TBL m "+
                           "WHERE c.IdNumber = m.IdNumber "+
                           "AND "+
                           "m.Gender = ? ";              
        try
        {
            Connection connection = DriverManager.getConnection(dbURL, userName, password);
            CityManager cm = new CityManager(connection);
            City city = null;
            Integer idNumber = 0;
            
            int option = showOption();
            
            while(option!=9)
            {
                switch(option)
                {
                    case 1:
                        //store a city into database
                        city = storeCity();
                        
                        if(cm.storeCity(city,storeSQLC, storeSQLM))
                            System.out.println("The city is stored\n");
                        else
                            System.err.println("The city is not stored\n");
                    break;
                        
                    case 2:
                        //change mayor details
                        city = changeMayorDetails();
                        
                        if(cm.changeMayor(city, changeDetailsSQLM))
                            System.out.println("The mayor is changed\n");
                        else
                            System.err.println("The mayor is not changed\n");
                    break;
                        
                    case 3:
                        //deleting record
                        idNumber = getIdNumber();
                        
                        if(cm.deleteCityRow(idNumber, deleteSQLC,deleteSQLM ))
                            System.out.println("The city is deleted\n");
                        else
                            System.err.println("The city is not deleted\n");
                    break;
                        
                    case 4:
                        //view city
                        idNumber = getIdNumber();
                        
                        city = cm.getCity(idNumber, citySQLC);
                        
                        if(city!=null)
                            displayCity(city);
                        else
                            System.err.println("The city is not found\n");
                    break;
                        
                    case 5:
                        //get all cities
                        List<City> theCity = cm.getCitites(citieSQLC);
                        
                        if(!theCity.isEmpty())
                            displayCities(theCity);
                        else
                            System.err.println("Nothing on the database");
                    break;
                        
                    case 6:
                        //hight population
                        String highestPopulation = cm.highestPopulation(highestSQL);
                        
                        if(!(highestPopulation.equals(null)))
                            System.out.println("The highest population is "+highestPopulation);
                        else
                            System.err.println("No populations");
                    break;
                        
                    case 7:
                        //lowest population
                        String lowestPopulation = cm.lowestPopulation(lowestSQL);
                        
                        if(!(lowestPopulation.equals(null)))
                            System.out.println("The lowest population is "+lowestPopulation);
                        else
                            System.err.println("No populations");
                    break;
                        
                    case 8:
                        //get cities according mayor gender
                        char gender = getGender();
                        List<String> theMayor = cm.mayorGenderCities(gender, citiesSQL);
                        
                        if(!theMayor.isEmpty())
                            displayCitiesName(theMayor);
                        else
                            System.err.println("No cities match that gender");
                    break;
                        
                    default:
                        System.err.println("Invalid option.Please re-enter again");
                    break;                           
                }
                option = showOption();
            }          
        } 
        
        catch (SQLException ex) 
        {
            System.err.println("Something went wrong\n"+ex.getMessage());
        }
        catch(InputMismatchException ex)
        {
            System.err.println("Invalid value. Enter a digit");
        }
        finally
        {
            System.out.println("\nThe application is closed\n");
        }
    }

    private static int showOption() 
    {
       Scanner sc = new Scanner(System.in);
       
       String menu= "\nPlease select on of the following option :\n\n"+
                    "1. Store city information in a database.\n"+
                    "2. Change the name and surname of a city mayor.\n"+
                    "3. Delete a city record.\n"+
                    "4. Get city information.\n"+
                    "5. Get the information of all the cities.\n"+
                    "6. Get the city with the highest population.\n"+
                    "7. Get the city with the lowest population.\n"+
                    "8. Get the cities that are led by mayors of a particular gender.\n"+
                    "9. Exit\n\n"+
                    "Your option :";
        
       System.out.print(menu);  
       
       return  sc.nextInt();
    }

    private static City storeCity() 
    {
        Scanner sc = new Scanner(System.in);
        
        System.out.print("\nCity"+
                         "\n-----\n");
        System.out.print("Enter the ID Number\t:");
        Integer idNumber = sc.nextInt();
       
        sc.nextLine();
        
        System.out.print("Enter the city name\t:");
        String cityName = sc.nextLine();
        
        System.out.print("Enter the population\t:");
        String population = sc.nextLine();
        
        System.out.print("\nMayor Details"+
                         "\n-------------\n");
        
        System.out.print("Enter the name\t\t:");
        String mayorName = sc.nextLine();
        
        System.out.print("Enter the surname\t:");
        String surname = sc.nextLine();
        
        System.out.print("Enter the Gender\t:");
        Character gender = sc.nextLine().toUpperCase().charAt(0);
        
        City city = new City(idNumber, cityName, population);
        city.setMayor(new Mayor(idNumber, mayorName, surname ,gender));
        
        return city;
    }

    private static City changeMayorDetails() 
    {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("\nMayor new Details"+
                           "\n-------------------\n");
        System.out.print("Enter the ID Number\t:");
        Integer idNumber = sc.nextInt();
        
        sc.nextLine();
        
        System.out.print("Enter the new name\t:");
        String mayorName = sc.nextLine();
        
        System.out.print("Enter the new surname\t:");
        String surname = sc.nextLine();
        
        Mayor mayor = new Mayor();
        
        mayor.setIdNumber(idNumber);
        mayor.setName(mayorName);
        mayor.setSurname(surname);
        
        City city = new City();
        city.setMayor(mayor);
        
        return city;
    }

    private static Integer getIdNumber() 
    {
        Scanner sc = new Scanner(System.in);
        
        System.out.print("\nEnter the ID Number\t:");
        
        return sc.nextInt();
    }

    private static void displayCity(City city) 
    {
        String outcome ="\nCity information"+
                        "\n----------------\n"+
                        "ID Number\t:"+city.getIdNumber()+"\n"+
                        "Name\t\t:"+city.getName()+"\n"+
                        "Population\t:"+city.getPopulation()+"\n"+
                
                        "\nMayor Information"+
                        "\n-----------------\n"+
                        "Name\t\t:"+city.getMayor().getName()+"\n"+
                        "Surname\t\t:"+city.getMayor().getSurname()+"\n"+
                        "Gender\t\t:"+city.getMayor().getGender()+"\n";
        System.out.println(outcome);
    }

    private static void displayCities(List<City> theCity) 
    {
        String outcome = "";
        for (City display : theCity) 
        {
            outcome +="\nCity information"+
                    "\n----------------\n"+
                    "ID Number\t:"+display.getIdNumber()+"\n"+
                    "Name\t\t:"+display.getName()+"\n"+
                    "Population\t:"+display.getPopulation()+"\n"+

                    "\nMayor Information"+
                    "\n-----------------\n"+
                    "Name\t\t:"+display.getMayor().getName()+"\n"+
                    "Surname\t\t:"+display.getMayor().getSurname()+"\n"+
                    "Gender\t\t:"+display.getMayor().getGender()+"\n";
        }
 
        System.out.println(outcome);
    }

    private static char getGender() 
    {
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Enter the gender of the mayor :");
        
        return  sc.nextLine().toUpperCase().charAt(0);
    }

    private static void displayCitiesName(List<String> theMayor) 
    {
        String outcome = "";
        for (String display : theMayor)
            outcome+=display+"\n";
        
        String display = "\nThe cities that match mayor's gender :"+
                         "\n--------------------------------------\n"+
                         outcome;
        
        System.out.println(display);
    }
    
}
