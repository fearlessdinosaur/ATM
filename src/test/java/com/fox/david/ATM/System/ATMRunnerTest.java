package com.fox.david.ATM.System;

import com.fox.david.ATM.model.dao.AccountDAO;
import com.fox.david.ATM.model.dao.TransactionDAO;
import com.fox.david.ATM.model.data.Account;
import com.fox.david.ATM.model.dto.AccountDTO;
import com.fox.david.ATM.model.dto.WithdrawalDTO;
import com.fox.david.ATM.model.repository.AccountRepository;
import com.fox.david.ATM.system.ATMRunner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class ATMRunnerTest {

    @Autowired
    AccountDAO accountDAO;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionDAO transactionDAO;

    ATMRunner atmRunner;

    long id;

    WithdrawalDTO withdrawalDTO;

    @BeforeEach
    void init() {
        atmRunner = new ATMRunner(accountDAO, transactionDAO);
        id = accountRepository.save(new Account(1234,200,100)).getId();
    }

    @Test
    void testValidWithdrawalRequest() {

    }

    @Test
    void testInvalidWithdrawalRequest_pinDoesNotMatch() {

    }

    @Test
    void testInvalidWithdrawalRequest_UnknownId() {

    }

    @Test
    void testInvalidWithdrawalRequest_NotEnoughInAccount() {

    }

    @Test
    void testInvalidWithdrawalRequest_NotEnoughInATM() {

    }

    @Test
    void testValidBalanceRequest() throws Exception {
        withdrawalDTO = new WithdrawalDTO("1", 0, 1234);
        atmRunner.getBalance(withdrawalDTO, id);
    }

}
