package com.faendir.minecraft.hadron.processor.processors;

import com.faendir.minecraft.hadron.annotation.GenerateWall;
import com.faendir.minecraft.hadron.annotation.Wall;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.TypeSpec;
import net.minecraft.block.Block;
import net.minecraft.block.WallBlock;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import java.util.Map;

import static com.faendir.minecraft.hadron.processor.util.Utils.*;

/**
 * @author lukas
 * @since 21.07.19
 */
public class GenerateWallProcessor extends BaseProcessor {
    public GenerateWallProcessor(ProcessingEnvironment processingEnv) {
        super(processingEnv);
    }

    @Override
    public void process(AnnotatedElementSupplier supplier, RoundEnvironment roundEnv, TypeSpec.Builder registry) throws Exception {
        for (Map.Entry<Element, GenerateWall> entry : supplier.getElementsAnnotatedWith(GenerateWall.class).entrySet()) {
            Element e = entry.getKey();
            GenerateWall generateWall = entry.getValue();
            String wallId = noPlural(removeNameSpace(generateWall.id())) + "_wall";
            String texture = generateWall.texture();
            registry.addField(FieldSpec.builder(Block.class, wallId.toUpperCase(), Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                    .addAnnotation(AnnotationSpec.builder(Wall.class)
                            .addMember("id", "$S", wallId)
                            .addMember("texture", "$S", texture)
                            .addMember("material", "$S", ensureNameSpaced(generateWall.id()))
                            .build())
                            .initializer("new $T($T.from($T.$L)).setRegistryName($S)", WallBlock.class, Block.Properties.class, e.getEnclosingElement(), e, wallId)
                    .build());
        }
    }
}
