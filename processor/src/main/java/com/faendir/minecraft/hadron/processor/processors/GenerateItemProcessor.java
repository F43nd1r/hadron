package com.faendir.minecraft.hadron.processor.processors;

import com.faendir.minecraft.hadron.annotation.ConfigEnabled;
import com.faendir.minecraft.hadron.annotation.GenerateItem;
import com.faendir.minecraft.hadron.annotation.Register;
import com.faendir.minecraft.hadron.annotation.Texture;
import com.faendir.minecraft.hadron.processor.util.Utils;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.faendir.minecraft.hadron.processor.util.Utils.PACKAGE;
import static com.faendir.minecraft.hadron.processor.util.Utils.ensureInfix;

/**
 * @author lukas
 * @since 01.07.19
 */
public class GenerateItemProcessor extends BaseProcessor {

    private static final String ITEM_BASE_CLASS_NAME = "HadronBlockItem";

    public GenerateItemProcessor(ProcessingEnvironment processingEnv) {
        super(processingEnv);
    }

    @Override
    public void process(AnnotatedElementSupplier supplier, RoundEnvironment roundEnv, TypeSpec.Builder modObjects) throws Exception {
        writeItemBaseClassIfNotPresent();
        ClassName base = ClassName.get(PACKAGE, ITEM_BASE_CLASS_NAME);
        for (Pair<Element, GenerateItem> entry : supplier.getElementsAnnotatedWith(GenerateItem.class)) {
            Element e = entry.getKey();
            GenerateItem generateItem = entry.getValue();
            String id = generateItem.value();
            FieldSpec.Builder builder = FieldSpec.builder(base, id.toUpperCase() + "_ITEM", Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                    .addAnnotation(AnnotationSpec.builder(Register.class).addMember("value", "$T.class", Item.class).build())
                    .initializer("($T) new $T($T.$L, new $T().group($T.$L)).setRegistryName($S)", base, base, e.getEnclosingElement(), e, Item.Properties.class, ItemGroup.class, generateItem.category().toUpperCase(), id);
            if (generateItem.configPath().length() != 0) {
                builder.addAnnotation(AnnotationSpec.builder(ConfigEnabled.class).addMember("value", "$S", generateItem.configPath()).build());
            }
            modObjects.addField(builder.build());
            Utils.writeAsset(processingEnv.getFiler(), Utils.AssetPath.ITEM_MODELS, id, new ItemJson(generateItem));

        }
    }

    private void writeItemBaseClassIfNotPresent() throws IOException {
        if (processingEnv.getElementUtils().getTypeElement(PACKAGE + "." + ITEM_BASE_CLASS_NAME) == null) {
            Utils.writeClass(processingEnv.getFiler(), TypeSpec.classBuilder(ITEM_BASE_CLASS_NAME)
                    .addModifiers(Modifier.PUBLIC)
                    .superclass(BlockItem.class)
                    .addField(FieldSpec.builder(boolean.class, "obtainable", Modifier.PRIVATE).build())
                    .addMethod(MethodSpec.constructorBuilder()
                            .addParameter(Block.class, "block")
                            .addParameter(Item.Properties.class, "properties")
                            .addStatement("super(block, properties)")
                            .addStatement("obtainable = getGroup() != null")
                            .build())
                    .addMethod(MethodSpec.methodBuilder("setObtainable")
                            .addModifiers(Modifier.PUBLIC)
                            .addParameter(boolean.class, "obtainable")
                            .addStatement("this.obtainable = obtainable")
                            .build())
                    .addMethod(MethodSpec.methodBuilder("fillItemGroup")
                            .addModifiers(Modifier.PUBLIC)
                            .addAnnotation(Override.class)
                            .addParameter(ItemGroup.class, "group")
                            .addParameter(ParameterizedTypeName.get(NonNullList.class, ItemStack.class), "items")
                            .beginControlFlow("if (obtainable)")
                            .addStatement("super.fillItemGroup(group, items)")
                            .endControlFlow()
                            .build())
                    .build());
        }
    }

    private static class ItemJson {
        private final String parent;
        private final Map<String, String> textures;

        ItemJson(GenerateItem generateItem) {
            String parent = generateItem.parent();
            if (parent.length() == 0) {
                parent = generateItem.value();
            }
            this.parent = ensureInfix(parent);
            this.textures = new LinkedHashMap<>();
            for (Texture texture : generateItem.textures()) {
                textures.put(texture.key(), ensureInfix(texture.id()));
            }
        }

        public String getParent() {
            return parent;
        }

        public Map<String, String> getTextures() {
            return textures;
        }
    }
}
