package org.xml.stax

import javax.xml.stream.XMLEventReader
import javax.xml.stream.XMLStreamConstants
import javax.xml.stream.XMLStreamReader


sealed class XMLReader( ){
    protected  var bool = false
    abstract fun<T , R > parse(
        startElement  : ( String,  Map<String , String>    ) ->  Unit
        , character :(String) -> Unit ,
        entElement :(String) -> Unit)
}






class XMLReaderStream (val  reader : XMLStreamReader) : XMLReader(){
    override fun <T , R , > parse( startElement  : ( String,  Map<String , String>    ) ->  Unit
                                   , character :(String) -> Unit ,
                                   entElement :(String) -> Unit  ) {
        if (bool) {
            bool = false
            val  element = mutableListOf<Element>()
            while (reader.hasNext()) {
                val stuck = mutableListOf<String>()
                reader.apply {
                    when{
                        isStartElement ->{

                        }
                        isCharacters ->{

                        }
                        isEndElement ->{}
                        isStandalone -> { }
                        isWhiteSpace ->{}

                    }

                }

                when(reader.eventType){
                    XMLStreamConstants.START_ELEMENT -> {
                        reader.localName
                    }
                    XMLStreamConstants.PROCESSING_INSTRUCTION -> {
                        println("eeee")
                    }
                    XMLStreamConstants.CHARACTERS -> {

                    }
                    XMLStreamConstants.COMMENT -> {
                        println("Comment")
                    }
                    XMLStreamConstants.SPACE -> {
                        println("Space")
                    }
                    XMLStreamConstants.START_DOCUMENT -> {
                        println("StartDocument")
                    }
                    XMLStreamConstants.END_DOCUMENT -> {
                        println("EndDocument")
                    }
                    XMLStreamConstants.ENTITY_REFERENCE -> {
                        println("Entite ")
                    }
                    XMLStreamConstants.ATTRIBUTE -> {
                        println("attribute")
                    }
                    XMLStreamConstants.DTD -> {
                        println("DTD")
                    }
                    XMLStreamConstants.CDATA -> {
                        println("CDATA")
                    }
                    XMLStreamConstants.NAMESPACE -> {
                        println("Namespace")
                    }
                    XMLStreamConstants.NOTATION_DECLARATION -> {
                        println("Notation")
                    }
                    XMLStreamConstants.ENTITY_DECLARATION -> {
                        println("ENTITY_DECLARATION")
                    }
                    XMLStreamConstants.END_ELEMENT -> {
                        /*stuck.removeLast()
                    println(stuck )*/
                        println("EntElement")
                    }
                }
            }
        }else{
            throw Exception()
        }

    }
}
class XMLReaderEvent  internal  constructor(val reader : XMLEventReader   ) : XMLReader(){

    override fun <T, R> parse(startElement  : ( String,  Map<String , String>    ) ->  Unit
                              , character :(String) -> Unit ,
                              entElement :(String) -> Unit    ) {
        if(bool) {
            val stuck = mutableListOf<String>()
            while (reader.hasNext() ){
                reader.nextEvent()!!.apply{
                    when{
                        isStartDocument->{


                        }
                        isStartElement->{
                            val a =  asStartElement()!!
                            a.attributes
                        }
                        isAttribute ->{

                        }
                        isCharacters ->{
                            val a = asCharacters()!!
                            a.data!!
                        }
                        isEntityReference->{

                        }
                        isNamespace-> {

                        }
                        isProcessingInstruction->{

                        }
                        isEndElement->{
                            val a  = asEndElement()!!
                            a.name.localPart
                        }

                       isEndDocument->{

                       }
                    }
                }


            }
        }else{
            throw Exception()
        }

    }

}