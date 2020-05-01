package main.java.lucia.client.content.menu.pizza;

import main.java.lucia.client.content.menu.item.descriptor.Descriptor;
import main.java.lucia.client.content.menu.item.descriptor.SimplePizzaDescriptor;

/**
 * A 'descriptor' that really describes a pizza with a size
 * @author Matthew Kwiatkowski
 */
public class PizzaInfo extends Descriptor {

    /**
     * The 'size' of this pizza
     */
    private int size;

    /**
     * The actual descriptor of this pizza
     */
    private SimplePizzaDescriptor descriptor;

    public PizzaInfo(int size, SimplePizzaDescriptor descriptor) {
        //id is not part of this.
        super(-1, descriptor.getBaseName());
        this.size = size;
    }

    /**
     * The 'size' of this pizza
     */
    public int getSize() {
        return size;
    }

    /**
     * The actual descriptor of this pizza
     */
    public SimplePizzaDescriptor getDescriptor() {
        return descriptor;
    }

    @Override
    public boolean equals(Object o){
        if(o == this){
            return true;
        }
        if(!(o instanceof PizzaInfo)){
            return false;
        }
        PizzaInfo pizza = (PizzaInfo)o;
        return pizza.size == size && pizza.descriptor.getId() == this.descriptor.getId();
    }

    /**
     * Checks whether two pizzaInfos are similar (i.e same size)
     */
    public boolean isSimilar(Object o){
        if(o == this){
            return true;
        }
        if(!(o instanceof PizzaInfo)){
            return false;
        }
        PizzaInfo pizza = (PizzaInfo)o;
        return pizza.size == size;
    }
}
