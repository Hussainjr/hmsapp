package com.hmsapp.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Account{
    @Id
    private Long id; // Unique identifier for the account

    @Column(name = "name", nullable = false)
    private String name; // Name of the account holder

    @Column(name = "balance", nullable = false)
    private Double balance; // Account balance

    @Version
    private Long version; // Optimistic locking version field

    //Q) If 1000 members are entering or trying to book something, how will you handle the traffic?
    //A) I used an optimistic locking mechanism.
    // I add one variable private long version with @Version.
    // every time we save a record a version id is automatically created, and
    // when every we read the record and try to update while reading that version id comes under
    // object automatically and while we are updating it if some users is already updated it, and
    // then I am updating then in that case, it will not take my updates as there is a version miss match.

}
