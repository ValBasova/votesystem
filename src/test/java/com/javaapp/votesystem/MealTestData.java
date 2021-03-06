package com.javaapp.votesystem;

import com.javaapp.votesystem.model.Meal;

import java.time.LocalDate;
import java.time.Month;

import static com.javaapp.votesystem.RestaurantTestData.RESTAURANT1;
import static com.javaapp.votesystem.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {

    public static final TestMatcher<Meal> MEAL_MATCHER = TestMatcher.usingFieldsComparator(Meal.class, "restaurant");

    public static final int MEAL_ID1 = START_SEQ + 7;
    public static final int MEAL_ID2 = START_SEQ + 8;
    public static final int MEAL_ID3 = START_SEQ + 9;
    public static final int MEAL_ID4 = START_SEQ + 10;
    public static final int MEAL_ID5 = START_SEQ + 11;
    public static final int MEAL_ID6 = START_SEQ + 12;
    public static final int MEAL_ID7 = START_SEQ + 13;
    public static final int MEAL_ID8 = START_SEQ + 14;
    public static final int MEAL_ID9 = START_SEQ + 15;
    public static final int MEAL_ID10 = START_SEQ + 16;

    public static final Meal MEAL1 = new Meal(MEAL_ID1, "meal1_1", LocalDate.of(2020, Month.AUGUST, 20),
            50);
    public static final Meal MEAL2 = new Meal(MEAL_ID2, "meal2_1", LocalDate.of(2020, Month.AUGUST, 20),
            15);
    public static final Meal MEAL3 = new Meal(MEAL_ID3, "meal3_1", LocalDate.of(2020, Month.AUGUST, 20),
            45);
    public static final Meal MEAL4 = new Meal(MEAL_ID4, "meal1_2", LocalDate.of(2020, Month.AUGUST, 20),
            19);
    public static final Meal MEAL5 = new Meal(MEAL_ID5, "meal2_2", LocalDate.of(2020, Month.AUGUST, 20),
            200);
    public static final Meal MEAL6 = new Meal(MEAL_ID6, "meal1_3", LocalDate.of(2020, Month.AUGUST, 20),
            70);
    public static final Meal MEAL7 = new Meal(MEAL_ID7, "meal1_1", LocalDate.of(2020, Month.AUGUST, 21),
            55);
    public static final Meal MEAL8 = new Meal(MEAL_ID8, "meal1_2", LocalDate.of(2020, Month.AUGUST, 21),
            35);
    public static final Meal MEAL9 = new Meal(MEAL_ID9, "meal1_3", LocalDate.of(2020, Month.AUGUST, 21),
            42);
    public static final Meal MEAL10 = new Meal(MEAL_ID10, "meal2_3", LocalDate.of(2020, Month.AUGUST, 21),
            4);

    public static Meal getNew() {
        return new Meal(null, "NewMeal", LocalDate.of(2020, Month.SEPTEMBER, 1), 12);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(MEAL1);
        updated.setName("UpdatedName");
        updated.setPrice(123);
        updated.setRestaurant(RESTAURANT1);
        return updated;
    }
}