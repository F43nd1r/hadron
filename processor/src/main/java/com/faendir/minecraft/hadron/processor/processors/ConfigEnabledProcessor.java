package com.faendir.minecraft.hadron.processor.processors;

import com.faendir.minecraft.hadron.annotation.ConfigEnabled;
import com.faendir.minecraft.hadron.processor.util.Utils;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import java.util.Collection;
import java.util.Map;

import static com.faendir.minecraft.hadron.processor.util.Utils.PACKAGE;
import static com.faendir.minecraft.hadron.processor.util.Utils.getConfigEventName;

/**
 * @author lukas
 * @since 02.08.19
 */
public class ConfigEnabledProcessor extends BaseProcessor {
    private static int count = 0;

    public ConfigEnabledProcessor(ProcessingEnvironment processingEnv) {
        super(processingEnv);
    }

    @Override
    public void process(AnnotatedElementSupplier supplier, RoundEnvironment roundEnv, TypeSpec.Builder modObjects) throws Exception {
        Multimap<String, Element> map = HashMultimap.create();
        for (Pair<Element, ConfigEnabled> entry : supplier.getElementsAnnotatedWith(ConfigEnabled.class)) {
            map.put(entry.getValue().value(), entry.getKey());
        }
        if (!map.isEmpty()) {
            TypeSpec.Builder builder = TypeSpec.classBuilder("ItemController" + count++)
                    .addModifiers(Modifier.PUBLIC)
                    .addAnnotation(AnnotationSpec.builder(Mod.EventBusSubscriber.class).addMember("modid", "$S", Utils.MOD_ID).addMember("bus", "$T.MOD", Mod.EventBusSubscriber.Bus.class).build());
            MethodSpec.Builder create = MethodSpec.methodBuilder("createConfig")
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .addAnnotation(SubscribeEvent.class)
                    .addParameter(ClassName.get(PACKAGE, getConfigEventName()), "event");
            MethodSpec.Builder load = MethodSpec.methodBuilder("loadConfig")
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .addAnnotation(SubscribeEvent.class)
                    .addParameter(ModConfig.ModConfigEvent.class, "event");
            for (Map.Entry<String, Collection<Element>> entry : map.asMap().entrySet()) {
                String name = entry.getKey().toUpperCase().replace('.', '_');
                builder.addField(ForgeConfigSpec.BooleanValue.class, name, Modifier.PRIVATE, Modifier.STATIC);
                create.addStatement("$L = event.getBuilder().define($S, true)", name, entry.getKey());
                for (Element register : entry.getValue()) {
                    load.addStatement("$T.$L.setObtainable($L.get())", register.getEnclosingElement(), register, name);
                }
            }
            load.addStatement("$T.getInstance().populateSearchTreeManager()", Minecraft.class);
            builder.addMethod(create.build()).addMethod(load.build());
            Utils.writeClass(processingEnv.getFiler(), builder.build());
        }

    }
}
