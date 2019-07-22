package com.faendir.minecraft.hadron.processor.processors;

import com.faendir.minecraft.hadron.annotation.GenerateStairs;
import com.faendir.minecraft.hadron.annotation.Stairs;
import com.faendir.minecraft.hadron.processor.util.Utils;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import net.minecraft.block.Block;
import net.minecraft.block.StairsBlock;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import java.util.Map;

import static com.faendir.minecraft.hadron.processor.util.Utils.noPlural;
import static com.faendir.minecraft.hadron.processor.util.Utils.withPrefix;

/**
 * @author lukas
 * @since 01.07.19
 */
public class GenerateStairsProcessor extends BaseProcessor {

    public GenerateStairsProcessor(ProcessingEnvironment processingEnv) {
        super(processingEnv);
    }

    @Override
    public void process(AnnotatedElementSupplier supplier, RoundEnvironment roundEnv) throws Exception {
        for (Map.Entry<Element, GenerateStairs> entry : supplier.getElementsAnnotatedWith(GenerateStairs.class).entrySet()) {
            Element e = entry.getKey();
            GenerateStairs generateStairs = entry.getValue();
            String stairId = noPlural(generateStairs.id()) + "_stairs";
            String texture = generateStairs.texture();
            TypeSpec.Builder builder = TypeSpec.classBuilder(noPlural(e.getSimpleName().toString()) + "Stairs")
                    .addModifiers(Modifier.PUBLIC)
                    .superclass(StairsBlock.class)
                    .addAnnotation(AnnotationSpec.builder(Stairs.class)
                            .addMember("id", "$S", stairId)
                            .addMember("texture", "$S", texture)
                            .addMember("material", "$S", withPrefix(generateStairs.id()))
                            .build())
                    .addMethod(MethodSpec.constructorBuilder()
                            .addStatement("super(new $1T().getDefaultState(), $2T.from(new $1T()))", TypeName.get(e.asType()), Block.Properties.class)
                            .addStatement("setRegistryName($S)", stairId).build());
            Utils.writeClass(processingEnv.getFiler(), builder.build());
        }
    }
}
