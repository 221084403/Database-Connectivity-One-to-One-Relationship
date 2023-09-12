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
public class Mayor
{
    private Integer idNumber;
    private String name;
    private String surname;
    private Character gender;

    public Mayor() {}
    
    public Mayor(Integer idNumber, String name, String surname, Character gender) 
    {
        this.idNumber = idNumber;
        this.name = name;
        this.surname = surname;
        this.gender = gender;
    }

    public Character getGender() {
        return gender;
    }

    public void setGender(Character gender) {
        this.gender = gender;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
    
}
