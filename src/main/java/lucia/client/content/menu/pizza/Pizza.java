package main.java.lucia.client.content.menu.pizza;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import main.java.lucia.client.content.menu.io.serializer.server.PizzaSerializer;
import main.java.lucia.client.content.menu.item.AbstractItem;
import main.java.lucia.client.content.menu.Menu;
import main.java.lucia.client.content.menu.item.IDAble;
import main.java.lucia.client.content.menu.item.IDCaster;
import main.java.lucia.client.content.menu.item.Item;
import main.java.lucia.client.content.menu.item.descriptor.Descriptor;
import main.java.lucia.client.content.menu.item.descriptor.SpecialtyPizzaDescriptor;
import main.java.lucia.client.content.menu.item.descriptor.SimpleItemDescriptor;
import main.java.lucia.client.content.menu.size.Size;
import main.java.lucia.net.packet.impl.GsonTypeFactory;

import java.lang.reflect.Type;
import java.util.*;

/**
 * Object created by matt to represent a pizza in the new
 * item format
 * @author Matt Kwiatkowski
 */
public class Pizza extends Item{

    /**
     * The specific id of this pizza on the server.
     * DO NOT SET. Let Gson do that.
     */
    private int rowNum = -1;

    /**
     * A list of all special instructions with this Pizza (thin, ls, etc).
     */
    private List<String> specialInstructions;

    /**
     * The toppings applied onto this pizza.
     */
    private List<Topping> toppings;

    /**
     * The size of this pizza
     */
    private Integer size;

    /**
     * Flag which indicates whether this pizza is a specialty pizza or not.
     */
    private boolean specialty = false;

    /**
     * The sauce on this pizza
     */
    private Sauce sauce;

    /**
     * The crust of this pizza
     */
    private Crust crust;

    /**
     * Flag which indicates whether or not this pizza is a part of a split halves pizza
     * AND that this is the FIRST pizza of the split pizza
     */
    private boolean splitHalves;

    /**
     * If this pizza is the first half, this object is the second half.
     */
    private Pizza secondHalf;

    /**
     * Construct a new specialty premade pizza with the provided toppings
     * @param size the Size of this pizza
     * @param is the
     */
    public Pizza(Integer size, SpecialtyPizzaDescriptor is) {
        super(is.getBaseName(), is.getPricingScheme().getPrice(size), is);
        this.toppings = new ArrayList<>(/*is.getToppings()*/);
//        for(Topping t: toppings){
//            this.toppings.add(t.deepCopy());
//        }
        this.specialty = true;
        this.crust = is.getCrust();
        this.sauce = is.getSauce();
        this.specialInstructions = new ArrayList<>(is.getSpecialInstructions());
        this.size = size;
    }

    /**
     * Constructs a new cheese pizza of the given size
     * @param size the size of the pizza.
     */
    public Pizza(Integer size) {
        super("", Menu.pizza.getBasePizza().getPrice(size),
                Menu.pizza.getBasePizza());
        this.toppings = new ArrayList<>();
        this.specialty = false;
        this.sauce = Menu.pizza.getRegularSauce();
        this.crust = Menu.pizza.getRegularCrust();
        this.specialInstructions = new ArrayList<>();
        this.sauce = Menu.pizza.getRegularSauce();
        this.size = size;
    }

    /**
     * The full constructor. EVERY parameter available.
     * Use with caution,
     */
    public Pizza(String displayName, String name, long price, long discountedPrice,
                 Descriptor itemDescriptor, int rowNum, List<String> specialInstructions,
                 List<Topping> toppings, Integer size, boolean specialty, Sauce sauce, Crust crust,
                 boolean splitHalves, Pizza secondHalf) {
        super(displayName, name, price, discountedPrice, itemDescriptor);
        this.rowNum = rowNum;
        this.specialInstructions = specialInstructions;
        this.toppings = toppings;
        this.size = size;
        this.specialty = specialty;
        this.sauce = sauce;
        this.crust = crust;
        this.splitHalves = splitHalves;
        this.secondHalf = secondHalf;
    }

