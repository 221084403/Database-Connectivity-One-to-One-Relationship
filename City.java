/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.tut.entities;

/**
 *
 * @author MNCEDISI
 */
public class City 
{
    private Integer idNumber;
    private String name;
    private String population;
    private Mayor mayor;

    public City(){}

    public City(Integer idNumber, String name, String population) 
    {
        this.idNumber = idNumber;
        this.name = name;
        this.population = population;
    }

    public Integer getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(Integer idNumber) {
        this.idNumber = idNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public Mayor getMayor() {
        return mayor;
    }

    public void setMayor(Mayor mayor) {
        this.mayor = mayor;
    }
}
