package org.xml


import org.xml.stax.XMLOutputBuilder
import java.io.OutputStream
import java.io.StringWriter
import java.io.Writer
import java.util.jar.Attributes
import javax.xml.transform.stream.StreamResult


interface Node {
    fun element(
        name: String,
        vararg attributes: Pair<String, String> = emptyArray(),
        children: ElementWriter.() -> Unit
    )

    fun comment(comment: String)
}

interface DocumentWriter : Node {

}

interface ElementWriter : Node {
    operator fun String.unaryPlus()
}


fun domWriter(version: String = "1.0", encoding: String = "utf-8", run: DocumentWriter.() -> Unit) =StringWriter().also{
    StreamResult(it).write(version, encoding, run)
}.buffer.toString()
fun staxStreamWriter(version: String = "1.0", encoding: String = "utf-8", run: DocumentWriter.() -> Unit) =StringWriter().also{
    XMLOutputBuilder.createXMLStreamWriter(it).write(version, encoding, run)
}.buffer.toString()
fun staxEventWriter(version: String = "1.0", encoding: String = "utf-8", run: DocumentWriter.() -> Unit)  = StringWriter().also{
    XMLOutputBuilder.createXMLEventWriter(it).write(version, encoding, run)
}.buffer.toString()


fun Writer.domWriter(version: String = "1.0", encoding: String = "utf-8", run: DocumentWriter.() -> Unit) =StreamResult(this).write(version, encoding, run)
fun Writer.staxStreamWriter(version: String = "1.0", encoding: String = "utf-8", run: DocumentWriter.() -> Unit) =
    XMLOutputBuilder.createXMLStreamWriter(this).write(version, encoding, run)

fun Writer.staxEventWriter(version: String = "1.0", encoding: String = "utf-8", run: DocumentWriter.() -> Unit) =
    XMLOutputBuilder.createXMLEventWriter(this).write(version, encoding, run)

fun OutputStream.domWriter(version: String = "1.0", encoding: String = "utf-8", run: DocumentWriter.() -> Unit)  =StreamResult(this ).write(version, encoding, run)
fun OutputStream.staxStreamWriter(version: String = "1.0", encoding: String = "utf-8", run: DocumentWriter.() -> Unit) =
    XMLOutputBuilder.createXMLStreamWriter(this).write(version, encoding, run)

fun OutputStream.staxEventWriter(version: String = "1.0", encoding: String = "utf-8", run: DocumentWriter.() -> Unit) =
    XMLOutputBuilder.createXMLEventWriter(this).write(version, encoding, run)