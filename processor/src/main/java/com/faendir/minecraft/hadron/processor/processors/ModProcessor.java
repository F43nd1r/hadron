package com.faendir.minecraft.hadron.processor.processors;

import com.faendir.minecraft.hadron.processor.util.Utils;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Modifier;

import static com.faendir.minecraft.hadron.processor.util.Utils.*;

/**
 * @author lukas
 * @since 01.08.19
 */
public class ModProcessor extends BaseProcessor {
    public ModProcessor(ProcessingEnvironment processingEnv) {
        super(processingEnv);
    }

    @Override
    public void process(AnnotatedElementSupplier supplier, RoundEnvironment roundEnv, TypeSpec.Builder modObjects) throws Exception {
        supplier.getElementsAnnotatedWith(Mod.class).stream().findAny().ifPresent(p -> MOD_ID = p.getValue().value());
        if (processingEnv.getElementUtils().getTypeElement(PACKAGE + "." + getConfigEventName()) == null) {
            TypeSpec eventClass = TypeSpec.classBuilder(getConfigEventName())
                    .addModifiers(Modifier.PUBLIC)
                    .superclass(Event.class)
                    .addField(FieldSpec.builder(ForgeConfigSpec.Builder.class, "builder", Modifier.PRIVATE, Modifier.FINAL).build())
                    .addMethod(MethodSpec.constructorBuilder()
                            .addParameter(ForgeConfigSpec.Builder.class, "builder")
                            .addStatement("this.builder = builder")
                            .build())
                    .addMethod(MethodSpec.methodBuilder("getBuilder")
                            .addModifiers(Modifier.PUBLIC)
                            .returns(ForgeConfigSpec.Builder.class)
                            .addStatement("return builder").build())
                    .build();
            writeClass(processingEnv.getFiler(), eventClass);
            writeClass(processingEnv.getFiler(), TypeSpec.classBuilder(getConfigHolderName())
                    .addModifiers(Modifier.PUBLIC)
                    .addAnnotation(AnnotationSpec.builder(Mod.EventBusSubscriber.class)
                            .addMember("modid", "$S", Utils.MOD_ID)
                            .addMember("bus", "$T.MOD", Mod.EventBusSubscriber.Bus.class)
                            .build())
                    .addField(ForgeConfigSpec.class, CONFIG, Modifier.PUBLIC, Modifier.STATIC)
                    .addMethod(MethodSpec.methodBuilder("setup")
                            .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                            .addParameter(RegistryEvent.NewRegistry.class, "e")
                            .addAnnotation(SubscribeEvent.class)
                            .addStatement("$1T builder = new $1T()", ForgeConfigSpec.Builder.class)
                            .addStatement("$1T event = new $1T(builder)", ClassName.get(PACKAGE, eventClass.name))
                            .addStatement("$T.get().getModEventBus().post(event)", FMLJavaModLoadingContext.class)
                            .addStatement("$L = builder.build()", CONFIG)
                            .addStatement("$T.get().registerConfig($T.COMMON, $L)", ModLoadingContext.class, ModConfig.Type.class, CONFIG)
                            .build())
                    .build());
        }
    }
}
