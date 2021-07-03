package com.cenibee.inflearn.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Getter @Setter
@Entity
public class Delivery {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    @OneToOne(fetch = LAZY, mappedBy = "delivery")
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status; //READY, COMP

}
