package com.fox.david.ATM.model.dao;

import com.fox.david.ATM.model.data.Account;
import com.fox.david.ATM.model.dto.AccountDTO;
import com.fox.david.ATM.model.repository.AccountRepository;
import com.fox.david.ATM.utils.AccountToDTOMapper;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class AccountDAO {
    AccountRepository accountRepository;
    Logger LOGGER = Logger.getLogger(this.getClass().getName());

    public AccountDAO(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public boolean fundsAvailable(int amount, long id) throws Exception {
        Account account = getAccount(id);
        if (account != null) {
            return (account.getBalance() + account.getOverdraft()) > amount;
        }
        throw new Exception("No Account Found");
    }


    public AccountDTO getBalance(long id) throws Exception {
        Account account = getAccount(id);
        if (account != null) {
            return AccountToDTOMapper.mapAccountToAccountDTO(account);
        }
        LOGGER.error("Account with id " + id + "does not exist");
        throw new Exception("No Account Found");
    }

    public boolean checkPin(long id, int pin) throws Exception {
        Account account = getAccount(id);
        if (account != null) {
            return pin == account.getPin();
        }
        LOGGER.error("Account with id " + id + "does not exist");
        throw new Exception("No Account Found");
    }

    public AccountDTO withdrawFunds(int amount, Long id, LinkedHashMap<String, Integer> map) throws Exception {
        Account account = getAccount(id);
        if (account != null) {
            if (account.getBalance() < amount) {
                account.setBalance(0);
                account.setOverdraft(account.getOverdraft() - (amount - account.getBalance()));
            } else {
                account.setBalance(account.getBalance() - amount);
            }
            LOGGER.info("dispensing the following funds:" + parseUsedCurrency(map));
            return AccountToDTOMapper.mapAccountToAccountDTOWithMessage(accountRepository.save(account), parseUsedCurrency(map));
        }
        LOGGER.error("Account with id " + id + "does not exist");
        throw new Exception("No Account Found");
    }

    public Account getAccount(Long id) {
        Optional<Account> optionalAccount = accountRepository.findById(id);
        return optionalAccount.orElse(null);
    }

    private String parseUsedCurrency(LinkedHashMap<String, Integer> input) {
        StringBuilder builder = new StringBuilder();
        builder.append("Dispensing requested amount in the form of:\n");
        for (Map.Entry<String, Integer> cursor : input.entrySet()) {
            if (cursor.getValue() != 0) {
                builder.append(cursor.getKey()).append(" euro notes:").append(cursor.getValue().toString()).append("\n");
            }
        }
        return builder.toString();
    }
}
