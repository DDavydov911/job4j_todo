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
        item = (Item) session.merge(item);
        session.getTransaction().commit();
        session.close();
        System.out.println("store: " + item);
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
        System.out.println(item);
        /* Почему запрос - Item item = session.get(Item.class, id) - не работает?
          Сообщение:
          class ru.job4j.todo.model.Item cannot be cast to class ru.job4j.todo.model.Item
          тоже самое происходит при запросе ниже
          Item item = (Item) session.createQuery("FROM ru.job4j.todo.model.Item WHERE id = : id")
                       .setParameter("id", id).list().get(0);

         */
        System.out.println("store:" + item.toString());
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
        session.createQuery("update ru.job4j.todo.model.Item set done = true where id = :id")
                .setParameter("id", itemId);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    public boolean deleteItemById(int itemId) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.createQuery("delete ru.job4j.todo.model.Item where id = :id")
                .setParameter("id", itemId);
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