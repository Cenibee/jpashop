package com.cenibee.inflearn.jpashop.service;

import com.cenibee.inflearn.jpashop.domain.Address;
import com.cenibee.inflearn.jpashop.domain.Member;
import com.cenibee.inflearn.jpashop.domain.Order;
import com.cenibee.inflearn.jpashop.domain.OrderStatus;
import com.cenibee.inflearn.jpashop.domain.item.Book;
import com.cenibee.inflearn.jpashop.domain.item.Item;
import com.cenibee.inflearn.jpashop.exception.item.NotEnoughStockException;
import com.cenibee.inflearn.jpashop.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired EntityManager em;
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    @Test
    @DisplayName("상품주문")
    public void 상품주문() {
        //given
        Member member = createMember();

        Book book = createBook();

        int orderCount = 2;

        //when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals("상품 주문시 상태는 ORDER", OrderStatus.ORDER, getOrder.getStatus());
        assertEquals("주문한 상품 종류 수가 정확해야 한다.", 1, getOrder.getOrderItems().size());
        assertEquals("주문 가격은 가격 * 수량이다.", 10000 * orderCount, getOrder.getTotalPrice());
        assertEquals("주문 수량 만큼 재고가 줄어야한다.", 8, book.getStockQuantity());
    }

    @Test
    @DisplayName("상품주문 재고수량초과")
    public void 상품주문_재고수량초과() {
        //given
        Member member = createMember();
        Item item = createBook();

        int orderCount = 11;

        //expected
        assertThrows(NotEnoughStockException.class, () -> orderService.order(member.getId(), item.getId(), orderCount));
    }

    @Test
    @DisplayName("주문취소")
    public void 주문취소() {
        //given
        Member member = createMember();
        Item item = createBook();

        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        //when
        orderService.cancelOrder(orderId);

        //then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals("주문 취소시 상태는 CANCEL 이다.", OrderStatus.CANCEL, getOrder.getStatus());
        assertEquals("주문이 취소된 상품은 그만큼 재고가 증가해야한다.", 10, item.getStockQuantity());
    }

    private Member createMember() {
        Member member = new Member();
        member.setUsername("회원1");
        member.setAddress(new Address("서울", "강가", "123-123"));
        em.persist(member);
        return member;
    }

    private Book createBook() {
        Book book = new Book();
        book.setName("시골 JPA");
        book.setPrice(10000);
        book.setStockQuantity(10);
        em.persist(book);
        return book;
    }

}