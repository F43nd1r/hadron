package com.faendir.minecraft.hadron.processor;

import com.faendir.minecraft.hadron.annotation.Composite;
import com.faendir.minecraft.hadron.processor.processors.BaseProcessor;
import com.faendir.minecraft.hadron.processor.processors.BlockStateProcessor;
import com.faendir.minecraft.hadron.processor.processors.GenerateItemProcessor;
import com.faendir.minecraft.hadron.processor.processors.GenerateSlabsProcessor;
import com.faendir.minecraft.hadron.processor.processors.GenerateStairsProcessor;
import com.faendir.minecraft.hadron.processor.processors.GenerateWallProcessor;
import com.faendir.minecraft.hadron.processor.processors.ModelProcessor;
import com.faendir.minecraft.hadron.processor.processors.RecipeProcessor;
import com.faendir.minecraft.hadron.processor.processors.RegisterProcessor;
import com.faendir.minecraft.hadron.processor.processors.TagProcessor;
import com.faendir.minecraft.hadron.processor.util.ExpandedAnnotationMirror;
import com.faendir.minecraft.hadron.processor.util.Utils;
import com.google.auto.service.AutoService;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.squareup.javapoet.TypeSpec;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.AbstractAnnotationValueVisitor8;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author lukas
 * @since 01.07.19
 */
