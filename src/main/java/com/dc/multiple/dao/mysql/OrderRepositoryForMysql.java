package com.dc.multiple.dao.mysql;

import com.dc.multiple.entity.mysql.OrderEntityForMysql;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepositoryForMysql extends JpaRepository<OrderEntityForMysql,Long> {
}
