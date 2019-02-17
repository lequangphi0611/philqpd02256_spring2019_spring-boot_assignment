package com.quangphi.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.quangphi.entity.Account;
import com.quangphi.exception.ExistsException;
import com.quangphi.model.AccountDTO;
import com.quangphi.repository.AccountRepository;
import com.quangphi.service.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    private List<AccountDTO> parseAccountDTOs(Iterable<Account> accounts) {
        List<AccountDTO> accountDTOs = new ArrayList<>();
        accounts.forEach(items -> accountDTOs.add(AccountDTO.parseAccountDTO(items)));
        return accountDTOs;
    }

    @Override
    public AccountDTO addAccount(AccountDTO account) {
        if (accountRepository.existsById(account.getUsername())) {
            throw new ExistsException("Error : " + Account.class.getSimpleName() + " width Username = \""
                    + account.getUsername() + "\" already exists ! ");
        }
        accountRepository.save(account.toAccount());
        return account;
    }

    @Override
    public AccountDTO updateAccount(AccountDTO accountDTO) {
        if (!accountRepository.existsById(accountDTO.getUsername())) {
            throw new ExistsException("Error : " + Account.class.getSimpleName() + " width Username = \""
                    + accountDTO.getUsername() + "\" does not exists ! ");
        }
        accountRepository.save(accountDTO.toAccount());
        return accountDTO;
    }

    @Override
    public List<AccountDTO> getAllAccounts() {
        return this.parseAccountDTOs(accountRepository.findAll());
    }

    @Override
    public boolean delete(String username) {
        if (!accountRepository.existsById(username)) {
            return false;
        }
        accountRepository.deleteById(username);
        return true;
    }

    @Override
    public List<AccountDTO> getByUsernameOrFullnameContaining(String keyword) {
        List<Account> result = accountRepository.findByUsernameContaining(keyword);
        List<Account> findByFullnameContaining = accountRepository.findByFullnameContaining(keyword);
        if (!findByFullnameContaining.isEmpty()) {
            for (Account items : findByFullnameContaining) {
                if(!existAccountInList(items, result)) {
                    result.add(items);
                }
            }
        }
        return this.parseAccountDTOs(result);
    }

    private boolean existAccountInList(Account account, Iterable<Account> list) {
        for(Account items : list) {
            if(account.getUsername().equals(items.getUsername())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public AccountDTO get(String username) {
        return AccountDTO.parseAccountDTO(accountRepository.findById(username).get());
    }

    @Override
    public boolean existsByIDAccount(String username) {
        return accountRepository.existsById(username);
    }
}