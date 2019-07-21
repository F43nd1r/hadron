package com.faendir.minecraft.hadron.processor.processors;

import com.faendir.minecraft.hadron.annotation.Model;
import com.faendir.minecraft.hadron.annotation.Models;
import com.faendir.minecraft.hadron.processor.util.Utils;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author lukas
 * @since 02.07.19
 */
public class ModelProcessor extends BaseProcessor {
    public ModelProcessor(ProcessingEnvironment processingEnv) {
        super(processingEnv);
    }

    @Override
    public void process(RoundEnvironment roundEnv) throws Exception {
        for(Element e : roundEnv.getElementsAnnotatedWithAny(Set.of(Model.class, Models.class))) {
            Models models = e.getAnnotation(Models.class);
            Model m = e.getAnnotation(Model.class);
            List<Model> modelList = new ArrayList<>();
            if(models != null) {
                Collections.addAll(modelList, models.value());
            }
            if(m != null) {
                modelList.add(m);
            }
            for (Model model : modelList) {
                Utils.writeAsset(processingEnv.getFiler(), Utils.BLOCK_MODELS, model.id(), new ModelJson(model));
            }
        }
    }

    private static class ModelJson {
        private final String parent;
        private final Map<String, String> textures;

        private ModelJson(Model model) {
            this.parent = model.parent();
            this.textures = new LinkedHashMap<>();
            for (Model.Texture texture : model.textures()){
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
