package com.faendir.minecraft.hadron.processor.processors;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;

/**
 * @author lukas
 * @since 01.07.19
 */
public abstract class BaseProcessor {
    protected final ProcessingEnvironment processingEnv;

    public BaseProcessor(ProcessingEnvironment processingEnv) {
        this.processingEnv = processingEnv;
    }

    public abstract void process(RoundEnvironment roundEnv) throws Exception;
}
