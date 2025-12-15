/* Section 1 : code utilisateur inclus en préambule de   */
/* la classe de l'analyseur lexical : package et import  */
package compil; // nom du paquetage à adapter
%%
/* Section 2 : directives, blocs, définitions régulières */
/* - des directives : "%..."                             */
/* - des blocs : "%...{"  ...  "%...}"                   */
/* - des définitions régulières : Nom = Regexp           */ 
%include Jflex.include
%include JflexCup.include

%{
/* code dans la classe de l'analyseur : attributs et     */
/* méthodes utiles pour les actions                      */
%}

%init{
/* code dans le constructeur : action initiale           */
%init}

%eof{
/* code en action finale à la sortie de l'analyseur      */
System.out.println("Fin de l'analyse lexicale.");
%eof}

// %caseless            /* confondre minuscules/majuscules   */
// %state   ETAT, ETAT2 /* États inclusifs du super-automate */
// %xstate  STATE       /* États exclusifs du super-automate */

WS         = [ \t\f] | \R
EOLComment = "//" .*
C89Comment = "/*" [^*]* ("*" ([^*/] [^*]*)?)* "*/"
Ignore     = {WS} | {EOLComment} | {C89Comment}

%%
/* Section 3 : règles lexicales sous la forme :              */
/* Rexexp  { /* Actions = code Java */ }                     */

"class"                    { return TOKEN(CLASS); }
"extends"                  { return TOKEN(EXTENDS); }
"public"                   { return TOKEN(PUBLIC); }
"return"                   { return TOKEN(RETURN); }
"int"                      { return TOKEN(INT); }
"new"                      { return TOKEN(NEW); }
"null"                     { return TOKEN(NULL); }

"System"                   { return TOKEN(SYSTEM); }
"out"                      { return TOKEN(OUT); }
"println"                  { return TOKEN(PRINTLN); }

"{"                        { return TOKEN(LBRACE); }
"}"                        { return TOKEN(RBRACE); }
"("                        { return TOKEN(LPAREN); }
")"                        { return TOKEN(RPAREN); }
";"                        { return TOKEN(SEMI); }
"."                        { return TOKEN(DOT); }
","                        { return TOKEN(COMMA); }
"="                        { return TOKEN(ASSIGN); }

[0-9]+                     { return TOKEN(INTEGER_LITERAL); }
[A-Za-z_][A-Za-z0-9_]*     { return TOKEN(IDENT); }

{Ignore}           		   { }
[^]                        { WARN("Caractère inattendu: '" + yytext() + "'"); }

