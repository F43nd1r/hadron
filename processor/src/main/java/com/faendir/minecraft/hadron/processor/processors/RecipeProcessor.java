package com.faendir.minecraft.hadron.processor.processors;

import com.faendir.minecraft.hadron.annotation.Recipe;
import com.faendir.minecraft.hadron.processor.util.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lukas
 * @since 01.07.19
 */
public class RecipeProcessor extends BaseProcessor{

    public RecipeProcessor(ProcessingEnvironment processingEnv) {
        super(processingEnv);
    }

    public void process(RoundEnvironment roundEnv) throws IOException {
        for (Element e : roundEnv.getElementsAnnotatedWith(Recipe.class)) {
            Recipe recipe = e.getAnnotation(Recipe.class);
            Map<String, ItemJson> map = new HashMap<>();
            for (Recipe.Key key : recipe.keys()) {
                map.put(key.key(), new ItemJson(key.value(), 1));
            }
            RecipeJson json = new RecipeJson(recipe.pattern(), map, new ItemJson(Utils.MOD_ID + ":" + recipe.id(), recipe.count()));
            try (Writer writer = processingEnv.getFiler().createResource(StandardLocation.CLASS_OUTPUT, "data.hadron.recipes.generated", recipe.id() + ".json", e)
                    .openWriter()) {
                new ObjectMapper().writeValue(writer, json);
            }
        }
    }

    public static class RecipeJson {
        private final String type = "minecraft:crafting_shaped";
        private final String[] pattern;
        private final Map<String, ItemJson> key;
        private final ItemJson result;

        public RecipeJson(String[] pattern, Map<String, ItemJson> key, ItemJson result) {
            this.pattern = pattern;
            this.key = key;
            this.result = result;
        }

        public String getType() {
            return type;
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
