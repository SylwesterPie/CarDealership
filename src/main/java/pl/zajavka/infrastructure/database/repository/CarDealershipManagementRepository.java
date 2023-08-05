package pl.zajavka.infrastructure.database.repository;

import org.springframework.stereotype.Repository;
import pl.zajavka.business.dao.managment.CarDealershipManagementDAO;

import java.util.List;

@Repository
public class CarDealershipManagementRepository implements CarDealershipManagementDAO {
    @Override
    public void purge() {

            session.createMutationQuery("DELETE FROM ServiceMechanicEntity ent").executeUpdate();
            session.createMutationQuery("DELETE FROM ServicePartEntity ent").executeUpdate();
            session.createMutationQuery("DELETE FROM CarServiceRequestEntity ent").executeUpdate();
            session.createMutationQuery("DELETE FROM InvoiceEntity ent").executeUpdate();
            session.createMutationQuery("DELETE FROM MechanicEntity ent").executeUpdate();
            session.createMutationQuery("DELETE FROM PartEntity ent").executeUpdate();
            session.createMutationQuery("DELETE FROM ServiceEntity ent").executeUpdate();
            session.createMutationQuery("DELETE FROM CarToServiceEntity ent").executeUpdate();
            session.createMutationQuery("DELETE FROM CarToBuyEntity ent").executeUpdate();
            session.createMutationQuery("DELETE FROM CustomerEntity ent").executeUpdate();
            session.createMutationQuery("DELETE FROM AddressEntity ent").executeUpdate();
            session.createMutationQuery("DELETE FROM SalesmanEntity ent").executeUpdate();
 
    }

    @Override
    public void savaAll(List<?> entities) {

            for (var entity : entities) {
                session.persist(entity);
            }
 
    }
}