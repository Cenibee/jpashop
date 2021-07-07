package com.cenibee.inflearn.jpashop.repository;

import com.cenibee.inflearn.jpashop.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderSearch {

    private String memberName; // 회원 이름
    private OrderStatus orderStatus;

}
