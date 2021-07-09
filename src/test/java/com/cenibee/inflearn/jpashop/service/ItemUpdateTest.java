package com.cenibee.inflearn.jpashop.service;

import com.cenibee.inflearn.jpashop.domain.item.Book;
import com.cenibee.inflearn.jpashop.domain.item.Item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@DataJpaTest
@Transactional
public class ItemUpdateTest {

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("업데이트 테스트")
    public void updateTest() throws Exception {

        //given
        Item book = em.find(Book.class, 1L);

        //when
        book.setName("asdf");

        //then

    }

}
