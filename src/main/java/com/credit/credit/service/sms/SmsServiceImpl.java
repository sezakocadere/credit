package com.credit.credit.service.sms;

import com.credit.credit.enums.LoanStatus;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class SmsServiceImpl implements SmsService, Runnable {

    @Override
    public void run() {
    }

    @Override
    public String sendSmsMessageByLoanStatus(LoanStatus status) {
        log.info("Loan apply status:" + status);
        return status.toString();
    }
}
