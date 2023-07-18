package pl.zajavka.business.management;

public interface Keys {

    enum InputDataGroup {
        INIT,
        BUY_FIRST_TIME,
        BUY_AGAIN,
        SERVICE_REQUEST
    }

    enum Entity {
        SALESMAN,
        MECHANIC,
        CAR,
        SERVICE,
        PART,
        CUSTOMER
    }

    enum Constants {
        WHAT
    }
}
