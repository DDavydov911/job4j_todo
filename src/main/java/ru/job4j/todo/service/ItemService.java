package ru.job4j.todo.service;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.model.User;
import ru.job4j.todo.store.ItemStore;

import java.util.List;

@Service
public class ItemService {

    private final ItemStore itemStore;

    public ItemService(ItemStore itemStore) {
        this.itemStore = itemStore;
    }

    public Item addItem(Item item) {
        return itemStore.addItem(item);
    }

    public List<Item> getAllItems(User user) {
        return itemStore.getAllItems(user);
    }

    public Item findItemById(int id) {
        return itemStore.findItemById(id);
    }

    public List<Item> getAllNewItems(User user) {
        return itemStore.getItemsWhereDoneIs(false, user);
    }

    public List<Item> getAllDoneItems(User user) {
        return itemStore.getItemsWhereDoneIs(true, user);
    }

    public boolean doneItemById(int itemId) {
        return itemStore.doneItemById(itemId);
    }

    public boolean deleteItemById(int itemId) {
        return itemStore.deleteItemById(itemId);
    }

    public Item update(Item item) {
        return itemStore.updateItem(item);
    }
}
