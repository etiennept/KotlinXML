package org.xml


import org.xml.stax.XMLOutputBuilder
import java.io.File
import java.io.OutputStream
import java.io.StringWriter
import java.io.Writer
import java.nio.charset.Charset
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
private fun writer(run : StringWriter.() -> Unit  ) = StringWriter().apply(run).buffer.toString()

fun writeXMLDom(version: String = "1.0", encoding: String = "utf-8", run: DocumentWriter.() -> Unit) =writer{
    writeXMLDom( version ,encoding , run )
}
fun writeXMLStaxStream(version: String = "1.0", encoding: String = "utf-8", run: DocumentWriter.() -> Unit) = writer {
    writeXMLStaxStream(version   , encoding , run )
}
fun writeXMLStaxEvent(version: String = "1.0", encoding: String = "utf-8", run: DocumentWriter.() -> Unit) = writer{
    writeXMLStaxEvent(version , encoding , run )
}

fun File.writeXMLDom(version: String = "1.0", encoding: String = "utf-8", charset: Charset = Charsets.UTF_8, run: DocumentWriter.() -> Unit) =
    writer(charset).writeXMLDom( version ,encoding , run )

fun File.writeXMLStaxStream(version: String = "1.0", encoding: String = "utf-8", charset: Charset = Charsets.UTF_8, run: DocumentWriter.() -> Unit) = writer {
    writer(charset).writeXMLStaxStream(version   , encoding , run )
}
fun File.writeXMLStaxEvent(version: String = "1.0", encoding: String = "utf-8", charset: Charset = Charsets.UTF_8, run: DocumentWriter.() -> Unit) = writer{
    writer(charset).writeXMLStaxEvent(version , encoding , run )
}



fun Writer.writeXMLDom(version: String = "1.0", encoding: String = "utf-8", run: DocumentWriter.() -> Unit) =
    StreamResult(this).write(version, encoding, run)

fun Writer.writeXMLStaxStream(version: String = "1.0", encoding: String = "utf-8", run: DocumentWriter.() -> Unit) =
    XMLOutputBuilder.createXMLStreamWriter(this).write(version, encoding, run)

fun Writer.writeXMLStaxEvent(version: String = "1.0", encoding: String = "utf-8", run: DocumentWriter.() -> Unit) =
    XMLOutputBuilder.createXMLEventWriter(this).write(version, encoding, run)

fun OutputStream.writeXMLDom(version: String = "1.0", encoding: String = "utf-8", run: DocumentWriter.() -> Unit) =
    StreamResult(this).write(version, encoding, run)

fun OutputStream.writeXMLStaxStream(version: String = "1.0", encoding: String = "utf-8", run: DocumentWriter.() -> Unit) =
    XMLOutputBuilder.createXMLStreamWriter(this).write(version, encoding, run)

fun OutputStream.writeXMLStaxEvent(version: String = "1.0", encoding: String = "utf-8", run: DocumentWriter.() -> Unit) =
    XMLOutputBuilder.createXMLEventWriter(this).write(version, encoding, run)