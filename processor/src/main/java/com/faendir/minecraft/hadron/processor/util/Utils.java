package com.faendir.minecraft.hadron.processor.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.Filer;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.io.Writer;
import java.util.Calendar;

/**
 * @author lukas
 * @since 01.07.19
 */
public final class Utils {
    public static final String PACKAGE = "com.faendir.minecraft.hadron.generated";
    public static final String BLOCKSTATES = "assets.hadron.blockstates";
    public static final String BLOCK_MODELS = "assets.hadron.models.block";
    public static final String ITEM_MODELS = "assets.hadron.models.item";
    public static final String BLOCK_TAGS = "data.minecraft.tags.blocks";
    public static final String RECIPES = "data.hadron.recipes.generated";
    public static final String MOD_ID = "hadron";

    private Utils() {
    }

    public static void writeClass(Filer filer, TypeSpec typeSpec) throws IOException {
        JavaFile.builder(PACKAGE, typeSpec)
                .skipJavaLangImports(true)
                .indent("    ")
                .addFileComment("Copyright (c) " + Calendar.getInstance().get(Calendar.YEAR) + "\n\n" +
                        "Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
                        "you may not use this file except in compliance with the License.\n\n" +
                        "http://www.apache.org/licenses/LICENSE-2.0\n\n" +
                        "Unless required by applicable law or agreed to in writing, software\n" +
                        "distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
                        "WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                        "See the License for the specific language governing permissions and\n" +
                        "limitations under the License.")
                .build()
                .writeTo(filer);
    }

    public static void writeAsset(Filer filer, String path, String name, Object object) throws IOException {
        try (Writer writer = filer.createResource(StandardLocation.CLASS_OUTPUT, path, name + ".json")
                .openWriter()) {
            new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL).setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
                    .writeValue(writer, object);
        }
    }

    public static String withPrefix(String id) {
        return MOD_ID + ":" +id;
    }

    public static String noPlural(String s) {
        if (s.endsWith("s")) {
            return s.substring(0, s.length() - 1);
        }
        return s;
    }
}
