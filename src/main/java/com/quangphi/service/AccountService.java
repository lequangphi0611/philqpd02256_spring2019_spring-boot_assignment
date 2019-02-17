package com.quangphi.service;

import java.util.List;
import com.quangphi.model.AccountDTO;


public interface AccountService {

    AccountDTO addAccount(AccountDTO accountDTO);

    AccountDTO updateAccount(AccountDTO accountDTO);

    List<AccountDTO> getAllAccounts();

    boolean delete(String username);

    List<AccountDTO> getByUsernameOrFullnameContaining(String keyword);

    AccountDTO get(String username);

    boolean existsByIDAccount(String username);
    
}