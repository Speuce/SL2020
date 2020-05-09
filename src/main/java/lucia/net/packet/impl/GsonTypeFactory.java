package main.java.lucia.net.packet.impl;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.java.lucia.client.content.employee.EmployeeNote;
import main.java.lucia.client.content.employee.ManagerNote;
import main.java.lucia.client.content.employee.type.Driver;
import main.java.lucia.client.content.employee.type.Employee;
import main.java.lucia.client.content.employee.type.Manager;
import main.java.lucia.client.content.menu.io.deserializer.server.PizzaDeserializer;
import main.java.lucia.client.content.menu.io.serializer.server.PizzaSerializer;
import main.java.lucia.client.content.menu.item.descriptor.*;
import main.java.lucia.client.content.menu.item.type.pizza.Pizza;
import main.java.lucia.client.content.menu.legacy.premade.impl.PremadePizza;
import main.java.lucia.client.content.structures.Exclude;
import main.java.lucia.client.content.time.io.TimeGson;
import main.java.lucia.net.packet.IncomingPacket;
import main.java.lucia.net.packet.impl.incoming.codec.login.IncomingLoginAttemptPacket;
import main.java.lucia.net.packet.impl.incoming.codec.login.IncomingNewAccountPacket;
import main.java.lucia.net.packet.impl.outgoing.codec.login.OutgoingLoginAttemptPacket;
import main.java.lucia.net.packet.impl.outgoing.codec.login.OutgoingLoginPacket;
import main.java.lucia.net.packet.impl.outgoing.codec.login.OutgoingNewAccountPacket;
import main.java.lucia.util.gson.RuntimeTypeAdapterFactory;

/**
 * The {@link Gson} Type Factory used by the client
 *
 * @author Brett Downey
 * @author Matt Kwiatkowski
 */
public class GsonTypeFactory {

  public static final Gson GENERIC_GSON = new Gson();

  /**
   * A pretty printing font {@link Gson}, used for
   * files which may need a better printing scheme
   */
  public static final Gson PRETTY_PRINT_GSON = new GsonBuilder().setPrettyPrinting().create();
  /**
   * The {@link Gson} used for incoming login attempt packets
   */
  public static final Gson LOGIN_GSON_INCOMING = loginGsonBuilderIncoming();
  /**
   * The {@link Gson} used for outgoing login attempt packets
   */
  public static final Gson LOGIN_GSON_OUTGOING = loginGsonBuilderOutgoing();
  /**
   * The {@link Gson} used for loading the menu items json file
   */
  public static final Gson MENU_ITEM_GSON = menuItemBuilder();

  public static final Gson EMPLOYEE_GSON = employeeBuilder();

  /**
   * The {@link Gson} used for loading/saving orders
   */
  public static final Gson ORDER_GSON = orderBuilder();

  public static final Gson BASIC_GSON = basicBuilder();

  public static final String CLIENT_TO_SERVER_LOGIN_ATTEMPT = "CLIENT_TO_SERVER_LOGIN_ATTEMPT";

  public static final String CLIENT_TO_SERVER_NEW_ACCOUNT_ATTEMPT = "CLIENT_TO_SERVER_NEW_ACCOUNT_ATTEMPT";

  public static final String SERVER_TO_CLIENT_LOGIN_ATTEMPT = "SERVER_TO_CLIENT_LOGIN_ATTEMPT";

  public static final String SERVER_TO_CLIENT_NEW_ACCOUNT_ATTEMPT = "SERVER_TO_CLIENT_NEW_ACCOUNT_ATTEMPT";

  /**
   * The builder for the {@link Gson} which is used for incoming login attempt packets
   *
   * @return The {@link Gson} which is used for incoming login attempt packets
   */
  private static Gson loginGsonBuilderIncoming() {
    RuntimeTypeAdapterFactory<IncomingPacket> typeAdapter = RuntimeTypeAdapterFactory
        .of(IncomingPacket.class)
        .registerSubtype(IncomingLoginAttemptPacket.class, SERVER_TO_CLIENT_LOGIN_ATTEMPT)
        .registerSubtype(IncomingNewAccountPacket.class, SERVER_TO_CLIENT_NEW_ACCOUNT_ATTEMPT);
    return new GsonBuilder().registerTypeAdapterFactory(typeAdapter).create();
  }

