package com.faendir.minecraft.hadron.processor.processors;

import com.faendir.minecraft.hadron.annotation.GenerateItem;
import com.faendir.minecraft.hadron.annotation.Register;
import com.faendir.minecraft.hadron.processor.util.Utils;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.registries.ObjectHolder;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import java.io.IOException;

/**
 * @author lukas
 * @since 01.07.19
 */
public class GenerateItemProcessor extends BaseProcessor{

    public GenerateItemProcessor(ProcessingEnvironment processingEnv) {
        super(processingEnv);
    }

    public void process(RoundEnvironment roundEnv) throws IOException {
        for (Element e : roundEnv.getElementsAnnotatedWith(GenerateItem.class)) {
            String name = e.getSimpleName().toString();
            TypeName typeName = TypeName.get(e.asType());
            GenerateItem generateItem = e.getAnnotation(GenerateItem.class);
            String id = generateItem.value();
            String parent = generateItem.parent();
            if(parent.length() == 0) {
                parent = id;
            }
            TypeSpec.Builder builder = TypeSpec.classBuilder(name + "Item")
                    .superclass(BlockItem.class)
                    .addModifiers(Modifier.PUBLIC)
                    .addAnnotation(AnnotationSpec.builder(Register.class).addMember("value", "$T.class", Item.class).build())
                    .addField(FieldSpec.builder(typeName, "block", Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                            .addAnnotation(AnnotationSpec.builder(ObjectHolder.class).addMember("value", "\"$L:$L\"", Utils.MOD_ID, id)
                                    .build())
                            .initializer("null").build())
                    .addMethod(MethodSpec.constructorBuilder()
                            .addStatement("super(block, new $T().group($T.BUILDING_BLOCKS))", Item.Properties.class, ItemGroup.class)
                            .addStatement("setRegistryName($S)", id).build());
            Utils.writeClass(processingEnv.getFiler(), builder.build());
            Utils.writeAsset(processingEnv.getFiler(), Utils.ITEM_MODELS, id, new ParentJson(Utils.withPrefix("block/"+ parent)));

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
