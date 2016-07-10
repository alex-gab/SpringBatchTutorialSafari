package io.spring.batch.domain;

import java.util.Date;

public final class Customer {
    private final long id;
    private final String firstName;
    private final String lastName;
    private final Date birthdate;

    public Customer(final long id,
                    final String firstName,
                    final String lastName,
                    final Date birthdate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
    }

    @Override
    public final String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthdate=" + birthdate +
                '}';
    }
}
