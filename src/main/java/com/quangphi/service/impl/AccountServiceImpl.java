package com.quangphi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quangphi.entity.Account;
import com.quangphi.exception.ExistsException;
import com.quangphi.model.AccountDTO;
import com.quangphi.repository.AccountRepository;
import com.quangphi.service.AccountService;
import com.quangphi.util.ConvertListSupport;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
	private ConvertListSupport<AccountDTO, Account> convertListSupport;

    private List<AccountDTO> parseAccountDTOs(Iterable<Account> accounts) {
        return (List<AccountDTO>) convertListSupport
        		.converting(accounts, accountEntity -> AccountDTO.parseAccountDTO(accountEntity));
    }

    @Override
    public AccountDTO addAccount(AccountDTO account) {
        if (accountRepository.existsById(account.getUsername())) {
            throw new ExistsException("Error : " + Account.class.getName() + " width Username = \""
                    + account.getUsername() + "\" already exists ! ");
        }
        accountRepository.save(account.toAccount());
        return account;
    }

    @Override
    public AccountDTO updateAccount(AccountDTO accountDTO) {
        if (!accountRepository.existsById(accountDTO.getUsername())) {
            throw new ExistsException("Error : " + Account.class.getName() + " width Username = \""
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