  /**
   * The builder for the {@link Gson} which is used for outgoing login attempt packets
   *
   * @return The {@link Gson} which is used for outgoing login attempt packets
   */
  private static Gson loginGsonBuilderOutgoing() {
    RuntimeTypeAdapterFactory<OutgoingLoginPacket> typeAdapter = RuntimeTypeAdapterFactory
        .of(OutgoingLoginPacket.class)
        .registerSubtype(OutgoingNewAccountPacket.class, CLIENT_TO_SERVER_NEW_ACCOUNT_ATTEMPT)
        .registerSubtype(OutgoingLoginAttemptPacket.class, CLIENT_TO_SERVER_LOGIN_ATTEMPT);
    return new GsonBuilder().registerTypeAdapterFactory(typeAdapter).create();
  }

  /**
   * The builder for the {@link Gson} which is used for loading the menu items
   *
   * @return The {@link Gson} which is used for loading the menu items
   */
  private static Gson menuItemBuilder() {
    GsonBuilder b = new GsonBuilder();
    addExclusionPolicy(b);
    b.setPrettyPrinting();
    b.serializeNulls();
    RuntimeTypeAdapterFactory<Descriptor> itemAdapter = RuntimeTypeAdapterFactory
            .of(Descriptor.class)
            .registerSubtype(AddonDescriptor.class)
            .registerSubtype(ItemModifiableDescriptor.class)
            .registerSubtype(SimpleItemDescriptor.class)
            .registerSubtype(SimplePizzaDescriptor.class)
            .registerSubtype(SpecialtyPizzaDescriptor.class);
    b.registerTypeAdapterFactory(itemAdapter);

    return b.create();
  }

  /**
   * The builder for the {@link Gson} which is used for loading orders
   *
   * @return The {@link Gson} which is used for loading orders
   */
  private static Gson orderBuilder(){
    GsonBuilder b = new GsonBuilder();

    addExclusionPolicy(b);
    b.setPrettyPrinting();
//    b.registerTypeAdapterFactory(typeAdapter);
    //b.registerTypeAdapterFactory(typeAdapter);
    TimeGson.addCustomJsonSerializers(b);
    b.registerTypeAdapter(Pizza.class, new PizzaSerializer());
    b.registerTypeAdapter(Pizza.class, new PizzaDeserializer(false));
    b.registerTypeAdapter(PremadePizza.class, PremadePizza.getJsonPremadeDeserializer());
    b.registerTypeAdapter(PremadePizza.class, PremadePizza.getJsonPremadeSerializer());
    b.serializeNulls();

    return b.create();
  }

  /**
   * The builder for the {@link Gson} which is used for loading employees
   *
   * @return The {@link Gson} which is used for loading employees
   */
  private static Gson employeeBuilder(){
    GsonBuilder b = new GsonBuilder();
    RuntimeTypeAdapterFactory<Employee> typeAdapter1 = RuntimeTypeAdapterFactory
            .of(Employee.class)
            .registerSubtype(Manager.class)
            .registerSubtype(Driver.class);
    RuntimeTypeAdapterFactory<EmployeeNote> typeAdapter2 = RuntimeTypeAdapterFactory
            .of(EmployeeNote.class)
            .registerSubtype(ManagerNote.class);
    addExclusionPolicy(b);
    b.setPrettyPrinting();
//    b.registerTypeAdapterFactory(typeAdapter);
    b.registerTypeAdapterFactory(typeAdapter1);
    b.registerTypeAdapterFactory(typeAdapter2);
    TimeGson.addCustomJsonSerializers(b);
    b.serializeNulls();

    return b.create();
  }

  /**
   * The builder for the {@link Gson} which is used for basic gson
   *
   * @return The {@link Gson} which is used for basic gson
   */
  private static Gson basicBuilder(){
    GsonBuilder b = new GsonBuilder();
    addExclusionPolicy(b);
    b.setPrettyPrinting();
    b.serializeNulls();
    TimeGson.addCustomJsonSerializers(b);

    return b.create();
  }

  public static void addExclusionPolicy(GsonBuilder b){
    b.setExclusionStrategies(new ExclusionStrategy() {
      @Override
      public boolean shouldSkipField(FieldAttributes f) {
        return f.getAnnotation(Exclude.class) != null;
      }

      @Override
      public boolean shouldSkipClass(Class<?> clazz) {
        return false;
      }
    });
  }



}