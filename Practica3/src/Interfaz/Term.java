package Interfaz;

import ByteCode.ByteCode;

public interface Term {
	Term parse(String term);
	ByteCode compile(Compiler compiler);
}
