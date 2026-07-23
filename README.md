# GLOS QA Automation

Selenium + TestNG login test for the GLOS QA environment at `https://qa.glosonline.com/`.

> The supplied address, `www.qa.glosonlin.com`, does not resolve. This project uses the reachable QA host above.

## Requirements

- Java 17+
- Maven 3.9+
- Chrome, Edge, or Firefox

Selenium Manager downloads/resolves a matching driver automatically on its first run.

## Run securely

Do not put credentials into `config.properties` or commit them. Supply the account at execution time:

```powershell
$env:GLOS_USERNAME = 'your-qa-email'
$env:GLOS_PASSWORD = 'your-qa-password'
mvn test
```

Or pass them as Maven properties:

```powershell
mvn test -Dusername='your-qa-email' -Dpassword='your-qa-password'
```

Optional settings: `-Dbrowser=edge`, `-Dbrowser=firefox`, or `-Dheadless=true`.

The sign-in page is provided by Clerk, so the test locates semantic login controls (`identifier`, `password`, and submit buttons) instead of application-specific generated IDs.
