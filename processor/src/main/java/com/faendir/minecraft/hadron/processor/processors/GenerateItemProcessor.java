package com.faendir.minecraft.hadron.processor.processors;

import com.faendir.minecraft.hadron.annotation.GenerateItem;
import com.faendir.minecraft.hadron.annotation.Register;
import com.faendir.minecraft.hadron.annotation.Texture;
import com.faendir.minecraft.hadron.processor.util.Utils;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.TypeSpec;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.faendir.minecraft.hadron.processor.util.Utils.ensureInfix;

/**
 * @author lukas
 * @since 01.07.19
 */
public class GenerateItemProcessor extends BaseProcessor {

    public GenerateItemProcessor(ProcessingEnvironment processingEnv) {
        super(processingEnv);
    }

    @Override
    public void process(AnnotatedElementSupplier supplier, RoundEnvironment roundEnv, TypeSpec.Builder modObjects) throws Exception {
        for (Pair<Element, GenerateItem> entry : supplier.getElementsAnnotatedWith(GenerateItem.class)) {
            Element e = entry.getKey();
            GenerateItem generateItem = entry.getValue();
            String id = generateItem.value();
            modObjects.addField(FieldSpec.builder(Item.class, id.toUpperCase() + "_ITEM", Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                    .addAnnotation(AnnotationSpec.builder(Register.class).addMember("value", "$T.class", Item.class).build())
                    .initializer("new $T($T.$L, new $T().group($T.$L)).setRegistryName($S)", BlockItem.class, e.getEnclosingElement(), e, Item.Properties.class, ItemGroup.class, generateItem.category().toUpperCase(), id)
                    .build());
            Utils.writeAsset(processingEnv.getFiler(), Utils.AssetPath.ITEM_MODELS, id, new ItemJson(generateItem));

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
