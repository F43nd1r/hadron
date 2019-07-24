package com.faendir.minecraft.hadron.processor.processors;

import com.faendir.minecraft.hadron.annotation.Register;
import com.faendir.minecraft.hadron.processor.util.Utils;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.MirroredTypeException;
import java.util.Collection;
import java.util.Map;

/**
 * @author lukas
 * @since 01.07.19
 */
public class RegisterProcessor extends BaseProcessor {
    private static int count = 0;

    public RegisterProcessor(ProcessingEnvironment processingEnv) {
        super(processingEnv);
    }

    @Override
    public void process(AnnotatedElementSupplier supplier, RoundEnvironment roundEnv, TypeSpec.Builder registry) throws Exception {
        Multimap<TypeName, Element> map = HashMultimap.create();
        for (Pair<Element, Register> entry : supplier.getElementsAnnotatedWith(Register.class)) {
            TypeName event;
            try {
                event = TypeName.get(entry.getValue().value());
            } catch (MirroredTypeException e) {
                event = TypeName.get(e.getTypeMirror());
            }
            map.put(event, entry.getKey());
        }
        if(!map.isEmpty()) {
            TypeSpec.Builder builder = TypeSpec.classBuilder("Registry" + count++)
                    .addModifiers(Modifier.PUBLIC)
                    .addAnnotation(AnnotationSpec.builder(Mod.EventBusSubscriber.class).addMember("modid", "$S", Utils.MOD_ID).addMember("bus", "$T.MOD", Mod.EventBusSubscriber.Bus.class).build());
            for (Map.Entry<TypeName, Collection<Element>> entry : map.asMap().entrySet()) {
                MethodSpec.Builder method = MethodSpec.methodBuilder("register" + ((ClassName) entry.getKey()).simpleName())
                        .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                        .addAnnotation(SubscribeEvent.class)
                        .addParameter(ParameterizedTypeName.get(ClassName.get(RegistryEvent.Register.class), entry.getKey()), "event");
                for (Element register : entry.getValue()) {
                    method.addStatement("event.getRegistry().register($T.$L)", register.getEnclosingElement(), register);
                }
                builder.addMethod(method.build());
            }
            Utils.writeClass(processingEnv.getFiler(), builder.build());
        }
    }
}
