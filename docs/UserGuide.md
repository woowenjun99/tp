# User Guide

## Introduction

MoneyMoover is a **CLI application for managing and transferring international currencies**, optimized for use via a Command Line Interface (CLI)
while still having the features of other money management applications.

Our app will help students to **track their expenses and income** in order to stay within their budgets, so that they have enough money to travel.
It will also help them convert to foreign currencies so they can see how much they have to spend overseas.

## Quick Start

{Give steps to get started quickly}

1. Ensure that you have Java 11 or above installed.
1. Down the latest version of `Duke` from [here](http://link.to/duke).

## Features

{Give detailed description of each feature}

### Getting the balances of an account or multiple accounts: `balance`

If the currency is specified, get the balance of the account with the currency. Otherwise, get the balances of all the accounts.

Format: `balance <Currency>`

- `<Currency>` is an optional argument. If the value of `<Currency>` is not provided, the balance of all the accounts will be shown.
- An error will be shown if the currency specified is not one of our registered currency or an account with the currency does not exist.

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
- An error will be thrown if either the currency or amount is not provided, the currency is not one of the registered currency, the amount is not numeric or the account with the currency does not exist.

Format: `add <Currency> <Amount>`

Currency: Compulsory argument. A registered currency.
Amount: Compulsory argument. The amount to be deposited.

Example of usage:

```text
>>> add CNY 200
>>> You have successfully added CNY 2.0000 into your account
```

### Exchange money between international currencies `exchange`

Both an 'initial' and 'target' currency must be specified. The value provided will be subtracted from the balance of the 'initial' currency, and exchanged into the 'target currency', and will be added
to the account of the target currency. You must have accounts for both the initial and target currency to perform this command.

Format: `exchange <initialCurrency> <targetCurrency> <amount>`

Examples of usage (assuming accounts are created) :

```text
>> exchange SGD USD  100
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

### Show the exchange rate between two currencies `show-rate`

The show rate has an optional parameter amount. The command will show the value of amount exchanged both ways. If amount is not provided,
the command will use a value of 1. The command must be given supported currencies, and numerical non-negative values.

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

## FAQ

**Q**: How do I transfer my data to another computer?

**A**: {your answer here}

## Command Summary

{Give a 'cheat sheet' of commands here}

- Get balance `balance <Currency>`
