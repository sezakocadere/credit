package com.credit.credit.service.sms;

import com.credit.credit.enums.LoanStatus;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Log4j2
public class SmsServiceImpl implements SmsService, Runnable {

    @Override
    public void run() {
    }

    @Override
    public String sendSmsMessageByLoanStatus(LoanStatus status, String phoneNumber) {
        String message = Objects.nonNull(phoneNumber) ? "Send SMS Message to " + phoneNumber + " for Loan apply status:" + status : "Sms could not be sent because of no registered phone number";
        log.info(message);
        return status.toString();
    }
}
