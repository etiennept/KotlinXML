package org.xml.stax

import javax.xml.stream.XMLEventWriter
import javax.xml.stream.XMLStreamReader

fun <T> XMLEventWriter.read(run :  (XMLEventWriter)  -> T ) = run( this )
fun <T> XMLStreamReader.read(run :  (XMLStreamReader)  ->  T )= run( this )


