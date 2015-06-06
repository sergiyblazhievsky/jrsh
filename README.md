JasperReports Server Shell (JRSH)
=================================
[![Build Status](https://travis-ci.org/Krasnyanskiy/jrsh.svg?branch=develop)](https://travis-ci.org/Krasnyanskiy/jrsh)
[![Stories in Ready](https://badge.waffle.io/Krasnyanskiy/jrsh.png?label=ready&title=Ready)](https://waffle.io/Krasnyanskiy/jrsh)

JRSH is a comprehensive Command Line Interface for JasperReports Server. It's designed for fast and easy interaction with JasperReports Server.

## Requirements
Java >= 1.7 (OpenJDK or Oracle JDK)

## Getting started
This short guide will walk you through getting a basic usage of JRSH in different modes, and demonstrate some simple operations.

First, you need to download a zip file with actual snapshot and unpack it. The snapshot folder contains such content as:
```bash
├── jrsh-XX-jar-with-dependencies.jar
└── run.sh
```
Second, you need to execute a bash script `run.sh` with parameters. The parameters determine in which mode the application will work. There are three different modes:

1. Script Mode
2. Tool Mode
3. Shell Mode

In Script mode you should specify a script file with `*.jrs` file extension. That file contains an operation sequence. The sequenced operations are performed one by one. To run the script just execute in your terminal:

```bash
$> ./run.sh --script /Users/alex/jrsh/scripts/my_script.jrs
```
Here is a script file example:
```bash
# my_script.jrs
login superuser%superuser@localhost:8080/jasperserver-pro
export /public/Samples
```

In the Tool mode an application executes only one operation. To switch that mode you must specify a connection string which has the following format: 
```
[username]|[organization]%[password]@[url] (organization is optional)
```
And after that you must add your operation. See example below.

```bash
$> ./run.sh superuser%superuser@localhost:8080/jasperserver-pro \ 
  import /Users/file.zip
```

And finally the last mode, `Shell`. This is an interactive mode in which operations are executed as you enter them until you interrupt the application. To switch that mode you must specify only connection string.

```bash
$> ./run.sh superuser%superuser@localhost:8080/jasperserver-pro
```

## Frequently used operations

#### Import
This is most frequently used operation. It imports resources to [JRS](http://community.jaspersoft.com/project/jasperreports-server). You can import a zip file with resources or specify the folder with the unzipped resources to import.

![](https://lh4.googleusercontent.com/15cRml-BaE-N-ohGCK_DECeiFworCR41_NVFSIKdbGqGH4E2T3p6BignF9PRUP1QBuE_utP1jBXaGyw=w2560-h1210)

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

![](https://lh3.googleusercontent.com/vWRiuc5f2LR8qWz5mKlzGlBS1-ebBpF_mIQtXZWLVCy68HdcYih8DmJ7DubzPIzCvoWRrjphYPcF8kY=w2560-h1210)

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
