package smyts.lab6.utils;


import smyts.lab6.common.entities.Route;

/**
 * class for the getting the fields
 */

public class RouteFactory {

    /**
     * terminal input
     */
    private final FieldsGetterInterface scanner;

    /**
     * file input
     */

    public RouteFactory(FieldsGetterInterface scanner) {
        this.scanner = scanner;
    }

    public Route start() {
        Route route = new Route();
        route.setName(scanner.getName());
        route.setCoordinates(scanner.getCoordinates());
        System.out.println("Введите координаты x (Float) y (long) z (Double) и имя начала (от до 75 символов) ");
        route.setFrom(scanner.getLocation());
        System.out.println("Введите координаты x (Float) y (long) z (Double) и имя конца (от до 75 символов) ");
        route.setTo(scanner.getLocation());
        route.setDistance(scanner.getDistance());
        return route;


    }

    public FieldsGetterInterface getScanner() {
        return scanner;
    }

}
