package model;

import java.util.List;

/**
 * Containable interface describes the ability to contain a series of objects of type T.
 * All portfolio classes must implement this interface
 *
 * @param <T> type of object contained in the portfolio
 */
public interface Containable<T> {
    // REQUIRES: item != null
    // MODIFIES: this
    // EFFECTS: add item to the container
    void add(T item);

    // MODIFIES: this
    // EFFECTS: remove the item in the container with the given id and returns whether the item
    //          is successfully removed. If the item is not present in the container, then return false.
    boolean delete(String id);

    // EFFECTS: returns the item with the given id in the container
    T search(String id);

    // EFFECTS: returns the list of items contained in the container
    List<T> getList();

    // EFFECTS: returns the number of items in the list
    int getNum();

    // EFFECTS: returns the name of the container
    String getName();

    // MODIFIES: this
    // EFFECTS: sets the name of the container to the given name
    void setName(String name);

    // EFFECTS: returns the item in the container which has the id starting with the prefix
    T searchByIdPrefix(String prefix);

    // EFFECTS: returns a list of display strings of items inside the container
    List<String> getDisplayString();

    // REQUIRES: item contains a unique id for indexing the item in the container
    // EFFECTS: modify the given item in the list
    // NOTE: if the provided id is not in the container, ignore it.
    void modify(T item);
}
