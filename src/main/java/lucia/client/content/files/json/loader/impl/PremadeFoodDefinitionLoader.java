package main.java.lucia.client.content.files.json.loader.impl;

import com.google.common.base.Charsets;
import com.google.gson.Gson;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import main.java.lucia.Client;
import main.java.lucia.client.content.files.FileType;
import main.java.lucia.client.content.files.json.loader.JsonHandler;
import main.java.lucia.client.content.menu.legacy.Menu;
import main.java.lucia.net.packet.impl.GsonTypeFactory;

public class PremadeFoodDefinitionLoader extends JsonHandler {

  @Override
  protected void load() {
    try (Stream<String> reader = Files.lines(Paths.get(type().getPath()))) {
      Menu.loadedPreMadeFoods = GsonTypeFactory.MENU_ITEM_GSON
          .fromJson(reader.collect(Collectors.joining()), type().getSource());
    } catch (IOException e) {
      Client.logger.error("An error has occurred while loading store information!", e);
    }
  }

  @Override
  protected FileType type() {
    return FileType.PREMADE_FOOD_LIST;
  }

  @Override
  protected void save() throws IOException {
    if (modified) {
      Gson writer;
      try {
        writer = GsonTypeFactory.MENU_ITEM_GSON.newBuilder().setPrettyPrinting().create();
      } catch (Exception e) {
        e.printStackTrace();
        return;
      }
      Files.write(Paths.get(type().getPath()),
          writer.toJson(Menu.loadedPreMadeFoods, type().getSource()).getBytes(Charsets.UTF_8));

      modified = false;
    }
  }
}