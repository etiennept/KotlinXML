package org.xml

import org.xml.dom.XMLBuilder
import org.xml.dom.readXMLDOM
import org.xml.sax.InputSource
import org.xml.stax.XMLInputBuilder
import java.io.InputStream
import java.io.Reader

interface ElementReader<T> {
    val name: String
    val attribute: Attribute
    val children: List<T>
}

interface Attribute {
    fun get(key: String): String?
}


fun <T : Any> String.domReader(run: ElementReader<T>.() -> T) = XMLBuilder.DocumentBuilder().parse(InputSource(this.reader())).read(run)
fun <T : Any> String.staxStreamReader(run: ElementReader<T>.() -> T) = XMLInputBuilder.createXMLStreamReader(this.reader()).read(run)
fun <T : Any> String.staxEventReader(run: ElementReader<T>.() -> T) = XMLInputBuilder.createXMLEventReader(this.reader()).read(run)

fun <T : Any> Reader.domReader(run: ElementReader<T>.() -> T) =
    XMLBuilder.DocumentBuilder().parse(InputSource(this)).read(run)

fun <T : Any> Reader.staxStreamReader(run: ElementReader<T>.() -> T) =
    XMLInputBuilder.createXMLStreamReader(this).read(run)

fun <T : Any> Reader.staxEventReader(run: ElementReader<T>.() -> T) =
    XMLInputBuilder.createXMLEventReader(this).read(run)

fun <T : Any> InputStream.domReader(run: ElementReader<T>.() -> T) =
    XMLBuilder.DocumentBuilder().parse(this)!!.read(run)

fun <T : Any> InputStream.staxStreamReader(run: ElementReader<T>.() -> T) =
    XMLInputBuilder.createXMLStreamReader(this).read(run)

fun <T : Any> InputStream.staxEventReader(run: ElementReader<T>.() -> T) =
    XMLInputBuilder.createXMLEventReader(this).read(run)