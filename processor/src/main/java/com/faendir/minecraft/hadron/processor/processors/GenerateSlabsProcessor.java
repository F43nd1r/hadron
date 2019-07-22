package com.faendir.minecraft.hadron.processor.processors;

import com.faendir.minecraft.hadron.annotation.GenerateSlabs;
import com.faendir.minecraft.hadron.annotation.Slabs;
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

import static com.faendir.minecraft.hadron.processor.util.Utils.noPlural;
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
            String slabId = noPlural(generateSlabs.id()) + "_slabs";
            String texture = generateSlabs.texture();
            TypeSpec.Builder builder = TypeSpec.classBuilder(noPlural(e.getSimpleName().toString()) + "Slabs")
                    .addModifiers(Modifier.PUBLIC)
                    .superclass(SlabBlock.class)
                    .addAnnotation(AnnotationSpec.builder(Slabs.class)
                            .addMember("id", "$S", slabId)
                            .addMember("texture", "$S", texture)
                            .addMember("material", "$S", withPrefix(generateSlabs.id()))
                            .addMember("doubleModel", "$S", withPrefix("block/" + generateSlabs.id()))
                            .build())
                    .addMethod(MethodSpec.constructorBuilder()
                            .addStatement("super($T.from(new $T()))", Block.Properties.class, TypeName.get(e.asType()))
                            .addStatement("setRegistryName($S)", slabId).build());
            Utils.writeClass(processingEnv.getFiler(), builder.build());
        }
    }
}
