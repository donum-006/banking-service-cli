# Banking Service CLI

A robust, console-based banking management application written in Java. Designed to demonstrate core object-oriented programming principles, secure account handling, file-based data persistence, and clean CLI architecture.

---

## 🚀 Features

* **Account Management:** Create new accounts, view balances, and manage user profile data.
* **Core Banking Operations:** Deposit, withdraw, and transfer funds securely between accounts.
* **Transaction History:** Record and review detailed histories for all deposits, withdrawals, and transfers with timestamps.
* **File Storage Persistence:** Automatically saves and loads account data and transaction logs to local flat files so state is preserved between sessions.
* **Interactive Terminal UI:** Intuitive command-line interface with immediate input validation and clear prompt feedback.
* **Modular Design:** Built with clean architecture separating domain models, file storage layers, custom exception handling, and user interface logic.

---

## 🛠️ Built With

* **Language:** Java
* **Environment:** Linux / POSIX Terminal
* **Build/Execution:** Standard Java Compiler (`javac`) & Java Runtime (`java`)

---

## 💻 Getting Started

### Prerequisites

Ensure you have the Java Development Kit (JDK 8 or higher) installed on your system.

```bash
java -version
javac -version
```
## ⚙️ Installation & Execution

1. **Clone the repository:**
   ```bash
   git clone https://github.com/donum-006/banking-service-cli.git
   cd banking-service-cli
   ```
2. **Compile the source files:**
   ```bash
   javac -d bin src/com/kena/banking/*.java src/com/kena/banking/*/*.java
   ```
3. **Run the application:**
   ```bash
   java -cp bin com.kena.banking.Main
   ```
 ---

## 📝 License

Distributed under the MIT License. See `LICENSE` for more information.
 
   

