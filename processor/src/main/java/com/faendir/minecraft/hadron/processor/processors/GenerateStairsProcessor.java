package com.faendir.minecraft.hadron.processor.processors;

import com.faendir.minecraft.hadron.annotation.GenerateStairs;
import com.faendir.minecraft.hadron.annotation.Stairs;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.TypeSpec;
import net.minecraft.block.Block;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;

import static com.faendir.minecraft.hadron.processor.util.Utils.*;

/**
 * @author lukas
 * @since 01.07.19
 */
public class GenerateStairsProcessor extends BaseProcessor {

    public GenerateStairsProcessor(ProcessingEnvironment processingEnv) {
        super(processingEnv);
    }

    @Override
    public void process(AnnotatedElementSupplier supplier, RoundEnvironment roundEnv, TypeSpec.Builder registry) throws Exception {
        for (Pair<Element, GenerateStairs> entry : supplier.getElementsAnnotatedWith(GenerateStairs.class)) {
            Element e = entry.getKey();
            GenerateStairs generateStairs = entry.getValue();
            String stairId = noPlural(removeNameSpace(generateStairs.id())) + "_stairs";
            String texture = generateStairs.texture();
            registry.addField(FieldSpec.builder(Block.class, stairId.toUpperCase(), Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                    .addAnnotation(AnnotationSpec.builder(Stairs.class)
                            .addMember("id", "$S", stairId)
                            .addMember("texture", "$S", texture)
                            .addMember("material", "$S", ensureNameSpaced(generateStairs.id()))
                            .build())
                    .initializer("new $1T($2T.$3L.getDefaultState(), $4T.from($2T.$3L)).setRegistryName($5S)", ClassName.get("com.faendir.minecraft.hadron.base", "HadronStairsBlock"), e.getEnclosingElement(), e, Block.Properties.class, stairId)
                    .build());
        }
    }
}
