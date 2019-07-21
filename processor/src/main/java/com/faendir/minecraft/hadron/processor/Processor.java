package com.faendir.minecraft.hadron.processor;

import com.faendir.minecraft.hadron.annotation.BlockState;
import com.faendir.minecraft.hadron.annotation.GenerateItem;
import com.faendir.minecraft.hadron.annotation.GenerateSlabs;
import com.faendir.minecraft.hadron.annotation.GenerateStairs;
import com.faendir.minecraft.hadron.annotation.GenerateWall;
import com.faendir.minecraft.hadron.annotation.Model;
import com.faendir.minecraft.hadron.annotation.Models;
import com.faendir.minecraft.hadron.annotation.Recipe;
import com.faendir.minecraft.hadron.annotation.Register;
import com.faendir.minecraft.hadron.annotation.Tag;
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
import com.google.auto.service.AutoService;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author lukas
 * @since 01.07.19
 */
@AutoService(javax.annotation.processing.Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class Processor extends AbstractProcessor {
    private List<BaseProcessor> processors = new ArrayList<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        processors.add(new RegisterProcessor(processingEnv));
        processors.add(new GenerateItemProcessor(processingEnv));
        processors.add(new RecipeProcessor(processingEnv));
        processors.add(new GenerateStairsProcessor(processingEnv));
        processors.add(new BlockStateProcessor(processingEnv));
        processors.add(new ModelProcessor(processingEnv));
        processors.add(new GenerateSlabsProcessor(processingEnv));
        processors.add(new GenerateWallProcessor(processingEnv));
        processors.add(new TagProcessor(processingEnv));
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return new HashSet<>(Arrays.asList(Register.class.getName(), Tag.class.getName(), GenerateItem.class.getName(), Recipe.class.getName(),
                GenerateStairs.class.getName(), GenerateSlabs.class.getName(), GenerateWall.class.getName(),
                BlockState.class.getName(), Model.class.getName(), Models.class.getName()));
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        try {
            for (BaseProcessor processor : processors) {
                processor.process(roundEnv);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
