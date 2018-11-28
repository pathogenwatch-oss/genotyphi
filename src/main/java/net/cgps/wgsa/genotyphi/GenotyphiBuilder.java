package net.cgps.wgsa.genotyphi;

import net.cgps.wgsa.genotyphi.core.GenotyphiSchema;
import net.cgps.wgsa.genotyphi.lib.MakeBlastDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class GenotyphiBuilder {

  private final Logger logger = LoggerFactory.getLogger(GenotyphiBuilder.class);

  public void run(final Path inputPath, final Path databasePath) {

    // Builds blast database & genotyphi schema doc.
    final Path dataCsv = Paths.get(inputPath.toString(), "data.csv");

    if (!Files.exists(dataCsv)) {
      throw new RuntimeException("Unable to find file {}" + dataCsv.toAbsolutePath().toString());
    }

    final GenotyphiSchema genotyphiSchema = new SchemaBuilder().apply(dataCsv);

    try {
      final Path schemaPath = Paths.get(databasePath.toString(), "schema.jsn");
      this.logger.info("Writing schema to {}", schemaPath.toString());
      Files.write(schemaPath, genotyphiSchema.toJson().getBytes(), StandardOpenOption.CREATE);
    } catch (final IOException e) {
      this.logger.error("Failed to write genotyphi {}", e);
      throw new RuntimeException(e);
    }

    final Path fastaPath = Paths.get(inputPath.toString(), "genes.fa");

    if (!Files.exists(dataCsv)) {
      throw new RuntimeException("Unable to find file {}" + fastaPath.toAbsolutePath().toString());
    }

    new MakeBlastDB(databasePath).accept("genotyphi", fastaPath);
    this.logger.info("Written blast DB to {} using {}", databasePath.toAbsolutePath().toString(), fastaPath.toAbsolutePath().toString());
  }
}
