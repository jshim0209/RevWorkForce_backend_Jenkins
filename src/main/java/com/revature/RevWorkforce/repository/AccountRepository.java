package com.revature.RevWorkforce.repository;

import com.revature.RevWorkforce.entity.Account;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    @Query("FROM account WHERE accountId = :accountid")
    Account getAccountName(@Param("accountid") Integer account_id);

    /**
     * Finds and returns a list of Account objects in the account database
     *
     * @return a list of all Account objects
     */
    @Query(value = "SELECT * FROM account", nativeQuery = true)
    public List<Account> findAllAccounts();

    /**
     * Finds and returns a list of Account objects with a matching account_type
     *
     * @param account_type the account_type of the Account to find
     * @return a list of Account objects that match account_type
     */
    @Query(value = "SELECT * FROM account WHERE account_type = :accountTypeVar", nativeQuery = true)
    public List<Account> findAccountsByAccount_type(@Param("accountTypeVar") String account_type);

    /**
     * Finds and returns the Account with a matching email
     *
     * @param email the email of the Account to find
     * @return the Account with a matching email
     */
    public Account findByEmail(String email);

    @Query(nativeQuery = true, value = "SELECT * FROM account WHERE account_id = :accountId")
    public Account getAccountByAccountId(@Param("accountId") Integer account_id);

    @Query(nativeQuery = true, value = "SELECT * FROM account WHERE managerid = :id")
    public List<Account> getAccountsByManager(@Param("id") Integer id);
}

