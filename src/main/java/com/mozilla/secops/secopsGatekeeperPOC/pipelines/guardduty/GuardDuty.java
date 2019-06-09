package com.mozilla.secops.secopsGatekeeperPOC.pipelines.guardduty;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.InitialPositionInStream;
import com.mozilla.secops.secopsGatekeeperPOC.ioutil.PrintToSTDOUT;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.kinesis.KinesisIO;
import org.apache.beam.sdk.io.kinesis.KinesisRecord;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.TypeDescriptors;

public class GuardDuty {

  public static void main(String[] args) {

    String STREAM_NAME = "foxsec-pipeline-delivery";
    String AWS_ACCESS_KEY_ID = "";
    String AWS_SECRET_ACCESS_KEY = "";

    // create options
    PipelineOptions options = PipelineOptionsFactory.create();

    // create pipeline
    Pipeline p = Pipeline.create(options);

    // read from kinesis stream
    PCollection<KinesisRecord> records =
        p.apply(
            KinesisIO.read()
                .withStreamName(STREAM_NAME)
                .withAWSClientsProvider(AWS_ACCESS_KEY_ID, AWS_SECRET_ACCESS_KEY, Regions.US_WEST_2)
                .withInitialPositionInStream(InitialPositionInStream.TRIM_HORIZON));

    // convert data in stream to string
    PCollection<String> recordData =
        records.apply(
            MapElements.into(TypeDescriptors.strings())
                .via(record -> new String(record.getDataAsBytes())));

    // print output to stdout
    recordData.apply(new PrintToSTDOUT());

    // block exit
    p.run().waitUntilFinish();
  }
}
