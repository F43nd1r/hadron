package com.faendir.minecraft.hadron.processor.processors;

import com.faendir.minecraft.hadron.annotation.BlockState;
import com.faendir.minecraft.hadron.processor.util.Utils;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.faendir.minecraft.hadron.processor.util.Utils.ensureInfix;

/**
 * @author lukas
 * @since 01.07.19
 */
public class BlockStateProcessor extends BaseProcessor {
    public BlockStateProcessor(ProcessingEnvironment processingEnv) {
        super(processingEnv);
    }

    @Override
    public void process(AnnotatedElementSupplier supplier, RoundEnvironment roundEnv, TypeSpec.Builder registry) throws Exception {
        for (Map.Entry<Element, BlockState> e : supplier.getElementsAnnotatedWith(BlockState.class).entrySet()) {
            BlockState blockState = e.getValue();
            Utils.writeAsset(processingEnv.getFiler(), Utils.BLOCKSTATES, blockState.id(), new BlockStateJson(blockState));
        }
    }

    private static class BlockStateJson {
        private final Map<String, ModelJson> variants;
        private final List<MultipartJson> multipart;


        public BlockStateJson(BlockState blockState) {
            if (blockState.variants().length != 0) {
                this.variants = new LinkedHashMap<>();
                for (BlockState.Variant variant : blockState.variants()) {
                    this.variants.put(variant.id(), new ModelJson(variant.model()));
                }
            } else {
                variants = null;
            }
            if (blockState.multipart().length != 0) {
                this.multipart = new ArrayList<>();
                for (BlockState.Multipart multipart : blockState.multipart()) {
                    this.multipart.add(new MultipartJson(multipart));
                }
            } else {
                this.multipart = null;
            }
        }

        public Map<String, ModelJson> getVariants() {
            return variants;
        }

        public List<MultipartJson> getMultipart() {
            return multipart;
        }
    }

    private static class MultipartJson {
        private final ModelJson apply;
        private final Map<String, String> when;
        private final List<Map<String, String>> OR;


        public MultipartJson(BlockState.Multipart multipart) {
            apply = new ModelJson(multipart.apply());
            switch (multipart.when().length) {
                case 0:
                    when = null;
                    OR = null;
                    break;
                case 1:
                    when = getConditionJson(multipart.when()[0].value());
                    OR = null;
                    break;
                default:
                    when = null;
                    OR = Stream.of(multipart.when()).map(c -> getConditionJson(c.value())).collect(Collectors.toList());
            }
        }

        public Map<String, String> getWhen() {
            return when;
        }

        public List<Map<String, String>> getOR() {
            return OR;
        }

        public ModelJson getApply() {
            return apply;
        }

        private static Map<String, String> getConditionJson(BlockState.Condition[] conditions) {
            return Stream.of(conditions).collect(Collectors.toMap(BlockState.Condition::name, BlockState.Condition::value));
        }
    }

    private static class ModelJson {
        private final String model;
        private final Integer x;
        private final Integer y;
        private final Boolean uvlock;

        public ModelJson(BlockState.Model model) {
            this.model = !"".equals(model.value()) ? ensureInfix(model.value()) : null;
            this.x = model.x() != 0 ? model.x() : null;
            this.y = model.y() != 0 ? model.y() : null;
            this.uvlock = model.uvlock() ? model.uvlock() : null;
        }

        public String getModel() {
            return model;
        }

        public Integer getX() {
            return x;
        }

        public Integer getY() {
            return y;
        }

        public Boolean isUvlock() {
            return uvlock;
        }
    }
}
