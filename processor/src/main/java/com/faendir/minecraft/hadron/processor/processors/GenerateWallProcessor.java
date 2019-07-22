package com.faendir.minecraft.hadron.processor.processors;

import com.faendir.minecraft.hadron.annotation.GenerateWall;
import com.faendir.minecraft.hadron.annotation.Wall;
import com.faendir.minecraft.hadron.processor.util.Utils;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import net.minecraft.block.Block;
import net.minecraft.block.WallBlock;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import java.util.Map;

import static com.faendir.minecraft.hadron.processor.util.Utils.noPlural;
import static com.faendir.minecraft.hadron.processor.util.Utils.withPrefix;

/**
 * @author lukas
 * @since 21.07.19
 */
public class GenerateWallProcessor extends BaseProcessor {
    public GenerateWallProcessor(ProcessingEnvironment processingEnv) {
        super(processingEnv);
    }

    @Override
    public void process(AnnotatedElementSupplier supplier, RoundEnvironment roundEnv) throws Exception {
        for (Map.Entry<Element, GenerateWall> entry : supplier.getElementsAnnotatedWith(GenerateWall.class).entrySet()) {
            Element e = entry.getKey();
            GenerateWall generateWall = entry.getValue();
            String wallId = noPlural(generateWall.id()) + "_wall";
            String texture = generateWall.texture();
            TypeSpec.Builder builder = TypeSpec.classBuilder(noPlural(e.getSimpleName().toString()) + "Wall")
                    .addModifiers(Modifier.PUBLIC)
                    .superclass(WallBlock.class)
                    .addAnnotation(AnnotationSpec.builder(Wall.class)
                            .addMember("id", "$S", wallId)
                            .addMember("texture", "$S", texture)
                            .addMember("material", "$S", withPrefix(generateWall.id()))
                            .build())
                    .addMethod(MethodSpec.constructorBuilder()
                            .addStatement("super($2T.from(new $1T()))", TypeName.get(e.asType()), Block.Properties.class)
                            .addStatement("setRegistryName($S)", wallId).build());
            Utils.writeClass(processingEnv.getFiler(), builder.build());
        }
    }
}
