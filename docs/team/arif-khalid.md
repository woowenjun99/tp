# Arif Khalid - Project Portfolio Page

## Overview

MoneyMoover is a **CLI application for managing and transferring international currencies**, optimized for use via a
Command Line Interface (CLI)
while still having the features of other money management applications.

Our app will help students to **track their expenses and income** in order to stay within their budgets, so that they
have enough money to travel.
It will also help them convert to foreign currencies so they can see how much they have to spend overseas.

### Summary of Contributions

* Code Contributed:
  [RepoSense Link](https://nus-cs2113-ay2223s2.github.io/tp-dashboard/?search=arif-khalid&breakdown=true)

##### Account and Account List classes

I implemented the `Account` and `AccountList` classes, providing the base implementation of necessary methods and
attributes from within the `AccountList` class that other developers can call and add on to when creating commands.

* Justification: The core functionality of the program relies on these classes to manage user's expense.
* Highlights:
    * Implementation required an analysis of design alternatives to determine what is the best way to implement
      these classes keeping in mind all the future functionality intended.
    * Future commands relied on these classes, therefore changes to it would be harder to perform in the future.

##### Create-account command

I implemented the create-account command, developing code that builds on the `Parser` and `Command` implementations.

* The `create-account` command accepted a currency as the input and created a new account of that currency
  with no initial balance.
* Justification: This command is part of the core functionality to allow users to keep track of their expenses.
  It allows users to keep multiple accounts of different currencies, useful for travellers.
* Highlights: This command required understanding and working with code made by others. In addition, deep understanding
  of the planned functionality was needed in order to satisfy requirements since this was a command which would be
  called frequently in testing and by all users.

##### Transaction commands

I implemented the `Transaction` and `TransactionManager` classes.
A `Transaction` stored information about any change in balance of an account while the `TransactionManager`
performed critical functions such as adding, erasing and retrieving transactions of a particular search parameter.

In addition, I implemented the `trans` command which printed transactions of a certain search parameter.
The user was allowed to search by date, month, description, currency or simply print all transactions.

* Justification: Transactions allowed user to see where their money went or where they earned
  the most from, allowing them to better plan their finances. This also provides a layer of security
  as fraudulent charges can be easily identified.
* Highlights:
    * Understanding and research on the java LocalDateTime API was required to store and search by dates.
    * Discussion and iterative development based on tester feedback was used to determine what search parameters users
      would want to have

##### Documentation

* User Guide
    * Added documentation for the `create-account` command and the `trans` show transactions command,
      along with all its possible search parameters and accompanying flags.
* Developer Guide
    * Added implementation details of the `Accounts` component
    * Added implementation details of the `Transactions` component
    * Added implementation details of the `create-account` feature
    * Added implementation details of the `trans` show transactions feature

##### Community

* PRs reviewed (with non-trivial review comments)
  [#37](https://github.com/AY2223S2-CS2113-T13-1/tp/pull/37),
  [#184](https://github.com/AY2223S2-CS2113-T13-1/tp/pull/184),
  [#185](https://github.com/AY2223S2-CS2113-T13-1/tp/pull/185)
* Suggestions for other teams
  [DoctorDuke](https://github.com/nus-cs2113-AY2223S2/tp/pull/54),
  [PetTracker](https://github.com/Arif-Khalid/ped/issues)