    /**
     * Adds a topping to the pizza, or changes the amount of a currently added topping
     * @param t the descriptor of the topping that you would like to add/change
     * @param amt the amount of the topping (1,2, or 3)
     */
    public void addTopping(ToppingType t, int amt){
        //first check if the topping is already there
        for(Topping top: toppings){
            if(top.getType() == t){
                //topping is there
                //change the amt and return
                top.setAmount(amt);
                return;
            }
        }
        //topping not there. Add it normally
        toppings.add(new Topping(t, amt));
    }

    /**
     * Removes the topping from the pizza, regardless of size.
     * Sets the topping to a negation if this pizza is a specialty pizza
     * and the removed topping is part of that specialty.
     * @param t the topping to negate/remove.
     */
    public void removeTopping(ToppingType t){
        Topping rem = null;
        for(Topping top: toppings){
            if(top.getType() == t){
                if(this.isSpecialty()){
                    for(ToppingType top2: this.getPizzaDescriptor().getToppings().keySet()){
                        if(top.getType() == top2){
                            //set to negation and return;
                            top.setAmount(0);
                            return;
                        }
                    }
                }
                //just remove
                rem = top;
                break;
            }
        }
        if(rem != null){
            toppings.remove(rem);
        }
    }

    /**
     * Enables the pizza to have a second half
     */
    public void enableSecondHalf(){
        this.splitHalves = true;
        secondHalf = new Pizza(this.getSize());
    }

    /**
     * Disables this pizza having a second half
     */
    public void disableSecondHalf(){
        this.splitHalves = false;
        this.secondHalf = null;
    }

    /**
     *
     * @return true if the pizza is a specialty/gourmet pizza, false otherwise
     */
    public boolean isSpecialty() {
        return specialty;
    }

    /**
     * Sets this pizza to the provided specialty. Clears all toppings.
     * @param special the special to set this pizza to.
     */
    public void setSpecialty(SpecialtyPizzaDescriptor special){
        this.toppings.clear();

    }

    public Pizza getSecondHalf(){
        return this.secondHalf;
    }

    /**
     * Get the size of the pizza
     */
    public Integer getSize() {
        return size;
    }

    /**
     * Add a special instruction to the pizza
     * @param s the special instruction to be added
     */
    public void addSpecialInstruction(String s){
        specialInstructions.add(s.toLowerCase());
    }

    /**
     * remove a special instruction to the pizza
     * @param s the special instruction to be removed
     */
    public void removeSpecialInstruction(String s){
        specialInstructions.remove(s.toLowerCase());
    }

    /**
     * check for a special instruction on the pizza
     * @param s the special instruction to be checked
     * @return true if the special instruction is added, false otherwiese
     */
    public boolean hasSpecialInstruction(String s){
       return specialInstructions.contains(s.toLowerCase());
    }

    /**
     * get the special instructions on the pizza
     * @return a list that contains all of the special instructions on the pizza
     */
    public List<String> getSpecialInstructions(){
        return specialInstructions;
    }


    /**
     * Copies the item and its' contents
     */
    @Override
    public AbstractItem deepCopy() {
        return null;
    }

    public Sauce getSauce() {
        return sauce;
    }

    public void setSauce(Sauce sauce) {
        this.sauce = sauce;
    }

    public Crust getCrust() {
        return crust;
    }

    public void setCrust(Crust crust) {
        this.crust = crust;
    }

    /**
     * Get the speciality descriptor if there is one,
     * null if there isnt one
     */
    public SpecialtyPizzaDescriptor getPizzaDescriptor(){
        if(this.isSpecialty()){
            return (SpecialtyPizzaDescriptor)this.getItemDescriptor();
        }else{
            return null;
        }
    }

