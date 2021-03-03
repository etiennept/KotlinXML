package org.xml.stax


import java.io.OutputStream
import java.io.Writer

object XMLOutputBuilder {
    private val output by lazy { XMLOutputFactory() }
    fun createXMLStreamWriter (writer: Writer) = output.createXMLStreamWriter(writer)!!
    fun createXMLStreamWriter(outputStream: OutputStream) = output.createXMLStreamWriter( outputStream)!!
    fun createXMLEventWriter(writer: Writer ) = output.createXMLEventWriter(writer)!!
    fun createXMLEventWriter(outputStream: OutputStream)  = output.createXMLEventWriter(outputStream )!!
}