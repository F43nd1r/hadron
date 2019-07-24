package com.faendir.minecraft.hadron.processor.processors;

import com.faendir.minecraft.hadron.annotation.GenerateItem;
import com.faendir.minecraft.hadron.annotation.Register;
import com.faendir.minecraft.hadron.processor.util.Utils;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.TypeSpec;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import java.util.Map;

import static com.faendir.minecraft.hadron.processor.util.Utils.withBlockInfix;

/**
 * @author lukas
 * @since 01.07.19
 */
public class GenerateItemProcessor extends BaseProcessor {

    public GenerateItemProcessor(ProcessingEnvironment processingEnv) {
        super(processingEnv);
    }

    @Override
    public void process(AnnotatedElementSupplier supplier, RoundEnvironment roundEnv, TypeSpec.Builder registry) throws Exception {
        for (Map.Entry<Element, GenerateItem> entry : supplier.getElementsAnnotatedWith(GenerateItem.class).entrySet()) {
            Element e = entry.getKey();
            GenerateItem generateItem = entry.getValue();
            String id = generateItem.value();
            String parent = generateItem.parent();
            if (parent.length() == 0) {
                parent = id;
            }
            registry.addField(FieldSpec.builder(Item.class, id.toUpperCase() + "_ITEM", Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                    .addAnnotation(AnnotationSpec.builder(Register.class).addMember("value", "$T.class", Item.class).build())
                    .initializer("new $T($T.$L, new $T().group($T.BUILDING_BLOCKS)).setRegistryName($S)", BlockItem.class, e.getEnclosingElement(), e, Item.Properties.class, ItemGroup.class, id)
                    .build());
            Utils.writeAsset(processingEnv.getFiler(), Utils.ITEM_MODELS, id, new ParentJson(withBlockInfix(parent)));

        }
    }

    private static class ParentJson {
        private final String parent;

        public ParentJson(String parent) {
            this.parent = parent;
        }

        public String getParent() {
            return parent;
        }
    }
}
