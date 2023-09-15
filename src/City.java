/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

public class City extends Government{
    //define the data fields
    private int zipcode;
    private double latitude;
    private double longitude;
    private int timezone;          //relative to GMT
    private boolean yesDaylight;   //true if following daylight savings time
    private String standardTimeAbbreviation;
    
    //constructor definition
    public City (int zip, String cName, double lat, double lon, int zone, boolean daylight) {
        super(cName);   //calls the Government constructor
        zipcode = zip;
        latitude = lat;
        longitude = lon;
        timezone = zone;
        yesDaylight = daylight;
        
        //convert timezone and daylight savings time information into an abbreviated string
        if (yesDaylight) {
            if (timezone == -4) {
                standardTimeAbbreviation = "ADT";
            } else if (timezone == -5) {
                standardTimeAbbreviation = "EDT";
            } else if (timezone == -6) {
                standardTimeAbbreviation = "CDT";
            } else if (timezone == -7) {
                standardTimeAbbreviation = "MDT";
            } else if (timezone == -8) {
                standardTimeAbbreviation = "PDT";
            } else if (timezone == -9) {
                standardTimeAbbreviation = "AKDT";
            } else if (timezone == -10) {
                standardTimeAbbreviation = "HDT";
            }
        }
        else {
            if (timezone == -4) {
                standardTimeAbbreviation = "AST";
            } else if (timezone == -5) {
                standardTimeAbbreviation = "EST";
            } else if (timezone == -6) {
                standardTimeAbbreviation = "CST";
            } else if (timezone == -7) {
                standardTimeAbbreviation = "MST";
            } else if (timezone == -8) {
                standardTimeAbbreviation = "PST";
            } else if (timezone == -9) {
                standardTimeAbbreviation = "AKST";
            } else if (timezone == -10) {
                standardTimeAbbreviation = "HST";
            }
        }
    }
    
    //accessor method to retrieve the zip code
    public int getZipcode () {
        return zipcode;
    }
    
    //accessor method to retrieve the time zone
    public String getTimezone () {
        return standardTimeAbbreviation;
    }
}