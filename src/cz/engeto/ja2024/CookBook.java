package cz.engeto.ja2024;

import java.util.ArrayList;
import java.util.List;

public class CookBook {

    private List<Dish> dishes;

    public CookBook() {
        this.dishes = new ArrayList<>();
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void addDish(Dish dish) {
        this.dishes.add(dish);
    }

    @Override
    public String toString() {
        return "CookBook{" +
                "dishes=" + dishes +
                '}';
    }
}

