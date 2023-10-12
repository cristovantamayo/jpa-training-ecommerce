package dev.cristovantamayo.ecommerce.jpql;

import dev.cristovantamayo.ecommerce.EntityManagerTest;
import dev.cristovantamayo.ecommerce.model.Client;
import dev.cristovantamayo.ecommerce.model.Product;
import dev.cristovantamayo.ecommerce.model.Purchase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class BasicJPQJTest extends EntityManagerTest {

    @Test
    public void findById() {
        /** Java Persistence Query Language - JPL.
         * JPQL - select p from purchase p join p.purchaseItems pi where pi.product_price > 10
         * SQL - select p.* from purchase p join purchase_item pi ON pi.id_product = p.id where pi.product_price > 10
         */
        /** entityManager.find(Purchase.class, 1)*/

        /** SQL almost equivalence: select p.* from purchase p where p.id = 1 */
        TypedQuery<Purchase> typedQuery = entityManager.createQuery("select p from Purchase p where p.id = 1", Purchase.class);
        /** Need to guarantee unique result */
        Purchase purchase = typedQuery.getSingleResult();

        /** working with all quantity results */
        List<Purchase> purchases = typedQuery.getResultList();

        Assertions.assertNotNull(purchase);
        Assertions.assertFalse(purchases.isEmpty());
    }

    @Test
    public void showDifferenceBetweenQueries() {

        final String jpql = "select p from Purchase p where p.id = 1";

        TypedQuery<Purchase> typedQuery = entityManager.createQuery(jpql, Purchase.class);
        Purchase purchase1 = typedQuery.getSingleResult();
        Assertions.assertNotNull(purchase1);

        Query query = entityManager.createQuery(jpql);
        Purchase purchase2 = (Purchase) query.getSingleResult();
        Assertions.assertNotNull(purchase2);
        List<Purchase> purchases = query.getResultList();
        Assertions.assertFalse(purchases.isEmpty());
    }

    @Test
    public void selectAttributeAsResult() {

        final String jpql = "select p.name from Product p";

        TypedQuery<String> typedQuery = entityManager.createQuery(jpql, String.class);
        List<String> purchasesNames = typedQuery.getResultList();
        Assertions.assertTrue(String.class.equals(purchasesNames.get(0).getClass()));

        final String jpqlClient = "select p.client from Purchase p";
        TypedQuery<Client> typedQueryClient = entityManager.createQuery(jpqlClient, Client.class);
        List<Client> clients = typedQueryClient.getResultList();
        Assertions.assertTrue(Client.class.equals(clients.get(0).getClass()));
    }
}
