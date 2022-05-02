package smyts.lab6.utils;


import smyts.lab6.common.entities.Coordinates;
import smyts.lab6.common.entities.Location;

import java.util.Scanner;

/**
 * Class for getting the fields from the terminal
 */

public class ScannerFieldsGetter implements FieldsGetterInterface {
    Scanner scanner;

    public ScannerFieldsGetter(Scanner sc) {
        this.scanner = sc;
    }

    @Override
    public Coordinates getCoordinates() {
        System.out.println("Введите координаты x (Float) и y (long)");
        while (true) {
            try {
                String[] splittedCoordinates = scanner.nextLine().split(" ");
                if (splittedCoordinates.length == 2) {
                    return new Coordinates(Double.parseDouble(splittedCoordinates[0]), Long.parseLong(splittedCoordinates[1]));
                } else throw new NumberFormatException();
            } catch (NumberFormatException e) {
                System.out.println("формат данных не соотвествует требованиям!");
            }
        }
    }

    @Override
    public Location getLocation() {
        while (true) {
            try {
                String[] splittedLocation = scanner.nextLine().split(" ");
                if (splittedLocation.length == 4 && splittedLocation[3].length() <= 75) {
                    return new Location(Float.parseFloat(splittedLocation[0]), Long.parseLong(splittedLocation[1]),
                            Double.parseDouble(splittedLocation[2]), splittedLocation[3]);
                } else throw new NumberFormatException();
            } catch (NumberFormatException e) {
                System.out.println("формат данных не соотвествует требованиям!");
            }
        }
    }

    @Override
    public float getDistance() {
        System.out.println("Введите дистанцию (float)");
        while (true) {
            try {
                String[] splittedDistance = scanner.nextLine().split(" ");
                if (splittedDistance.length == 1) {
                    if (Float.parseFloat(splittedDistance[0]) > 1) {
                        return Float.parseFloat(splittedDistance[0]);
                    } else throw new NumberFormatException();
                } else throw new NumberFormatException();
            } catch (NumberFormatException e) {
                System.out.println("формат данных не соотвествует требованиям!");
            }
        }
    }

    @Override
    public String getName() {
        System.out.println("Введите имя");
        while (true) {
            String name = scanner.nextLine();
            if (!name.equals("\\s+") && !name.equals("") && name.length() <= 75) {
                return name;
            } else {
                System.out.println("Имя должно содержать хотя бы один символ и менее 75 символов ");
            }
        }
    }
}
