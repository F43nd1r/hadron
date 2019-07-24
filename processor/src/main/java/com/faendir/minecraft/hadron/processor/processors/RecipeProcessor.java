package com.faendir.minecraft.hadron.processor.processors;

import com.faendir.minecraft.hadron.annotation.Recipe;
import com.faendir.minecraft.hadron.processor.util.Utils;
import com.squareup.javapoet.TypeSpec;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static com.faendir.minecraft.hadron.processor.util.Utils.ensureNameSpaced;

/**
 * @author lukas
 * @since 01.07.19
 */
public class RecipeProcessor extends BaseProcessor {
    int count = 0;

    public RecipeProcessor(ProcessingEnvironment processingEnv) {
        super(processingEnv);
    }

    @Override
    public void process(AnnotatedElementSupplier supplier, RoundEnvironment roundEnv, TypeSpec.Builder registry) throws Exception {
        for (Pair<Element, Recipe.Shaped> pair : supplier.getElementsAnnotatedWithRepeatable(Recipe.Shaped.class, Recipe.Shaped.Repeat.class)) {
            Recipe.Shaped recipe = pair.getValue();
            Map<String, ItemJson> map = new HashMap<>();
            for (Recipe.Key key : recipe.keys()) {
                map.put(key.key(), new ItemJson(key.value(), 1));
            }
            ShapedRecipeJson json = new ShapedRecipeJson(recipe.pattern(), map, new ItemJson(ensureNameSpaced(recipe.id()), recipe.count()));
            Utils.writeAsset(processingEnv.getFiler(), Utils.RECIPES, count++ + recipe.id(), json);
        }
        for (Pair<Element, Recipe.Shapeless> pair : supplier.getElementsAnnotatedWithRepeatable(Recipe.Shapeless.class, Recipe.Shapeless.Repeat.class)) {
            Recipe.Shapeless recipe = pair.getValue();
            ShapelessRecipeJson json = new ShapelessRecipeJson(Stream.of(recipe.ingredients()).map(i -> new ItemJson(ensureNameSpaced(i), 1)).toArray(ItemJson[]::new), new ItemJson(ensureNameSpaced(recipe.id()), recipe.count()));
            Utils.writeAsset(processingEnv.getFiler(), Utils.RECIPES, count++ + recipe.id(), json);
        }
    }

    public static class ShapelessRecipeJson {
        private final ItemJson[] ingredients;
        private final ItemJson result;

        public ShapelessRecipeJson(ItemJson[] ingredients, ItemJson result) {
            this.ingredients = ingredients;
            this.result = result;
        }

        public String getType() {
            return "minecraft:crafting_shapeless";
        }

        public ItemJson[] getIngredients() {
            return ingredients;
        }

        public ItemJson getResult() {
            return result;
        }
    }

    public static class ShapedRecipeJson {
        private final String[] pattern;
        private final Map<String, ItemJson> key;
        private final ItemJson result;

        public ShapedRecipeJson(String[] pattern, Map<String, ItemJson> key, ItemJson result) {
            this.pattern = pattern;
            this.key = key;
            this.result = result;
        }

        public String getType() {
            return "minecraft:crafting_shaped";
        }

        public String[] getPattern() {
            return pattern;
        }

        public Map<String, ItemJson> getKey() {
            return key;
        }

        public ItemJson getResult() {
            return result;
        }
    }

    public static class ItemJson {
        private final String item;
        private final int count;

        public ItemJson(String item, int count) {
            this.item = item;
            this.count = count;
        }

        public String getItem() {
            return item;
        }

        public Integer getCount() {
            return count == 1 ? null : count;
        }
    }
}
