package compil;

%%
%include Jflex.include
%include JflexCup.include

WhiteSpace = [ \t\r\n\f]+
Ident      = [A-Za-z_][A-Za-z0-9_]*
IntLit     = [0-9]+

%%

{WhiteSpace}               { }
"//".*                     { }
"/*"([^*]|\*+[^*/])*\*+"/" { }

"class"     { return TOKEN(CLASS); }
"extends"   { return TOKEN(EXTENDS); }
"public"    { return TOKEN(PUBLIC); }
"return"    { return TOKEN(RETURN); }
"int"       { return TOKEN(INT); }
"new"       { return TOKEN(NEW); }
"null"      { return TOKEN(NULL); }

"System"    { return TOKEN(SYSTEM); }
"out"       { return TOKEN(OUT); }
"println"   { return TOKEN(PRINTLN); }

"{"         { return TOKEN(LBRACE); }
"}"         { return TOKEN(RBRACE); }
"("         { return TOKEN(LPAREN); }
")"         { return TOKEN(RPAREN); }
";"         { return TOKEN(SEMI); }
"."         { return TOKEN(DOT); }
","         { return TOKEN(COMMA); }
"="         { return TOKEN(ASSIGN); }

{IntLit}    { return TOKEN(INTEGER_LITERAL, yytext()); }
{Ident}     { return TOKEN(IDENT, yytext()); }

[^] { WARN("Unknown char.: " + yytext()); return ERROR(); }
