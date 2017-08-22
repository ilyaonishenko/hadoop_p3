package com.example

object Browsers extends Enumeration{

  type Browser = Val

  protected case class Val(name: String) extends super.Val{
    def getName: String = name
  }
  implicit def valueToVal(x: Value) = x.asInstanceOf[Val]

  val Mozilla = Val("Mozilla")
  val Opera = Val("Opera")
  val IE = Val("InternetExplorer")
  val Baiduspider = Val("Baiduspider")
  val MediapartnersGoogle = Val("Mediapartners-Google")
  val SeznamBot = Val("SeznamBot/3.0-beta")
  val SaladSpoon = Val("SaladSpoon/ShopSalad")
  val netEstate = Val("netEstate")
  val Unknown = Val("?")
}
