package com.adrianosela.pipeliner.ioutil;

import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.PDone;

/** An output transform that simply prints a string */
public class PrintOutput extends PTransform<PCollection<String>, PDone> {
  private static final long serialVersionUID = 1L;

  @Override
  public PDone expand(PCollection<String> input) {
    input.apply(
        ParDo.of(
            new DoFn<String, Void>() {
              private static final long serialVersionUID = 1L;

              @ProcessElement
              public void processElement(@Element String str) {
                System.out.println(str);
              }
            }));
    return PDone.in(input.getPipeline());
  }
}
