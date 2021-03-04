package org.xml

import javax.xml.stream.XMLStreamWriter
import kotlin.Exception


class StaxStreamDocumentWriter internal constructor(private val writer: XMLStreamWriter ,  version :String  = "1.0" , encoding: String  = "utf-8") : DocumentWriter {
    init {
        writer.writeStartDocument(encoding , version)
    }
    private var bool = true
    override fun element(name: String, vararg attributes: Pair<String, String>, children: ElementWriter.() -> Unit) =
        if (bool ){bool = false
            writer.element(name, attributes, children) }
        else  throw Exception()
    override fun comment(comment: String) =
        writer.writeComment(comment)

    internal fun close (){
        if (bool) throw  Exception()
        else
            writer.run {
                writeEndDocument()
                flush()
                close()}

    }
}

class StaxStreamElementWriter internal constructor(private val writer: XMLStreamWriter) : ElementWriter {
    override fun String.unaryPlus() =
        writer.writeCharacters(this)

    override fun element(name: String, vararg attributes: Pair<String, String>, children: ElementWriter.() -> Unit) =
        writer.element(name, attributes, children)


    override fun comment(comment: String) =
        writer.writeComment(comment)

}

internal fun XMLStreamWriter.element(
    name: String,
    attributes: Array<out Pair<String, String>>,
    init: ElementWriter.() -> Unit
) {
    if (init == {}) {
        writeEmptyElement(name)
        attribute(attributes)
    } else {
        writeStartElement(name)
        attribute(attributes)
        StaxStreamElementWriter(this).init()
        writeEndElement()
    }
}

internal fun XMLStreamWriter.attribute(attributes: Array<out Pair<String, String>>) {
    attributes.forEach {
        this.writeAttribute(it.first, it.second)

    }
}
fun  XMLStreamWriter.write(version: String  , encoding: String , run : DocumentWriter.()  -> Unit  ) =  StaxStreamDocumentWriter( this  , version , encoding ).apply(run).close()




