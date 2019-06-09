package com.adrianosela.pipeliner.pipelines.guardduty;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.InitialPositionInStream;
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
            .withStreamName("streamName")
            .withAWSClientsProvider("AWS_KEY", "AWS_SECRET", Regions.US_WEST_2)
            .withInitialPositionInStream(InitialPositionInStream.TRIM_HORIZON));

    // TODO

    // block exit
    p.run().waitUntilFinish();
  }
}
