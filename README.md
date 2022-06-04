<<<<<<< HEAD
# asdb-baseballstat
=======

Aerospike Basic Application Template
====================================

This is a sample application which models a single object type with several supported Aerospike operations including: load, put, get, getbatch, scan and trunc. The intent is that the directory can be cloned to build applications for demos, and PoCs.


# tl;dnr #

## building ##
mvn package

## cloning ##
cp -r aerospike-app-basic aerospike-app-new
edit pom.xml
edit CmdlnParser.java
move and edit *Object1*.java to new data model files


# Hierarchy #

The hierarchy of files is as follows.

# main/ #

The *main* directory holds the application's main class and command line parsing wrapper class.  All command line switches are defined in the wrapper and provided by the Cmdln utility class defined in the *util* directory.

## Main.java ##

This is the entry point to the application.  It calls the command line parser, gets various switch values, and runs the chosen command.

## CmdlnParser.java ##

An application specific wrapper to the command line parsing utility.  It defines the switches, defaults and flags supported by the application.

# model/ #

The *model* directory contains the object model for the data being manipulated by the application.  Cloned versions of this application will define their data models here.

## Object1.java ##

The template application supports a single data object with representative member types in support of native types, as well as CDT types.  This is just the data model, itself.  All Aerospike specific code (serialization, deserialization, put, get, etc) is found within the *aero* directory hierarchy.

# aero/ #

The *aero* directory contains all of the Aerospike specific code in support of this simple data model.

## Aero.java ##

Wrapper class around Aerospike operations specific classes.

# aero/load/ #

Initial data load module.  This leverages the basic Aerospike operations (*ops* dir), and serialization and deserialization (*serde* dir) code.  This module is a combination of application specific code along with Aerospike specific code.

The loader module supports various record configs and counts, multi-threading configurations, and update reporting similar to that provided by the Aerospike benchmark tools (see *--verbose* and *--updateRate* switches).

## LoadObject1.java ##

The *Object1" data loader module.  This module should be replicated to produce loaders for new data models when cloning - in order to retains the threading and update capabilities.

# aero/ops/ #

Basic put, get, scan, etc Aerospike operations for each object type required by the application.

## OpsObject1.java ##

Aerospike operations in support of the *Object1* data type.

# aero/serde/ #

Basic serialization and deserialization operations.

## SerdeObject1.java ##

*Object1* specific serialization and deserialization operations.

# seed/ #

The seeder utilities allow for creating sample data via random strings, ints, longs, lists, names, places, etc.

## SeedObject1.java ##

*Object1* specific seeder.

## Seeder.java ##

Seeder wrapper.  Provides *random* driver.

## SeedUtils.java ##

Various seeder utilities in support of first name, last name, city, state, zip, strings, numbers, etc, etc.  Thank you Tim, Andre, and all who've congtributed!

# util/ #

Common utilities.

## Cmdln.java ##

Simple command line parser for creating command line arguments with long and short switches, descriptions, default values, allowed values, various types (int, long, str, bool, flag) and flags such as *required*, and *noswitch*.

## NumParser.java ##

The number parser supports number attributes of M, K, G, T.

## Timer.java ##

The timer utility enables timer creation, duration, and reset capabilities in seconds, millis, and nanos.


# Cloning #

Cloning this application template requires:

- copying the directory hierarchy (cp -r aerospike-app-basic aerospike-app-new)
- editing the *pom.xml* file to reflect the new application name (	<artifactId>aerospike-app-new</artifactId>)
- updating the command line parsing module
- updating the data mode - move *Object1* modules to new object names under the *seed*, *model*, *ops*, *load*, and *serde* directories


# Building #
mvn package


# Running #

Execute *bin/tst* script to drive a few basic commands.

Execute *bin/app* script to drive manual commands.
- bin/app --command load --recCount 10 --recStartId 0 --threadCount 10
- bin/app --command load --recCount 1M --recStartId 10M --threadCount 300 -v
- bin/app -c scan --limit 2
- bin/app -c put -uk 0 -of1 "a1" -of2 "b1" -otyp "T1" -oi1 1 -osl "str1,str2,str3"
- bin/app -c get -uk 0
>>>>>>> This is the first commit of this app to my personel GitHub repo
