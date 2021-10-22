package com.dc.multiple.dao.ck;

import com.dc.multiple.entity.ck.OrderEntityForCK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepositoryForCK extends JpaRepository<OrderEntityForCK,Long> {
}
