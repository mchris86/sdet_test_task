# SDET Intern @ Functional Automation Test Task

## Overview
This repository contains an **automated UI test** for IntelliJ IDEA using the Starter test framework for IntelliJ-based IDEs.

The test runs **IntelliJ IDEA Ultimate**, loads a sample project from GitHub and performs UI interactions to verify settings behavior.

---
## Test Scenario
The test performs the following steps:
1. Open IntelliJ IDEA
2. Open Settings
3. Choose "Version Control" and then "Changelists"
4. Select the checkbox that is called "Create changelists automatically"
5. Check that it is selected
6. Click on the OK button

---
## How to run
```
git clone https://github.com/mchris86/sdet_test_task.git
cd sdet_test_task
export LICENSE_KEY=<IU_license_key>
./gradlew test
```

---
## CI - GitHub Actions
**CI** runs automatically on every push and PR to `main` branch.

The `LICENSE_KEY` is configured as a GitHub Actions **repository secret** and used as an environment variable during CI runs.

### OS Support in CI
I initially experimented with running tests on Ubuntu, Windows, and macOS.

In practice (according to this [**JetBrains blog post**](https://blog.jetbrains.com/platform/2025/04/integration-tests-for-plugin-developers-github-actions-and-setting-up-continuous-integration)):
- Linux works out of the box for UI tests
- Windows may require additional configuration
- macOS requires accessibility permissions that are difficult to configure in CI

For simplicity, I limited CI runs to `ubuntu-latest`.

---
## Notes
- The test is parameterized to run in both **split and non-split modes**.
- If `LICENSE_KEY` is not provided in repository secrets, UI tests will be skipped in CI.