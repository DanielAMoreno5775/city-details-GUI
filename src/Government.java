/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Daniel Moreno
 */

import java.lang.Comparable;

public class Government implements Comparable<Government> {
    private String govName;
    
    //constructor
    public Government (String name) {
        govName = name;
    }
    
    //toString that overrides default version
    @Override
    public String toString () {
        return String.format("%s",govName);
    }
    
    //compareTo that override default version to compare them using the name
    @Override
    public int compareTo (Government otherGov) {
        return this.govName.compareTo(otherGov.govName);
    }
}
