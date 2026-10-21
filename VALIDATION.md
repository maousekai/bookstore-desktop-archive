# Validation on 2026-10-21

These checks describe source snapshots and limited offline behavior. They do not establish production readiness, full UI behavior or a complete recovery of project data.

| Check | Pass | Fail | Not run |
|---|---:|---:|---:|
| JSON parse | 1 | 0 | 0 |
| Java compile | 2 | 0 | 0 |
| XML parse | 33 | 0 | 0 |

Gitleaks v8.30.1 scans the complete publication tree before push, with redaction enabled. A supplemental scan checks credential assignments. A clean scan cannot prove the absence of every possible secret.

Both JavaFX versions compiled successfully with JDK 24 targeting Java 21, using JavaFX 21.0.5, jBCrypt 0.4 and JFreeChart 1.5.6. 66 Java files compiled in bookstore-management-system and 27 in javafx-quan-ly-sach.

SQL Server, database schemas/data, application login, images and interactive UI were not tested. JDBC passwords were replaced with environment lookups in these snapshots.

