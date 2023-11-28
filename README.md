# KotlinXML 
 Welcome to Exposed, an XML Api for Kotlin JVM

 # Dowlead  
 
 ### Gradle 

` implementation  "org.kotlin:org.xml:0.2" `


Write  
* `writeXMLDom`
* `writeXMLStaxStream`
* `writeXMLStaxEvent`

# Exemple

## Write

``` 
writeXMLDom {
        element("name" , "id" to  "grand"  ){
            element("value"){
                +"hello"
            }
            element("value"){
                +"bye bye "
            }
        } 
    }
```

### Generated XML
`<?xml version="1.0" encoding="utf-8" standalone="no"?><name id="grand"><value>hello</value><value>bye bye </value></name>`




