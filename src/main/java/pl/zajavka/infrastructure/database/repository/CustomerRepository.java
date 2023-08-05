package pl.zajavka.infrastructure.database.repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.ParameterExpression;
import jakarta.persistence.criteria.Root;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import pl.zajavka.business.dao.CustomerDAO;
import pl.zajavka.infrastructure.database.entity.CustomerEntity;

import java.util.Objects;
import java.util.Optional;

@Repository
public class CustomerRepository implements CustomerDAO {
    @Override
    public Optional<CustomerEntity> findCustomerByEmail(String email) {
         
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<CustomerEntity> criteriaQuery = criteriaBuilder.createQuery(CustomerEntity.class);
            Root<CustomerEntity> root = criteriaQuery.from(CustomerEntity.class);
            ParameterExpression<String> parameter = criteriaBuilder.parameter(String.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("email"), parameter));

            Query<CustomerEntity> query = session.createQuery(criteriaQuery);
            query.setParameter(parameter, email);
            try {
                CustomerEntity result = query.getSingleResult();
                transaction.commit();
                return Optional.of(result);
            } catch (Throwable ex) {
                transaction.commit();
                return Optional.empty();
            }
        }
    }

    @Override
    public void issueInvoice(CustomerEntity customer) {
        

            if (Objects.isNull(customer.getCustomerId())) {
                session.persist(customer);
            }
            customer.getInvoices().stream()
                    .filter(invoice -> Objects.isNull(invoice.getInvoiceId()))
                    .forEach(invoice -> {
                        invoice.setCustomer(customer);
                        session.persist(invoice);
                    });
 
    }

    @Override
    public void saveServiceRequest(CustomerEntity customer) {
        

            customer.getCarServiceRequests().stream()
                    .filter(request -> Objects.isNull(request.getCarServiceRequestId()))
                    .forEach(session::persist);
 
    }

    @Override
    public CustomerEntity saveCustomer(CustomerEntity customer) {
        
            session.persist(customer);
            transaction.commit();
            return customer;
        }
    }
}