@AutoService(javax.annotation.processing.Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes("*")
public class Processor extends AbstractProcessor {
    private List<BaseProcessor> processors = new ArrayList<>();
    private static int count = 0;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        processors.add(new RegisterProcessor(processingEnv));
        processors.add(new GenerateItemProcessor(processingEnv));
        processors.add(new RecipeProcessor(processingEnv));
        processors.add(new BlockStateProcessor(processingEnv));
        processors.add(new ModelProcessor(processingEnv));
        processors.add(new GenerateSlabsProcessor(processingEnv));
        processors.add(new GenerateStairsProcessor(processingEnv));
        processors.add(new GenerateWallProcessor(processingEnv));
        processors.add(new TagProcessor(processingEnv));
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        try {
            Multimap<Element, ExpandedAnnotationMirror> elements = HashMultimap.create();
            for (Element e : roundEnv.getElementsAnnotatedWithAny(annotations.toArray(new TypeElement[0]))) {
                if (!e.getModifiers().contains(Modifier.ABSTRACT)) {
                    buildAnnotationList(e).forEach(a -> elements.put(e, a));
                }
            }
            BaseProcessor.AnnotatedElementSupplier supplier = new BaseProcessor.AnnotatedElementSupplier(processingEnv, elements);
            supplier.getElementsAnnotatedWith(Mod.class).stream().findAny().ifPresent(p -> Utils.MOD_ID = p.getValue().value());
            TypeSpec.Builder registry = TypeSpec.classBuilder("ModObjects" + count++).addModifiers(Modifier.PUBLIC);
            for (BaseProcessor processor : processors) {
                processor.process(supplier, roundEnv, registry);
            }
            TypeSpec spec = registry.build();
            if (!spec.fieldSpecs.isEmpty() || !spec.methodSpecs.isEmpty()) {
                Utils.writeClass(processingEnv.getFiler(), spec);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Find all annotations on the given element and expand composite annotations
     *
     * @param e the element
     * @return all annotations, including those only present on composites
     */
    private List<ExpandedAnnotationMirror> buildAnnotationList(Element e) {
        List<ExpandedAnnotationMirror> result = new ArrayList<>();
        for (AnnotationMirror process : e.getAnnotationMirrors()) {
            result.add(new ExpandedAnnotationMirror(process, e));
            Element processElement = processingEnv.getTypeUtils().asElement(process.getAnnotationType());
            if (processElement.getAnnotation(Composite.class) != null) {
                Map<String, String> replacements = process.getElementValues().entrySet().stream()
                        .filter(entry -> entry.getKey().getReturnType().toString().equals(String.class.getName()))
                        .collect(Collectors.toMap(entry -> "{" + entry.getKey().getSimpleName() + "}", entry -> (String) entry.getValue().getValue()));
                List<ExpandedAnnotationMirror> toExpand = buildAnnotationList(processElement);
                for (ExpandedAnnotationMirror expand : toExpand) {
                    expand(replacements, expand);
                }
                result.addAll(toExpand);
            }
        }
        return result;
    }

    /**
     * replace placeholders with their value
     *
     * @param replacements applicable replacements
     * @param expand the annotation to expand
     */
    private void expand(Map<String, String> replacements, ExpandedAnnotationMirror expand) {
        outer:
        for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : expand.getMirror().getElementValues().entrySet()) {
            ExecutableElement method = entry.getKey();
            AnnotationValue value = entry.getValue();
            String methodName = method.getSimpleName().toString();
            if (method.getReturnType().toString().equals(String.class.getName())) {
                expand.putReplacement(methodName, doExpand(replacements, (String) value.getValue()));
            } else if (method.getReturnType().getKind() == TypeKind.DECLARED) {
                AnnotationMirror mirror = tryGetAnnotationMirror(value);
                if (mirror != null) {
                    ExpandedAnnotationMirror child = new ExpandedAnnotationMirror(mirror, expand.getOwner());
                    expand(replacements, child);
                    expand.putReplacement(methodName, child);
                }
            } else if (method.getReturnType().getKind() == TypeKind.ARRAY) {
                TypeMirror componentType = ((ArrayType) method.getReturnType()).getComponentType();
                if (componentType.toString().equals(String.class.getName())) {
                    //noinspection unchecked
                    List<AnnotationValue> s = (List<AnnotationValue>) value.getValue();
                    String[] out = new String[s.size()];
                    for (int i = 0; i < out.length; i++) {
                        out[i] = doExpand(replacements, (String) s.get(i).getValue());
                    }
                    expand.putReplacement(methodName, out);
                } else if (componentType.getKind() == TypeKind.DECLARED) {
                    //noinspection unchecked
                    List<AnnotationValue> s = (List<AnnotationValue>) value.getValue();
                    ExpandedAnnotationMirror[] out = new ExpandedAnnotationMirror[s.size()];
                    for (int i = 0; i < out.length; i++) {
                        AnnotationMirror mirror = tryGetAnnotationMirror(s.get(i));
                        if (mirror != null) {
                            ExpandedAnnotationMirror child = new ExpandedAnnotationMirror(mirror, expand.getOwner());
                            expand(replacements, child);
                            out[i] = child;
                        } else {
                            continue outer;
                        }
                    }
                    expand.putReplacement(methodName, out);
                }
            }
        }
    }

    /**
     * apply all replacements to a given string
     *
     * @param replacements the replacements
     * @param s the string
     * @return the string with all replacements applied
     */
    private String doExpand(Map<String, String> replacements, String s) {
        for (Map.Entry<String, String> replacement : replacements.entrySet()) {
            s = s.replace(replacement.getKey(), replacement.getValue());
        }
        return s;
    }

    /**
     * try to get an annotation mirror from a given annotation value
     *
     * @param value the value
     * @return an annotation mirror, or null if the value has another type
     */
    private AnnotationMirror tryGetAnnotationMirror(AnnotationValue value) {
        return new AbstractAnnotationValueVisitor8<AnnotationMirror, Void>() {
            @Override
            public AnnotationMirror visitBoolean(boolean b, Void aVoid) {
                return null;
            }

            @Override
            public AnnotationMirror visitByte(byte b, Void aVoid) {
                return null;
            }

            @Override
            public AnnotationMirror visitChar(char c, Void aVoid) {
                return null;
            }

            @Override
            public AnnotationMirror visitDouble(double d, Void aVoid) {
                return null;
            }

            @Override
            public AnnotationMirror visitFloat(float f, Void aVoid) {
                return null;
            }

            @Override
            public AnnotationMirror visitInt(int i, Void aVoid) {
                return null;
            }

            @Override
            public AnnotationMirror visitLong(long i, Void aVoid) {
                return null;
            }

            @Override
            public AnnotationMirror visitShort(short s, Void aVoid) {
                return null;
            }

            @Override
            public AnnotationMirror visitString(String s, Void aVoid) {
                return null;
            }

            @Override
            public AnnotationMirror visitType(TypeMirror t, Void aVoid) {
                return null;
            }

            @Override
            public AnnotationMirror visitEnumConstant(VariableElement c, Void aVoid) {
                return null;
            }

            @Override
            public AnnotationMirror visitAnnotation(AnnotationMirror a, Void aVoid) {
                return a;
            }

            @Override
            public AnnotationMirror visitArray(List<? extends AnnotationValue> vals, Void aVoid) {
                return null;
            }
        }.visit(value);
    }
}
