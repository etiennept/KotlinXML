package org.xml

import org.xml.stax.EventBluider
import javax.xml.namespace.QName
import javax.xml.stream.XMLEventWriter

class StaxEventDocumentWriter internal constructor( private val writer: XMLEventWriter ,version : String  , encoding : String ) : DocumentWriter {
    private var bool = true
    init {
        writer.add( EventBluider.createStartDocument(encoding , version ) )
    }
    override fun element(name: String, vararg attributes: Pair<String, String>, children: ElementWriter.() -> Unit) =
        if (bool) {
            bool = false
            writer.element(name, attributes, children)
        } else throw Exception()

    override fun comment(comment: String) = writer.comment(comment)

    fun close() = if (bool) throw Exception()
    else writer.run {
        writer.add( EventBluider.createEndDocument())
        flush()
        close()
    }
}

class StaxEventElementWriter internal constructor(private val writer: XMLEventWriter) : ElementWriter {
    override fun String.unaryPlus() = writer.add(EventBluider.createCharacters(this))

    override fun element(name: String, vararg attributes: Pair<String, String>, children: ElementWriter.() -> Unit) =
        writer.element(name, attributes, children)

    override fun comment(comment: String) {
        writer.comment(comment)
    }

}

private fun XMLEventWriter.element(
    name: String,
    attributes: Array<out Pair<String, String>>,
    children: ElementWriter.() -> Unit
) {
    add(EventBluider.createStartElement(QName(name)))
    attributes.forEach {
        add(EventBluider.createAttribute(it))
    }
    StaxEventElementWriter(this).children()
    add(EventBluider.createEndElement(QName(name)))
}

private fun XMLEventWriter.comment(comment: String) =
    add(EventBluider.createComment(comment))


fun XMLEventWriter.write ( version: String , encoding: String , run : DocumentWriter.() -> Unit ) = StaxEventDocumentWriter(this  , version , encoding ).apply(run).close()