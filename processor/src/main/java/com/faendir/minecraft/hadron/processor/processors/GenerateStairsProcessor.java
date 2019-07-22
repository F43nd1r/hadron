package com.faendir.minecraft.hadron.processor.processors;

import com.faendir.minecraft.hadron.annotation.BlockState;
import com.faendir.minecraft.hadron.annotation.GenerateStairs;
import com.faendir.minecraft.hadron.annotation.Model;
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
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
            String name = e.getSimpleName().toString();
            if (name.endsWith("s")) {
                name = name.substring(0, name.length() - 1);
            }
            String stairId = generateStairs.id();
            if (stairId.endsWith("s")) {
                stairId = stairId.substring(0, stairId.length() - 1);
            }
            stairId += "_stairs";
            String texture = generateStairs.texture();
            TypeSpec.Builder builder = TypeSpec.classBuilder(name + "Stairs")
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

    private AnnotationSpec createModel(String name, String parent, String texture) {
        return AnnotationSpec.builder(Model.class)
                .addMember("id", "$S", name)
                .addMember("parent", "$S", parent)
                .addMember("textures", "{$L, $L, $L}", createTexture("top", texture), createTexture("bottom", texture), createTexture("side", texture))
                .build();
    }

    private AnnotationSpec createTexture(String key, String location) {
        return AnnotationSpec.builder(Model.Texture.class).addMember("key", "$S", key).addMember("id", "$S", location).build();
    }

    private AnnotationSpec createBlockState(String stairId) {
        String normal = withPrefix("block/" + stairId);
        String outer = withPrefix("block/" + stairId + "_outer");
        String inner = withPrefix("block/" + stairId + "_inner");
        return AnnotationSpec.builder(BlockState.class).addMember("id", "$S", stairId)
                .addMember("variants", Stream.generate(() -> "$L").limit(40).collect(Collectors.joining(",\n", "{", "}")),
                        variant("facing=east,half=bottom,shape=straight", model(normal).build()),
                        variant("facing=west,half=bottom,shape=straight", model(normal, 0, 180)),
                        variant("facing=south,half=bottom,shape=straight", model(normal, 0, 90)),
                        variant("facing=north,half=bottom,shape=straight", model(normal, 0, 270)),
                        variant("facing=east,half=bottom,shape=outer_right", model(outer).build()),
                        variant("facing=west,half=bottom,shape=outer_right", model(outer, 0, 180)),
                        variant("facing=south,half=bottom,shape=outer_right", model(outer, 0, 90)),
                        variant("facing=north,half=bottom,shape=outer_right", model(outer, 0, 270)),
                        variant("facing=east,half=bottom,shape=outer_left", model(outer, 0, 270)),
                        variant("facing=west,half=bottom,shape=outer_left", model(outer, 0, 90)),
                        variant("facing=south,half=bottom,shape=outer_left", model(outer).build()),
                        variant("facing=north,half=bottom,shape=outer_left", model(outer, 0, 180)),
                        variant("facing=east,half=bottom,shape=inner_right", model(inner).build()),
                        variant("facing=west,half=bottom,shape=inner_right", model(inner, 0, 180)),
                        variant("facing=south,half=bottom,shape=inner_right", model(inner, 0, 90)),
                        variant("facing=north,half=bottom,shape=inner_right", model(inner, 0, 270)),
                        variant("facing=east,half=bottom,shape=inner_left", model(inner, 0, 270)),
                        variant("facing=west,half=bottom,shape=inner_left", model(inner, 0, 90)),
                        variant("facing=south,half=bottom,shape=inner_left", model(inner).build()),
                        variant("facing=north,half=bottom,shape=inner_left", model(inner, 0, 180)),
                        variant("facing=east,half=top,shape=straight", model(normal, 180, 0)),
                        variant("facing=west,half=top,shape=straight", model(normal, 180, 180)),
                        variant("facing=south,half=top,shape=straight", model(normal, 180, 90)),
                        variant("facing=north,half=top,shape=straight", model(normal, 180, 270)),
                        variant("facing=east,half=top,shape=outer_right", model(outer, 180, 90)),
                        variant("facing=west,half=top,shape=outer_right", model(outer, 180, 270)),
                        variant("facing=south,half=top,shape=outer_right", model(outer, 180, 180)),
                        variant("facing=north,half=top,shape=outer_right", model(outer, 180, 0)),
                        variant("facing=east,half=top,shape=outer_left", model(outer, 180, 0)),
                        variant("facing=west,half=top,shape=outer_left", model(outer, 180, 180)),
                        variant("facing=south,half=top,shape=outer_left", model(outer, 180, 90)),
                        variant("facing=north,half=top,shape=outer_left", model(outer, 180, 270)),
                        variant("facing=east,half=top,shape=inner_right", model(inner, 180, 90)),
                        variant("facing=west,half=top,shape=inner_right", model(inner, 180, 270)),
                        variant("facing=south,half=top,shape=inner_right", model(inner, 180, 180)),
                        variant("facing=north,half=top,shape=inner_right", model(inner, 180, 0)),
                        variant("facing=east,half=top,shape=inner_left", model(inner, 180, 0)),
                        variant("facing=west,half=top,shape=inner_left", model(inner, 180, 180)),
                        variant("facing=south,half=top,shape=inner_left", model(inner, 180, 90)),
                        variant("facing=north,half=top,shape=inner_left", model(inner, 180, 270)))
                .build();
    }

    private AnnotationSpec variant(String id, AnnotationSpec model) {
        return AnnotationSpec.builder(BlockState.Variant.class).addMember("id", "$S", id).addMember("model", "$L", model).build();
    }

    private AnnotationSpec model(String model, int x, int y) {
        return model(model).addMember("x", "$L", x).addMember("y", "$L", y).addMember("uvlock", "$L", true).build();
    }

    private AnnotationSpec.Builder model(String model) {
        return AnnotationSpec.builder(BlockState.Model.class).addMember("value", "$S", model);
    }
}
