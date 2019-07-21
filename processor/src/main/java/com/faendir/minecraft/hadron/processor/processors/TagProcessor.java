package com.faendir.minecraft.hadron.processor.processors;

import com.faendir.minecraft.hadron.annotation.Tag;
import com.faendir.minecraft.hadron.processor.util.Utils;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import java.util.Collection;
import java.util.Map;

/**
 * @author lukas
 * @since 21.07.19
 */
public class TagProcessor extends BaseProcessor {
    private final Multimap<String, String> tags = HashMultimap.create();

    public TagProcessor(ProcessingEnvironment processingEnv) {
        super(processingEnv);
    }

    @Override
    public void process(AnnotatedElementSupplier supplier, RoundEnvironment roundEnv) throws Exception {
        for (Map.Entry<Element, Tag> e : supplier.getElementsAnnotatedWith(Tag.class).entrySet()) {
            Tag tag = e.getValue();
            tags.put(tag.tag(), Utils.MOD_ID + ":" + tag.id());
        }
        if (roundEnv.processingOver()) {
            for (Map.Entry<String, Collection<String>> entry : tags.asMap().entrySet()) {
                Utils.writeAsset(processingEnv.getFiler(), Utils.BLOCK_TAGS, entry.getKey(), new TagJson(entry.getValue()));
            }
        }
    }

    private static class TagJson {
        private final Collection<String> values;

        private TagJson(Collection<String> values) {
            this.values = values;
        }

        public boolean getReplace() {
            return false;
        }

        public Collection<String> getValues() {
            return values;
        }
    }
}
