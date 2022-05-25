package ru.job4j.todo.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.model.User;

import java.util.List;
import java.util.function.Function;

@Repository
public class ItemStore {

    private final SessionFactory sf;

    public ItemStore(SessionFactory sf) {
        this.sf = sf;
    }

    private <T> T tx(final Function<Session, T> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public Item addItem(Item item) {
        return this.tx(
                session -> {
                    session.save(item);
                    return item;
                }
        );
    }

    public List<Item> getAllItemsOfUser(User user) {
        return this.tx(
                session -> session.createQuery("SELECT DISTINCT i FROM ru.job4j.todo.model.Item i"
                                + " JOIN FETCH i.categories where user_id = :userId ORDER BY i.id")
                        .setParameter("userId", user.getId())
                        .list()
        );
    }

    public Item findItemById(int id) {
        return this.tx(
                session -> session.get(Item.class, id)
        );
    }

    public List<Item> getItemsWhereDoneIs(boolean done, User user) {
        return this.tx(
                session -> session.createQuery("from ru.job4j.todo.model.Item "
                                + "where done = :done AND user_id =:userId ORDER BY id")
                        .setParameter("done", done)
                        .setParameter("userId", user.getId())
                        .list()
        );
    }

    public boolean doneItemById(int itemId) {
        return this.tx(
                session -> {
                    Item item = session.get(Item.class, itemId);
                    item.setDone(true);
                    return item.isDone();
                }
        );
    }

    public boolean deleteItemById(int id) {
        return this.tx(
                session -> {
                    session.createQuery("DELETE ru.job4j.todo.model.Item where id = :id")
                            .setParameter("id", id)
                            .executeUpdate();
                    return true;
                }
        );
    }

    public Item updateItem(Item item) {
        return this.tx(
                session -> {
                    session.update(item);
                    return item;
                }
        );
    }

}