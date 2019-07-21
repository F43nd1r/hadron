package com.faendir.minecraft.hadron.processor.processors;

import com.faendir.minecraft.hadron.annotation.BlockState;
import com.faendir.minecraft.hadron.annotation.GenerateItem;
import com.faendir.minecraft.hadron.annotation.GenerateWall;
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
import net.minecraft.block.WallBlock;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public void process(RoundEnvironment roundEnv) throws Exception {
        for (Element e : roundEnv.getElementsAnnotatedWith(GenerateWall.class)) {
            String name = e.getSimpleName().toString();
            if (name.endsWith("s")) {
                name = name.substring(0, name.length() - 1);
            }
            GenerateWall generateWall = e.getAnnotation(GenerateWall.class);
            String wallId = generateWall.id();
            if (wallId.endsWith("s")) {
                wallId = wallId.substring(0, wallId.length() - 1);
            }
            wallId += "_wall";
            String texture = generateWall.texture();
            TypeSpec.Builder builder = TypeSpec.classBuilder(name + "Wall")
                    .addModifiers(Modifier.PUBLIC)
                    .superclass(WallBlock.class)
                    .addAnnotation(AnnotationSpec.builder(Register.class).addMember("value", "$T.class", Block.class).build())
                    .addAnnotation(AnnotationSpec.builder(GenerateItem.class)
                            .addMember("value", "$S", wallId)
                            .addMember("parent", "$S", wallId + "_inventory")
                            .build())
                    .addAnnotation(AnnotationSpec.builder(Recipe.class).addMember("pattern", "{\"   \",\"xxx\",\"xxx\"}")
                            .addMember("keys", "@$T(key = \"x\", value = $S)", Recipe.Key.class, withPrefix(generateWall.id()))
                            .addMember("id", "$S", wallId)
                            .addMember("count", "6").build())
                    .addAnnotation(createBlockState(wallId))
                    .addAnnotation(createModel(wallId + "_inventory", "minecraft:block/wall_inventory", texture))
                    .addAnnotation(createModel(wallId + "_post", "minecraft:block/template_wall_post", texture))
                    .addAnnotation(createModel(wallId + "_side", "minecraft:block/template_wall_side", texture))
                    .addAnnotation(AnnotationSpec.builder(Tag.class).addMember("id", "$S", wallId).addMember("tag", "$S", "walls").build())
                    .addMethod(MethodSpec.constructorBuilder()
                            .addStatement("super($2T.from(new $1T()))", TypeName.get(e.asType()), Block.Properties.class)
                            .addStatement("setRegistryName($S)", wallId).build());
            Utils.writeClass(processingEnv.getFiler(), builder.build());
        }
    }

    private AnnotationSpec createModel(String name, String parent, String texture) {
        return AnnotationSpec.builder(Model.class)
                .addMember("id", "$S", name)
                .addMember("parent", "$S", parent)
                .addMember("textures", "$L", createTexture("wall", texture))
                .build();
    }

    private AnnotationSpec createTexture(String key, String location) {
        return AnnotationSpec.builder(Model.Texture.class).addMember("key", "$S", key).addMember("id", "$S", location).build();
    }


    private AnnotationSpec createBlockState(String wallId) {
        String post = withPrefix("block/" + wallId + "_post");
        String side = withPrefix("block/" + wallId + "_side");
        return AnnotationSpec.builder(BlockState.class).addMember("id", "$S", wallId)
                .addMember("multipart", arrayInitializer(5),
                        multipart("up", model(post).build()),
                        multipart("north", model(side, 0)),
                        multipart("east", model(side, 90)),
                        multipart("south", model(side, 180)),
                        multipart("west", model(side, 270)))
                .build();
    }

    private AnnotationSpec multipart(String direction, AnnotationSpec model) {
        return AnnotationSpec.builder(BlockState.Multipart.class).addMember("when", "$L", conditions(condition(direction)))
                .addMember("apply", "$L", model).build();
    }

    private AnnotationSpec condition(String name) {
        return AnnotationSpec.builder(BlockState.Condition.class).addMember("name", "$S", name).addMember("value", "$S", true).build();
    }

    private AnnotationSpec conditions(AnnotationSpec... conditions) {
        return AnnotationSpec.builder(BlockState.Conditions.class).addMember("value", arrayInitializer(conditions.length), (Object[]) conditions).build();
    }

    private AnnotationSpec model(String model, int y) {
        return model(model).addMember("y", "$L", y).addMember("uvlock", "$L", true).build();
    }

    private AnnotationSpec.Builder model(String model) {
        return AnnotationSpec.builder(BlockState.Model.class).addMember("value", "$S", model);
    }

    private String arrayInitializer(int size) {
        return Stream.generate(() -> "$L").limit(size).collect(Collectors.joining(",\n", "{", "}"));
    }
}
