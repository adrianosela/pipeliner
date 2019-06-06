package com.adrianosela.pipeliner.entrypoint;

import java.util.Arrays;

import com.adrianosela.pipeliner.ioutil.PrintOutput;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.Count;
import org.slf4j.LoggerFactory;
import org.apache.beam.sdk.transforms.Filter;
import org.apache.beam.sdk.transforms.FlatMapElements;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.TypeDescriptors;

public class Entrypoint {

    public static void main(String[] args) {
        PipelineOptions options = PipelineOptionsFactory.create();

        Pipeline p = Pipeline.create(options);

        // This example reads a public data set consisting of the complete works of Shakespeare.
        p.apply(TextIO.read().from("data/sample"))
                // This transform splits the lines in PCollection<String>, where each element is an
                // individual word in Shakespeare's collected texts.
                .apply(
                        FlatMapElements.into(TypeDescriptors.strings())
                                .via((String word) -> Arrays.asList(word.split("[^\\p{L}]+"))))
                // We use a Filter transform to avoid empty word
                .apply(Filter.by((String word) -> !word.isEmpty()))
                // Concept #3: Apply the Count transform to our PCollection of individual words
                .apply(Count.perElement())
                // Apply a transform that formats our PCollection of word counts into a printable string
                .apply(
                        MapElements.into(TypeDescriptors.strings())
                                .via(
                                        (KV<String, Long> wordCount) ->
                                                wordCount.getKey() + ": " + wordCount.getValue()))
                // write transform to write the contents of a PCollection to a series of text files.
                //
                // By default, it will write to a set of files with names like wordcounts-00001-of-00005
                .apply(new PrintOutput());

        p.run().waitUntilFinish();
    }
}