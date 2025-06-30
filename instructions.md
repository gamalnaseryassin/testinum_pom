project-root/
│
├── specs/
│   └── login.spec                  ← Gauge steps, simple human language
│
├── src/test/java/com/testinium/
│   ├── base/
│   │   └── BaseTest.java
│   │
│   ├── model/
│   │   └── LoginData.java          ← class holds login data for DDT
│   │
│   ├── pages/
│   │   └── LoginPage.java          ← Page main elements and actions
│   │
│   ├── steps/
│   │   └── LoginSteps.java         ← spec definitions
│
├── resources/
│   └── login-data.csv              ← optional: json/csv, .. data source for DDT (username/password), system dynamic locator mapping
│
└── pom.xml                         ← project config build with maven
