package com.lieferando;

abstract class Ingredient {}
abstract class Spice extends Ingredient {}
abstract class Meal {}
abstract class SeasonedMeal extends Meal {}
abstract class MixedIngredient<I1 extends Ingredient,
        I2 extends Ingredient> extends Ingredient {}

interface CookingStep<I extends Ingredient, R extends Meal> {
    R cook(I ingredient);
}

interface MixingStep<I1 extends Ingredient, I2 extends
        Ingredient, R extends MixedIngredient<I1, I2>> {
    R mix(I1 firstIng, I2 secondIng);
}

interface SeasoningStep<M extends Meal, S extends Spice,
        R extends SeasonedMeal> {
    R season(M meal, S spice);
}

abstract class Recipe<R extends SeasonedMeal> {
    abstract R doRecipe();
}

abstract class MixAndCookAndSeasonRecipe<R extends 
        SeasonedMeal, I1 extends Ingredient, I2 extends 
        Ingredient, MI extends MixedIngredient<I1, I2>, N 
        extends Meal, S extends Spice> extends Recipe<R> {

    I1 i1;
    I2 i2;
    S s;
    CookingStep<MI, N> cookingStep;
    MixingStep<I1, I2, MI> mixingStep;
    SeasoningStep<N, S, R> seasoningStep;

    @Override
    R doRecipe() {
        MI mixed = mixingStep.mix(i1, i2);
        N cooked = cookingStep.cook(mixed);
        R seasoned = seasoningStep.season(cooked, s);
        return seasoned;
    }
}
