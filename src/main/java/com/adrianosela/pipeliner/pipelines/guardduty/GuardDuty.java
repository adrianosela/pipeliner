package com.adrianosela.pipeliner.pipelines.guardduty;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.kinesis.KinesisIO;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;

public class GuardDuty {

  public static void main(String[] args) {
    // create options
    PipelineOptions options = PipelineOptionsFactory.create();

    // create pipeline
    Pipeline p = Pipeline.create(options);

    p.apply(
        KinesisIO.read()
            .from("streamName", InitialPositionInStream.TRIM_HORIZON)
            .withClientProvider("AWS_KEY", "AWS_SECRET", "us-west-2"));

    // TODO

    // block exit
    p.run().waitUntilFinish();
  }
}
