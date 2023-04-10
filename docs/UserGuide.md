# User Guide

<!-- TOC -->

- [User Guide](#user-guide)
  - [Introduction](#introduction)
  - [Quick Start](#quick-start)
  - [Features](#features)
    - [Notes](#notes)
    - [Viewing help `help`](#viewing-help-help)
    - [Creating accounts `create-account`](#creating-accounts-create-account)
    - [Delete currency account `delete-account`](#delete-currency-account-delete-account)
    - [Getting the balances of an account or multiple accounts `balance`](#getting-the-balances-of-an-account-or-multiple-accounts-balance)
    - [Deposit money into existing account `add`](#deposit-money-into-existing-account-add)
    - [Exchange money between international currencies `exchange`](#exchange-money-between-international-currencies-exchange)
    - [Withdrawing money `withdraw`](#withdrawing-money-withdraw)
    - [Show the exchange rate between two currencies `show-rate`](#show-the-exchange-rate-between-two-currencies-show-rate)
    - [Show transactions `trans`](#show-transactions-trans)
    - [Exiting the program `exit`](#exiting-the-program-exit)
  - [Command Summary](#command-summary)

<!-- TOC -->

## Introduction

MoneyMoover is a **CLI application for managing and transferring international currencies**, optimized for use via a
Command Line Interface (CLI)
while still having the features of other money management applications.

Our app will help students to **track their expenses and income** in order to stay within their budgets, so that they
have enough money to travel.
It will also help them convert to foreign currencies so they can see how much they have to spend overseas.

## Quick Start

1. Ensure that you have Java 11 or above installed.
2. Download the latest version of `MoneyMoover` from [here](https://github.com/AY2223S2-CS2113-T13-1/tp).

## Features

### Notes

-   **Command Parameters**
    -   Parameters in `UPPER_CASE` are compulsory and must be provided.
    -   Parameters in square brackets `[OPTIONAL]` are optional parameters.
-   **Number Format**
    -   User are only allowed to input **max 2 decimal points** float amount.
        -   For example: `1.03`, `1`, `196.74`.
    -   You are not allowed to store more than $10,000,000 of any currency in your account.
    -   User are only allowed to input **pure numerical character**.
        -   Example of invalid number format : `1,000`, `5_000_000`.
-   **Transactions Related**
    -   `add`, `withdraw` and `exchange` action will be saved in transaction list automatically
        -   `DESCRIPTION` of `add` and `withdraw` command accepts all sort **non-space** char/string.
        -   `DESCRIPTION` is limited to 100 characters only.
        -   transaction description of `exchange` command is fixed by default as the relative exchange info.
            -   Example: `exchange 10 SGD to 50 THB`
-   **Storage Related**
    -   Account balances and transactions are stored in the "data" folder in the same directory as the JAR file.
    -   Users are not meant to edit the data files directly, doing so may cause the application to behave in an
        unexpected manner.
    -   If any of the files become corrupted, MoneyMoover will attempt to create a new empty file to replace it.
-   **Currency Related**
    -   All currency related parameters are case-insensitive.
    -   All currency related parameters are limited to the following currencies:
        -   SGD, USD, EUR, GBP, THB, MYR, IDR, VND, CNY, JPY, KRW
    -   All currency related parameters are limited to 3 characters only.
    -   All currency related parameters are limited to alphabetic characters only.
-   **Date Related**
    -   All date related parameters are limited to the following format:
        -   DD is interpreted as the day of the month, from 01 to 31.
        -   MM is interpreted as the month of the year, from 01 to 12.
        -   YYYY is interpreted as the year, from 0000 to 9999.
    -   Padded zeroes are necessary, for example 1-1-2023 is not a valid input, while 01-01-2023 is.
    -   Invalid dates are not allowed, for example 13-2023, 00-2023, 32-01-2000.

### Viewing help `help`

Format: `help`

Examples:

```text
>> help
>> Here are the commands available:
       help - show list of commands
       add CURRENCY AMOUNT [DESCRIPTION] - adds that amount of money into that currency account
       balance [CURRENCY] - view balances of accounts
           balance CURRENCY - View the balance of the specified currency in the account
           balance - displays all currencies
       exchange CURRENCY1 CURRENCY2 AMOUNT - transfer funds from a currency1 account
                                             into its equivalent value in currency2 account
       withdraw CURRENCY AMOUNT [DESCRIPTION] - withdraws that amount of money from that currency account
       show-rate CURRENCY1 CURRENCY2 [AMOUNT] - shows the value of each dollar in CURRENCY1 in terms of CURRENCY2
       delete-account CURRENCY - deletes the account of that currency
       create-account CURRENCY - creates an account of that currency
       trans [FLAG] [SEARCH_PARAM]  - Appropriate flags are
           i) desc : search by the description as search parameter
           ii) c : search by currency as search parameter
           iii) d : search by date as search parameter in the form DD-MM-YYYY
           iv) m : search by month as search parameter in the form MM-YYYY
       exit - exits the program
       Available Currencies: SGD, USD, EUR, GBP, THB, MYR, IDR, VND, CNY, JPY, KRW

```

### Creating accounts `create-account`

Creates an account of the specified currency.

Format: `create-account CURRENCY`

- `CURRENCY`: Compulsory argument. A registered currency.
- Initial amount stored in the account is 0.
- An error will be shown if the `CURRENCY` provided is not one of our registered currencies.
- An error will be shown if the `CURRENCY` account already exists.

Example:

```text
>>> create-account SGD
>>> You have successfully added the SGD account
```

### Delete currency account `delete-account`

Deletes the specified accounts.

Format: `delete-account CURRENCY`

- `CURRENCY`: Compulsory argument. A registered currency.
- Your account must have a balance of 0 to be deleted.
- You must have an account of `CURRENCY` to delete it.

Examples:

```text
>> delete-account SGD
>> You have successfully deleted your SGD account
```

### Getting the balances of an account or multiple accounts `balance`

If the currency is specified, get the balance of the account with the currency. Otherwise, get the balances of all accounts.

Format: `balance [CURRENCY]`

- `[CURRENCY]`: Optional argument. A registered currency.
- If `[CURRENCY]` is not provided, the balance of all the accounts will be shown.
- An error will be shown if the `CURRENCY` specified is not one of our registered currencies or user does not have an account with the `CURRENCY`.

Example of usage:

```text
>>> balance
>>> Here are the balances that you have requested:
    USD: 2.00
    JPY: 100.00

>>> balance USD
>>> Here are the balances that you have requested:
    USD: 2.00
```

### Deposit money into existing account `add`

Adds money into an existing user account.

Format: `add CURRENCY AMOUNT [DESCRIPTION]`

- `CURRENCY`: Compulsory argument. A registered currency.
- `AMOUNT` : Compulsory argument. The amount to be deposited.
- `[DESCRIPTION]`: Optional argument. Is set to `NIL` if not provided.
- `[DESCRIPTION]` must consist of at least one non-space character.
- Multiple consecutive spaces in `[DESCRIPTION]` will be replaced with a single space.
- An error will be thrown if either the `CURRENCY` or `AMOUNT` is not provided, the `CURRENCY` is not one of the
  registered currencies, the `AMOUNT` is not numeric or the account with the `CURRENCY` does not exist.

Example of usage:

```text
>>> add CNY 200
>>> You have successfully added CNY 200.00 into your account

>> add SGD 5 part time
>> You have successfully added SGD 5.00 into your account
```

### Exchange money between international currencies `exchange`

Exchanges money from one currency to another.

Format: `exchange CURRENCY1 CURRENCY2 AMOUNT`

- `CURRENCY1`: Compulsory argument. A registered currency, the initial `CURRENCY`.
- `CURRENCY2`: Compulsory argument. A registered currency, the target `CURRENCY`.
- `AMOUNT`: Compulsory argument. The amount to exchange.
- The value provided will be subtracted from the balance of the `CURRENCY1` and exchanged into the `CURRENCY2`,
  and will be added to the account of the `CURRENCY2`. You must have accounts for both the initial and target
  `CURRENCY` to perform this command.
- If the converted amount is less than 0.01, the user will be asked to convert a value that calculates
  to at least 0.01 in the target `CURRENCY` to avoid loss of precision and money.

Examples of usage (assuming accounts are created) :

```text
>> exchange SGD USD 100
>> Exchanging from SGD to USD
   Balance of initial account --> SGD: 895.00
   Balance of target account --> USD: 77.14

//Above example are for reference only. Actual rates might vary depend on the market.
```

### Withdrawing money `withdraw`

Withdraw the amount of money of specified currency.

Format: `withdraw CURRENCY AMOUNT [DESCRIPTION]`

- `CURRENCY`: Compulsory argument. A registered currency.
- `AMOUNT` : Compulsory argument. The amount to be deposited.
- `[DESCRIPTION]`: Optional argument. Is set to `NIL` if not provided.
- `[DESCRIPTION]` must consist of at least one non-space character.
- Multiple consecutive spaces in `[DESCRIPTION]` will be replaced with a single space.

Example:

```text
>> withdraw SGD 10
>> You have successfully withdrawn 10.00 SGD from your account
   Now you have remaining 90.00 SGD in your account

>> withdraw SGD 5 Chicken Rice
>> You have successfully withdrawn 5.00 SGD from your account
   Now you have remaining 880.00 SGD in your account
```

### Show the exchange rate between two currencies `show-rate`

Format: `show-rate CURRENCY1 CURRENCY2 [AMOUNT]`

- `CURRENCY1`: Compulsory argument. A registered currency, the initial `CURRENCY`.
- `CURRENCY2`: Compulsory argument. A registered currency, the target `CURRENCY`.
- `[AMOUNT]`: Optional argument. The amount to show-rate for. Is set to 1 if not provided.
- The `[AMOUNT]` must be within the range of 0.01 and 10,000,000 for the initial currency to avoid exchange
  inaccuracies.
- The command will show the value of amount exchanged both ways.
- The command must be given supported currencies, and numerical non-negative values.

Examples of usage:

```text
>>> show-rate THB SGD
>>> 1.00 THB =   0.039105 SGD
    1.00 SGD =  25.571956 THB

>>> show-rate THB SGD 0.56
>>> 0.56 THB =   0.021899 SGD
    0.56 SGD =  14.320295 THB

>>> show-rate JPY USD 105.2
>>> 105.20 JPY =   0.804589 USD
    105.20 USD = 13,754.897026 JPY
```

### Show transactions `trans`

If a flag and search parameter is specified, prints the transactions found under that parameter.
Otherwise, prints all transactions in reverse chronological order

Format `trans [FLAG] [SEARCH_PARAMETER]`:

- `[FLAG]`: Optional argument. Determines the category to search the transactions by.
- `[SEARCH_PARAMETER]`: Optional argument. What to search the transactions for.
- Appropriate `[FLAG]` are
    1. `desc` - search by the description as search parameter
    2. `c` - search by currency as search parameter
    3. `d` - search by date as search parameter in the form `DD-MM-YYYY`
    4. `m` - search by month as search parameter in the form `MM-YYYY`
- All transactions are printed in reverse-chronological order
- `[FLAG]` and `[SEARCH_PARAMETER]` are optional, neglecting them will print all transactions
- If `[FLAG]` is provided, `[SEARCH_PARAMETER]` must also be provided.
- Users are only allowed to input **one** flag at a time.
- An error is thrown if the flag is invalid or search parameter is invalid

Examples of usage:

```text
>>> trans
>>> Below are all your transactions in reverse chronological order:
    +USD 200.00
    Amount in account after transaction: USD 200.00
    Description: Pass Go
    At: 28 Mar 2023, 5:17:53PM

>>> trans desc go
>>> Below are all your transactions with the description go:
    +USD 200.00
    Amount in account after transaction: USD 200.00
    Description: Pass Go
    At: 28 Mar 2023, 5:17:53PM

>>> trans c SGD
>>> You have no transactions of the specified search parameters

>>> trans d 28-03-2023
>>> Below are all your transactions with the date 28-03-2023:
    +USD 200.00
    Amount in account after transaction: USD 200.00
    Description: Pass Go
    At: 28 Mar 2023, 5:17:53PM

>>> trans m 03-2023
>>> Below are all your transactions during the month of 03-2023:
    +USD 200.00
    Amount in account after transaction: USD 200.00
    Description: Pass Go
    At: 28 Mar 2023, 5:17:53PM
```

### Exiting the program `exit`

Exits the program.

Format: `exit`

Examples:

```text
>> exit
>> Thank you for using MoneyMoover! We hope to see you again soon:)
```

## Command Summary

| Action                  | Format                                   | Example                                                                                      |
| :---------------------- | :--------------------------------------- | -------------------------------------------------------------------------------------------- |
| Viewing help            | `help`                                   | `help`                                                                                       |
| Depositing money        | `add CURRENCY AMOUNT [DESCRIPTION]`      | `add SGD 10`, `add SGD 5.5 part time`                                                        |
| Getting the balances    | `balance CURRENCY`                       | `balance SGD`, `balance`                                                                     |
| Exchange money          | `exchange CURRENCY1 CURRENCY2 AMOUNT`    | `exchange SGD USD 10`                                                                        |
| Withdrawing money       | `withdraw CURRENCY AMOUNT [DESCRIPTION]` | `withdaw SGD 100`, `withdraw USD 3 Chicken Rice`                                             |
| Show the exchange rate  | `show-rate CURRENCY1 CURRENCY2 [AMOUNT]` | `show-rate SGD THB 100`                                                                      |
| Show transactions       | `trans [FLAG] [SEARCH_PARAMETER]`        | `trans`, `trans desc part time`, `trans c SGD`, <br/>`trans d 28-03-2023`, `trans m 03-2023` |
| Delete currency account | `delete-account CURRENCY`                | `delete-account USD`                                                                         |
| Creating accounts       | `create-account CURRENCY`                | `create-account EUR`                                                                         |
| Exiting the program     | `exit`                                   | `exit`                                                                                       |
