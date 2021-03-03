package org.xml.stax

import org.xml.stax.EventBluider.createAttribute
import org.xml.stax.EventBluider.createCharacters
import org.xml.stax.EventBluider.createComment
import org.xml.stax.EventBluider.createEndElement
import org.xml.stax.EventBluider.createStartDocument
import org.xml.stax.EventBluider.createStartElement
import javax.xml.namespace.QName
import javax.xml.stream.XMLEventWriter
import javax.xml.stream.XMLStreamWriter


fun XMLStreamWriter.write(version: String = "1.0", encoding: String = "", run: XMLWriter.() -> Unit) {


}

fun XMLEventWriter.write(version: String = "1.0", encoding: String = "", run: XMLWriter.() -> Unit) {}

sealed class XMLWriter {
    abstract fun comment(string: String)
    abstract fun startDocument(version: String, encoding: String)
    abstract fun element(name: String, children: () -> Unit = {})
    abstract fun emptyElement(name: String, children: () -> Unit = {})
    abstract fun attribute(pair: Pair<String, String>)
    abstract fun characters(string: String)
    abstract fun endDocument()
}

class XMlWriteStream internal constructor(private val stream: XMLStreamWriter) : XMLWriter() {


    override fun comment(string: String) {

    }

    override fun startDocument(version: String, encoding: String) {
        stream.writeStartDocument(
            version, encoding
        )
    }

    override fun element(name: String, children: () -> Unit) {
        stream.writeStartElement(name)
        children()
        stream.writeEndElement()
    }

    override fun emptyElement(name: String, children: () -> Unit) {
        stream.writeEmptyElement(name)
        children()
    }

    override fun attribute(pair: Pair<String, String>) {
        stream.writeAttribute(pair.first, pair.second)
    }

    override fun characters(string: String) {
        stream.writeCharacters(string)
    }

    override fun endDocument() {
        stream.apply {
            writeEndDocument()
            flush()
            close()
        }
    }

}

class XMLWriteEvent internal constructor(private val event: XMLEventWriter) : XMLWriter() {
    override fun comment(string: String) {
        event.add(createComment(string))
    }

    override fun startDocument(version: String, encoding: String) {
        event.add(createStartDocument(version, encoding))
    }

    override fun element(name: String, children: () -> Unit) {
        event.add(createStartElement(QName( name ) ))
        children()
        event.add(createEndElement(QName(name)))
    }

    override fun emptyElement(name: String, children: () -> Unit) {
        event.add(createStartElement(name, "", ""))
        children()
        event.add(createEndElement(name, "", ""))
    }

    override fun attribute(pair: Pair<String, String>) {
        event.add(createAttribute(pair))
    }

    override fun characters(string: String) {
        event.add(createCharacters(string))
    }

    override fun endDocument() {
        event.run {
            flush()
            close()
        }
    }
}
