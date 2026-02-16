package com.fidelity.mts.servcie;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fidelity.mts.dto.TransferRequest;
import com.fidelity.mts.dto.TransferResponse;
import com.fidelity.mts.entity.Account;
import com.fidelity.mts.entity.TransactionLog;
import com.fidelity.mts.enums.AccountStatus;
import com.fidelity.mts.enums.TransactionStatus;
import com.fidelity.mts.exception.AccountNotActiveException;
import com.fidelity.mts.exception.AccountNotFoundException;
import com.fidelity.mts.exception.InsufficientBalanceException;
import com.fidelity.mts.repo.AccountRepo;
import com.fidelity.mts.repo.TransactionLogRepo;

@Service
public class TransferServiceImplementation implements TransferService{
	
	@Autowired
	AccountService accountService;
	@Autowired 
	AccountRepo repo;
	@Autowired
	TransactionLogRepo logrepo;
	
	@Override
	public TransferResponse transferMoney(TransferRequest transferRequest) {
		
		TransactionLog log = new TransactionLog(transferRequest.getFromId(),transferRequest.getToId(),transferRequest.getAmount(),TransactionStatus.SUCCESS );
		
	    Account fromAcc = accountService.findById(transferRequest.getFromId());
	    if (fromAcc == null) {
	        throw new AccountNotFoundException();
	    }
	    
	    if(fromAcc.getStatus()!= AccountStatus.ACTIVE) {
	    	log.setStatus(TransactionStatus.FAILED);
	    	log.setFailureReason("ACC-403 Account not Active");
	    	logrepo.save(log);
	    	throw new AccountNotActiveException();
	    }
	    
	    Account toAcc = accountService.findById(transferRequest.getToId());
	    
	    if (toAcc == null) {
	        throw new AccountNotFoundException();
	    }
	    
	    if(toAcc.getStatus()!= AccountStatus.ACTIVE) {
	    	log.setStatus(TransactionStatus.FAILED);
	    	log.setFailureReason("ACC-403 Account not Active");
	    	logrepo.save(log);
	    	throw new AccountNotActiveException();
	    }
	    
	    logrepo.save(log);

		BigDecimal balance = fromAcc.getBalance();
		BigDecimal amount  = transferRequest.getAmount();
		
		if (balance.compareTo(amount) < 0) {
			log.setStatus(TransactionStatus.FAILED);
	    	log.setFailureReason("TRX-400 Insufficient Funds.");
	    	logrepo.save(log);
		    throw new InsufficientBalanceException();
		}
	
	    accountService.debit(fromAcc, transferRequest.getAmount());
	    accountService.credit(toAcc, transferRequest.getAmount());
			
	    repo.save(fromAcc);
		repo.save(toAcc);
		logrepo.save(log);
			
		TransferResponse response = new TransferResponse(
				log.getId(),log.getStatus(),"Successfull",log.getFromAccountId(),log.getToAccountId(),log.getAmount());
		return response;
			
		}
	
    @Override
    public List<TransactionLog> getTransaction(Long id){
        return logrepo.findByFromAccountIdOrToAccountId(id, id);
    }

    @Override
    public List<TransactionLog> findByFromAccountId(Long id) {
	   return logrepo.findByFromAccountId(id);
    }

    @Override
    public List<TransactionLog> findByToAccountId(Long id) {
	  return logrepo.findByToAccountId(id);
    }

}
