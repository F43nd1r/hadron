package com.faendir.minecraft.hadron.processor.processors;

import com.electronwill.nightconfig.core.Config;
import com.faendir.minecraft.hadron.annotation.Recipe;
import com.faendir.minecraft.hadron.processor.util.Utils;
import com.google.gson.JsonObject;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IConditionSerializer;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BooleanSupplier;
import java.util.stream.Stream;

import static com.faendir.minecraft.hadron.processor.util.Utils.*;

/**
 * @author lukas
 * @since 01.07.19
 */
public class RecipeProcessor extends BaseProcessor {
    private int count = 0;
    private static final String CONFIG_JSON = "config";

    public RecipeProcessor(ProcessingEnvironment processingEnv) {
        super(processingEnv);
    }

    @Override
    public void process(AnnotatedElementSupplier supplier, RoundEnvironment roundEnv, TypeSpec.Builder modObjects) throws Exception {
        writeConfigConditionFactoryIfNotPresent();
        for (Pair<Element, Recipe.Shaped> pair : supplier.getElementsAnnotatedWithRepeatable(Recipe.Shaped.class, Recipe.Shaped.Repeat.class)) {
            Recipe.Shaped recipe = pair.getValue();
            Map<String, ItemJson> map = new HashMap<>();
            for (Recipe.Key key : recipe.keys()) {
                map.put(key.key(), new ItemJson(key.value(), 1));
            }
            ShapedRecipeJson json = new ShapedRecipeJson(recipe.pattern(), map, new ItemJson(ensureNameSpaced(recipe.id()), recipe.count()), getConditionFromConfigPath(recipe.configPath()));
            Utils.writeAsset(processingEnv.getFiler(), Utils.AssetPath.RECIPES, count++ + recipe.id(), json);
        }
        for (Pair<Element, Recipe.Shapeless> pair : supplier.getElementsAnnotatedWithRepeatable(Recipe.Shapeless.class, Recipe.Shapeless.Repeat.class)) {
            Recipe.Shapeless recipe = pair.getValue();
            ShapelessRecipeJson json = new ShapelessRecipeJson(Stream.of(recipe.ingredients()).map(i -> new ItemJson(ensureNameSpaced(i), 1)).toArray(ItemJson[]::new), new ItemJson(ensureNameSpaced(recipe.id()), recipe.count()), getConditionFromConfigPath(recipe.configPath()));
            Utils.writeAsset(processingEnv.getFiler(), Utils.AssetPath.RECIPES, count++ + recipe.id(), json);
        }
    }

    private ConditionJson[] getConditionFromConfigPath(String configPath) {
        if (configPath.length() == 0) {
            return null;
        }
        return new ConditionJson[]{new ConditionJson(configPath)};
    }

    private void writeConfigConditionFactoryIfNotPresent() throws IOException {
        String name = firstToUpper(MOD_ID) + "ConfigConditionFactory";
        String fullName = PACKAGE + "." + name;
        if (processingEnv.getElementUtils().getTypeElement(fullName) == null) {
            Utils.writeClass(processingEnv.getFiler(), TypeSpec.classBuilder(name)
                    .addModifiers(Modifier.PUBLIC)
                    .addSuperinterface(IConditionSerializer.class)
                    .addMethod(MethodSpec.methodBuilder("parse")
                            .addAnnotation(Override.class)
                            .addModifiers(Modifier.PUBLIC)
                            .addParameter(JsonObject.class, "json")
                            .addStatement("$T setting = $T.getString(json, $S, null)", String.class, JSONUtils.class, CONFIG_JSON)
                            .beginControlFlow("if (setting != null)")
                            .addStatement("return $L", TypeSpec.anonymousClassBuilder("")
                                    .superclass(BooleanSupplier.class)
                                    .addMethod(MethodSpec.methodBuilder("getAsBoolean")
                                            .addAnnotation(Override.class)
                                            .addModifiers(Modifier.PUBLIC)
                                            .beginControlFlow("try")
                                            .addStatement("$T f = $T.class.getDeclaredField(\"childConfig\")", Field.class, ForgeConfigSpec.class)
                                            .addStatement("f.setAccessible(true)")
                                            .addStatement("$1T config = ($1T) f.get($2T.$3L)", Config.class, ClassName.get(PACKAGE, getConfigHolderName()), CONFIG)
                                            .addStatement("return config.getOrElse(setting, true)")
                                            .nextControlFlow("catch($T e)", Exception.class)
                                            .addStatement("return true")
                                            .endControlFlow()
                                            .returns(boolean.class)
                                            .build())
                            .build())
                            .nextControlFlow("else")
                            .addStatement("return () -> true")
                            .endControlFlow()
                            .returns(BooleanSupplier.class)
                            .build())
                    .addAnnotation(AnnotationSpec.builder(Mod.EventBusSubscriber.class)
                            .addMember("modid", "$S", Utils.MOD_ID)
                            .addMember("bus", "$T.MOD", Mod.EventBusSubscriber.Bus.class)
                            .build())
                    .addMethod(MethodSpec.methodBuilder("setup")
                            .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                            .addParameter(FMLCommonSetupEvent.class, "e")
                            .addAnnotation(SubscribeEvent.class)
                            .addStatement("$T.register(new $T($S, $S), new $L())", CraftingHelper.class, ResourceLocation.class, MOD_ID, CONFIG_JSON, name)
                            .build())
                    .build());
            //_factories.json not working rn
            /*Map<String, Map<String, String>> factories = new HashMap<>();
            Map<String, String> conditions = new HashMap<>();
            conditions.put(CONFIG_JSON, fullName);
            factories.put("conditions", conditions);
            Utils.writeAsset(processingEnv.getFiler(), AssetPath.RECIPES, "_factories", factories);*/
        }
    }

    public static class ShapelessRecipeJson extends RecipeJson {
        private final ItemJson[] ingredients;

        public ShapelessRecipeJson(ItemJson[] ingredients, ItemJson result, ConditionJson[] conditions) {
            super(result, conditions);
            this.ingredients = ingredients;
        }

        @Override
        public String getType() {
            return "minecraft:crafting_shapeless";
        }

        public ItemJson[] getIngredients() {
            return ingredients;
        }

    }

    private static abstract class RecipeJson {
        private final ItemJson result;
        private final ConditionJson[] conditions;

        private RecipeJson(ItemJson result, ConditionJson[] conditions) {
            this.result = result;
            this.conditions = conditions;
        }

        public abstract String getType();

        public ItemJson getResult() {
            return result;
        }

        public ConditionJson[] getConditions() {
            return conditions;
        }
    }

    public static class ShapedRecipeJson extends RecipeJson {
        private final String[] pattern;
        private final Map<String, ItemJson> key;
        private final ItemJson result;

        public ShapedRecipeJson(String[] pattern, Map<String, ItemJson> key, ItemJson result, ConditionJson[] conditions) {
            super(result, conditions);
            this.pattern = pattern;
            this.key = key;
            this.result = result;
        }

        @Override
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

    public static class ConditionJson {
        private final String config;

        public ConditionJson(String config) {
            this.config = config;
        }

        public String getType() {
            return ensureNameSpaced(CONFIG_JSON);
        }

        public String getConfig() {
            return config;
        }
    }
}
