package com.revature.RevWorkforce;

import com.revature.RevWorkforce.entity.AccountWithoutHash;
import com.revature.RevWorkforce.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class RevWorkforceOnStartup {
    @Autowired
    AccountService accountService;

    @EventListener
    public void appReady(ApplicationReadyEvent event) {
        accountService.addAccount(new AccountWithoutHash(
                "Jay",
                "Shim",
                "jshim@email.com",
                "password",
                "1231231234",
                "123 main st, main, va 12345",
                "manager",
                null,
                LocalDate.of(1990, 2, 9),
                LocalDate.now()
        ));

        accountService.addAccount(new AccountWithoutHash(
                "Kay",
                "Kim",
                "kkim@email.com",
                "password",
                "1231231234",
                "123 main st, main, va 12345",
                "employee",
                1,
                LocalDate.of(1989, 3, 23),
                LocalDate.now()
        ));

        accountService.addAccount(new AccountWithoutHash(
                "Employee",
                "RevWorkforce",
                "employee@email.com",
                "employee",
                "1234567890",
                "123 Main Street, Anywhere, NC",
                "employee",
                4,
                LocalDate.of(2005, 9, 14),
                LocalDate.now()
        ));

        accountService.addAccount(new AccountWithoutHash(
                "Manager",
                "RevWorkforce",
                "manager@email.com",
                "manager",
                "1234567890",
                "123 Main Street, Anywhere, NC",
                "manager",
                null,
                LocalDate.of(1982, 3, 17),
                LocalDate.now()
        ));

        accountService.addAccount(new AccountWithoutHash(
                "Admin",
                "RevWorkforce",
                "admin@email.com",
                "admin",
                "1234567890",
                "123 Main Street, Anywhere, NC",
                "admin",
                null,
                LocalDate.of(1972, 6, 19),
                LocalDate.of(1999, 2, 18)
        ));
    }
}