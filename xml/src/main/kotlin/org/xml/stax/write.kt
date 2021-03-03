package org.xml.stax



import org.xml.stax.*
import java.lang.Exception
import javax.xml.stream.XMLEventWriter
import javax.xml.stream.XMLStreamWriter







fun XMLStreamWriter.writer(version: String  ="utf-8", encoding: String  ="1.0",   document: Document.()-> Unit  )
    = XMlWriteStream(this  ).xmlWrite(version  ,  encoding   , document )

fun XMLEventWriter.writer(version: String ="utf-8", encoding: String ="1.0" ,   document: Document.()-> Unit )
    = XMLWriteEvent( this ).xmlWrite(version  ,  encoding  , document )




internal fun XMLWriter.xmlWrite (version: String, encoding: String, run  : Document.() ->  Unit    ){
    startDocument(version , encoding )
    Document().apply(run).test().forEach {
        when( it ){
            is Element -> writeElement(it)
            is Comment -> comment(it.string )
        }


    }
    endDocument()
}

internal fun XMLWriter.writeElement(element: Element){
    element.children?.let {
        element( element.name){
            writerTag(element.tag)
            it.forEach {
                when(it){
                    is Comment -> comment(it.string )
                    is Charachter ->  characters( it.string)
                    is Element -> writeElement(it)
                }
            }

        }
    } ?: emptyElement( element.name){
        writerTag(element.tag)
    }
}
internal fun XMLWriter.writerTag(list: List<TagNode>) {
    list.forEach {
        when(it ){
            is Comment -> comment(it.string )
            is Attribute -> attribute( it.pair)
        }
    }
}


abstract class XML constructor(  ){
    internal val list =  mutableListOf<XMLNode>()
    fun comment  ( string: String ){
        list.add(Comment(string) )
    }
}


class Tag internal constructor ( )  : XML(  ){
      operator fun Pair<String , String>.unaryPlus(){
          list.add(Attribute(this))
      }

}
abstract class InitElement internal constructor(  ) : XML()  {
   @Suppress("UNCHECKED_CAST")
   open fun element(name : String, attributes : Tag.() -> Unit  = { }, children : (Children.()  ->  Unit)? = { }  ) {
       list.add(Element( name , Tag().apply(attributes).list as MutableList<TagNode> , children?.let {return@let Children().apply( it ).list as MutableList<ChildrenNode>}    ))
    }
}
class Children internal constructor ( )  : InitElement()  {
    operator fun String.unaryPlus(){
        list.add( Charachter(this))
    }
}
class Document internal constructor()   : InitElement()  {
    private var  boolean =  true

    override fun element(name: String, attributes: Tag.() -> Unit, children: (Children.() -> Unit)?) {
        if (boolean ) {
            super.element(name, attributes, children)
            boolean = false
        }else {
            throw Exception("")
        }
    }
    @Suppress("UNCHECKED_CAST")
    internal fun test() =if(boolean){
            throw Exception("")
        }else  list as MutableList<DocumentNode>

}



