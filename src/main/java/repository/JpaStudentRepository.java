package repository;

import jakarta.persistence.*;
import model.denormalised.Student;

import java.util.List;

public class JpaStudentRepository implements StudentRepository<Student> {
    private final EntityManagerFactory emf;

    public JpaStudentRepository() {
        // The name "SchoolDBPersistenceUnit" must match the one in persistence.xml
        this.emf = Persistence.createEntityManagerFactory("SchoolDBPersistenceUnit");
    }

    @Override
    public void create(Student student) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = em.getTransaction();
            transaction.begin();
            em.persist(student); // Persisting student will also persist its address due to CascadeType.ALL

        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace(); // Log the exception
        } finally {
            em.close();
        }
    }

    @Override
    public List<Student> getAll() {
        EntityManager em = emf.createEntityManager();
        // Here, "FROM Student" correctly queries the 'students_denormalised' table
        // and Hibernate knows how to map rows back to their specific subtypes
        // due to the discriminator column.
        // FetchType.LAZY means address is not loaded by default.
        // To force eager loading here, you could use a FETCH JOIN:
        // TypedQuery<Student> query = em.createQuery("SELECT s FROM Student s JOIN FETCH s.address", Student.class);
        TypedQuery<Student> query = em.createQuery("FROM Student", Student.class);
        List<Student> students = query.getResultList();
        em.close();
        return students;
    }

    @Override
    public void update(Student student) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = em.getTransaction();
            transaction.begin();
            // merge handles both new and existing entities.
            // If the student is detached, merge reattaches it.
            // If address changed, merge will update it due to CascadeType.ALL.
            em.merge(student);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(String emailAddress) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = em.getTransaction();
            transaction.begin();
            // Find the student by email first
            TypedQuery<Student> query = em.createQuery("SELECT s FROM Student s" +
                                                          " WHERE s.emailAddress = :email",
                                                          Student.class);
            query.setParameter("email", emailAddress);

            Student student = null;
            try {
                student = query.getSingleResult();
            } catch (NoResultException e) {
                System.out.println("Student with email "
                        + emailAddress + " not found for deletion.");
            }
            if (student != null) {
                // If the student is managed, remove(student) is enough.
                // If it's detached, you need to merge it first: em.remove(em.merge(student));
                // In this case, 'student' is managed as it came directly from 'query.getSingleResult()'
                em.remove(student); // CascadeType.ALL on @OneToOne will also remove the associated Address
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    // A helper method to close the factory
    // when the application exits
    public void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
