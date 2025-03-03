package com.swiftbank.dal;

import com.swiftbank.model.Client;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

public class ClientDAO implements IClientDAO, AutoCloseable { // Implémente AutoCloseable
    private final EntityManagerFactory emf;
    private final EntityManager em;

    public ClientDAO() {
        emf = Persistence.createEntityManagerFactory("SwiftBankPU");
        em = emf.createEntityManager();
    }

    @Override
    public void addClient(Client client) {
        em.getTransaction().begin();
        em.persist(client);
        em.getTransaction().commit();
    }

    @Override
    public Client getClientById(int id) {
        return em.find(Client.class, id);
    }

    @Override
    public List<Client> getAllClients() {
        return em.createQuery("SELECT c FROM Client c", Client.class).getResultList();
    }

    @Override
    public void updateClient(Client client) {
        em.getTransaction().begin();
        em.merge(client);
        em.getTransaction().commit();
    }

    @Override
    public void deleteClient(int id) {
        Client client = em.find(Client.class, id);
        if (client != null) {
            em.getTransaction().begin();
            em.remove(client);
            em.getTransaction().commit();
        }
    }

    @Override
    public void close() { // Implémentation de la méthode close()
        if (em != null) {
            em.close();
        }
        if (emf != null) {
            emf.close();
        }
    }
}