JasperReports Server Shell (JRSH)
=================================
[![Join the chat at https://gitter.im/Krasnyanskiy/jrsh](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/Krasnyanskiy/jrsh?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

[![Build Status](https://travis-ci.org/Krasnyanskiy/jrsh.svg?branch=develop)](https://travis-ci.org/Krasnyanskiy/jrsh) [![GitHub Release](https://img.shields.io/github/release/Krasnyanskiy/jrsh.svg)](https://github.com/Krasnyanskiy/jrsh/releases)

JRSH is a comprehensive Command Line Interface for JasperReports Server. It's designed for fast and easy interaction with JasperReports Server.

## Requirements
Java >= 1.7 (OpenJDK or Oracle JDK)

## Getting started
This short guide will walk you through getting a basic usage of JRSH in different modes, and demonstrate some simple operations.

First, you need to download a zip file with actual snapshot and unpack it. The snapshot folder contains such content as:
```bash
├── jrsh-XX-jar-with-dependencies.jar
└── jrsh
```
Second, you need to execute a bash script `jrsh` with parameters. The parameters determine in which mode the application will work. There are three different modes:

1. Script Mode
2. Tool Mode
3. Shell Mode

In Script mode you should specify a script file with `*.jrs` file extension. That file contains an operation sequence. The sequenced operations are performed one by one. To run the script just execute in your terminal:

```bash
$> jrsh --script /Users/alex/jrsh/scripts/my_script.jrs
```
Here is a script file example:
```bash
# my_script.jrs
login superuser%superuser@localhost:8080/jasperserver-pro
export /public/Samples
```

In the Tool mode an application executes only one operation. To switch it you must specify a connection string which follows the format:
```
[username]|[organization]%[password]@[url] (organization is optional)
```
See usage example example below.

```bash
$> jrsh superuser%superuser@localhost:8080/jasperserver-pro \ 
   import /Users/file.zip
```

And the last mode is `Shell`. This is an interactive mode. It executes your operation until you interrupt the app using Ctrl+C key. To switch to Shell mode all you need is tospecify a connection string.

```bash
$> jrsh superuser%superuser@localhost:8080/jasperserver-pro
```

## Frequently used operations

#### Import
This is most frequently used operation. It imports resources to [JRS](http://community.jaspersoft.com/project/jasperreports-server). You can import a zip file with resources or specify the folder with the unzipped resources to import.

![](http://i.imgur.com/Z4Fz4X1.gif)

`Import` examples:

Resources import
```bash
$> import /Users/alex/folder
```
Zip file import
```bash
$> import /Users/alex/archive.zip
```
Import resources with specifying import arguments
```bash
$> import /Users/alex/folder \ 
   with-update \ 
   with-skip-user-update \ 
   with-include-access-events
```

#### Export 
Export operation is used to export resources from the JRS. You can specify which resource you want to export, and it is also possible to specify where you want to save it. Similar to Import operation, you can specify the arguments of Export operation.

![](http://i.imgur.com/kpBraKF.gif)

`Export` examples:

Export repository
```bash
$> export repository /public/Samples/Reports/RevenueDetailReport
```
Export repository with specifying export arguments
```bash
$> export repository /public/Samples/Reports/RevenueDetailReport \ 
   with-include-access-events with-user-roles
```
Export repository with specifying an export file
```bash
$> export repository /public/Samples/Reports/RevenueDetailReport \
   to /Users/alex/jrs/downloads/export.zip
```
