<?php

abstract class Ingredient {}
abstract class Spice extends Ingredient {}
abstract class Meal {}
abstract class SeasonedMeal extends  Meal {}
abstract class MixedIngredient extends Ingredient {
    public function __construct(Ingredient $i1, Ingredient $i2 ){}
}

interface CookingStep {
    public function cookStep(Ingredient $i): Meal;
}

interface MixingStep {
    public function mixStep(Ingredient $i1, Ingredient $i2 ): MixedIngredient;
}

interface SeasoningStep {
    public function seasonStep(Meal $meal, Spice $spice): SeasonedMeal;
}

abstract class Recipe extends SeasonedMeal {
    public abstract function makeRecipe(): SeasonedMeal;
}

abstract class MixAndCookAndSeasonRecipe extends Recipe implements CookingStep, MixingStep, SeasoningStep {

    /**
     * @var Ingredient
     */
    protected $i1;

    /**
     * @var Ingredient
     */
    protected $i2;

    /**
     * @var Spice
     */
    protected $s;

    public function makeRecipe(): SeasonedMeal
    {
        $mixed = $this->mixStep($this->i1, $this->i2);
        $cooked = $this->cookStep($mixed);
        $seasoned = $this->seasonStep($cooked, $this->s);
        return $seasoned;
    }
}
