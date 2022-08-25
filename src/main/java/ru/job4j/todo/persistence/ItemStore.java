package ru.job4j.todo.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.Store;
import ru.job4j.todo.entity.Item;

import java.util.List;

@Repository
public class ItemStore implements Store<Item> {

    private final SessionFactory sf;

    public ItemStore(SessionFactory sf) {
        this.sf = sf;
    }

    @Override
    public Item create(Item item) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(item);
        session.getTransaction().commit();
        session.close();
        return item;
    }

    @Override
    public List<Item> findAll() {
        Session session = sf.openSession();
        session.beginTransaction();
        List<Item> rsl = session.createQuery(
                "from Item", Item.class
        ).list();
        session.getTransaction().commit();
        session.close();
        return rsl;
    }

    public List<Item> findCompleted() {
        Session session = sf.openSession();
        session.beginTransaction();
        List<Item> rsl = session.createQuery(
                "from Item where done = true", Item.class
        ).list();
        session.getTransaction();
        session.close();
        return rsl;
    }

    public List<Item> findNew() {
        List<Item> all = findAll();
        List<Item> allCompleted = findCompleted();
        all.removeAll(allCompleted);
        return all;
    }

    @Override
    public boolean update(int id, Item item) {
        Session session = sf.openSession();
        session.beginTransaction();
        boolean rsl = session.createQuery(
                        "update Item set name = :fName, description = :fDesc,"
                                + " done = :fDone where id = :fId"
                ).setParameter("fName", item.getName())
                .setParameter("fDesc", item.getDescription())
                .setParameter("fDone", item.isDone())
                .setParameter("fId", id)
                .executeUpdate() > 0;
        session.getTransaction().commit();
        session.close();
        return rsl;
    }

    @Override
    public boolean delete(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        boolean rsl = session.createQuery(
                        "delete from Item where id = :fId"
                ).setParameter("fId", id)
                .executeUpdate() > 0;
        session.getTransaction().commit();
        session.close();
        return rsl;
    }

    @Override
    public Item findById(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        Item rsl = session.createQuery(
                        "from Item where id = :fId", Item.class
                ).setParameter("fId", id)
                .uniqueResult();
        session.getTransaction().commit();
        session.close();
        return rsl;
    }

    @Override
    public void close() {
        sf.close();
    }
}
