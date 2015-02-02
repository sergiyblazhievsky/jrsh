JasperReports Server Shell
==========================
[![Build Status](https://travis-ci.org/Krasnyanskiy/jrsh-project.svg?branch=master)](https://travis-ci.org/Krasnyanskiy/jrsh-project) [![Coverage Status](https://img.shields.io/coveralls/Krasnyanskiy/jrs-command-line-tool.svg)](https://coveralls.io/r/Krasnyanskiy/jrs-command-line-tool?branch=master)
[![Gitter](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/Krasnyanskiy/jrs-command-line-tool?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=body_badge)
# Overview
JRSH is a comprehensive CLI tool for JasperReports Server. It's designed for fast and easy interaction with JasperReports Server across various operating systems.
## Usage
The following code snippets show how to run
### Login command
```bash
login --server "http://localhost:8080/jasperserver-pro" --username "superuser" --password "superuser"
```
### Import command
```bash
import "/folder/import.zip"
```
### Export command
```bash
export "/public/Samples/Reports/06g.ProfitDetailReport"
```
### Help command
```bash
help import
```
## Requirements
JRSH requires Java 7 or higher.
## Download and Installation
The current stable release of JRSH: 1.0-alpha.
### Download links:
- Source: https://github.com/Krasnyanskiy/jrsh-project
- JAR package: [soon]
- Bin archive: [soon]

## How to build
To build [JRSH](https://github.com/Krasnyanskiy/jrs-command-line-tool), just pen a terminal window and invoke
```java
mvn clean install
```
And you'll find executable scripts in the `target` folder of your project.
