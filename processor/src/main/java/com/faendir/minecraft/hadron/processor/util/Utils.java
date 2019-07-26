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

/**
 * @author lukas
 * @since 01.07.19
 */
public final class Utils {
    public enum AssetPath {
        BLOCKSTATES("assets.", ".blockstates"),
        BLOCK_MODELS("assets.", ".models.block"),
        ITEM_MODELS("assets.", ".models.item"),
        BLOCK_TAGS("data.minecraft.tags.blocks"),
        RECIPES("data.", ".recipes");
        private final String prefix;
        private final String suffix;
        private final boolean containsModId;

        AssetPath(String path) {
            this(path, "", false);
        }

        AssetPath(String prefix, String suffix) {
            this(prefix, suffix, true);
        }

        AssetPath(String prefix, String suffix, boolean containsModId) {
            this.prefix = prefix;
            this.suffix = suffix;
            this.containsModId = containsModId;
        }

        public String getPath() {
            return containsModId ? prefix + MOD_ID + suffix : prefix;
        }
    }

    public static final String PACKAGE = "com.faendir.minecraft.hadron.generated";
    public static String MOD_ID = "mod_id_not_found";

    private Utils() {
    }

    public static void writeClass(Filer filer, TypeSpec typeSpec) throws IOException {
        JavaFile.builder(PACKAGE, typeSpec)
                .skipJavaLangImports(true)
                .indent("    ")
                .build()
                .writeTo(filer);
    }

    public static void writeAsset(Filer filer, AssetPath path, String name, Object object) throws IOException {
        try (Writer writer = filer.createResource(StandardLocation.CLASS_OUTPUT, path.getPath(), name + ".json")
                .openWriter()) {
            new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL).setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
                    .writeValue(writer, object);
        }
    }

    public static String withModNameSpace(String id) {
        return MOD_ID + ":" + id;
    }

    public static String ensureNameSpaced(String id) {
        int i = id.indexOf(':');
        if (i == -1) {
            return withModNameSpace(id);
        }
        return id;
    }

    public static String removeNameSpace(String id) {
        int i = id.indexOf(':');
        if (i != -1) {
            return id.substring(i + 1);
        }
        return id;
    }

    public static String ensureInfix(String id) {
        id = ensureNameSpaced(id);
        if (id.contains("/")) {
            return id;
        }
        int i = id.indexOf(':');
        return id.substring(0, i + 1) + "block/" + id.substring(i + 1);
    }

    public static String noPlural(String s) {
        if (s.endsWith("s")) {
            return s.substring(0, s.length() - 1);
        }
        return s;
    }
}
