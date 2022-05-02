//package smyts.lab6.utils;
//
//
//import smyts.lab6.common.entities.Coordinates;
//import smyts.lab6.common.entities.Location;
//import smyts.lab6.exceptions.EndOfFileException;
//
//
///**
// * class for getting the fields from the file
// */
//
//public class ReaderFieldsGetter implements FieldsGetterInterface {
//
//    private DataReader reader;
//
//    public ReaderFieldsGetter(DataReader reader) {
//        this.reader = reader;
//    }
//
//    @Override
//    public Coordinates getCoordinates() {
//        while (true) {
//            try {
//                String coordinates = reader.readline();
//
//                String[] splittedCoordinates = coordinates.split(" ");
//
//                if (splittedCoordinates.length == 2) {
//                    return new Coordinates(Double.parseDouble(splittedCoordinates[0]), Long.parseLong(splittedCoordinates[1]));
//                } else throw new NumberFormatException();
//            } catch (NumberFormatException e) {
//            }
//        }
//    }
//
//    @Override
//    public Location getLocation() throws EndOfFileException {
//        while (true) {
//            try {
//                String location = reader.readline();
//
//                if (location == null) {
//                    throw new EndOfFileException();
//                }
//
//                String[] splittedLocation = location.split(" ");
//
//                if (splittedLocation.length == 4) {
//                    Float.parseFloat(splittedLocation[0]);
//                    Long.parseLong(splittedLocation[1]);
//                    Double.parseDouble(splittedLocation[2]);
//                    return new Location(Float.parseFloat(splittedLocation[0]), Long.parseLong(splittedLocation[1]),
//                            Double.parseDouble(splittedLocation[2]), splittedLocation[3]);
//                }
//            } catch (NumberFormatException e) {
//            }
//        }
//    }
//
//    @Override
//    public float getDistance() throws EndOfFileException {
//        while (true) {
//            try {
//                String distance = reader.readline();
//
//                if (distance == null) {
//                    throw new EndOfFileException();
//                }
//
//                String[] splittedDistance = distance.split(" ");
//
//                if (splittedDistance.length == 1) {
//                    if (Float.parseFloat(splittedDistance[0]) > 1) {
//                        return Float.parseFloat(splittedDistance[0]);
//                    } else throw new NumberFormatException();
//                } else throw new NumberFormatException();
//            } catch (NumberFormatException e) {
//            }
//        }
//    }
//
//    @Override
//    public String getName() throws EndOfFileException {
//        while (true) {
//            try {
//                String name = reader.readline();
//                if (name == null) {
//                    throw new EndOfFileException();
//                }
//                if (!name.equals("") && !name.equals("\\s+")) {
//                    return name;
//                }
//            } catch (NumberFormatException e) {
//            }
//        }
//    }
//}
