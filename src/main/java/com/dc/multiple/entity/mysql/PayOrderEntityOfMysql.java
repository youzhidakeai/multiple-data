package com.dc.multiple.entity.mysql;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pay_order")
public class PayOrderEntityOfMysql {

    @Id
    private Long id;

    private String payType;

    private String orderMonth;

    private Long merchantNo;

    private Long storeCode;

    private Long terminalId;

    private Long terminalTrace;

    private Long terminalTime;

    private BigDecimal totalFee;

    private Integer brandId;

    private String remark;

    private Integer status;

    private Date ctime;

    private Date utime;

}
