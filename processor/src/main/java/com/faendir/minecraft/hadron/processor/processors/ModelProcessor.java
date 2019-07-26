package com.faendir.minecraft.hadron.processor.processors;

import com.faendir.minecraft.hadron.annotation.Model;
import com.faendir.minecraft.hadron.annotation.Texture;
import com.faendir.minecraft.hadron.processor.util.Utils;
import com.squareup.javapoet.TypeSpec;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.faendir.minecraft.hadron.processor.util.Utils.ensureInfix;

/**
 * @author lukas
 * @since 02.07.19
 */
public class ModelProcessor extends BaseProcessor {
    public ModelProcessor(ProcessingEnvironment processingEnv) {
        super(processingEnv);
    }

    @Override
    public void process(AnnotatedElementSupplier supplier, RoundEnvironment roundEnv, TypeSpec.Builder modObjects) throws Exception {
        for (Pair<Element, Model> model : supplier.getElementsAnnotatedWithRepeatable(Model.class, Model.Repeat.class)) {
            Utils.writeAsset(processingEnv.getFiler(), Utils.AssetPath.BLOCK_MODELS, model.getValue().id(), new ModelJson(model.getValue()));
        }
    }

    private static class ModelJson {
        private final String parent;
        private final Map<String, String> textures;

        private ModelJson(Model model) {
            this.parent = ensureInfix(model.parent());
            this.textures = new LinkedHashMap<>();
            for (Texture texture : model.textures()) {
                textures.put(texture.key(), ensureInfix(texture.id()));
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
