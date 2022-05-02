package smyts.lab6.utils;


import smyts.lab6.common.entities.Coordinates;
import smyts.lab6.common.entities.Location;

public interface FieldsGetterInterface {
    Coordinates getCoordinates();
    Location getLocation();
    float getDistance();
    String getName();
}
