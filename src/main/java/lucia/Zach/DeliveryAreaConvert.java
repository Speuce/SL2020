/*
 * Copyright (c) Zachery Unrau
 */

package main.java.lucia.Zach;

import java.util.HashMap;

public class DeliveryAreaConvert {

    public String getAreaHenderson(String input) {
        if(input.equals("Rossmere - B") || input.equals("Munroe West"))
            return "EK";
        else if(input.equals("Kildonan Drive"))
            return "NK-W";
        else if(input.equals("Rossmere - A"))
            return "NK";
        else if(input.equals("East St. Paul"))
            return "ESP";
        else if(input.equals("River East") || input.equals("Valhalla"))
            return "NK-N";
        else if(input.equals("Springfield North") || input.equals("Springfield South") || input.equals("Mcleod Industrial"))
            return "NK-E";
        else if(input.equals("Valley Gardens"))
            return "VG";
        else if(input.equals("Eaglemere"))
            return "VG-E";
        else if(input.equals("Munroe East") || input.equals("Talbot - Grey") || input.equals("East Elmwood") || input.equals("Tyne - Tees"))
            return "EELM";
        else if(input.equals("Chalmers") || input.equals("Glenelm"))
            return  "ELM";
        else if(input.equals("North Point Douglas") || input.equals("South Point Douglas"))
            return "PTDG";
        else if(input.equals("Ki l- Cona Park") || input.equals("North Transcona Yards") || input.equals("Grassie"))
            return "HVS";
        return "OUT";
    }
}
