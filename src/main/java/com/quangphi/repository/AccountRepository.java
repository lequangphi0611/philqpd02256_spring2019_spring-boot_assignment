package com.quangphi.repository;

import java.util.List;

import com.quangphi.entity.Account;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<Account,String> {

    Account findByUsernameAndPassword(String username, String password);

    List<Account> findByUsernameContaining(String username);

    List<Account> findByFullnameContaining(String fullname);
     
}