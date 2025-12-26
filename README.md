# SecureLogX

SecureLogX is a Java-based project that analyzes system log files to detect suspicious login activity and generate security incident reports.

The project focuses on automatically identifying repeated authentication failures from logs and includes a secure vault component for protecting generated security reports.

---

# What does this project do?

SecureLogX currently:

- Reads system log files (such as Linux SSH authentication logs)
- Identifies repeated failed login attempts from the same IP address
- Flags suspicious login behavior
- Generates a clear security incident report

The output helps in understanding whether a system might be under attack.

---

# How does it work?

SecureLogX processes system log files by automatically scanning them for suspicious login behavior. The system reads raw log entries and looks for repeated authentication failures coming from the same IP address, which is a common indicator of brute-force or unauthorized access attempts.

When such patterns are detected, the relevant information is extracted and converted into a structured security incident. These incidents are then compiled into a report that summarizes potentially harmful activity in a clear and readable format.

The project also includes the foundation of a secure vault component, which is designed to protect generated security reports from unauthorized access. This vault focuses on safeguarding sensitive findings so that security reports can be safely stored or shared when required.
