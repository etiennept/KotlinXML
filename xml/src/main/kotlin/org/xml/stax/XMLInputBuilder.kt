package org.xml.stax

import java.io.InputStream
import java.io.Reader

object XMLInputBuilder {
     private val  input  by  lazy {  XMLInputFactory() }
     init {

     }
     fun createXMLStreamReader( reader : Reader)  = input.createXMLStreamReader(reader )!!
     fun createXMLStreamReader(inputStream: InputStream ) = input.createXMLStreamReader(inputStream)!!
     fun createXMLEventReader( reader: Reader ) = input.createXMLEventReader(reader)!!
     fun createXMLEventReader( inputStream: InputStream )  = input.createXMLEventReader(inputStream  )!!
}