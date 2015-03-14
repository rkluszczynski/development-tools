# signed-json-mapper

## Objectives

* Easy to use substitution for commonly known ObjectMapper
* Sign and verify messages with shared key or RSA key pair
* Present full message as JSON (not encode its content message)

Current implementation do not address:
* performance issues,
* problems due to different JVMs (I mean major version and provider),
* problems due to different versions between signing and verifying client.

## Configuration

    repositories {
        maven {
            url  "http://dl.bintray.com/rkluszczynski/maven"
        }
    }
