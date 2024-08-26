package cz.engeto.ja2024;

import java.math.BigDecimal;

public class Dish {

    private int dishId;
    private String title;
    private BigDecimal price;
    private int preparationTimeInMinutes;
    private String image;

    public Dish(int dishId, String title, BigDecimal price, int preparationTimeInMinutes, String image) {
        this.dishId = dishId;
        this.title = title;
        this.price = price;
        this.preparationTimeInMinutes = preparationTimeInMinutes;
        this.image = image;
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

    public void setPreparationTimeInMinutes(int preparationTimeInMinutes) {
        this.preparationTimeInMinutes = preparationTimeInMinutes;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
