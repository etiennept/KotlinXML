package org.xml.dom


import java.io.File
import java.io.InputStream


fun InputStream.readXMLDOM()= XMLBuilder.DocumentBuilder().parse( this  )!!
fun File.readXMLDOM() = (if( exists() ) XMLBuilder.DocumentBuilder().parse(this)
    else  XMLBuilder.DocumentBuilder().newDocument())!!


