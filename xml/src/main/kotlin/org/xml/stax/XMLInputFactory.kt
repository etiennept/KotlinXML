package org.xml.stax

import org.xml.stax.XMLInputBuilder
import java.io.InputStream

import java.io.Reader


fun XMLInputFactory() = javax.xml.stream.XMLInputFactory.newInstance()!!
fun Reader.xmlStreamReader() = XMLInputBuilder.createXMLStreamReader(this )
fun InputStream.xmlStreamReader( ) = XMLInputBuilder.createXMLStreamReader(this )
fun Reader.xmlEventReader () = XMLInputBuilder.createXMLEventReader(this )
fun InputStream.xmlEventReader () = XMLInputBuilder.createXMLEventReader(this )