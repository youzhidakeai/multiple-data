package com.dc.multiple.entity.mysql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "goods_order")
public class OrderEntityForMysql {

    @Id
    private Long id;

    private Long merchantId;

    private Long userId;

    private Integer price;

    private String payType;

    private String productLine;

    private String orderMonth;

    private String skuInfo;

    private Date createTime;

    private Date updateTime;

    private Long createBy;

    private Long updateBy;
}
