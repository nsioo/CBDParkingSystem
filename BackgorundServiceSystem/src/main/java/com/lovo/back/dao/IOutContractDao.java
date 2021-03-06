package com.lovo.back.dao;

import com.lovo.back.entity.OutContractEntity;
import com.lovo.back.entity.StallEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

@Repository
@Transactional
public interface IOutContractDao extends CrudRepository<OutContractEntity,Integer> {

    @Transactional
    @Modifying
    @Query(value = "update t_out_contract set state=0 where pk_id=?1",nativeQuery = true)
      public void updateState(int id);


    /**
     * 续约合同  根据id修改新合同编号
     * @param id
     * @param oldContractNo
     * @param newContractNo
     */
    @Transactional
    @Modifying
    @Query(value = "update t_out_contract set out_contract_no=?3,old_contract_no=?2 where pk_id=?1",nativeQuery = true)
    public void updateContractNo(int  id,String oldContractNo,String newContractNo);




    @Query(value="select * from  t_out_contract "
            ,
            countQuery = " select count(*) from t_out_contract "+
                    " order by ?#{#pageable}",nativeQuery = true)
    public List<OutContractEntity> findByPage(Pageable pageable);


    @Query(value="select count(*) from t_out_contract ",
            nativeQuery = true)
    public List<BigInteger> findByItemsCount();

}
