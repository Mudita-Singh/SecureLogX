# SecureLogX (CLI Version)

SecureLogX is a Java-based command-line project that analyzes system log files to find suspicious login activity.

This project was built to understand how real-world authentication logs work and how repeated failed login attempts can indicate possible brute-force or unauthorized access attempts.

The CLI version focuses purely on the core logic: reading logs, analyzing patterns, and generating incident reports.

---

# What does SecureLogX do?

The CLI version of SecureLogX can:

- Read system log files (for example, Linux SSH authentication logs)
- Detect repeated failed login attempts
- Identify IP addresses with suspicious behavior
- Generate a structured security incident report

The output helps in understanding whether a system might be under a login attack.

---

# How does it work?

SecureLogX reads a log file line by line and looks for authentication failure messages such as “Failed password”.

Each failed attempt is tracked by IP address.  
If the same IP shows multiple failures, it is marked as suspicious.

Once the analysis is complete, the detected incidents are compiled into a report that summarizes potentially harmful activity in a clear and readable format.

---

# Why this project?

This project was created as a learning exercise to:

- Understand how system authentication logs are structured
- Learn how brute-force login attempts appear in real logs
- Practice file handling and string processing in Java
- Apply basic security concepts in a practical way

---

# Technologies Used

- Java
- File I/O
- Core Java collections
- Command-line execution

---

# Project Status

- Log analysis logic implemented
- Incident report generation working
- CLI-based (no user interface by design)



