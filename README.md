# ISO 3 Enum Generator - java

## Overview
* generates Java enum for ISO 3166-1 alpha-3 country codes
* source data: [ISO3 Country Code List](https://en.wikipedia.org/wiki/ISO_3166-1_alpha-3)

## Output
* can specify custom enum class name
* enum values correspond to the alpha-3 country code
* stores full country name

```java
Country canada = Country.CAN;
String countryName = canada.getName();
```

## Technologies
* Java 8
* Maven
* JSoup

### Future
* add lombok
* expand enum to include other country code formats (ex. ISO-2)