    /**
     * Generate the name of this item without the special instructions
     * @return the name of this item without special instructions.
     */
    private String generateNameNoInstructions(){
        //add the sauce (if there is one)
        String result = "";
        if(!sauce.getDisplayName().equals("RS") ||
                (isSpecialty() && ((SpecialtyPizzaDescriptor)getItemDescriptor()).getSauce() != this.getSauce())){
            result+="("+sauce.getDisplayName()+")";
        }
        //finally, the toppings
        if(!toppings.isEmpty()){
            StringJoiner join = new StringJoiner(" ", this.getItemDescriptor().getBaseName(), "");
            for(Topping t: toppings){
                join.add(t.generateShortName());
            }
            result += join.toString();
        }else{
            result += "Chz Pizza";
        }
        return result;
    }

    /**
     * Generates the display name of the item
     */
    @Override
    public String generateDisplayName() {
        String result = "";
        //add the crust name
        if(specialInstructions.contains("thin")){
            result+= "(Thin" + crust.getDisplayName() + ")";
        }else if(!crust.getDisplayName().equals("")){
            result+="(" + crust.getDisplayName() + ")";
        }
        String addlater = "";
        //now add all of the other special instructions (other than sauce & crust instructions)
        for(String t: specialInstructions){
            if(!t.contains("thin")){
                if(!t.endsWith("s")){
                    result += "(" + t + ")";
                }else{
                    addlater+="(" + t + ")";
                }

            }
        }
        result+= addlater;
        result+= generateNameNoInstructions();
        //Now worry about split halves
        if(this.splitHalves){
            result += " *** ";
            result += this.getSecondHalf().generateNameNoInstructions();
        }
        return result;
    }

    /**
     * Calculates the normal price of the item (excluding any discounts)
     */
    @Override
    public long calculatePrice() {
        //get the base price
        long result = (isSpecialty()) ?
                ((SpecialtyPizzaDescriptor)super.getItemDescriptor()).getPrice(this.getSize()) :
                ((SimpleItemDescriptor)super.getItemDescriptor()).getBasePrice();
        //add the price of the toppings
        result += getToppingPrice();
        //add the cost for sauce and crust
        if(isSpecialty()){
            //if the sauce/crust has changed, add the price difference
            if(this.getCrust() != getPizzaDescriptor().getCrust()){
                long myCrustPrice = this.getCrust().getPricingScheme().getPrice(this.getSize());
                long oldCrustPrice = getPizzaDescriptor().getCrust().getPricingScheme().getPrice(this.getSize());
                result+=(myCrustPrice-oldCrustPrice);
            }
            if(this.getSauce() != getPizzaDescriptor().getSauce()){
                long mySaucePrice = this.getSauce().getPricingScheme().getPrice(this.getSize());
                long oldSaucePrice = getPizzaDescriptor().getSauce().getPricingScheme().getPrice(this.getSize());
                result+=(mySaucePrice-oldSaucePrice);
            }
        }else{
            //not an included crust, just add the price
            result+=this.getCrust().getPricingScheme().getPrice(this.getSize());
            result+=this.getSauce().getPricingScheme().getPrice(this.getSize());
        }
        //If this is a split pizza, charge the MAXIMUM of the two halves.
        if(splitHalves && secondHalf != null){
            result = Math.max(result, secondHalf.calculatePrice());
        }
        return result;
    }


    /**
     * Adds the properties of this Pizza object
     *  to the given JsonObject,
     *  meant to be used for serialization
     * @param ret the JsonObject for which the properties will be added to.
     */
    @Override
    public void addJsonProperties(JsonObject ret){
        super.addJsonProperties(ret);
        ret.addProperty("rowNum", rowNum);
        ret.addProperty("size", getSize());
        ret.addProperty("isSpecialty", specialty);
        ret.addProperty("specialty", getIdSafe(getPizzaDescriptor()));
        ret.addProperty("sauce", getIdSafe(sauce));
        ret.addProperty("crust", getIdSafe(crust));
        ret.addProperty("splitHalves", splitHalves);
        ret.add("specialInstructions", GsonTypeFactory.BASIC_GSON.toJsonTree(getSpecialInstructions()));
        ret.add("secondHalf", new GsonBuilder().registerTypeAdapter(
                Pizza.class,
                new PizzaSerializer()).create().toJsonTree(getSecondHalf()));
        addToppingJsonProperty(ret);
    }

