JRSH (JasperReport Shell)
=====================================
[![Build Status](https://travis-ci.org/Krasnyanskiy/jrs-command-line-tool.svg?branch=master)](https://travis-ci.org/Krasnyanskiy/jrs-command-line-tool) [![Coverage Status](https://img.shields.io/coveralls/Krasnyanskiy/jrs-command-line-tool.svg)](https://coveralls.io/r/Krasnyanskiy/jrs-command-line-tool?branch=master)

[![Gitter](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/Krasnyanskiy/jrs-command-line-tool?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=body_badge)

# Overview

JRSH is a [CLI](https://en.wikipedia.org/wiki/Command-line_interface) tool. It's designed for fast and easy interaction with JasperResport Web Services across various operating systems such as OS X, Linux, Windows, and many others. If you like Unix shell, you'll like this tool as well.

## Usage examples

The following code snippets demostrate some simple operations.

### Show operation

Show resources tree starting from specific root.
```bash
jrs -s "http://54.163.3.100/jasperserver-pro" -u "admin" -p "secret" \
show resources --path "/public/monitoring/reports"
```
Or show such serser information details as edition and version.

```bash
jrs -s "http://54.163.3.100/jasperserver-pro" -u "admin" -p "secret" \
show server-info --version --edition
```

### Import operation

```bash
jrs -s "http://54.163.3.100/jasperserver-pro" -u "admin" -p "secret" \
import --zip "/Users/alexkrasnyanskiy/test/test-import-data.zip"
```

## How to install

To install [Command Line Tool](https://github.com/Krasnyanskiy/jrs-command-line-tool), just pen a terminal window by typing Ctrls-Alt-T and invoke
```java
mvn clean install
```
After that you can find compiled binary files in your target directory.
