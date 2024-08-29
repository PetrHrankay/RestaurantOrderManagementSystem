package cz.engeto.ja2024;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Dish extends FileManager {

    private int dishId;
    private String title;
    private BigDecimal price;
    private int preparationTimeInMinutes;
    private String image;

    private static List<Dish> cookBook = new ArrayList<>();    // Zkus opravit na private static List<Dish> cookBook = Collections.synchronizedList(new ArrayList<>());
                                                               // Tohle řešení už zajistí, že všechny operace se seznamem budou synchronizované.
    private static FileManager fileManager = new FileManager();

    public Dish(int dishId, String title, BigDecimal price, int preparationTimeInMinutes, String image) throws DishException {
        this.dishId = dishId;
        this.title = title;
        this.price = price;
        setPreparationTimeInMinutes(preparationTimeInMinutes);
        this.image = image;
        cookBook.add(this);
        saveCookBookToFile();
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

    public static List<String> getAllDishTitlesFromCookBook() {
        List<String> dishTitles = new ArrayList<>();
        for (Dish dish : cookBook) {
            dishTitles.add(dish.getTitle());
        }
        return dishTitles;
    }



    public static void removeDishFromCookBookById(int selectedId) throws FileManagerException {
        for (Dish item : Dish.cookBook) {
            if (item.getDishId() == selectedId) {
                cookBook.remove(item);
                fileManager.saveDishToFile(Settings.getCookBookFileName());
                return;
            }
        }
        throw new IllegalArgumentException("Dish with ID " + selectedId + " not found.");
    }

    private void saveCookBookToFile() {
        try {
            fileManager.saveDishToFile(Settings.getCookBookFileName());
        } catch (FileManagerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Dish{" +
                "dishId=" + dishId +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", preparationTimeInMinutes=" + preparationTimeInMinutes +
                ", image='" + image + '\'' +
                '}';
    }
}
