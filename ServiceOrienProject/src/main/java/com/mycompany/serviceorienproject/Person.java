/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.serviceorienproject;

import java.sql.Timestamp;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlRootElement(name = "person")
@XmlType(propOrder = { "id", "firstName", "lastName", "car", "country", "city"})
public class Person {
    private int id;
	private String firstName;
	private String lastName;
	private String car;
        private String city;
	private String country;
        private String time;

    public Person(String firstName, String lastName, String car, String city, String country) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.car = car;
        this.city = city;
        this.country = country;
    }

    public Person(int id, String firstName, String lastName, String car, String city, String country) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.car = car;
        this.city = city;
        this.country = country;
    }
	

	
	public Person(int id, String firstName, String lastName, String car, String country, String city, String time) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.car = car;
		this.city = city;
		this.country = country;
                this.time = time;
	}
	public Person() {
		
	}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Person{" + "id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", car=" + car + ", city=" + city + ", country=" + country + ", time=" + time + '}';
    }

    
}
