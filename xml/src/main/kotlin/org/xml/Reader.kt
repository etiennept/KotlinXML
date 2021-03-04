package org.xml

import org.xml.dom.XMLBuilder
import org.xml.dom.readXMLDOM
import org.xml.sax.InputSource
import org.xml.stax.XMLInputBuilder

import java.io.File
import java.io.InputStream
import java.io.Reader
import java.nio.charset.Charset
import kotlin.text.Charsets.UTF_8

interface ElementReader<T> {
    val name: String
    val attribute: Attribute
    val children: List<T>
}

interface Attribute {
    fun get(key: String): String?
}


fun <T : Any> String.readXMLDom(run: ElementReader<T>.() -> T) = reader().readXMLDom(run)
fun <T : Any> String.readXMLStaxStream(run: ElementReader<T>.() -> T) = reader().readXMLStaxStream(run)
fun <T : Any> String.readXMLStaxEvent(run: ElementReader<T>.() -> T) = reader().readXMLStaxEvent(run)


fun <T : Any> File.readXMLDom(charset: Charset = UTF_8 , run: ElementReader<T>.() -> T) = reader(charset ).readXMLDom(run)
fun <T : Any> File.readXMLStaxStream(charset: Charset =UTF_8,run: ElementReader<T>.() -> T) = reader(charset).readXMLStaxStream(run)
fun <T : Any> File.readXMLStaxEvent(charset: Charset = UTF_8,run: ElementReader<T>.() -> T) = reader(charset).readXMLStaxEvent(run)



fun <T : Any> Reader.readXMLDom(run: ElementReader<T>.() -> T) =
    XMLBuilder.DocumentBuilder().parse(InputSource(this)).read(run)

fun <T : Any> Reader.readXMLStaxStream(run: ElementReader<T>.() -> T) =
    XMLInputBuilder.createXMLStreamReader(this).read(run)

fun <T : Any> Reader.readXMLStaxEvent(run: ElementReader<T>.() -> T) =
    XMLInputBuilder.createXMLEventReader(this).read(run)

fun <T : Any> InputStream.readXMLDom(run: ElementReader<T>.() -> T) =
    XMLBuilder.DocumentBuilder().parse(this)!!.read(run)

fun <T : Any> InputStream.readXMLStaxStream(run: ElementReader<T>.() -> T) =
    XMLInputBuilder.createXMLStreamReader(this).read(run)

fun <T : Any> InputStream.readXMLStaxEvent(run: ElementReader<T>.() -> T) =
    XMLInputBuilder.createXMLEventReader(this).read(run)