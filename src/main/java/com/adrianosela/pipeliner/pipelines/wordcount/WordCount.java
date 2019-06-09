package com.adrianosela.pipeliner.pipelines.wordcount;

import com.adrianosela.pipeliner.ioutil.PrintToSTDOUT;
import java.util.Arrays;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.Count;
import org.apache.beam.sdk.transforms.Filter;
import org.apache.beam.sdk.transforms.FlatMapElements;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.TypeDescriptors;

public class WordCount {

  public static void main(String[] args) {
    // create options
    PipelineOptions options = PipelineOptionsFactory.create();

    // create pipeline
    Pipeline p = Pipeline.create(options);

    // read file
    PCollection<String> input = p.apply(TextIO.read().from("data/sample"));

    // parse words
    PCollection<String> words =
        input.apply(
            FlatMapElements.into(TypeDescriptors.strings())
                .via((String word) -> Arrays.asList(word.split("[^\\p{L}]+"))));

    // filter out empty words
    PCollection<String> nonEmptyWords = words.apply(Filter.by((String word) -> !word.isEmpty()));

    // count unique appearances of each word
    PCollection<KV<String, Long>> wordCounts = nonEmptyWords.apply(Count.perElement());

    // format onto the pretty string we want as output
    PCollection<String> formattedOutputs =
        wordCounts.apply(
            MapElements.into(TypeDescriptors.strings())
                .via(
                    (KV<String, Long> wordCount) ->
                        wordCount.getKey() + ": " + wordCount.getValue()));

    // print output
    formattedOutputs.apply(new PrintToSTDOUT());

    // block exit
    p.run().waitUntilFinish();
  }
}
