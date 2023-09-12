/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.tut.bl;

import java.sql.*;
import java.util.*;
import za.ac.tut.entities.*;

/**
 *
 * @author MNCEDISI
 */
public class CityManager implements CityInterface<City>
{
    private Connection connection;

    public CityManager(Connection connection) 
    {
        this.connection = connection;
    }

    @Override
    public boolean storeCity(City city, String sqlC, String sqlM) throws SQLException
    {
        PreparedStatement psc = getConnection().prepareStatement(sqlC);
        psc.setInt(1, city.getIdNumber());
        psc.setString(2, city.getName());
        psc.setString(3, city.getPopulation());
        
        PreparedStatement psm = getConnection().prepareStatement(sqlM);
        psm.setInt(1, city.getMayor().getIdNumber());
        psm.setString(2, city.getMayor().getName());
        psm.setString(3, city.getMayor().getSurname());
        psm.setInt(4, city.getMayor().getIdNumber());
        psm.setString(5,String.valueOf(city.getMayor().getGender()));
        
        int sendC = psc.executeUpdate();
        int sendM = psm.executeUpdate();
        
        psc.close();
        psm.close();
        
        return sendC!=0&& sendM!=0?true:false;
    }

    @Override
    public boolean changeMayor(City city, String sqlM) throws SQLException
    {
        PreparedStatement ps = getConnection().prepareStatement(sqlM);
        ps.setString(1,city.getMayor().getName());
        ps.setString(2,city.getMayor().getSurname());
        ps.setInt(3 , city.getMayor().getIdNumber());
        
        int send = ps.executeUpdate();
        
        ps.close();
        
        return send!=0?true:false;
    }

    @Override
    public boolean deleteCityRow(Integer idNumber, String sqlC, String sqlM) throws SQLException 
    {
        PreparedStatement psc = getConnection().prepareStatement(sqlC);
        psc.setInt(1, idNumber);
        
        PreparedStatement psm = getConnection().prepareStatement(sqlM);
        psm.setInt(1, idNumber);
        
        int sendM = psm.executeUpdate();
        
        if(sendM!=0)
        {
            psc.executeUpdate();
            return true;
        }
        return false;
    }

    @Override
    public City getCity(Integer idNumber, String sql) throws SQLException 
    {
        PreparedStatement ps = getConnection().prepareStatement(sql);
        ps.setInt(1, idNumber);
        
        ResultSet rs = ps.executeQuery();
        
        if(rs.next())
        {
            //city
            String cityName = rs.getString("CityName");
            String population = rs.getString("Population");
            
            //mayor
            String mayorName = rs.getString("MayorName");
            String surname = rs.getString("Surname");
            Character gender = rs.getString("Gender").charAt(0);
            
            City city = new City(idNumber, cityName, population);
            city.setMayor(new Mayor(idNumber, mayorName, surname , gender));
            
            rs.close();
            return  city;
        }
        return null;
        
    }

    @Override
    public List<City> getCitites(String sql) throws SQLException 
    {
        PreparedStatement ps = getConnection().prepareStatement(sql);
        
        ResultSet rs = ps.executeQuery();
        
        List<City> theCity = new ArrayList<>();
        
        while(rs.next())
        {
            //City
            Integer idNumber = rs.getInt("IdNumber");
            String cityName = rs.getString("CityName");
            String population = rs.getString("Population");
            
            //mayor
            String mayorName = rs.getString("MayorName");
            String surname = rs.getString("Surname");
            Character gender = rs.getString("Gender").charAt(0);
 
            City city = new City(idNumber, cityName, population);
            city.setMayor(new Mayor(idNumber, mayorName, surname , gender));
            
            theCity.add(city);
        }
        
        rs.close();
        return theCity;
    }

    public Connection getConnection() {
        return connection;
    }

    @Override
    public String highestPopulation(String sql) throws SQLException
    {
        PreparedStatement ps = getConnection().prepareStatement(sql);
        
        ResultSet rs = ps.executeQuery();
        
        if(rs.next())
        {
            String highestPopulation = rs.getString("Population");
            
            rs.close();
            return  highestPopulation;
        }
        
        rs.close();
        return null;
    }

    @Override
    public String lowestPopulation(String sql) throws SQLException 
    {
        PreparedStatement ps = getConnection().prepareStatement(sql);
        
        ResultSet rs = ps.executeQuery();
        
        if(rs.next())
        {
            String lowerPopulation = rs.getString("Population");
            
            rs.close();
            return lowerPopulation;
        }
        
        rs.close();
        return null;
    }

    @Override
    public List<String> mayorGenderCities(Character gender, String sql) throws SQLException 
    {
        PreparedStatement ps = getConnection().prepareStatement(sql);  
        
        ps.setString(1, String.valueOf(gender));
        
        ResultSet rs = ps.executeQuery();
        
        List<String> theCities = new ArrayList<>();
        
        while(rs.next())
        {
            String mayorCities = rs.getString("Cities");
            theCities.add(mayorCities);
        }
        
        rs.close();
        return theCities;
    }
    
}
