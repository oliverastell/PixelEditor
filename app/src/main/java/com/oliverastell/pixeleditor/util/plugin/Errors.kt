package com.oliverastell.pixeleditor.util.plugin

class ModuleMayNotBeLoaded : RuntimeException("Module may not be loaded yet, calls are illegal")
class MalformedIdentifier(shouldBe: String) : RuntimeException("Malformed import, should be formed: $shouldBe")
class InvalidIdentifier() : RuntimeException("Identifier does not point to any data")
class UnsupportedScriptFormat(format: String) : RuntimeException("Module script is in unsupported format \".$format\"")