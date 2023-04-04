# Jacob Stein - Project Portfolio Page

## Overview - MoneyMoover

MoneyMoover is a Java CLI application modeled after Revolut, a currency exchange application for travelers. MoneyMoover supports conversion for currencies across Asia, and pulls live exchange rates from the Internet using an API.

This project particularly resonates with me because I am an exchange student in Singapore. During my time studying at the National University of Singapore, I have taken several trips to countries across Asia, including Thailand, Cambodia, Malaysia, Indonesia, Taiwan, and hopefully more. Memorizing exchange rates between the various currencies and the USD can be quite a hassle, and having conversion apps has saved me a lot of time and money. Thus, I was very happy to work on a project that has been particularly helpful to me during my time in Singapore.

### Summary of Contributions

Given below are my contributions to the project:

- **Code Contributed:** [RepoSense Link](https://nus-cs2113-ay2223s2.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2023-02-17&tabOpen=true&tabType=authorship&tabAuthor=jacob-stein1&tabRepo=AY2223S2-CS2113-T13-1%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)
- **New Feature:** Forex objects to represent currency relationships
  - What it does: Represents a relationship between two currencies on the platform.
  - Justification: Forex objects are a core feature of MoneyMoover because they are the backbone of two key feature: exchanging and displaying exchange rates. Forex objects concretize the relationship between two currencies so it can be used to perform important functions
  - Highlights: The Forex class also contains the function to convert between two currencies. When creating this class, I decided it would be better to use a single currency as an intermediary for conversions so that we would not need to store a 2D data structure of exchange rates. So, when a conversion is performed, the value is first converted into USD, then converted from USD into the target currency. This way, we only needed to store the rates from 1 USD to all other currencies.
- **New Feature:** Exchange money between currencies
  - What it does: Move money between currency accounts according to live exchange rates
  - Justification: Exchanging money between currencies is the cornerstone of MoneyMoover. Exchanging with live rates is the purpose of our application, and what makes it useful to our users.
  - Highlights: The exchange command makes use of Forex objects, which are used to represent the relationship between currencies. The actual method to convert between currencies is contained with the Forex class, but exception handling is done within the exchange command class.
- **New Feature:** Display exchange rates between currencies
  - What it does: Displays the exchange rate between two currencies.
  - Justification: Prior to moving money between accounts, the user may want a preview of what the exchange rates are. This command can also display rates with a chosen value, and shows conversion both ways.
  - Highlights: By default, this command will show the exchange rate of 1:X going both ways. The user can instead input a value if they want to see what a transaction might look like before its performed
- **Enhancement to Existing Features:** Open Exchange Rates API
- **Contributions to UG**
- **Contributions to DG**

### User Guide Extracts

## Developer Guide Extracts
