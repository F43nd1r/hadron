package com.faendir.minecraft.hadron.processor.processors;

import com.faendir.minecraft.hadron.annotation.BlockState;
import com.faendir.minecraft.hadron.annotation.GenerateItem;
import com.faendir.minecraft.hadron.annotation.GenerateSlabs;
import com.faendir.minecraft.hadron.annotation.Model;
import com.faendir.minecraft.hadron.annotation.Recipe;
import com.faendir.minecraft.hadron.annotation.Register;
import com.faendir.minecraft.hadron.annotation.Tag;
import com.faendir.minecraft.hadron.processor.util.Utils;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import java.util.Map;

import static com.faendir.minecraft.hadron.processor.util.Utils.withPrefix;

/**
 * @author lukas
 * @since 02.07.19
 */
public class GenerateSlabsProcessor extends BaseProcessor {
    public GenerateSlabsProcessor(ProcessingEnvironment processingEnv) {
        super(processingEnv);
    }

    @Override
    public void process(AnnotatedElementSupplier supplier, RoundEnvironment roundEnv) throws Exception {
        for (Map.Entry<Element, GenerateSlabs> entry : supplier.getElementsAnnotatedWith(GenerateSlabs.class).entrySet()) {
            Element e = entry.getKey();
            GenerateSlabs generateSlabs = entry.getValue();
            String name = e.getSimpleName().toString();
            if (name.endsWith("s")) {
                name = name.substring(0, name.length() - 1);
            }
            String slabId = generateSlabs.id();
            if (slabId.endsWith("s")) {
                slabId = slabId.substring(0, slabId.length() - 1);
            }
            slabId += "_slabs";
            String texture = generateSlabs.texture();
            TypeSpec.Builder builder = TypeSpec.classBuilder(name + "Slabs")
                    .addModifiers(Modifier.PUBLIC)
                    .superclass(SlabBlock.class)
                    .addAnnotation(AnnotationSpec.builder(Register.class).addMember("value", "$T.class", Block.class).build())
                    .addAnnotation(AnnotationSpec.builder(GenerateItem.class).addMember("value", "$S", slabId).build())
                    .addAnnotation(AnnotationSpec.builder(Recipe.class).addMember("pattern", "{\"   \",\"   \",\"xxx\"}")
                            .addMember("keys", "@$T(key = \"x\", value = $S)", Recipe.Key.class, withPrefix(generateSlabs.id()))
                            .addMember("id", "$S", slabId)
                            .addMember("count", "6").build())
                    .addAnnotation(AnnotationSpec.builder(BlockState.class).addMember("id", "$S", slabId)
                            .addMember("variants", "{$L,\n$L,\n$L}",
                                    AnnotationSpec.builder(BlockState.Variant.class).addMember("id", "$S", "type=bottom").addMember("model", "$L", AnnotationSpec.builder(BlockState.Model.class).addMember("value", "$S", withPrefix("block/" + slabId)).build()).build(),
                                    AnnotationSpec.builder(BlockState.Variant.class).addMember("id", "$S", "type=top").addMember("model", "$L", AnnotationSpec.builder(BlockState.Model.class).addMember("value", "$S", withPrefix("block/" + slabId)).addMember("x", "$L", 180).addMember("uvlock", "$L", true).build()).build(),
                                    AnnotationSpec.builder(BlockState.Variant.class).addMember("id", "$S", "type=double").addMember("model", "$L", AnnotationSpec.builder(BlockState.Model.class).addMember("value", "$S", withPrefix("block/" + generateSlabs.id())).addMember("x", "$L", 180).addMember("uvlock", "$L", true).build()).build())
                            .build())
                    .addAnnotation(AnnotationSpec.builder(Model.class)
                            .addMember("id", "$S", slabId)
                            .addMember("parent", "$S", "minecraft:block/slab")
                            .addMember("textures", "{$L, $L, $L}", createTexture("top", texture), createTexture("bottom", texture), createTexture("side", texture))
                            .build())
                    .addAnnotation(AnnotationSpec.builder(Tag.class).addMember("id", "$S", slabId).addMember("tag", "$S", "slabs").build())
                    .addMethod(MethodSpec.constructorBuilder()
                            .addStatement("super($T.from(new $T()))", Block.Properties.class, TypeName.get(e.asType()))
                            .addStatement("setRegistryName($S)", slabId).build());
            Utils.writeClass(processingEnv.getFiler(), builder.build());
        }
    }

    private AnnotationSpec createTexture(String key, String location) {
        return AnnotationSpec.builder(Model.Texture.class).addMember("key", "$S", key).addMember("id", "$S", location).build();
    }
}
