package ru.job4j.todo.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Item;

import java.util.List;

@Repository
public class ItemStore {

    private final SessionFactory sf;

    public ItemStore(SessionFactory sf) {
        this.sf = sf;
    }

    public Item addItem(Item item) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(item);
        session.getTransaction().commit();
        session.close();
        return item;
    }

    public List<Item> getAllItems() {
        Session session = sf.openSession();
        session.beginTransaction();
        List result = session.createQuery("from ru.job4j.todo.model.Item").list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    public Item findItemById(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        System.out.println(id);
        Item item = session.get(Item.class, id);
        session.getTransaction().commit();
        session.close();
        return item;
    }

    public List<Item> getItemsWhereDoneIs(boolean done) {
        Session session = sf.openSession();
        session.beginTransaction();
        List result = session.createQuery("from ru.job4j.todo.model.Item where done = :done")
                .setParameter("done", done)
                .list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    public boolean doneItemById(int itemId) {
        Session session = sf.openSession();
        session.beginTransaction();
        Item item = session.get(Item.class, itemId);
        item.setDone(true);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    public boolean deleteItemById(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.createQuery("DELETE ru.job4j.todo.model.Item where id = :id")
                .setParameter("id", id)
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
        return true;
    }

    public Item updateItem(Item item) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.update(item);
        session.getTransaction().commit();
        session.close();
        return item;
    }
}