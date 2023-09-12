/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.tut.bl;

import java.sql.*;
import java.util.*;

/**
 *
 * @author MNCEDISI
 */
public interface CityInterface<T>
{
    public boolean storeCity(T t , String sqlC , String sqlM) throws SQLException;
    
    public boolean changeMayor(T t , String sqlM) throws SQLException;
    
    public boolean deleteCityRow(Integer idNumber , String sqlC , String sqlM) throws SQLException;
    
    public T getCity(Integer idNumber , String sql) throws SQLException;
    
    public List<T> getCitites(String sql) throws SQLException;
    
    public String highestPopulation(String sql) throws SQLException;
    
    public String lowestPopulation(String sql) throws SQLException;
    
    public List<String> mayorGenderCities(Character gender , String sql) throws SQLException; 
}
