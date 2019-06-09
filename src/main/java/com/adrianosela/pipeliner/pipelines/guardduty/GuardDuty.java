package com.adrianosela.pipeliner.pipelines.guardduty;

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

public class GuardDuty {

  public static void main(String[] args) {
    // create options
    PipelineOptions options = PipelineOptionsFactory.create();

    // create pipeline
    Pipeline p = Pipeline.create(options);

    // TODO

    // block exit
    p.run().waitUntilFinish();
  }
}
