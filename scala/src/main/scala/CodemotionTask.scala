object CodemotionTask {

  object MealComponents {

    trait Ingredient

    trait MixedIngredient[F <: Ingredient, S <: Ingredient] extends Ingredient {
      def firstIngredient: F

      def secondIngredient: S
    }

    trait Spice extends Ingredient

    trait Meal

    trait SeasonedMeal extends Meal

  }

  object RecipeSteps {

    import MealComponents._

    trait CookingStep[I1 <: Ingredient, R <: Meal] {
      def cook(i: Seq[I1]): Seq[R]
    }

    trait MixingStep[I1 <: Ingredient, I2 <: Ingredient, R <: MixedIngredient[I1, I2]] {
      def mix(i1: Seq[I1], i2: Seq[I2]): Seq[R]
    }

    trait SeasoningStep[M <: Meal, I <: Spice, R <: SeasonedMeal] {
      def season(m: Seq[M], s: Seq[I]): Seq[R]
    }

  }

  object Recipes {

    import MealComponents._
    import RecipeSteps._

    trait Recipe[M <: SeasonedMeal] {
      def makeRecipe: Seq[M]
    }

    trait MixAndCookAndSeasonRecipe[M <: SeasonedMeal, I1 <: Ingredient, I2 <: Ingredient, MI <: MixedIngredient[I1, I2], N <: Meal, S <: Spice] extends Recipe[SeasonedMeal] {
      def i1: Seq[I1]

      def i2: Seq[I2]

      def s: Seq[S]

      def mixingStep: MixingStep[I1, I2, MI]

      def cookingStep: CookingStep[MI, N]

      def seasoningStep: SeasoningStep[N, S, M]

      override def makeRecipe: Seq[M] = {
        val mixed: Seq[MI] = mixingStep.mix(i1, i2)
        val cooked: Seq[N] = cookingStep.cook(mixed)
        val seasoned = seasoningStep.season(cooked, s)
        seasoned
      }
    }

  }

}