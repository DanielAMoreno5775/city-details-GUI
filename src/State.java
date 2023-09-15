/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Daniel Moreno
 */
public class State extends Government{
    private OrderedAddOnce<City> cityList = new OrderedAddOnce <>();
    
    public State (String name) {
        super(name);
    }
    
    public void addCity (City newCity) {
        cityList.addOnce(newCity);
    }
    
    public OrderedAddOnce<City> getCityList () {
        return cityList;
    }
    
    public int getNumOfCities(){
        return cityList.getLength();
    }
    
    public void empty () {
        cityList.empty();
    }
}
