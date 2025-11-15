package org.example;
import org.example.utils.HibernateHelper;
import org.example.entities.CategoryEntity;
import org.hibernate.Session;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        // 1. CREATE (Створення) - Додаємо 4 записи
        System.out.println("--- 1. Створення записів (CREATE) ---");
        SimpleInsertFactory();

        // 2. READ (Читання) - Перевіряємо, чи всі записи вставлені
        System.out.println("--- 2. Читання всіх записів (READ ALL) ---");
        SimpleRead();

        // Припускаємо, що ID 1 існує після вставки
        final int ID_TO_UPDATE = 1;

        // 3. UPDATE (Оновлення) - Змінюємо запис з ID 1
        System.out.println("--- 3. Оновлення запису (UPDATE ID: " + ID_TO_UPDATE + ") ---");
        SimpleUpdate(ID_TO_UPDATE, "Оновлена Калабуджа");

        // Перевіряємо оновлення
        System.out.println("--- 4. Перевірка оновлення (READ ID: " + ID_TO_UPDATE + ") ---");
        SimpleReadById(ID_TO_UPDATE);

        // 4. DELETE (Видалення) - Видаляємо запис з ID 2
        final int ID_TO_DELETE = 2;
        System.out.println("--- 5. Видалення запису (DELETE ID: " + ID_TO_DELETE + ") ---");
        SimpleDelete(ID_TO_DELETE);

        // Перевіряємо остаточний список
        System.out.println("--- 6. Фінальний список записів ---");
        SimpleRead();

        // Закриваємо SessionFactory у кінці роботи
        HibernateHelper.getSessionFactory().close();
    }

    // =================================================================
    // C - CREATE (Створення)
    // =================================================================


    private static void SimpleInsertFactory() {
        var sessionFactory = HibernateHelper.getSessionFactory();
        sessionFactory.inTransaction(session -> {
            session.persist(new CategoryEntity("Кабачок"));
            session.persist(new CategoryEntity("Диня"));
        });
        System.out.println("--> Вставлено Кабачок та Диня (через Factory).");
    }

    // =================================================================
    // R - READ (Читання)
    // =================================================================

    private static void SimpleRead() {
        Session session = HibernateHelper.getSession();
        try {
            // HQL-запит для вибору всіх сутностей
            List<CategoryEntity> result = session.createSelectionQuery("from CategoryEntity", CategoryEntity.class)
                    .getResultList();

            if (result.isEmpty()) {
                System.out.println("Таблиця категорій порожня.");
                return;
            }

            System.out.println("Знайдено записів: " + result.size());
            result.forEach(System.out::println);
        } catch (Exception e) {
            System.err.println("Помилка в SimpleRead: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    private static void SimpleReadById(int id) {
        Session session = HibernateHelper.getSession();
        try {
            // Метод find() знаходить сутність за її первинним ключем
            CategoryEntity category = session.find(CategoryEntity.class, id);

            if (category != null) {
                System.out.println("Знайдено категорію за ID " + id + ": " + category);
            } else {
                System.out.println("Категорію з ID " + id + " не знайдено.");
            }
        } catch (Exception e) {
            System.err.println("Помилка в SimpleReadById: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    // =================================================================
    // U - UPDATE (Оновлення)
    // =================================================================

    private static void SimpleUpdate(int id, String newName) {
        var sessionFactory = HibernateHelper.getSessionFactory();

        // Використовуємо inTransaction для автоматичного керування транзакцією
        sessionFactory.inTransaction(session -> {
            CategoryEntity category = session.find(CategoryEntity.class, id);

            if (category != null) {
                String oldName = category.getName();
                category.setName(newName); // Змінюємо поле
                // Hibernate автоматично відстежує зміни, session.persist/merge тут не потрібен
                System.out.printf("--> Оновлено ID %d: '%s' -> '%s'%n", id, oldName, newName);
            } else {
                System.out.println("--> Помилка: Категорію з ID " + id + " для оновлення не знайдено.");
            }
        });
    }

    // =================================================================
    // D - DELETE (Видалення)
    // =================================================================

    private static void SimpleDelete(int id) {
        var sessionFactory = HibernateHelper.getSessionFactory();

        sessionFactory.inTransaction(session -> {
            CategoryEntity category = session.find(CategoryEntity.class, id);

            if (category != null) {
                session.remove(category); // Видалення сутності
                System.out.println("--> Успішно видалено категорію: " + category);
            } else {
                System.out.println("--> Помилка: Категорію з ID " + id + " для видалення не знайдено.");
            }
        });
    }


}