    /**
     * Serializes the set of toppings on this pizza,
     * and inputs the topping property into the given jsonobject
     * @param o the JsonObject to modify.
     */
    private void addToppingJsonProperty(JsonObject o){
        /*Serialize Topping List */
        Map<String, Integer> toppings = new HashMap<>();
        for(Topping t: this.toppings){
            toppings.put(t.getType().getId() + "", t.getAmount());
        }
        /* this jsonobject will be the "topping map" json object */
        JsonObject j = new JsonObject();
        /* add it to ret */
        toppings.forEach(j::addProperty);
        o.add("toppings", j);
    }

    /**
     * Safely gets the id of an IDable, without the
     * risk of an NPE
     * @param d the idable t get the id of
     * @return the id of the idable, or 0 if it is null
     */
    private int getIdSafe(IDAble d){
        return (d==null) ? 0 : d.getId();
    }

//    /**
//     * Compares a pizza to the other.
//     * Larger sizes are considered 'infront' of smaller sizes.
//     */
//    @Override
//    public int compareTo(Pizza other) {
//        return this.size.compareTo(other.size);
//    }

    public int getRowNum() {
        return rowNum;
    }

    /**
     * Get the list of toppings applied
     * @return the list of applied toppings
     */
    public List<Topping> getToppings() {
        return toppings;
    }

    /**
     * Calculates the price of all the toppings combined.
     * For specialty pizzas, this is the specialty price minus base pizza price
     * @return the price for the toppings
     */
    public long getToppingPrice(){
        //get the base price
        long basePrice;
        long result;
        if(isSpecialty()){
            basePrice = getPizzaDescriptor().getPrice(getSize());
            //add the price of the toppings
            result = basePrice;
            for(Topping t: toppings){
                if(getPizzaDescriptor().getToppings().containsKey(t.getType())){
                    int amt = getPizzaDescriptor().getToppings().get(t.getType());
                    if(amt != t.getAmount()){
                        long topprice = t.getType().getPricingScheme().getPrice(getSize());
                        result += (t.getAmount()-amt)*topprice;
                    }
                }else{
                    result+=t.calculatePrice(this.size);
                }
                //make sure removing toppings from specialty hasn't decreased the price of the specialty
                result = Math.max(result, basePrice);
            }
        }else{
            //add the price of the toppings
            basePrice = Menu.pizza.getBasePizza().getPrice(getSize());
            result = basePrice;
            for(Topping t: toppings){
                //no negations in a b-y-o
                assert(t.getAmount() > 0);
                result+=t.calculatePrice(this.size);
            }
        }
        result -= basePrice;
        return result;
    }

    /**
     * Gets a map of ALL toppings on a pizza including those from the specialty
     * (if applicable) (doesn't include negations)
     */
    public Map<ToppingType, Integer> getAllToppingsOnPizza(){
        Map<ToppingType, Integer> tp = new HashMap<>();
        //add all the specialty toppings
        if(isSpecialty()){
            for(Map.Entry<ToppingType, Integer> topping: getPizzaDescriptor().getToppings().entrySet()){
                tp.put(topping.getKey(), topping.getValue());
            }
        }
        //add the other toppings; handle negations
        for(Topping t: this.toppings){
            if(t.getAmount() == 0){
                tp.remove(t.getType());
            }else {
                tp.put(t.getType(), t.getAmount());
            }
        }
        return tp;
    }



}
