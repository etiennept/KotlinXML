package org.xml.stax

import org.xml.stax.XMLOutputBuilder
import java.io.OutputStream
import java.io.*

fun XMLOutputFactory() = javax.xml.stream.XMLOutputFactory.newInstance()!!
fun Writer.xmlStreamWriter() =  XMLOutputBuilder.createXMLStreamWriter(this )
fun OutputStream.xmlStreamWriter()= XMLOutputBuilder.createXMLStreamWriter(this )
fun Writer.xmlEventWriter() = XMLOutputBuilder.createXMLEventWriter(this )
fun OutputStream .xmlEventWriter()= XMLOutputBuilder.createXMLEventWriter(this )
