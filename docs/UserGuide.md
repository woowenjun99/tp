# User Guide

## Introduction

{Give a product intro}

## Quick Start

{Give steps to get started quickly}

1. Ensure that you have Java 11 or above installed.
1. Down the latest version of `Duke` from [here](http://link.to/duke).

## Features

{Give detailed description of each feature}

### Adding a todo: `todo`

Adds a new item to the list of todo items.

Format: `todo n/TODO_NAME d/DEADLINE`

-   The `DEADLINE` can be in a natural language format.
-   The `TODO_NAME` cannot contain punctuation.

Example of usage:

`todo n/Write the rest of the User Guide d/next week`

`todo n/Refactor the User Guide to remove passive voice d/13/04/2020`

### Getting the balances of an account or multiple accounts: `balance`

If the currency is specified, get the balance of the account with the currency. Otherwise, get the balances of all the accounts.

Format: `balance <Currency>`

-   `<Currency>` is an optional argument. If the value of `<Currency>` is not provided, the balance of all the accounts will be shown.
-   An error will be shown if the currency specified is not one of our registered currency or an account with the currency does not exist.

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

-   If the user has an existing account, he can choose to add money into it.
-   An error will be thrown if either the currency or amount is not provided, the currency is not one of the registered currency, the amount is not numeric or the account with the currency does not exist.

Format: `add <Currency> <Amount>`

Currency: Compulsory argument. A registered currency.
Amount: Compulsory argument. The amount to be deposited.

Example of usage:

```text
>>> add CNY 200
>>> You have successfully added CNY 2.0000 into your account
```

## FAQ

**Q**: How do I transfer my data to another computer?

**A**: {your answer here}

## Command Summary

{Give a 'cheat sheet' of commands here}

-   Get balance `balance <Currency>`
