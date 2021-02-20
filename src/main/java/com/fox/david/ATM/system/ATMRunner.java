package com.fox.david.ATM.system;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fox.david.ATM.model.dao.AccountDAO;
import com.fox.david.ATM.model.dao.TransactionDAO;
import com.fox.david.ATM.model.dto.AccountDTO;
import com.fox.david.ATM.model.dto.WithdrawalDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ATMRunner {
    AccountDAO accountDAO;
    TransactionDAO transactionDAO;

    public ATMRunner(AccountDAO accountDAO, TransactionDAO transactionDAO) {
        this.accountDAO = accountDAO;
        this.transactionDAO = transactionDAO;
    }


    @GetMapping("/account/{id}")
    public AccountDTO getBalance(@RequestBody WithdrawalDTO dto, @PathVariable Long id) throws Exception {
        if (accountDAO.checkPin(id,dto.getPin())) {
            return accountDAO.getBalance(id);
        }

        throw new Exception("Entered PIN is incorrect");
    }
}
