package com.faendir.minecraft.hadron.processor.processors;

import com.faendir.minecraft.hadron.annotation.Model;
import com.faendir.minecraft.hadron.annotation.Models;
import com.faendir.minecraft.hadron.processor.util.Utils;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author lukas
 * @since 02.07.19
 */
public class ModelProcessor extends BaseProcessor {
    public ModelProcessor(ProcessingEnvironment processingEnv) {
        super(processingEnv);
    }

    @Override
    public void process(AnnotatedElementSupplier supplier, RoundEnvironment roundEnv) throws Exception {
        List<Model> models = Stream.concat(supplier.getElementsAnnotatedWith(Model.class).values().stream(),
                supplier.getElementsAnnotatedWith(Models.class).values().stream().flatMap(e -> Stream.of(e.value())))
                .collect(Collectors.toList());
        for (Model model : models) {
            Utils.writeAsset(processingEnv.getFiler(), Utils.BLOCK_MODELS, model.id(), new ModelJson(model));
        }
    }

    private static class ModelJson {
        private final String parent;
        private final Map<String, String> textures;

        private ModelJson(Model model) {
            this.parent = model.parent();
            this.textures = new LinkedHashMap<>();
            for (Model.Texture texture : model.textures()) {
                textures.put(texture.key(), texture.id());
            }
        }

        public String getParent() {
            return parent;
        }

        public Map<String, String> getTextures() {
            return textures;
        }
    }
}
