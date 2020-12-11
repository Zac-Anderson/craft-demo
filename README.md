# Craft Demo #

## Table of Contents ##
- [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Workspace Setup](#workspace-setup)

## Getting Started ##
Follow the steps below to get setup.

### Prerequisites ###
- Java 11
- Postgres
- Gradle

```
brew install postgresql gradle
```

### Workspace Setup ###
```
cd ~/workspace
git clone git@github.com:Zac-Anderson/craft-demo.git

createuser -s demo_user

dropdb --if-exists craft_demo
dropdb --if-exists craft_demo_test
createdb -U demo_user -e craft_demo
createdb -U demo_user -e craft_demo_test
```
