package org.xml

interface ElementReader <T>  {
    val name : String
    val attribute : Attribute
    val children : List<T>
}
interface  Attribute{
    fun get(key: String ) : String?
}