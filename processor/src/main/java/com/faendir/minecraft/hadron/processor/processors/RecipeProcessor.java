package com.faendir.minecraft.hadron.processor.processors;

import com.faendir.minecraft.hadron.annotation.Recipe;
import com.faendir.minecraft.hadron.processor.util.Utils;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import java.util.HashMap;
import java.util.Map;

import static com.faendir.minecraft.hadron.processor.util.Utils.ensureNameSpaced;

/**
 * @author lukas
 * @since 01.07.19
 */
public class RecipeProcessor extends BaseProcessor{

    public RecipeProcessor(ProcessingEnvironment processingEnv) {
        super(processingEnv);
    }

    @Override
    public void process(AnnotatedElementSupplier supplier, RoundEnvironment roundEnv, TypeSpec.Builder registry) throws Exception {
        for (Map.Entry<Element, Recipe> e : supplier.getElementsAnnotatedWith(Recipe.class).entrySet()) {
            Recipe recipe = e.getValue();
            Map<String, ItemJson> map = new HashMap<>();
            for (Recipe.Key key : recipe.keys()) {
                map.put(key.key(), new ItemJson(key.value(), 1));
            }
            RecipeJson json = new RecipeJson(recipe.pattern(), map, new ItemJson(ensureNameSpaced(recipe.id()), recipe.count()));
            Utils.writeAsset(processingEnv.getFiler(), Utils.RECIPES, recipe.id(), json);
        }
    }

    public static class RecipeJson {
        private final String[] pattern;
        private final Map<String, ItemJson> key;
        private final ItemJson result;

        public RecipeJson(String[] pattern, Map<String, ItemJson> key, ItemJson result) {
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

        public int getCount() {
            return count;
        }
    }
}
