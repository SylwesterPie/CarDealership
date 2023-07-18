package pl.zajavka.business.management;

public interface Keys {

    enum InputDataGroup {
        INIT,
        BUY_FIRST_TIME,
        BUY_AGAIN
    }

    enum Entity {
        SALESMAN,
        MECHANIC,
        CAR,
        SERVICE,
        PART,
        CUSTOMER
    }
}
