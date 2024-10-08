package cz.engeto.ja2024;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Dish {

    private int dishId;
    private String title;
    private BigDecimal price;
    private int preparationTimeInMinutes;
    private String image;

    private static List<Dish> cookBook = Collections.synchronizedList(new ArrayList<>());

    public Dish(int dishId, String title, BigDecimal price, int preparationTimeInMinutes,
                String image) throws DishException {
        this.dishId = dishId;
        this.title = title;
        this.price = price;
        setPreparationTimeInMinutes(preparationTimeInMinutes);
        this.image = image;
        cookBook.add(this);
        if (image == null || image.isEmpty()) {
            this.image = "Blank";
        } else {
            this.image = image;
        }
    }

    public Dish(int dishId, String title, BigDecimal price, int preparationTimeInMinutes) throws DishException {
        this(dishId, title, price, preparationTimeInMinutes, "Blank");
    }

    public int getDishId() {
        return dishId;
    }

    public void setDishId(int dishId) {
        this.dishId = dishId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getPreparationTimeInMinutes() {
        return preparationTimeInMinutes;
    }

    public void setPreparationTimeInMinutes(int preparationTimeInMinutes) throws DishException {
        if (preparationTimeInMinutes >= 0) {
            this.preparationTimeInMinutes = preparationTimeInMinutes;
        } else {
            throw new DishException(
                    "The entered preparation time must be greater than 0. You entered: " + preparationTimeInMinutes);
        }
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public static List<Dish> getAllDishesFromCookBook() {
        return cookBook;
    }

    public static void removeDishFromCookBook(Dish dish) throws FileManagerException {
        if (cookBook.remove(dish)) {
            System.out.println("Selected dish: " + dish.getTitle() + " was removed from cookbook.");
            FileManager.saveCookBookToFile(Settings.getCookBookFileName());
        } else {
            throw new IllegalArgumentException("Selected dish: " + dish.getTitle() + " not found.");
        }
    }

    public static void removeDishFromCookBookByItsId(int selectedId) throws FileManagerException {
        for (Dish item : Dish.cookBook) {
            if (item.getDishId() == selectedId) {
                System.out.println("You have selected ID: " + selectedId + " This corresponds to dish: "
                        + item.getTitle() + " .Selected dish was removed from cookbook.");
                cookBook.remove(item);
                FileManager.saveCookBookToFile(Settings.getCookBookFileName());
                return;
            }
        }
        throw new IllegalArgumentException("Dish with ID " + selectedId + " not found.");
    }

    @Override
    public String toString() {
        return "Dish{" +
                "dishId: " + dishId +
                ", title: '" + title + '\'' +
                ", price: " + price +
                ", preparationTimeInMinutes=" + preparationTimeInMinutes +
                ", image='" + image + '\'' +
                '}';
    }
}
