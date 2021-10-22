package com.dc.multiple.controller;

import com.dc.multiple.dao.ck.OrderRepositoryForCK;
import com.dc.multiple.dao.mysql.OrderRepositoryForMysql;
import com.dc.multiple.entity.mysql.OrderEntityForMysql;
import com.dc.multiple.utils.IdUtil;
import com.dc.multiple.enums.PayTypeEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@RestController
@RequestMapping("/test")
public class TestController {

    private final ObjectMapper getObjectMapper;

    private final OrderRepositoryForMysql orderRepository;

    private final OrderRepositoryForCK orderRepositoryForCK;

    public TestController(OrderRepositoryForMysql orderRepository, OrderRepositoryForCK orderRepositoryForCK,
                          ObjectMapper getObjectMapper) {
        this.orderRepository = orderRepository;
        this.orderRepositoryForCK = orderRepositoryForCK;
        this.getObjectMapper = getObjectMapper;
    }

    @SneakyThrows
    @PostMapping("/create")
    public void save() {
        OrderEntityForMysql entity = new OrderEntityForMysql();

        entity.setId(IdUtil.generateId());
        entity.setMerchantId( IdUtil.generateId());
        entity.setUserId( IdUtil.generateId());
        entity.setPrice(1200);
        entity.setOrderMonth(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
        entity.setPayType(PayTypeEnum.MIANJIAN.getCode());
        entity.setProductLine("狗东");
        entity.setSkuInfo("");
        entity.setCreateBy(IdUtil.generateId());
        entity.setUpdateBy(IdUtil.generateId());
        entity.setCreateTime(new Date());
        entity.setUpdateTime(new Date());
        System.out.println(getObjectMapper.writeValueAsString(entity));

        orderRepository.save(entity);
    }
}
