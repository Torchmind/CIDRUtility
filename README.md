[![License](https://img.shields.io/github/license/Torchmind/CIDRUtility.svg?style=flat-square)](https://www.apache.org/licenses/LICENSE-2.0.txt)
[![Maven Central](https://img.shields.io/maven-central/v/com.torchmind.utility/version.svg?style=flat-square)](https://search.maven.org/#search%7Cga%7C1%7Cg%3Acom.torchmind.utility%20a%3Acidr)
[![GitHub Release](https://img.shields.io/github/release/Torchmind/CIDRUtility.svg?style=flat-square)](https://github.com/Torchmind/CIDRUtility/releases)
[![CircleCI](https://img.shields.io/circleci/project/github/Torchmind/CIDRUtility.svg?style=flat-square)](https://circleci.com/gh/Torchmind/CIDRUtility)

CIDR Notation Utility
=====================

Table of Contents
-----------------
* [About](#about)
* [Contacts](#contacts)
* [Issues](#issues)
* [Building](#building)
* [Contributing](#contributing)

About
-----

Provides POJO representations for the CIDR notation.

Contacts
--------

* [#.start](http://webchat.esper.net/?channels=.start&prompt=1) on EsperNet
* [GitHub](https://github.com/Torchmind/CIDRUtility)

Using
-----

When running maven you may simply add a new dependency along with our repository to your ```pom.xml```:

```xml
<dependencies>
  <dependency>
    <groupId>com.torchmind.utility</groupId>
    <artifactId>cidr</artifactId>
    <version>2.1.0</version>
  </dependency>
</dependencies>
```

Loading a configuration file by using the Candle implementation:
```java
CIDRNotation range = CIDRNotation.of("10.0.0.0/8");

if (range.matches("10.13.37.1")) {
  // Things
}

// OR

InetAddress address = ...;
if (range.matches(address)) {
  // Things
}
```

Issues
------

You encountered problems with the library or have a suggestion? Create an issue!

1. Make sure your issue has not been fixed in a newer version (check the list of [closed issues](https://github.com/Torchmind/CIDRUtility/issues?q=is%3Aissue+is%3Aclosed)
1. Create [a new issue](https://github.com/Torchmind/CIDRUtility/issues/new) from the [issues page](https://github.com/Torchmind/CIDRUtility/issues)
1. Enter your issue's title (something that summarizes your issue) and create a detailed description containing:
   - What is the expected result?
   - What problem occurs?
   - How to reproduce the problem?
   - Crash Log (Please use a [Pastebin](http://www.pastebin.com) service)
1. Click "Submit" and wait for further instructions

Building
--------

1. Clone this repository via ```git clone https://github.com/Torchmind/CIDRUtility.git``` or download a [zip](https://github.com/Torchmind/CIDRUtility/archive/master.zip)
1. Build the modification by running ```mvn clean install```
1. The resulting jars can be found in ```target```

Contributing
------------

Before you add any major changes to the library you may want to discuss them with us (see [Contact](#contact)) as
we may choose to reject your changes for various reasons. All contributions are applied via [Pull-Requests](https://help.github.com/articles/creating-a-pull-request).
Patches will not be accepted. Also be aware that all of your contributions are made available under the terms of the
[Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0.txt). Please read the [Contribution Guidelines](CONTRIBUTING.md)
for more information.
