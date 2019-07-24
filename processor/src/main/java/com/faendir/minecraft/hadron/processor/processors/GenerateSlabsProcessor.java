package com.faendir.minecraft.hadron.processor.processors;

import com.faendir.minecraft.hadron.annotation.GenerateSlabs;
import com.faendir.minecraft.hadron.annotation.Slabs;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.TypeSpec;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import java.util.Map;

import static com.faendir.minecraft.hadron.processor.util.Utils.*;

/**
 * @author lukas
 * @since 02.07.19
 */
public class GenerateSlabsProcessor extends BaseProcessor {
    public GenerateSlabsProcessor(ProcessingEnvironment processingEnv) {
        super(processingEnv);
    }

    @Override
    public void process(AnnotatedElementSupplier supplier, RoundEnvironment roundEnv, TypeSpec.Builder registry) throws Exception {
        for (Map.Entry<Element, GenerateSlabs> entry : supplier.getElementsAnnotatedWith(GenerateSlabs.class).entrySet()) {
            Element e = entry.getKey();
            GenerateSlabs generateSlabs = entry.getValue();
            String slabId = noPlural(removeNameSpace(generateSlabs.id())) + "_slabs";
            String texture = generateSlabs.texture();
            registry.addField(FieldSpec.builder(Block.class, slabId.toUpperCase(), Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                    .addAnnotation(AnnotationSpec.builder(Slabs.class)
                            .addMember("id", "$S", slabId)
                            .addMember("texture", "$S", texture)
                            .addMember("material", "$S", ensureNameSpaced(generateSlabs.id()))
                            .addMember("doubleModel", "$S", ensureInfix(generateSlabs.id()))
                            .build())
                    .initializer("new $T($T.from($T.$L)).setRegistryName($S)", SlabBlock.class, Block.Properties.class, e.getEnclosingElement(), e, slabId)
                    .build());
        }
    }
}
