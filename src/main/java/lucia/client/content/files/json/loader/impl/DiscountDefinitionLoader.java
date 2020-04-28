package main.java.lucia.client.content.files.json.loader.impl;

import com.google.common.base.Charsets;
import com.google.gson.Gson;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import main.java.lucia.Client;
import main.java.lucia.client.content.order.pricing.DiscountOthersCalculator;
import main.java.lucia.client.content.files.FileType;
import main.java.lucia.client.content.files.json.loader.JsonHandler;
import main.java.lucia.net.packet.impl.GsonTypeFactory;

public class DiscountDefinitionLoader extends JsonHandler {

  @Override
  protected void load() {
    try (Stream<String> reader = Files.lines(Paths.get(type().getPath()))) {
      DiscountOthersCalculator loaded = GsonTypeFactory.GENERIC_GSON
          .fromJson(reader.collect(Collectors.joining()), type().getSource());

      DiscountOthersCalculator.setInstance(loaded);
    } catch (IOException e) {
      Client.logger.error("An error has occurred while loading store information!", e);
    }
  }

  @Override
  protected FileType type() {
    return FileType.DISCOUNT_DEFINITIONS;
  }

  @Override
  protected void save() throws IOException {
    if (modified) {
      Gson writer = GsonTypeFactory.PRETTY_PRINT_GSON;
      Files.write(Paths.get(type().getPath()),
          writer.toJson(DiscountOthersCalculator.getInstance(), type().getSource()).getBytes(Charsets.UTF_8));

      modified = false;
    }
  }
}