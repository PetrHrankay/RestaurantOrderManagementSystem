package cz.engeto.ja2024;

import java.io.*;


public class FileManager {

    public void saveDishToFile(String fileName) throws FileManagerException {
        String delimiter = Settings.getDelimiter();
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(fileName)))) {
            for (Dish item : Dish.getAllDishesFromCookBook()) {
                writer.println(
                        item.getDishId() + delimiter
                                + item.getTitle() + delimiter
                                + item.getPrice() + delimiter
                                + item.getPreparationTimeInMinutes() + delimiter
                                + item.getImage());
            }
        } catch (FileNotFoundException e) {
            throw new FileManagerException("File " + fileName + " not found!\n"
                    + e.getLocalizedMessage());
        } catch (IOException e) {
            throw new FileManagerException("Output error while writing to the file: " + fileName
                    + ":\n" + e.getLocalizedMessage());
        }
    }

    public void saveOrderToFile(String fileName) throws FileManagerException {
        String delimiter = Settings.getDelimiter();
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(fileName)))) {
            for (Order item : Order.getAllOrdersFromReceivedOrdersList()) {
                writer.println(
                        item.getTableNumber() + delimiter
                        + item.getDishId() + delimiter
                        + item.getQuantityOrdered() + delimiter
                        + item.getOrderedTime() + delimiter
                        + item.getFulfilmentTime() + delimiter
                        + item.isPaid() + delimiter
                        + item.isFulfilledOrNot());
            }
        } catch (FileNotFoundException e) {
            throw new FileManagerException("File " + fileName + " not found!\n"
                    + e.getLocalizedMessage());
        } catch (IOException e) {
            throw new FileManagerException("Output error while writing to the file: " + fileName
                    + ":\n" + e.getLocalizedMessage());
        }

    }
}