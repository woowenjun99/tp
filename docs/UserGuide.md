# User Guide

<!-- TOC -->

- [User Guide](#user-guide)
  - [Introduction](#introduction)
  - [Quick Start](#quick-start)
  - [Features](#features)
    - [Viewing help: `help`](#viewing-help--help)
    - [Creating accounts `create-account`](#creating-accounts-create-account)
    - [Delete currency account: `delete-account`](#delete-currency-account--delete-account)
    - [Getting the balances of an account or multiple accounts `balance`](#getting-the-balances-of-an-account-or-multiple-accounts-balance)
    - [Deposit money into existing account `add`](#deposit-money-into-existing-account-add)
    - [Exchange money between international currencies `exchange`](#exchange-money-between-international-currencies-exchange)
    - [Withdrawing money: `withdraw`](#withdrawing-money--withdraw)
    - [Show the exchange rate between two currencies `show-rate`](#show-the-exchange-rate-between-two-currencies-show-rate)
    - [Show transactions `trans`](#show-transactions-trans)
    - [Exiting the program: `Exit`](#exiting-the-program--exit)
  - [FAQ](#faq)
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
2. Down the latest version of `Duke` from [here](http://link.to/duke).

## Features

### Viewing help: `help`

Format: help

Examples:

```text
>> help
>> Here are the commands available:
            help - show list of commands"
            add CURRENCY AMOUNT [DESCRIPTION] - adds that amount of money into that currency account
            balance [CURRENCY] - view balances of accounts
            exchange CURRENCY1 CURRENCY2 AMOUNT - transfer funds from a currency1 account
                                         into its equivalent value in currency2 account
            withdraw CURRENCY AMOUNT [DESCRIPTION] - withdraws that amount of money from that currency account
            show-rate CURRENCY1 CURRENCY2 [AMOUNT] - shows the value of each dollar in CURRENCY1 in terms of CURRENCY2
            trans [FLAG] [SEARCH PARAM]  - Appropriate flags are
                                             - desc : search by the description as search parameter
                                             - c : search by currency as search parameter
                                             - d : search by date as search parameter in the form dd-MM-yyyy
                                             - m : search by month as search parameter in the form MM-yyyy
            delete-account CURRENCY - deletes the account of that currency
            create-account CURRENCY - creates an account of that currency
            exit - exits the program"
            Available Currencies: SGD, USD, EUR, GBP, THB, MYR, IDR, VND, CNY, JPY, KRW"),

```

### Creating accounts `create-account`

Creates an account of the specified currency, the initial balance of any account created is 0.

Format: `create-account <Currency>`

- `Currency` is a compulsory parameter denoting which currency the account to be created stores.
- An error will be shown if the `Currency` provided is not one of our registered currencies
- An error will be shown if the `Currency` account already exists

```text
>>> create-account SGD
>>> You have successfully added the SGD account

>>> create-account SGD
>>> You already have an account of this currency

>>> create-account ME
>>> An invalid currency has been provided
```

### Delete currency account: `delete-account`

Deletes the specified accounts.

Format: `delete-account CURRENCY`

- Your account must have a balance of 0 to be deleted.
- You must have an account of CURRENCY to delete it.

Examples:

```text
>> delete-account USD
>> You have successfully deleted your SGD account
```

### Getting the balances of an account or multiple accounts `balance`

If the currency is specified, get the balance of the account with the currency. Otherwise, get the balances of all the
accounts.

Format: `balance [Currency]`

- `[Currency]` is an optional argument. If the value of `<Currency>` is not provided, the balance of all the accounts
  will be shown.
- An error will be shown if the currency specified is not one of our registered currencies or an account with the
  currency

Example of usage:

```text
>>> balance
>>> Here are the balances that you have requested:
>>> USD: 2.0000
>>> JPY: 100.0000

>>> balance USD
>>> Here are the balances that you have requested:
>>> USD: 2.0000
```

Example of error messages:

```text
>>> balance ME
>>> An invalid currency has been provided.

>>> balance JPY
>>> You do not have an account for the currency.
```

### Deposit money into existing account `add`

- If the user has an existing account, he can choose to add money into it.
- An error will be thrown if either the currency or amount is not provided, the currency is not one of the registered
  currency, the amount is not numeric or the account with the currency does not exist.

Format: `add <Currency> <Amount>`

Currency: Compulsory argument. A registered currency.
Amount: Compulsory argument. The amount to be deposited.

Example of usage:

```text
>>> add CNY 200
>>> You have successfully added CNY 2.0000 into your account
```

### Exchange money between international currencies `exchange`

Both an 'initial' and 'target' currency must be specified. The value provided will be subtracted from the balance of
the 'initial' currency, and exchanged into the 'target currency', and will be added
to the account of the target currency. You must have accounts for both the initial and target currency to perform this
command.

Format: `exchange <initialCurrency> <targetCurrency> <amount>`

Examples of usage (assuming accounts are created) :

```text
>> exchange SGD USD 100
// S$100 will be moved from your $SGD account, transferred into $USD, then added to your $USD account

>> exchange THB MYR 5000
// ฿5000 will be moved from your ฿THB account, transferred into $MYR, then added to your $MYR account
```

Examples of error messages:

```text
>>> exchange THB SGD
>>> Please structure your exchange as 'exchange STARTING_CURRENCY TARGET_CURRENCY AMOUNT_IN_STARTING'

>>> exchange THB SGD 5000
>>> Please ensure you have enough money in your starting currency account to perform this transaction

>>> exchange THB SGD -10
>>> Please enter a valid number to exchange
```

### Withdrawing money: `withdraw`

Format: `withdraw CURRENCY AMOUNT`

- Withdraw the amount of money of specified currency.

```text
>> withdraw SGD 10
>> You have successfully withdrawn 10.00 SGD from your account
   Now you have remaining 0.00 SGD in your account
```

### Show the exchange rate between two currencies `show-rate`

The show rate has an optional parameter amount. The command will show the value of amount exchanged both ways. If amount
is not provided,
the command will use a value of 1. The command must be given supported currencies, and numerical non-negative values. The
value must also be within the range of 0.01 and 1,000,000 for the starting currency to avoid exchange inaccuracies.

Format: `show-rate <initialCurrency> <targetCurrency> [amount]`

Examples of usage:

```text
>>> show-rate THB SGD
>>> 1.00 THB =   0.039105 SGD
>>> 1.00 SGD =  25.571956 THB

>>> show-rate THB SGD 0.56
>>> 0.56 THB =   0.021899 SGD
>>> 0.56 SGD =  14.320295 THB

>>> show-rate JPY USD 105.2
>>> 105.20 JPY =   0.804589 USD
>>> 105.20 USD = 13,754.897026 JPY
```

Examples of error messages:

```text
>>> show-rate SGD
>>> Please structure show-rate as 'show-rate CURRENCY CURRENCY [AMOUNT]'

>>> show-rate SGD XYZ
>>> An invalid currency has been provided.

>>> show-rate KRW CNY -12
>>> Please enter a positive number to show the rate!
```

### Show transactions `trans`

If a flag and search parameter is specified, prints the transactions found under that parameter.
Otherwise, prints all transactions in reverse chronological order

Format `trans [flag] [search parameter]`:

- Appropriate flags are
  - desc - search by the description as search parameter
  - c - search by currency as search parameter
  - d - search by date as search parameter in the form dd-MM-yyyy
  - m - search by month as search parameter in the form MM-yyyy
- All transactions are printed in reverse-chronological order
- Flag and search parameter is optional, neglecting them will print all transactions
- An error is thrown if the flag is invalid or search parameter is invalid

Examples of usage:

```text
>>> trans
>>> Below are all your transactions in reverse chronological order:
>>> +USD 200.00
>>> Amount in account after transaction: USD 200.00
>>> Description: Pass Go
>>> At: 28 Mar 2023, 5:17:53PM

>>> trans desc go
>>> Below are all your transactions with the description go:
>>> +USD 200.00
>>> Amount in account after transaction: USD 200.00
>>> Description: Pass Go
>>> At: 28 Mar 2023, 5:17:53PM

>>> trans c SGD
>>> You have no transactions of the specified search parameters

>>> trans d 28-03-2023
>>> Below are all your transactions with the description go:
>>> +USD 200.00
>>> Amount in account after transaction: USD 200.00
>>> Description: Pass Go
>>> At: 28 Mar 2023, 5:17:53PM

>>> trans m 03-2023
>>> Below are all your transactions with the description go:
>>> +USD 200.00
>>> Amount in account after transaction: USD 200.00
>>> Description: Pass Go
>>> At: 28 Mar 2023, 5:17:53PM
```

### Exiting the program: `Exit`

Exits the program.

Format: `exit`

Examples:

```text
>> exit
>> Thank you for using MoneyMoover!We hope to see you again soon:)
```

## FAQ

**Q**: How do I transfer my data to another computer?

**A**: {your answer here}

## Command Summary

| Action         | Format & Example                                                          |
| -------------- | ------------------------------------------------------------------------- |
| help           | `help`                                                                    |
| add            | `add CURRENCY AMOUNT [DESCRIPTION]`<br/> e.g. `add SGD 10`                |
| balance        | `balance CURRENCY`<br/>e.g. `balance SGD`                                 |
| exchange       | `exchange CURRENCY1 CURRENCY2 AMOUNT`<br/>e.g. `exchange SGD USD 10`      |
| withdraw       | `withdraw CURRENCY AMOUNT [DESCRIPTION]`<br/>e.g. `withdaw SGD 100`       |
| show-rate      | `show-rate CURRENCY1 CURRENCY2 [AMOUNT]`<br/>e.g. `show-rate SGD THB 100` |
| delete-account | `delete-account CURRENCY`<br/>e.g. delete-account USD`                    |
| create-account | `create-account CURRENCY`<br/>e.g. `create-account EUR`                   |
| exit           | `exit`                                                                    |
