JasperReports Server Shell
==========================
[![Build Status](https://travis-ci.org/Krasnyanskiy/jrsh.svg?branch=master)](https://travis-ci.org/Krasnyanskiy/jrsh) [![Coverage Status](https://coveralls.io/repos/Krasnyanskiy/jrsh/badge.svg)](https://coveralls.io/r/Krasnyanskiy/jrsh?branch=master)
[![Gitter](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/Krasnyanskiy/jrs-command-line-tool?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=body_badge)
# Overview
JRSH is a comprehensive CLI tool for JasperReports Server. It's designed for fast and easy interaction with JasperReports Server across various operating systems.
## Usage
The following code snippets show how to run
#### Login command
```bash
login --server "http://localhost:8080/jasperserver-pro" --username "superuser" --password "superuser"
```
#### Import command
```bash
import "/folder/import.zip"
```
#### Export command
```bash
export "/public/Samples/Reports/06g.ProfitDetailReport"
```
#### Help command
```bash
help import
```
## Requirements
JRSH requires Java 7 or higher.
## Download and Installation
The current stable release of JRSH: 1.0-alpha.
#### Download links:
- Source: https://github.com/Krasnyanskiy/jrsh
- JAR package: https://github.com/Krasnyanskiy/jrsh/blob/master/lib/jrsh-1.0-SNAPSHOT-jar-with-dependencies.jar
- Bin archive: [soon]

## How to build
To build executable Java archive, just pen a terminal window and invoke
```java
mvn clean install
```
And you'll find a jar file in `target` folder of your project.
