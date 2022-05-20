package ru.job4j.todo.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

@Repository
public class CategoryStore {

    private final SessionFactory sf;

    public CategoryStore(SessionFactory sf) {
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

    public Optional<Set<Category>> add(Set<Category> categorySet) {
        return Optional.ofNullable(
                this.tx(
                        session -> {
                            for (Category category : categorySet) {
                                session.save(category);
                            }
                            return categorySet;
                        })
        );
    }

    public List<Category> getAllCategories() {
        return this.tx(
                session -> session.createQuery("from Category ORDER BY id")
                        .list()
        );
    }

    public Category findCategoryById(int id) {
        return this.tx(
                session -> session.get(Category.class, id)
        );
    }

    public Set<Category> findCategories(Set<Integer> ids) {
        Set<Category> result = new HashSet<>();
        for (Integer id : ids) {
            result.add(findCategoryById(id));
        }
        return result;
    }
}
