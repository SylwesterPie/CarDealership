package pl.zajavka.business.dao.managment;

import java.util.List;

public interface CarDealershipManagementDAO {

    void purge();

    void savaAll(List<?> entities);
}
