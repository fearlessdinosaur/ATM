package com.fox.david.ATM.utils;

import com.fox.david.ATM.model.data.Account;
import com.fox.david.ATM.model.dto.AccountDTO;

public class AccountToDTOMapper {

    public static AccountDTO mapAccountToAccountDTO(Account account) {
        if (account != null) {
            AccountDTO dto = new AccountDTO();
            dto.setBalance(account.getBalance());
            dto.setOverdraft(account.getOverdraft());
            return dto;
        }
        return null;
    }

    public static AccountDTO mapAccountToAccountDTOWithMessage(Account account, String message) {
        if (account != null) {
            AccountDTO dto = new AccountDTO();
            dto.setBalance(account.getBalance());
            dto.setOverdraft(account.getOverdraft());
            dto.setMessage(message);
            return dto;
        }
        return null;
    }
}
