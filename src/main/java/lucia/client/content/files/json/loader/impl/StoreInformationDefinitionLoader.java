package main.java.lucia.client.content.files.json.loader.impl;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import main.java.lucia.Client;
import main.java.lucia.client.content.files.FileType;
import main.java.lucia.client.content.files.json.StoreInformationDefinition;
import main.java.lucia.client.content.files.json.loader.JsonHandler;
import main.java.lucia.net.packet.impl.GsonTypeFactory;

public class StoreInformationDefinitionLoader extends JsonHandler {

  @Override
  public void load() {
    List<StoreInformationDefinition> hours;

    try (Stream<String> reader = Files.lines(Paths.get(type().getPath()))) {
      Type listType = type().getSource();
      hours = GsonTypeFactory.GENERIC_GSON.fromJson(reader.collect(Collectors.joining()), listType);
      for (StoreInformationDefinition store : hours) {
        StoreInformationDefinition.definitions.put(store.getStore(), store);
      }
    } catch (IOException e) {
      Client.logger.error("An error has occurred while loading store information!", e);
    }
  }

  @Override
  public FileType type() {
    return FileType.HOURS_INFORMATION;
  }

  @Override
  public void save()  {
    // We do not save anything here since this file should not be edited by any clients
    // and therefore does not need to be saved.
  }
}