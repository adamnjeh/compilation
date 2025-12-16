package compil;

import java_cup.runtime.Symbol;
import java_cup.runtime.SymbolFactory;

%%

%public
%class Yylex
%unicode
%line
%column
%char

%cup
%function next_token
%type java_cup.runtime.Symbol

%eofval{
  return token(sym.EOF, null);
%eofval}

%{
  private SymbolFactory sf;

  public Yylex(java.io.Reader in, SymbolFactory sf) {
    this(in);
    this.sf = sf;
  }

  private Symbol token(int code, Object value) {
    if (sf != null) return sf.newSymbol(sym.terminalNames[code], code, value);
    return new Symbol(code, value);
  }

  private Symbol token(int code) { return token(code, null); }

  private Symbol errorTok() {
    Compiler.incrementFailures();
    return token(sym.error, yytext());
  }
%}

WhiteSpace = [ \t\r\n\f]+
Ident      = [A-Za-z_][A-Za-z0-9_]*
IntLit     = [0-9]+

%%

{WhiteSpace}               { /* ignore */ }
"//".*                     { /* ignore */ }
"/*"([^*]|\*+[^*/])*\*+"/" { /* ignore */ }

/* keywords */
"class"     { return token(sym.CLASS); }
"extends"   { return token(sym.EXTENDS); }
"public"    { return token(sym.PUBLIC); }
"return"    { return token(sym.RETURN); }
"int"       { return token(sym.INT); }
"new"       { return token(sym.NEW); }
"null"      { return token(sym.NULL); }

/* println chain */
"System"    { return token(sym.SYSTEM); }
"out"       { return token(sym.OUT); }
"println"   { return token(sym.PRINTLN); }

/* punctuation */
"{"         { return token(sym.LBRACE); }
"}"         { return token(sym.RBRACE); }
"("         { return token(sym.LPAREN); }
")"         { return token(sym.RPAREN); }
";"         { return token(sym.SEMI); }
"."         { return token(sym.DOT); }
","         { return token(sym.COMMA); }
"="         { return token(sym.ASSIGN); }

/* values */
{IntLit}    { return token(sym.INTEGER_LITERAL, yytext()); }
{Ident}     { return token(sym.IDENT, yytext()); }

/* fallback */
.           { return errorTok(); }
