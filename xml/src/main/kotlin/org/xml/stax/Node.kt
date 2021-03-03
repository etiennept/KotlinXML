package org.xml.stax

interface XMLNode
interface TagNode : XMLNode
interface ChildrenNode : XMLNode
interface DocumentNode : XMLNode


data class Comment(var string :String) :  TagNode, ChildrenNode, DocumentNode
data class Attribute ( var pair: Pair<String , String> ) : TagNode
data class Element(var name : String, val tag : MutableList<TagNode>, var  children : MutableList<ChildrenNode>?   ) :
    ChildrenNode, DocumentNode
data class Charachter( var  string  : String  )  : ChildrenNode
